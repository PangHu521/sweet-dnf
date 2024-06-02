package com.sweet.simple.login.intellijthemes;

import java.io.File;

/**
 * 主题信息
 */
public class IJThemeInfo {
    public String name;
    public String resourceName;
    public boolean dark;
    public String license;
    public String licenseFile;
    public String sourceCodeUrl;
    public String sourceCodePath;
    public File themeFile;
    public String lafClassName;

    public IJThemeInfo(String name, String resourceName, boolean dark, String license, String licenseFile, String sourceCodeUrl, String sourceCodePath, File themeFile, String lafClassName) {
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
