package com.github.tncrazvan.quarkus.tools.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import static com.github.tncrazvan.quarkus.tools.SharedObject.LOGGER;


/**
 *
 * @author Razvan Tanase
 */
public interface Fetch {
    /**
     * Make a synchronous Http GET request to the given url.
     * @param targetURL url to make the request to.
     * @return the result of the request.
     */
    public static FetchResult get(final String targetURL) {
        return request("GET", targetURL, null, new HashMap<>());
    }

    /**
     * Make a synchronous Http GET request to the given url.
     * @param targetURL url to make the request to.
     * @param headers headers of the request.
     * @return the result of the request.
     */
    public static FetchResult get(final String targetURL, final Map<String, String> headers) {
        return request("GET", targetURL, null, headers);
    }


    /**
     * Make a synchronous Http POST request to the given url.
     * @param targetURL url to make the request to.
     * @return the result of the request.
     */
    public static FetchResult post(final String targetURL) {
        return request("POST", targetURL, null, new HashMap<>());
    }

    /**
     * Make a synchronous Http POST request to the given url.
     * @param targetURL url to make the request to.
     * @param data body of the request.
     * @return the result of the request.
     */
    public static FetchResult post(final String targetURL, final String data) {
        return request("POST", targetURL, data, new HashMap<>());
    }

    /**
     * Make a synchronous Http POST request to the given url.
     * @param targetURL url to make the request to.
     * @param data body of the request.
     * @param headers of the request.
     * @return the result of the request.
     */
    public static FetchResult post(final String targetURL, final String data, final Map<String, String> headers) {
        return request("POST", targetURL, data, headers);
    }

    /**
     * Make a synchronous Http request to the given url.
     * @param method the http method to use.
     * @param targetURL url to make the request to.
     * @param data body of the request.
     * @return the result of the request.
     */
    public static FetchResult request(final String method, final String targetURL, final String data) {
        return request(method, targetURL, data, new HashMap<>());
    }

    /**
     * Make a synchronous Http request to the given url.
     * @param method the http method to use.
     * @param targetURL url to make the request to.
     * @param data body of the request.
     * @param headers headers of the request.
     * @return the result of the request.
     */
    public static FetchResult request(final String method, final String targetURL, final String data, final Map<String, String> headers) {
        try {
            // Create connection
            final URL url = new URL(targetURL);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);

            if (headers != null)
                headers.keySet().forEach((key) -> {
                    connection.setRequestProperty(key, headers.get(key));
                });

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            if (method.equals("POST")) {
                // Send request
                final DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(data == null ? "" : data);
                wr.close();
            }

            // Get Response
            final InputStream is = connection.getInputStream();

            return new FetchResult(is.readAllBytes());
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, null, e);
            return null;
        }
    }
}
