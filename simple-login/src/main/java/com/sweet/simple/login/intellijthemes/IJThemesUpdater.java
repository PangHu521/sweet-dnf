package com.sweet.simple.login.intellijthemes;

import com.formdev.flatlaf.util.LoggingFacade;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * This tool updates all IntelliJ themes listed in themes.json by downloading
 * them from the source code repositories.
 *
 * @author Karl Tauber
 */
@Slf4j
public class IJThemesUpdater {
    public static void main(String[] args) {
        IJThemesManager themesManager = new IJThemesManager();
        themesManager.loadBundledThemes();
        for (IJThemeInfo ti : themesManager.bundledThemes) {
            if (ti.sourceCodeUrl == null || ti.sourceCodePath == null) {
                System.out.println("    " + ti.name + " NOT downloaded. Needs manual update from release on JetBrains Plugin portal.");
                continue;
            }

            String fromUrl = ti.sourceCodeUrl + "/" + ti.sourceCodePath;
            if (fromUrl.contains("github.com"))
                fromUrl += "?raw=true";
            else if (fromUrl.contains("gitlab.com"))
                fromUrl = fromUrl.replace("/blob/", "/raw/");

            String toPath = "../flatlaf-intellij-themes/src/main/resources" + IJThemesPanel.THEMES_PACKAGE + ti.resourceName;

            download(fromUrl, toPath);
        }
    }

    private static void download(String fromUrl, String toPath) {
        log.info("Download " + fromUrl);
        Path out = new File(toPath).toPath();
        try {
            URL url = new URL(fromUrl.replace(" ", "%20"));
            URLConnection con = url.openConnection();
            Files.copy(con.getInputStream(), out, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException ex) {
            LoggingFacade.INSTANCE.logSevere(null, ex);
        }
    }
}
