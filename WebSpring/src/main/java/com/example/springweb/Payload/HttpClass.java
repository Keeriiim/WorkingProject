package com.example.springweb.Payload;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;


public class HttpClass {
    private URL url;
    private User user;

    private ObjectMapper objectMapper;

    public HttpClass() throws MalformedURLException {
        this.url = new URL("http://localhost:8080/kafka/publish");
        this.objectMapper = new ObjectMapper();
    }

    public void sendPost(User user) {
        this.user = user;

        /*    HTTP CLIENT       */
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url.toURI());

            // Convert User object to JSON string
            String jsonPayload = objectMapper.writeValueAsString(user);

            // Set the JSON request body
            StringEntity entity = new StringEntity(jsonPayload);
            httpPost.setEntity(entity);


         //   StringEntity entity = new StringEntity(jsonPayload, ContentType.APPLICATION_JSON);

            // Skicka förfrågan och hantera svaret
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    String responseString = EntityUtils.toString(responseEntity);
                    System.out.println("Svar från server: " + responseString);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void getLocalKafka(String httpGET) throws MalformedURLException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(httpGET))
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();
                // Process the response body as needed
                System.out.println(responseBody);
            } else {
                System.out.println("Request failed with status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // Handle exceptions properly in your actual implementation
        }
    }

    public CompletableFuture<User> getLocalDB(String httpGET) throws MalformedURLException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(httpGET))
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.toString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();
                // Process the response body as needed
                System.out.println(responseBody);
            } else {
                System.out.println("Request failed with status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // Handle exceptions properly in your actual implementation
        }
        return null;
    }

    public void countUsers(String httpGET) throws MalformedURLException {

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(httpGET))
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.toString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();
                // Process the response body as needed
                System.out.println(responseBody);
            } else {
                System.out.println("Request failed with status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // Handle exceptions properly in your actual implementation
        }
    }


}