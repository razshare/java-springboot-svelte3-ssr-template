package com.github.tncrazvan.quarkus.tools.http;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author Razvan Tanase
 */
public class FetchResult{
    private final byte[] data;

    public FetchResult(final byte[] data) {
        this.data = data;
    }

    public final byte[] getBytes() {
        return data;
    }

    public final String getString() throws UnsupportedEncodingException {
        return getString("UTF-8");
    }

    public final String getString(final String charset) throws UnsupportedEncodingException {
        return new String(data,charset);
    }

    public final boolean isEmpty(){
        return data == null;
    }

    public final boolean isNull(){
        return data == null;
    }

    public final boolean isNullOrEmpty(){
        return data == null || data.length == 0;
    }

    public final boolean isBlank(){
        return new String(data).trim().equals("");
    }

    public final boolean isNullOrBLank(){
        return data == null || new String(data).trim().equals("");
    }

}