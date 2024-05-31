package com.sweet.simple.login;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class WinCmdTest {

    public static void test01() {
        try {
            // 创建一个ProcessBuilder对象，指定要执行的命令
            String runCmd = "MY-DNF\\可玩版本\\菲菲游戏-单机-70\\客户端\\DNF.exe bKBsf5HmOV8DmkrINo236x4RdJ+2YzKXDSXtTyQSlKW/4ng696bFeMyr1hihL6bTHk+pAaOaaykB/AvmjIq30ST+fhotspNM0nT43i1rHSYHnLadxkgfDZK7WlQxr78YueJJDVVkpBClPsN8Me2bPiyQDFfcLNYtv1aWFE3d2PhB6+jb7BRDO+N1Z0Mf/Dii4drv+cT20q2KHzK14FVTFU4lXmK5thmRND1r9c60Jtp9x0biaLemIvwEuEjc2w3yqfjuUlMFC11tq/6ePFgt2YP1HxIMXwMOfDd2kUtTd2q6TOn7mKx1MRdj0PYhg62vPNe+rIhRU05URsBZVBvKug==";
            log.info(runCmd);
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", runCmd);
            // 设置命令执行的工作目录
            processBuilder.directory(new File("E:\\"));
            // 启动进程并执行命令
            Process process = processBuilder.start();
            // 读取命令执行的输出结果
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }
            // 等待一段时间，让游戏有足够的时间来加载资源并显示窗口
            ThreadUtil.safeSleep(2000);
            // 等待命令执行完毕
            // int exitCode = process.waitFor();
            // System.out.println("命令执行完毕，退出码为：" + exitCode);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test01();
    }
}
