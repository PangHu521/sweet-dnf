package com.formdev.flatlaf.demo.intellijthemes;

import java.io.File;

/**
 * @author Karl Tauber
 */
class IJThemeInfo
{
	final String name;
	final String resourceName;
	final boolean dark;
	final String license;
	final String licenseFile;
	final String sourceCodeUrl;
	final String sourceCodePath;
	final File themeFile;
	final String lafClassName;

	IJThemeInfo( String name, String resourceName, boolean dark,
		String license, String licenseFile,
		String sourceCodeUrl, String sourceCodePath,
		File themeFile, String lafClassName )
	{
		this.name = name;
		this.resourceName = resourceName;
		this.dark = dark;
		this.license = license;
		this.licenseFile = licenseFile;
		this.sourceCodeUrl = sourceCodeUrl;
		this.sourceCodePath = sourceCodePath;
		this.themeFile = themeFile;
		this.lafClassName = lafClassName;
	}
}
