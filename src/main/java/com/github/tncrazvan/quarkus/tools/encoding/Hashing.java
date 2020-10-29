/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.tncrazvan.quarkus.tools.encoding;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import static com.github.tncrazvan.quarkus.tools.SharedObject.LOGGER;

/**
 *
 * @author Razvan Tanase
 */
public interface Hashing {
    
    /**
     * Encodes String to sha1.
     * @param str input String.
     * @param charset character set to use
     * @return encoded String.
     */
    public static String getSha1String(final String str, final String charset) {
        try {
            final MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(str.getBytes(charset));

            return new BigInteger(1, crypt.digest()).toString(16);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            LOGGER.log(Level.WARNING, null, ex);
            return null;
        }
    }

    /**
     * Encodes String to sha1 byte array.
     * 
     * @param input   input String.
     * @param charset character set to use
     * @return encoded byte array.
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static byte[] getSha1Bytes(final String input, final String charset)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {

        return MessageDigest.getInstance("SHA-1").digest(input.getBytes(charset));
    }

    /**
     * Encodes the given String to sha512.
     * 
     * @param value   input String.
     * @param salt    salt String. Can be empty.
     * @param charset character set to use
     * @return encoded String.
     */
    public static String getSha512String(final String value, final String salt, final String charset) {
        String result = null;
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(charset));
            final byte[] bytes = md.digest(value.getBytes(charset));
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            result = sb.toString();

        } catch (final NoSuchAlgorithmException e) {
        } catch (final UnsupportedEncodingException ex) {
            LOGGER.log(Level.WARNING, null, ex);
        }
        return result;
    }

    /**
     * Encodes the given String to a sha512 byte array.
     * 
     * @param value   input String.
     * @param salt    salt String. Can be empty.
     * @param charset character set to use
     * @return encoded byte array.
     */
    public static byte[] getSha512Bytes(final String value, final String salt, final String charset) {
        try {
            return getSha512String(value, salt, charset).getBytes(charset);
        } catch (final UnsupportedEncodingException ex) {
            LOGGER.log(Level.WARNING, null, ex);
            return null;
        }
    }

    static class BCrypt {
        private static String generateStorngPasswordHash(final String password)
                throws NoSuchAlgorithmException, InvalidKeySpecException {
            final int iterations = 1000;
            final char[] chars = password.toCharArray();
            final byte[] salt = getSalt();

            final PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
            final SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            final byte[] hash = skf.generateSecret(spec).getEncoded();
            return iterations + ":" + toHex(salt) + ":" + toHex(hash);
        }

        private static byte[] getSalt() throws NoSuchAlgorithmException {
            final SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            final byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return salt;
        }

        private static String toHex(final byte[] array) throws NoSuchAlgorithmException {
            final BigInteger bi = new BigInteger(1, array);
            final String hex = bi.toString(16);
            final int paddingLength = (array.length * 2) - hex.length();
            if (paddingLength > 0) {
                return String.format("%0" + paddingLength + "d", 0) + hex;
            } else {
                return hex;
            }
        }

        private static boolean validatePassword(final String originalPassword, final String storedPassword)
                throws NoSuchAlgorithmException, InvalidKeySpecException {
            final String[] parts = storedPassword.split(":");
            final int iterations = Integer.parseInt(parts[0]);
            final byte[] salt = fromHex(parts[1]);
            final byte[] hash = fromHex(parts[2]);

            final PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
            final SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            final byte[] testHash = skf.generateSecret(spec).getEncoded();

            int diff = hash.length ^ testHash.length;
            for (int i = 0; i < hash.length && i < testHash.length; i++) {
                diff |= hash[i] ^ testHash[i];
            }
            return diff == 0;
        }

        private static byte[] fromHex(final String hex) throws NoSuchAlgorithmException {
            final byte[] bytes = new byte[hex.length() / 2];
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
            }
            return bytes;
        }
    }

    /**
     * Encodes the value to BCrypt. Note that encoding the same value twice will
     * generate a different BCrypt string. This means you cannot simply check two
     * encoded string to find out if they were generated from the same value like
     * Sha1. Use Hashing#validateBCryptString to validate an encoded strings.
     * 
     * @param value input String.
     * @return encoded String.
     */
    public static String getBCryptString(final String value) {
        try {
            return BCrypt.generateStorngPasswordHash(value);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            LOGGER.log(Level.WARNING, null, ex);
            return null;
        }
    }

    /**
     * Checks if the given cryptoStrong has been created from the given
     * originalString.
     * 
     * @param originalString this is the validation string. The encrypted string
     *                       will be validated using this value.
     * @param cryptoString   this is the encoded string.
     * @return true if the cryptoString was created from the originalString, false
     *         otherwise.
     */
    public static boolean validateBCryptString(final String originalString, final String cryptoString) {
        try {
            return BCrypt.validatePassword(originalString, cryptoString);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            LOGGER.log(Level.WARNING,null,ex);
            return false;
        }
    }
}
