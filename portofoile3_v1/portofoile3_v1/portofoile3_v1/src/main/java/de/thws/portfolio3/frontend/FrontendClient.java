package de.thws.portfolio3.frontend;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.Scanner;

public class FrontendClient {
    private static final String BASE_URL = "http://localhost:8080/api/v1";

    private String sendRequest(String endpoint, String method, String jsonInput) throws Exception {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Accept", "application/json");

        if (jsonInput != null) {
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
        }

        return getResponse(conn);
    }

    private String getResponse(HttpURLConnection conn) throws Exception {
        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode); // Logging the response code

        if (responseCode >= 200 && responseCode < 300) {
            try (InputStream in = conn.getInputStream();
                 Scanner scanner = new Scanner(in, "UTF-8")) {
                return scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            }
        } else {
            // Read error response if available
            try (InputStream in = conn.getErrorStream();
                 Scanner scanner = new Scanner(in, "UTF-8")) {
                String errorResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "No error details";
                throw new RuntimeException("Failed : HTTP error code : " + responseCode + ". " + errorResponse);
            }
        }
    }

    public String get(String endpoint) throws Exception {
        return sendRequest(endpoint, "GET", null);
    }

    public String post(String endpoint, String jsonInput) throws Exception {
        return sendRequest(endpoint, "POST", jsonInput);
    }

    public String put(String endpoint, String jsonInput) throws Exception {
        return sendRequest(endpoint, "PUT", jsonInput);
    }

    public int deleteWithResponseCode(String endpoint) throws Exception {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");
        int responseCode = conn.getResponseCode();
        System.out.println("Response Code: " + responseCode); // Logging the response code
        return responseCode;
    }
}
