package com.sweet.simple.login;

import com.sweet.simple.login.util.SpringContextUtil;
import com.sweet.simple.login.view.LoginSwing;

import java.awt.*;

public class ViewStart {

	/**
	 * 是否处于截图模式
	 */
	public static boolean screenshotsMode = Boolean.parseBoolean(System.getProperty("flatlaf.demo.screenshotsMode"));

	public static final String KEY_TAB = "tab";

	public static void run() {
		EventQueue.invokeLater(() -> SpringContextUtil.getBean(LoginSwing.class).setVisible(true));
	}
}
