package com.sweet.simple.login.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cn.hutool.core.date.DatePattern.PURE_DATETIME_PATTERN;
import static cn.hutool.core.text.CharPool.LF;
import static cn.hutool.core.text.CharSequenceUtil.format;
import static cn.hutool.core.util.CharsetUtil.CHARSET_UTF_8;
import static cn.hutool.extra.ssh.JschUtil.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.concurrent.CompletableFuture.runAsync;

/**
 * 链接游戏服务器操作工具
 *
 * @author 大师兄
 */
public class SshUtil {

    /**
     * ip地址
     */
    private final static String HOST = "192.168.200.131";

    /**
     * 端口号
     */
    private final static Integer PORT = 22;

    /**
     * 账号
     */
    private  final static String USERNAME = "game";

    /**
     * 密码
     */
    private  final static String PASSWORD = "uu5!^%jg";

    /**
     * 游戏目录是 /home/dxf/game 输出 dxf；游戏目录是 /home/neople/game 输出 neople
     */
    private final static String GAME_DIR = "[ -d /home/dxf/game ] && echo \"dxf\" ; [ -d /home/neople/game ] && echo \"neople\"";

    /**
     * 拷贝公钥命令
     */
    private final static String COPY_KEY = "cp /home/{}/game/publickey.pem /home/{}/game/publickey_{}.pem";

    /**
     * 公钥服务器路径，不包含文件名称
     */
    private final static String KEY_REMOTE_PATH = "/home/{}/game/";

    /**
     * 获取game目录下的*.cfg文件
     */
    private final static String GAME_CFG = "find /home/{}/game/cfg/ -type f -name \"*.cfg\"";

    /**
     * 启动游戏命令
     */
    private final static String START_CMD = "cd /root/;./stop;./stop;./run;";

    /**
     * 停止游戏命令
     */
    private final static String STOP_CMD = "cd /root/;./stop;./stop;";

    /**
     * 查看指定端口列表数组是否启动监听，只输出端口号
     */
    private final static String GAME_PORT_LIST = "netstat -tuln | grep -E \":({}) \" | awk '{print $4}' | awk -F \":\" '{print $NF}'";

    /**
     * 上传公钥
     */
    @SneakyThrows
    public void uploadPublicKey() {
        // 用户本地选择 TODO
        String keyLocalPath = "公钥本地路径";
        Session session = getSession(HOST, PORT, USERNAME, PASSWORD);
        ChannelSftp channelSftp = openSftp(session);
        // 判断游戏目录
        String gameWord = exec(session, GAME_DIR, CHARSET_UTF_8);
        String nowTime = DateUtil.format(new Date(), PURE_DATETIME_PATTERN);
        String copyKeyCmd = format(COPY_KEY, gameWord, gameWord, nowTime);
        // 备份原始公钥
        exec(session, copyKeyCmd, UTF_8);
        // 上传新的公钥
        channelSftp.put(keyLocalPath, format(KEY_REMOTE_PATH, gameWord));
        // 关闭会话
        close(channelSftp);
        close(session);
    }

    /**
     * 启动游戏服务
     */
    public void startGame() {
        Session session = getSession(HOST, PORT, USERNAME, PASSWORD);
        // 异步执行启动游戏命令
        runAsync(() -> exec(session, START_CMD, CHARSET_UTF_8));
        // 获取cfg文件并从文件中得到游戏端口号
        String gameCfgListStr = exec(session, GAME_CFG, CHARSET_UTF_8);
        List<String> gameCfgList = StrUtil.split(gameCfgListStr, "\n");
        CollUtil.removeBlank(gameCfgList);
        List<String> tcpPorts = new ArrayList<>();
        for (String gameCfg : gameCfgList) {
            String gameCfgContent = exec(session, "cat " + gameCfg, CHARSET_UTF_8);
            String tcpPortStr = ReUtil.getGroup0("\\s*tcp_port\\s*=\\s*(\\d+)", gameCfgContent);
            if (StrUtil.isNotBlank(tcpPortStr)) {
                String tcpPort = StrUtil.trim(StrUtil.split(tcpPortStr, "=").get(1));
                tcpPorts.add(tcpPort);
            }
        }
        String joinPortStr = StrUtil.join("|", tcpPorts);
        String findPortCmd = StrUtil.format(GAME_PORT_LIST, joinPortStr);
        // 暂且循环100次 TODO
        for (int i = 0; i < 100; ++i) {
            // 检查游戏端口号是否使用，使用中表示游戏已经启动
            String listenPortStr = exec(session, findPortCmd, CHARSET_UTF_8);
            List<String> listenPorts = StrUtil.split(listenPortStr, LF);
            CollUtil.removeBlank(listenPorts);
            boolean startFlag = BooleanUtil.and(CollUtil.containsAll(tcpPorts, listenPorts), CollUtil.isNotEmpty(listenPorts));
            if (startFlag)
                break;
            if (i == 99)
                Assert.isTrue(true, "启动游戏失败");

            // 睡眠6秒
            ThreadUtil.safeSleep(6000);
        }
        close(session);
    }

    /**
     * 停止游戏服务
     */
    public void stopGame() {
        Session session = getSession(HOST, PORT, USERNAME, PASSWORD);
        runAsync(() -> exec(session, STOP_CMD, CHARSET_UTF_8));
    }
}
