package com.sweet.simple.login.intellijthemes;

import cn.hutool.core.io.resource.ResourceUtil;
import com.formdev.flatlaf.json.Json;
import com.formdev.flatlaf.util.LoggingFacade;
import com.formdev.flatlaf.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Karl Tauber
 */
public class IJThemesManager {
    public List<IJThemeInfo> bundledThemes = new ArrayList<>();
    public List<IJThemeInfo> moreThemes = new ArrayList<>();
    private final Map<File, Long> lastModifiedMap = new HashMap<>();

    @SuppressWarnings("unchecked")
    public void loadBundledThemes() {
        bundledThemes.clear();
        // load themes.json
        Map<String, Object> json;
        InputStream inputStream = ResourceUtil.getStream("com/formdev/flatlaf/demo/intellijthemes/themes.json");
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
        // try (Reader reader = new InputStreamReader(getClass().getResourceAsStream("themes.json"), StandardCharsets.UTF_8)) {
            json = (Map<String, Object>) Json.parse(reader);
        }
        catch (IOException ex) {
            LoggingFacade.INSTANCE.logSevere(null, ex);
            return;
        }

        // add info about bundled themes
        for (Map.Entry<String, Object> e : json.entrySet()) {
            String resourceName = e.getKey();
            Map<String, String> value = (Map<String, String>) e.getValue();
            String name = value.get("name");
            boolean dark = Boolean.parseBoolean(value.get("dark"));
            String license = value.get("license");
            String licenseFile = value.get("licenseFile");
            String sourceCodeUrl = value.get("sourceCodeUrl");
            String sourceCodePath = value.get("sourceCodePath");

            bundledThemes.add(new IJThemeInfo(name, resourceName, dark, license, licenseFile, sourceCodeUrl, sourceCodePath, null, null));
        }
    }

    public void loadThemesFromDirectory() {
        // get current working directory
        File directory = new File("").getAbsoluteFile();

        File[] themeFiles = directory.listFiles((dir, name) -> {
            return name.endsWith(".theme.json") || name.endsWith(".properties");
        });
        if (themeFiles == null)
            return;

        lastModifiedMap.clear();
        lastModifiedMap.put(directory, directory.lastModified());

        moreThemes.clear();
        for (File f : themeFiles) {
            String fname = f.getName();
            String name = fname.endsWith(".properties")
                    ? StringUtils.removeTrailing(fname, ".properties")
                    : StringUtils.removeTrailing(fname, ".theme.json");
            moreThemes.add(new IJThemeInfo(name, null, false, null, null, null, null, f, null));
            lastModifiedMap.put(f, f.lastModified());
        }
    }

    public boolean hasThemesFromDirectoryChanged() {
        for (Map.Entry<File, Long> e : lastModifiedMap.entrySet()) {
            if (e.getKey().lastModified() != e.getValue().longValue())
                return true;
        }
        return false;
    }
}
