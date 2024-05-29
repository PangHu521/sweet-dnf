package com.sweet.simple.login.view;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.mysql.jdbc.StringUtils;
import com.sweet.simple.login.entity.Accounts;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: [ph]
 * @Date: 2024/5/29 21:06
 * @Description:
 **/
@Component("RegisterSwing")
public class RegisterSwing extends JFrame {

    private final JPanel root;
    private final JTextField usernameFiled;
    private final JTextField passwordFiled;
    private final JTextField confirmPasswordFiled;
    private final JTextField qqAccountFiled;

    /**
     * @Author 「ph」
     * @Description 注册窗口
     * @param:
     * @return: null
     * @Version 1.0.0
     * @Date 2024/05/29 22:33
     */
    public RegisterSwing() {
        setTitle("注册界面");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 400);
        setLocationRelativeTo(null);
        root = new JPanel();
        root.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(root);
        root.setLayout(null);
        JLabel totalLbl = new JLabel("账户注册");
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

        JLabel passwordLbl = new JLabel("密码");
        passwordLbl.setBounds(81, 101, 60, 15);
        root.add(passwordLbl);
        passwordFiled = new JTextField();
        passwordFiled.setBounds(151, 98, 210, 21);
        root.add(passwordFiled);
        passwordFiled.setColumns(10);

        JLabel confirmPasswordLbl = new JLabel("确认密码");
        confirmPasswordLbl.setBounds(81, 131, 60, 15);
        root.add(confirmPasswordLbl);
        confirmPasswordFiled = new JTextField();
        confirmPasswordFiled.setBounds(151, 128, 210, 21);
        root.add(confirmPasswordFiled);
        confirmPasswordFiled.setColumns(10);

        JLabel qqLbl = new JLabel("qq号");
        qqLbl.setBounds(81, 161, 60, 15);
        root.add(qqLbl);
        qqAccountFiled = new JTextField();
        qqAccountFiled.setBounds(151, 158, 210, 21);
        root.add(qqAccountFiled);
        qqAccountFiled.setColumns(10);

        JButton registerBtn = new JButton("注册");
        // 注册操作
        registerBtn.addActionListener(e -> registerAction());
        registerBtn.setBounds(179, 214, 92, 23);
        root.add(registerBtn);
    }

    /**
     * @Author 「ph」
     * @Description 注册功能
     * @param:
     * @return: void
     * @Version 1.0.0
     * @Date 2024/05/29 22:32
     */
    public void registerAction() {
        String username = usernameFiled.getText();
        String password = passwordFiled.getText();
        String confirmPassword = confirmPasswordFiled.getText();
        String qqAccount = qqAccountFiled.getText();
        // 1，校验是否为null或者空字符串
        if (StringUtils.isNullOrEmpty(username) || StringUtils.isNullOrEmpty(password) || StringUtils.isNullOrEmpty(confirmPassword) || StringUtils.isNullOrEmpty(qqAccount)) {
            JOptionPane.showMessageDialog(null, "所填写注册信息均不能为空", "提示", JOptionPane.ERROR_MESSAGE);
            // TODO 暂时先这样写，后续定义返回结果类统一处理
            // 不符合校验直接返回，不走后面的逻辑
            return;
        }
        // 2，校验用户名长度
        if (username.length() < 6 || username.length() > 16) {
            JOptionPane.showMessageDialog(null, "用户名长度应在6到16位之间", "提示", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // 3，校验密码长度
        if (password.length() < 6 || password.length() > 16) {
            JOptionPane.showMessageDialog(null, "密码长度应在6到16位之间", "提示", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // 4，校验用户名是否包含特殊字符
        String regex = "^[a-zA-Z0-9]$";
        if (!username.matches(regex)) {
            JOptionPane.showMessageDialog(null, "用户名只能包含字母或数字", "提示", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // 5，校验qq号是否合法
        String qqRegex = "^[1-9][0-9]{4,11}$";
        if (!qqAccount.matches(qqRegex)) {
            JOptionPane.showMessageDialog(null, "请输入有效的QQ号（5到12位数字）", "提示", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // 6，校验密码与确认密码是否相同
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "密码与确认密码应相同", "提示", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // 7，校验用户名是否重复(放在最后一步校验可以减少与数据库交互次数，节约资源)
        List<String> accountsList = Db.lambdaQuery(Accounts.class).list()
                .stream().map(Accounts::getAccountName)
                .collect(Collectors.toList());
        if (accountsList.contains(username)) {
            JOptionPane.showMessageDialog(null, "用户名已存在，请更换", "提示", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // 8，对密码进行加密
        String md5Password = DigestUtils.md5DigestAsHex((password).getBytes());
        Accounts accounts = Accounts.builder()
                .accountName(username)
                .password(md5Password)
                .qq(qqAccount).build();
        boolean success = Db.save(accounts);
        if (success) {
            JOptionPane.showMessageDialog(null, "注册成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            // 注册成功后跳转回登录界面
            EventQueue.invokeLater(() -> {
                LoginSwing loginFrame = new LoginSwing();
                loginFrame.setVisible(true);
            });
            this.setVisible(false);
            this.dispose();
        }
        JOptionPane.showMessageDialog(null, "注册失败，请联系管理员", "提示", JOptionPane.ERROR_MESSAGE);
    }
}
