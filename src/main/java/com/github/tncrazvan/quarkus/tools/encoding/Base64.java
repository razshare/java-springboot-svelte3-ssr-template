/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.tncrazvan.quarkus.tools.encoding;

import static com.github.tncrazvan.quarkus.tools.SharedObject.BASE64_ENCODER;
import static com.github.tncrazvan.quarkus.tools.SharedObject.BASE64_DECODER;
import static com.github.tncrazvan.quarkus.tools.SharedObject.LOGGER;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

/**
 *
 * @author Razvan Tanase
 */
public interface Base64 {
    
    /**
     * Decodes base64 String.
     * @param value base64 String.
     * @param charset character set to use
     * @return decoded String.
     */
    public static String atob(final String value, final String charset) {
        try {
            return new String(BASE64_DECODER.decode(value.getBytes(charset)), charset);
        } catch (final UnsupportedEncodingException ex) {
            LOGGER.log(Level.WARNING, null, ex);
        }
        return null;
    }

    /**
     * Decodes base64 String to byte array.
     * 
     * @param value   encoded String.
     * @param charset character set to use
     * @return decoded byte array.
     */
    public static byte[] atobByte(final String value, final String charset) {
        try {
            return BASE64_DECODER.decode(value.getBytes(charset));
        } catch (final UnsupportedEncodingException ex) {
            LOGGER.log(Level.WARNING, null, ex);
        }
        return new byte[0];
    }

    /**
     * Decodes base64 byte array.
     * 
     * @param value encoded byte array.
     * @return decoded byte array.
     */
    public static byte[] atobByte(final byte[] value) {
        return BASE64_DECODER.decode(value);
    }

    /**
     * Encodes String to base64.
     * 
     * @param value   input String.
     * @param charset character set to use
     * @return encoded String.
     */
    public static String btoa(final byte[] value, final String charset) {
        try {
            return new String(BASE64_ENCODER.encode(value), charset);
        } catch (final UnsupportedEncodingException ex) {
            LOGGER.log(Level.WARNING, null, ex);
        }
        return "";
    }

    /**
     * Encodes String to base64.
     * 
     * @param value   input String.
     * @param charset character set to use
     * @return encoded String.
     */
    public static String btoa(final String value, final String charset) {
        try {
            return new String(BASE64_ENCODER.encode(value.getBytes(charset)), charset);
        } catch (final UnsupportedEncodingException ex) {
            LOGGER.log(Level.WARNING, null, ex);
        }
        return "";
    }

    /**
     * Encodes String to base64 byte array.
     * 
     * @param value   input String.
     * @param charset character set to use
     * @return encoded byte array.
     */
    public static byte[] btoaGetBytes(final String value, final String charset) {
        try {
            return BASE64_ENCODER.encode(value.getBytes(charset));
        } catch (final UnsupportedEncodingException ex) {
            LOGGER.log(Level.WARNING, null, ex);
        }
        return new byte[0];
    }

    /**
     * Encodes byte array to base64.
     * 
     * @param value input byte array.
     * @return encoded byte array.
     */
    public static byte[] btoaGetBytes(final byte[] value) {
        return BASE64_ENCODER.encode(value);
    }
}
