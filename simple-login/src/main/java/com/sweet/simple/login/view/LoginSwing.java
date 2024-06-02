package com.sweet.simple.login.view;

import com.sweet.simple.login.service.AccountsService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * 用户登录窗体
 */
@Slf4j
@Component
public class LoginSwing extends JFrame {

    /**
     * 游戏目录
     */
    private final static String GAME_PATH = "E:\\MY-DNF\\可玩版本\\菲菲游戏-单机-70\\客户端";

    @Resource
    private AccountsService accountsService;

    /**
     * 启动脚本
     */
    private final String CMD_PATH = "cmd /c start {}/DNF.exe {}";

    private final JPanel root;
    private final JTextField usernameFiled;


    public LoginSwing() {
        // 设置主题
        com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMoonlightIJTheme.setup();
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
    @SneakyThrows
    private void loginAction() {
        // 测试弹窗
        SweetPreferences.getState().putBoolean("test", false);
        HintManager.Hint testHint = new HintManager.Hint("Use 'Font' menu to increase/decrease font size or try different fonts.",
                usernameFiled, SwingConstants.BOTTOM, "test", null);
        log.info("执行了。。。。。");
        HintManager.showHint(testHint);

        // SwingUtilities.invokeLater(this::showHints);

        /*String username = usernameFiled.getText();
        if (StrUtil.isBlank(username)) {
            JOptionPane.showMessageDialog(null, "账号不能为空", "提示", JOptionPane.ERROR_MESSAGE);
        }
        Accounts accounts = accountsService.getOne(new LambdaQueryWrapper<Accounts>().eq(Accounts::getAccountName, username));
        if (accounts == null) {
            JOptionPane.showMessageDialog(null, "账号不存在", "提示", JOptionPane.ERROR_MESSAGE);
        }
        String loginParams = RsaUtil.secureId(RsaUtil.PRIVATE_KEY_CONTENT, accounts.getUId());
        String runCmd = "start DNF.exe " + loginParams;
        log.info(runCmd);
        // 创建一个ProcessBuilder对象，指定要执行的命令
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", runCmd);
        // 设置命令执行的工作目录
        processBuilder.directory(new File(GAME_PATH));
        // 启动进程并执行命令
        processBuilder.start();
        // 登陆成功，本窗口隐藏
        setVisible(false);
        // 销毁本窗口
        dispose();*/
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
