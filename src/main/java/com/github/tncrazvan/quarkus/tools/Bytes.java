/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.tncrazvan.quarkus.tools;

import java.util.Arrays;

/**
 *
 * @author Razvan Tanase
 */
public interface Bytes {
    /**
     * Removes unset bytes in byte array starting from the right most byte, which is bytes[bytes.length-1].
     * @param bytes input byte array.
     * @return trimmed byte array.
     */
    public static byte[] trim(final byte[] bytes) {
        int i = bytes.length - 1;
        while (i >= 0 && bytes[i] == 0) {
            --i;
        }

        return Arrays.copyOf(bytes, i + 1);
    }

    /**
     * Return a new byte array containing a sub-portion of the source array
     * 
     * @param source   The source array of bytes
     * @param srcBegin The beginning index (inclusive)
     * @return The new, populated byte array
     */
    public static byte[] subBytes(final byte[] source, final int srcBegin) {
        return subBytes(source, srcBegin, source.length);
    }

    /**
     * Return a new byte array containing a sub-portion of the source array
     * 
     * @param source   The source array of bytes
     * @param srcBegin The beginning index (inclusive)
     * @param srcEnd   The ending index (exclusive)
     * @return The new, populated byte array
     */
    public static byte[] subBytes(final byte[] source, final int srcBegin, final int srcEnd) {
        byte destination[];

        destination = new byte[srcEnd - srcBegin];
        getBytes(source, srcBegin, srcEnd, destination, 0);

        return destination;
    }

    /**
     * Copies bytes from the source byte array to the destination array
     * 
     * @param source      The source array
     * @param srcBegin    Index of the first source byte to copy
     * @param srcEnd      Index after the last source byte to copy
     * @param destination The destination array
     * @param dstBegin    The starting offset in the destination array
     */
    public static void getBytes(final byte[] source, final int srcBegin, final int srcEnd, final byte[] destination,
            final int dstBegin) {
        System.arraycopy(source, srcBegin, destination, dstBegin, srcEnd - srcBegin);
    }

    /**
     * Checks if the given byte array is emtpy.
     * 
     * @param array input byte array.
     * @return true if array is empty, false otherwise.
     */
    public static boolean byteArrayIsEmpty(final byte[] array) {
        int sum = 0;
        for (final byte b : array) {
            sum |= b;
        }
        return (sum == 0);
    }
}
