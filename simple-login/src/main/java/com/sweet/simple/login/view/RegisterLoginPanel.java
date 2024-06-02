package com.sweet.simple.login.view;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

/**
 * 注册登录面板
 *
 * @author 大师兄
 */
public class RegisterLoginPanel extends JPanel {

    private JPasswordField passwordField;

    public RegisterLoginPanel() {
        initComponents();
        // 为密码输入框添加一个显示密码按钮，以便用户可以切换密码的可见性
        passwordField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton: true");
    }

    private void initComponents() {
        passwordField = new JPasswordField();
        setLayout(new MigLayout(
                "insets dialog,hidemode 3",
                // columns
                "[]" +
                        "[sizegroup 1]" +
                        "[sizegroup 1]" +
                        "[sizegroup 1]" +
                        "[]" +
                        "[]",
                // rows
                "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]para" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]0" +
                        "[]"));

        JLabel labelLabel = new JLabel();
        labelLabel.setText("JLabel:");
        add(labelLabel, "cell 0 0");
        passwordField.setText("");
        add(passwordField, "cell 1 0,growx");

        //---- comboBox3 ----
        JComboBox<String> comboBox3 = new JComboBox<>();
        comboBox3.setModel(new DefaultComboBoxModel<>(new String[]{
                "Not editable",
                "a",
                "bb",
                "ccc"
        }));
        add(comboBox3, "cell 0 1,growx");
    }
}
