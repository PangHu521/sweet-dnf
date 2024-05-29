package com.sweet.simple.login.view;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sweet.simple.login.entity.Accounts;
import com.sweet.simple.login.service.AccountsService;
import com.sweet.simple.login.util.RsaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

/**
 * 用户登录窗体
 */

@Slf4j
@Component("LoginSwing")
public class LoginSwing extends JFrame {

    /**
     * 游戏目录
     */
    // private final static String GAME_PATH = "E:/MY-DNF/可玩版本/菲菲游戏-单机-70/客户端";
    private final static String GAME_PATH = "D:/chromeDownload/地下城与勇士2024/地下城与勇士";
    @Resource
    private AccountsService accountsService;

    /**
     * 启动脚本
     */
    private final String CMD_PATH = "cmd /c start {}/DNF.exe {}";

    private final JPanel root;
    private final JTextField usernameFiled;


    public LoginSwing() {
        setTitle("登陆界面");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setLocationRelativeTo(null);
        root = new JPanel();
        root.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(root);
        root.setLayout(null);
        JLabel totalLbl = new JLabel("账户登录");
        totalLbl.setFont(new Font("黑体", Font.BOLD, 20));
        totalLbl.setBounds(151, 10, 121, 28);
        root.add(totalLbl);
        JLabel usernameLbl = new JLabel("用户名");
        usernameLbl.setBounds(81, 71, 60, 15);
        root.add(usernameLbl);
        usernameFiled = new JTextField();
        usernameFiled.setBounds(151, 68, 210, 21);
        root.add(usernameFiled);
        usernameFiled.setColumns(10);
        JButton loginBtn = new JButton("登录");
        // 登陆操作
        loginBtn.addActionListener(e -> loginAction());
        loginBtn.setBounds(81, 214, 93, 23);
        root.add(loginBtn);
        // 添加注册按钮
        JButton registerBtn = new JButton("注册");
        registerBtn.addActionListener(e -> showRegisterWindow());
        registerBtn.setBounds(210, 214, 93, 23);
        root.add(registerBtn);
    }

    /**
     * 登录操作
     */
    private void loginAction() {
        String username = usernameFiled.getText();
        if (StrUtil.isBlank(username)) {
            JOptionPane.showMessageDialog(null, "账号不能为空", "提示", JOptionPane.ERROR_MESSAGE);
        }

        Accounts accounts = accountsService.getOne(new LambdaQueryWrapper<Accounts>().eq(Accounts::getAccountName, username));
        if (accounts == null) {
            JOptionPane.showMessageDialog(null, "账号不存在", "提示", JOptionPane.ERROR_MESSAGE);
        }

        String loginParams = RsaUtil.secureId(RsaUtil.PRIVATE_KEY_CONTENT, accounts.getUId());
        // 最终调用exe文件的脚本
        String runCmd = StrUtil.format(CMD_PATH, GAME_PATH, loginParams);
        log.info(runCmd);
        Runtime run = Runtime.getRuntime();
        try {
            run.exec(runCmd);
        }
        catch (IOException ignored) {}
        setVisible(false); // 登陆成功，本窗口隐藏
        dispose(); // 销毁本窗口
    }

    /**
     * 显示注册窗口
     */
    private void showRegisterWindow() {
        EventQueue.invokeLater(() -> {
            try {
                RegisterSwing registerFrame = new RegisterSwing();
                registerFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
