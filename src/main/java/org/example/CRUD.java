package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CRUD
{
    public static void create(String urlString, String jsonInputString) throws Exception
    {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream())
        {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        System.out.println("POST Response Code:" + responseCode);
    }

    public static void read(String urlString) throws Exception
    {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        System.out.println("GET Response: " + responseCode);

        if (responseCode >= 200 && responseCode < 300)
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null)
            {
                response.append(responseLine.trim());
            }
            System.out.println("Response Body: " + response.toString());
        }
        else
        {
            System.out.println("Error: HTTP response code " + responseCode);
        }
    }

    public static void update(String urlString, String jsonInputString) throws Exception
    {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream())
        {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        System.out.println("PUT Response Code: " + responseCode);
    }

    public static void delete(String urlString) throws Exception
    {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("DELETE");

        int responseCode = conn.getResponseCode();
        System.out.println("DELETE Response Code: " + responseCode);
    }

    public static void main(String[] args) throws Exception
    {
        String url = "https://jsonplaceholder.typicode.com/posts";
        String json = "{\"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}";
        create(url, json);

        read(url + "/1");

        String updatedJson = "{\"id\": 1, \"title\": \"foo updated\", \"body\": \"bar updated\", \"userId\": 1}";
        update(url + "/1", updatedJson);

        delete(url + "/1");
    }
}
