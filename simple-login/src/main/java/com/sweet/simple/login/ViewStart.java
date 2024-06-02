package com.sweet.simple.login;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import com.formdev.flatlaf.fonts.jetbrains_mono.FlatJetBrainsMonoFont;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.fonts.roboto_mono.FlatRobotoMonoFont;
import com.sweet.simple.login.view.SweetFrame;
import com.sweet.simple.login.view.SweetPreferences;

import javax.swing.*;

public class ViewStart {

	/**
	 * 用户偏好设置保存根路径
	 */
	public static final String PREFERENCES_ROOT_PATH = "/sweet-dnf";

	// 是否处于截图模式
	public static boolean screenshotsMode = Boolean.parseBoolean(System.getProperty("flatlaf.demo.screenshotsMode"));

	public static final String KEY_TAB = "tab";
	
	public static void run(String[] args) {
		SwingUtilities.invokeLater(() -> invokeLater(args));
	}

	private static void invokeLater(String[] args) {
		SweetPreferences.init(PREFERENCES_ROOT_PATH);
		// 延迟加载字体
		FlatInterFont.installLazy();
		FlatJetBrainsMonoFont.installLazy();
		FlatRobotoFont.installLazy();
		FlatRobotoMonoFont.installLazy();
		// 注册自定义的 UI 默认值源
		FlatLaf.registerCustomDefaultsSource("com.formdev.flatlaf.demo");
		// 用户偏好设置
		SweetPreferences.setupLaf(args);

		SweetFrame sweetFrame = new SweetFrame();

		// LoginSwing loginSwing = SpringContextUtil.getBean(LoginSwing.class);
		sweetFrame.pack();
		sweetFrame.setLocationRelativeTo(null);
		sweetFrame.setVisible(true);
	}
}
