package com.sweet.simple.login.view;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sweet.simple.login.entity.Accounts;
import com.sweet.simple.login.entity.CdkManagement;
import com.sweet.simple.login.entity.CharacInfo;
import com.sweet.simple.login.entity.Postal;
import com.sweet.simple.login.service.impl.CharacInfoServiceImpl;
import com.sweet.simple.login.service.impl.PostalServiceImpl;

import javax.annotation.Resource;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: [ph]
 * @Date: 2024/6/1 17:05
 * @Description:
 **/
public class CdkSwing extends JFrame {

    private final JPanel root;
    // cdk码
    private final JTextField cdkCodeFiled;
    // uid
    private Integer uid;
    // 角色名称下拉框
    private JComboBox<String> characterNameBox;
    @Resource
    private CharacInfoServiceImpl characInfoServiceImpl;

    @Resource
    private PostalServiceImpl postalServiceImpl;

    public CdkSwing(String characterName) {
        setTitle("cdk兑换界面");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 400);
        setLocationRelativeTo(null);
        root = new JPanel();
        root.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(root);
        root.setLayout(null);

        // 兑换码输入框
        cdkCodeFiled = new JTextField();
        cdkCodeFiled.setBounds(180, 141, 200, 30);
        root.add(cdkCodeFiled);
        cdkCodeFiled.setColumns(10);

        if (characterName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "请先在登录界面输入账号", "提示", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // 根据登录页面传递的账号查询uid
        Accounts accounts = Db.lambdaQuery(Accounts.class)
                .eq(Accounts::getAccountName, characterName).one();
        if (ObjectUtil.isEmpty(accounts)) {
            JOptionPane.showMessageDialog(null, "该账号不存在，请重新输入", "提示", JOptionPane.ERROR_MESSAGE);
            return;
        }
        uid = accounts.getUId();
        // TODO 出现异常待解决 String[] allCharacterNames = characInfoServiceImpl.getAllCharacterNames(uid);
        // 根据uid获取的所有角色
        List<String> characterNameList = Db.lambdaQuery(CharacInfo.class).eq(CharacInfo::getmId, uid)
                .list().stream().map(CharacInfo::getCharacName).collect(Collectors.toList());
        String[] characterNameArr = characterNameList.stream().toArray(String[]::new);

        JLabel characterNameLabel = new JLabel("角色名称");
        characterNameLabel.setFont(new Font("黑体", Font.BOLD, 20));
        characterNameLabel.setBounds(80, 91, 100, 30);
        root.add(characterNameLabel);
        // 角色名下拉选框
        characterNameBox = new JComboBox<>(characterNameArr);
        characterNameBox.setBounds(180, 91, 200, 30);
        root.add(characterNameBox);

        JLabel cdkCodeLbl = new JLabel("兑换码");
        cdkCodeLbl.setFont(new Font("黑体", Font.BOLD, 20));
        cdkCodeLbl.setBounds(80, 141, 100, 30);
        root.add(cdkCodeLbl);

        // 兑换操作
        JButton exchangeBtn = new JButton("确认兑换");
        exchangeBtn.addActionListener(e -> exchangeAction());
        exchangeBtn.setBounds(175, 201, 100, 30);
        root.add(exchangeBtn);
    }

    public void exchangeAction() {
        String cdkCode = cdkCodeFiled.getText();
        // 获取所有有效的cdk
        List<String> cdkList = Db.lambdaQuery(CdkManagement.class).eq(CdkManagement::getUseFlag, 0)
                .list().stream().map(CdkManagement::getCdkCode)
                .collect(Collectors.toList());
        if (!cdkList.isEmpty()) {
            if (!cdkList.contains(cdkCode)) {
                JOptionPane.showMessageDialog(null, "输入的cdk有误或该cdk已被使用", "提示", JOptionPane.ERROR_MESSAGE);
            }
        }
        // 构建插入对象
        Postal postal = Postal.builder()
                .occTime(LocalDateTime.now())
                .sendCharacNo(uid)
                .sendCharacName(characterNameBox.getName())
                .itemId(3340)
                .addInfo(5).build();
        // 插入
        boolean save = postalServiceImpl.save(postal);
        if (save) {
            // cdk状态设置为使用
        }
    }
}
