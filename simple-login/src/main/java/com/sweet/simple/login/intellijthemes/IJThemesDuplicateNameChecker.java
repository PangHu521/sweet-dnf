package com.sweet.simple.login.intellijthemes;

import com.formdev.flatlaf.json.Json;
import com.formdev.flatlaf.json.ParseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;

/**
 * This tool checks whether there are duplicate name fields in all theme .json files.
 *
 * This is important for following file, where the name is used for theme specific UI defaults:
 *   flatlaf-core/src/main/resources/com/formdev/flatlaf/IntelliJTheme$ThemeLaf.properties
 *
 * @author Karl Tauber
 */
public class IJThemesDuplicateNameChecker
{
	public static void main( String[] args ) {
		IJThemesManager themesManager = new IJThemesManager();
		themesManager.loadBundledThemes();

		HashSet<String> names = new HashSet<>();
		for( IJThemeInfo ti : themesManager.bundledThemes ) {
			if( ti.sourceCodeUrl == null || ti.sourceCodePath == null )
				continue;

			String jsonPath = "../flatlaf-intellij-themes/src/main/resources" + IJThemesPanel.THEMES_PACKAGE + ti.resourceName;
			String name;
			try {
				name = readNameFromJson( jsonPath );
			} catch( IOException ex ) {
				System.err.println( "Failed to read '" + jsonPath + "'" );
				continue;
			}

			if( names.contains( name ) )
				System.out.println( "Duplicate name '" + name + "'" );
			names.add( name );
		}
	}

	private static String readNameFromJson( String jsonPath ) throws IOException {
		try( Reader reader = new InputStreamReader( new FileInputStream( jsonPath ), StandardCharsets.UTF_8 ) ) {
			@SuppressWarnings( "unchecked" )
			Map<String, Object> json = (Map<String, Object>) Json.parse( reader );
			return (String) json.get( "name" );
		} catch( ParseException ex ) {
			throw new IOException( ex.getMessage(), ex );
		}
	}
}
