package com.travelrecommendation.utils;

import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.net.URI;

public class ApiClient {

    /**
     * Makes a GET request to the specified URL.
     *
     * @param url The URL to call.
     * @return The response body as a String.
     * @throws Exception if the request fails.
     */
    public static String get(String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            URI uri = new URIBuilder(url).build();
            HttpGet request = new HttpGet(uri);

            CloseableHttpResponse response = httpClient.execute(request);

            try {
                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode != 200) {
                    throw new Exception("Received non-OK response code " + statusCode);
                }

                return EntityUtils.toString(response.getEntity());

            } finally {
                response.close();
            }

        } finally {
            httpClient.close();
        }
    }
}
