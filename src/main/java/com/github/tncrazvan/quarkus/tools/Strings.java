/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.tncrazvan.quarkus.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

/**
 *
 * @author Razvan Tanase
 */
public interface Strings {
    
    //REGEX PATTERNS
    static final String PATTERN_LEFT_START = "<\\s*(?=script)";
    static final String PATTERN_LEFT_END = "<\\s*\\/\\s*(?=script)";
    static final String PATTERN_RIGHT_END = "(?<=&lt;\\/script)>";
    static final String PATTERN_RIGHT_START_1 = "(?<=\\&lt\\;script)\\s*>";
    static final String PATTERN_RIGHT_START_2 = "(?<=\\&lt\\;script).*\\s*>";
    
    /**
     * Escapes the html script tag by using html character entities.
     * 
     * @param js input javascript code.
     * @return escaped javascript.
     */
    public static String escapeJavaScript(final String js) {
        return js.replaceAll(PATTERN_LEFT_START, "&lt;").replaceAll(PATTERN_LEFT_END, "&lt;/")
                .replaceAll(PATTERN_RIGHT_END, "&gt;").replaceAll(PATTERN_RIGHT_START_1, "&gt;")
                .replaceAll(PATTERN_RIGHT_START_2, "&gt;");

    }

    public static String normalizePathSlashes(String path) {
        path = path.trim().toLowerCase();
        final int classPathLength = path.length();
        if (classPathLength >= 1 && path.charAt(0) != '/') {
            path = '/' + path;
        }
        if (classPathLength > 1 && path.charAt(classPathLength - 1) == '/') {
            path = path.substring(0, classPathLength - 1);
        }

        return path.replaceAll("\\/{2,}", "/");
    }

    /**
     * 
     * 
     * @param value input string
     * @return a capitalized version of the value.
     */
    public static String capitalize(String value) {
        value = value.toLowerCase();
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    /**
     * Decodes URL String.
     * 
     * @param data URL String.
     * @return decoded String.
     */
    public static String decodeUrl(String data) {
        try {
            data = data.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            data = data.replaceAll("\\+", "%2B");
            data = URLDecoder.decode(data, "utf-8");
        } catch (final UnsupportedEncodingException e) {
         e.printStackTrace(System.out);
      }
      return data;
   }
    
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
