/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.tncrazvan.quarkus.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Razvan Tanase
 */
public interface Regex {
    /**
     * Split a String according to a regular expression.
     * @param subject The string to analyze.
     * @param regex Your regex.
     * @param limit Limits the result size.
     * @param flags Regex flags.
     * @return the resulting String array.
     */
    public static String[] split(final String subject, final String regex, final int limit, final int flags) {
        final Pattern pattern = Pattern.compile(regex, flags);
        return pattern.split(subject,limit);
    }

    /**
     * Split a String according to a regular expression.
     * @param subject The string to analyze.
     * @param regex Your regex.
     * @param limit Limits the result size.
     * @return the resulting String array.
     */
    public static String[] split(final String subject, final String regex, final int limit) {
        final Pattern pattern = Pattern.compile(regex);
        return pattern.split(subject,limit);
    }
    
    /**
     * Split a String according to a regular expression.
     * @param subject The string to analyze.
     * @param regex Your regex.
     * @return the resulting String array.
     */
    public static String[] split(final String subject, final String regex) {
        final Pattern pattern = Pattern.compile(regex);
        return pattern.split(subject);
    }
    
    
    
    /**
     * Matches a regular expression on the given subject String.
     * @param subject The string to analyze.
     * @param regex Your regex.
     * @param flags Regex flags.
     * @return the first group matched.
     */
    public static boolean match(final String subject, final String regex, final int flags) {
        final Pattern pattern = Pattern.compile(regex, flags);
        final Matcher matcher = pattern.matcher(subject);
        return matcher.find();
    }

    /**
     * Matches a regular expression on the given subject String.
     * 
     * @param subject The string to analyze.
     * @param regex   Your regex.
     * @return the first group matched.
     */
    public static boolean match(final String subject, final String regex) {
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(subject);
        return matcher.find();
    }

    /**
     * Extracts the nth occurrence of the given regular expression on the given
     * subject String.
     * 
     * @param subject the input String.
     * @param regex your regular expression.
     * @param replacement replacement string.
     * @return the nth occurred String.
     */
    public static String replace(final String subject, final String regex, final String replacement) {
        return subject.replaceAll(regex, replacement);
    }

    /**
     * Extracts the nth occurrence of the given regular expression on the given
     * subject String.
     * 
     * @param subject the input String.
     * @param regex your regular expression.
     * @param n the occurences counter.
     * @param flags Regex flags
     * @return the nth occurred String.
     */
    public static String group(final String subject, final String regex, int n, final int flags) {
        final Pattern pattern = Pattern.compile(regex, flags);
        final Matcher matcher = pattern.matcher(subject);
        if (matcher.find()) {
            if (n < 0) {
                n = matcher.groupCount() + n;
            }
            return matcher.group(n);
        }
        return null;
    }

    /**
     * Extracts the nth occurrence of the given regular expression on the given
     * subject String.
     * 
     * @param subject the input String.
     * @param regex your regular expression.
     * @param counter the occurences counter.
     * @return the nth occurred String.
     */
    public static String group(final String subject, final String regex, int counter) {
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(subject);
        if (matcher.find()) {
            if (counter < 0) {
                counter = matcher.groupCount() + counter;
            }
            return matcher.group(counter);
        }
        return null;
    }

    /**
     * Extracts the first occurrence of the given regular expression on the given
     * subject String.
     * 
     * @param subject the input String.
     * @param regex your regular expression.
     * @param flags Regex flags
     * @return the first occurred String.
     */
    public static String extract(final String subject, final String regex, final int flags) {
        return Regex.group(subject, regex, 0, flags);
    }

    /**
     * Extracts the first occurrence of the given regular expression on the given
     * subject String.
     * 
     * @param subject the input String.
     * @param regex your regular expression.
     * @return the first occurred String.
     */
    public static String extract(final String subject, final String regex) {
        return group(subject, regex, 0);
    }
}
