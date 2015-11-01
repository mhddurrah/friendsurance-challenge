package com.friendsurance.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * application configuration handler, load configurations from a property file
 * configurations may be: rules folder, input folder, output folder,
 * 
 * @author durrah
 *
 */
public class Configs {
	static Properties PROPERTIES = new Properties();

	static {
		InputStream is = Configs.class.getClassLoader().getResourceAsStream("com/friendsurance/impl/config.properties");
		try {
			PROPERTIES.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		return (String) PROPERTIES.get(key);
	}

}
