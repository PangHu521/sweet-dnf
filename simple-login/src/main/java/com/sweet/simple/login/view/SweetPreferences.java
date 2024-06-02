package com.sweet.simple.login.view;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatPropertiesLaf;
import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.demo.intellijthemes.IJThemesPanel;
import com.formdev.flatlaf.util.LoggingFacade;
import com.formdev.flatlaf.util.StringUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.prefs.Preferences;

/**
 * 管理用户偏好设置
 *
 * @author 大师兄
 */
public class SweetPreferences {

    public static final String KEY_LAF = "laf";
    public static final String KEY_LAF_THEME = "lafTheme";

    public static final String RESOURCE_PREFIX = "res:";
    public static final String FILE_PREFIX = "file:";

    public static final String THEME_UI_KEY = "__FlatLaf.demo.theme";

    /**
     * 保存用户的偏好设置的静态变量
     */
    private static Preferences state;

    /**
     * 获取用户偏好设置
     */
    public static Preferences getState() {
        return state;
    }

    /**
     * 初始化偏好设置。接受一个根路径参数，并且使用 Preferences.userRoot().node(rootPath) 来获取用户偏好设置的根节点
     *
     * @param rootPath 根路径参数
     */
    public static void init(String rootPath) {
        state = Preferences.userRoot().node(rootPath);
    }

    /**
     * 设置用户偏好
     */
    public static void setupLaf(String[] args) {
        // set look and feel
        try {
            // 尝试根据传入的命令行参数设置外观
            if (args.length > 0)
                UIManager.setLookAndFeel(args[0]);

            // 如果没有传入参数，则根据用户偏好设置中的值来设置外观
            else {
                String lafClassName = state.get(KEY_LAF, FlatLightLaf.class.getName());
                if (IntelliJTheme.ThemeLaf.class.getName().equals(lafClassName)) {
                    String theme = state.get(KEY_LAF_THEME, "");
                    if (theme.startsWith(RESOURCE_PREFIX))
                        IntelliJTheme.setup(IJThemesPanel.class.getResourceAsStream(IJThemesPanel.THEMES_PACKAGE + theme.substring(RESOURCE_PREFIX.length())));
                    else if (theme.startsWith(FILE_PREFIX))
                        FlatLaf.setup(IntelliJTheme.createLaf(new FileInputStream(theme.substring(FILE_PREFIX.length()))));
                    else
                        FlatLightLaf.setup();

                    if (!theme.isEmpty())
                        UIManager.getLookAndFeelDefaults().put(THEME_UI_KEY, theme);
                }
                else if (FlatPropertiesLaf.class.getName().equals(lafClassName)) {
                    String theme = state.get(KEY_LAF_THEME, "");
                    if (theme.startsWith(FILE_PREFIX)) {
                        File themeFile = new File(theme.substring(FILE_PREFIX.length()));
                        String themeName = StringUtils.removeTrailing(themeFile.getName(), ".properties");
                        FlatLaf.setup(new FlatPropertiesLaf(themeName, themeFile));
                    } else
                        FlatLightLaf.setup();

                    if (!theme.isEmpty())
                        UIManager.getLookAndFeelDefaults().put(THEME_UI_KEY, theme);
                } else
                    UIManager.setLookAndFeel(lafClassName);
            }
        }
        catch (Throwable ex) {
            LoggingFacade.INSTANCE.logSevere(null, ex);
            // fallback
            FlatLightLaf.setup();
        }

        // remember active look and feel
        // 添加属性更改监听器来记录当前的外观设置
        UIManager.addPropertyChangeListener(e -> {
            if ("lookAndFeel".equals(e.getPropertyName()))
                state.put(KEY_LAF, UIManager.getLookAndFeel().getClass().getName());
        });
    }
}
