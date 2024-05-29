package com.sweet.simple.login;

import java.awt.*;

public class ViewStart {
	
	public static void run() {
		EventQueue.invokeLater(() -> {
            try {
                // 获取LoginPage界面实例并显示
                // SpringContextUtils.getBean(XXXX.class).setVisible(true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
	}
}
