package com.formdev.flatlaf.demo;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatInspector;
import com.formdev.flatlaf.extras.FlatUIDefaultsInspector;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import com.formdev.flatlaf.fonts.jetbrains_mono.FlatJetBrainsMonoFont;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.formdev.flatlaf.util.SystemInfo;

import javax.swing.*;
import java.awt.*;

public class FlatLafDemo {
    public static final String PREFS_ROOT_PATH = "/flatlaf-demo";
    public static final String KEY_TAB = "tab";

    // 是否处于截图模式
    static boolean screenshotsMode = Boolean.parseBoolean(System.getProperty("flatlaf.demo.screenshotsMode"));

    public static void main(String[] args) {
        // macOS  (see https://www.formdev.com/flatlaf/macos/)
        if (SystemInfo.isMacOS) {
            // enable screen menu bar
            // (moves menu bar from JFrame window to top of screen)
            System.setProperty("apple.laf.useScreenMenuBar", "true");

            // application name used in screen menu bar
            // (in first menu after the "apple" menu)
            System.setProperty("apple.awt.application.name", "FlatLaf Demo");

            // appearance of window title bars
            // possible values:
            //   - "system": use current macOS appearance (light or dark)
            //   - "NSAppearanceNameAqua": use light appearance
            //   - "NSAppearanceNameDarkAqua": use dark appearance
            // (must be set on main thread and before AWT/Swing is initialized;
            //  setting it on AWT thread does not work)
            System.setProperty("apple.awt.application.appearance", "system");
        }

        // Linux
        if (SystemInfo.isLinux) {
            // enable custom window decorations
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
        }

        // 设置系统属性 "flatlaf.uiScale" 的值为 "2x"，用于指定 UI 的缩放比例。这样做的目的可能是在截图模式下，并且在旧版本的 Java 运行时中，强制将 UI 缩放设置为 2 倍，以获得更好的截图效果或显示效果
        if (FlatLafDemo.screenshotsMode && !SystemInfo.isJava_9_orLater && System.getProperty(
                "flatlaf.uiScale") == null)
            System.setProperty("flatlaf.uiScale", "2x");

        SwingUtilities.invokeLater(() -> {
            DemoPrefs.init(PREFS_ROOT_PATH);

            // install fonts for lazy loading
            FlatInterFont.installLazy();
            FlatJetBrainsMonoFont.installLazy();
            FlatRobotoFont.installLazy();
            FlatRobotoMonoFont.installLazy();

            // use Inter font by default
            //			FlatLaf.setPreferredFontFamily( FlatInterFont.FAMILY );
            //			FlatLaf.setPreferredLightFontFamily( FlatInterFont.FAMILY_LIGHT );
            //			FlatLaf.setPreferredSemiboldFontFamily( FlatInterFont.FAMILY_SEMIBOLD );

            // use Roboto font by default
            //			FlatLaf.setPreferredFontFamily( FlatRobotoFont.FAMILY );
            //			FlatLaf.setPreferredLightFontFamily( FlatRobotoFont.FAMILY_LIGHT );
            //			FlatLaf.setPreferredSemiboldFontFamily( FlatRobotoFont.FAMILY_SEMIBOLD );

            // use JetBrains Mono font
            //			FlatLaf.setPreferredMonospacedFontFamily( FlatJetBrainsMonoFont.FAMILY );

            // use Roboto Mono font
            //			FlatLaf.setPreferredMonospacedFontFamily( FlatRobotoMonoFont.FAMILY );

            // application specific UI defaults
            FlatLaf.registerCustomDefaultsSource("com.formdev.flatlaf.demo");

            // set look and feel
            DemoPrefs.setupLaf(args);

            // 安装 FlatLaf 的检查器
            // 1.FlatInspector.install(String)：这个方法用于安装 FlatLaf 的外观检查器，以便在运行时查看和调整 Swing 组件的外观属性。它接受一个字符串参数，指定了激活检查器的键盘快捷键。
            // 2."ctrl shift alt X"：这是一个字符串参数，指定了激活 FlatLaf 外观检查器的键盘快捷键。按下这个组合键时，可以打开外观检查器来查看和调整 Swing 组件的外观属性。
            // 3.FlatUIDefaultsInspector.install(String)：这个方法用于安装 FlatLaf 的 UI 默认值检查器，以便在运行时查看和调整 Swing 组件的 UI 默认值。它也接受一个字符串参数，指定了激活检查器的键盘快捷键。
            // 4."ctrl shift alt Y"：这是一个字符串参数，指定了激活 FlatLaf UI 默认值检查器的键盘快捷键。按下这个组合键时，可以打开 UI 默认值检查器来查看和调整 Swing 组件的 UI 默认值。
            FlatInspector.install("ctrl shift alt X");
            FlatUIDefaultsInspector.install("ctrl shift alt Y");

            // 创建窗体
            DemoFrame frame = new DemoFrame();

            // 如果当前 Java 运行时是 Java 9 或更高版本，则设置大小为 (830, 440)，否则设置大小为 (1660, 880)
            if (FlatLafDemo.screenshotsMode) {
                frame.setPreferredSize(SystemInfo.isJava_9_orLater ? new Dimension(830, 440) : new Dimension(1660, 880));
            }

            // show frame
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
