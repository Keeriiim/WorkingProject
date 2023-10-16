package org.example;



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
import java.util.Scanner;


public class HttpClass {
    private URL url;
    private JSONObject jsonObject;
    private Long count = 0L;

    private Scanner scan;


    public HttpClass() {
        this.scan = new Scanner(System.in);
    }

    public void sendPostAddUser(JSONObject jsonObject)  { // Sends a post request to the DB to add a user
        this.jsonObject = jsonObject;

        try{
            this.url = new URL("http://localhost:8080/kafka/publish"); // URL to send the post request to my DB
            countUsers("http://localhost:8080/kafka/CountUsers"); // Gets the number of users in the DB
            this.jsonObject.put("id", this.count+1); // Adds the id to the json object based on the number of users in the DB +1

            /*    HTTP CLIENT       */
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url.toURI());

            // Create a JSON request payload
            String jsonPayload = jsonObject.toJSONString();
            StringEntity entity = new StringEntity(jsonPayload, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            // Send a JSON request and get the JSON response
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity);
            System.out.println(responseString);

            } catch (Exception e) {
                System.out.println("Error, " + e.getMessage());
            }
    }
    public Long countUsers(String httpGET) { // Prints the number of users in the DB
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(httpGET))
                .GET()
                .build();

               HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
               String responseBody = response.body();
               this.count = Long.parseLong(responseBody);
           }
           catch (Exception e){
               System.out.println("Error, please check that you are calling the right URL" + e.getMessage());
           }
        return this.count;
    }
    public void getUser(String httpGET){ // Gets the user from the DB based on the requested id

       try {
           HttpClient httpClient = HttpClient.newHttpClient();
           HttpRequest request = HttpRequest.newBuilder()
                   .uri(URI.create(httpGET))
                   .GET()
                   .build();

           HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
           System.out.println(response.body());
       }
       catch (Exception e) {
        System.out.println(e.getMessage());
       }
    }
    public void ListUsers(String httpGET)  { // Gets the user from the DB based on the requested id
        try{
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(httpGET))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateUser(String httpPut){
        String httpPUT = httpPut; // Stores the url to send the put request to
        countUsers("http://localhost:8080/kafka/CountUsers"); // Gets the number of users in the DB
        System.out.println("You have " + this.count + " users in the DB, with user 1 being the first");

        System.out.println("Enter the id of the user you want to update: ");
        Long updateID = scan.nextLong();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", updateID);


        System.out.println("Update first name: ");
        String firstName = scan.next();
        jsonObject.put("firstName", firstName);

        System.out.println("Update last name: ");
        String lastName = scan.next();
        jsonObject.put("lastName", lastName);

        System.out.println("Update phone number: ");
        while (!scan.hasNextLong()) { // Checks if the input is a number
            System.out.println("Invalid input, please enter a number for phone number: ");
            scan.next(); // Clear invalid input
        }
        Long phoneNumber = scan.nextLong();
        jsonObject.put("phoneNumber", phoneNumber);

        // Har sparat mitt nya json object

        System.out.println("Before: "); getUser("http://localhost:8080/kafka/ListUser/"+updateID);

        try{
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(httpPUT+"1"))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonObject.toJSONString()))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("After: "); getUser("http://localhost:8080/kafka/ListUser/"+updateID);

        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }

    }
    public void deleteUser(){
        // String httpDelete = httpDELETE;
        countUsers("http://localhost:8080/kafka/CountUsers");

        if (this.count == 0){ // Checks if there are any users in the DB
            System.out.println("No users to delete");
        }
        else{
            System.out.println("You have " + this.count + " users in the DB, with user 1 being the first");
            System.out.println("Enter the id of the user you want to delete: ");
            String deleteID = scan.next();
            String httpDELETE = "http://localhost:8080/kafka/DeleteUser/" + deleteID; // Adds the id to the url
            System.out.println(httpDELETE);

            try {
                HttpClient httpClient = HttpClient.newHttpClient(); // Sends a delete request to the DB
                HttpRequest request = HttpRequest.newBuilder()      // to delete the user with the requested id
                    .uri(URI.create(httpDELETE))
                    .DELETE()
                    .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("User with id " + deleteID + " has been deleted");

            } catch (Exception e) {
                System.out.println(e.getMessage());;
            }
        }
    }

    public void deleteAllUsers(){
        countUsers("http://localhost:8080/kafka/CountUsers");

        if (this.count == 0){ // Checks if there are any users in the DB
            System.out.println("No users to delete");
        }
        else{
            System.out.println("You have " + this.count + " users in the DB, all will be deleted");
            String httpDeleteAll = "http://localhost:8080/kafka/DeleteAllUsers"; // Adds the id to the url
            try {
                HttpClient httpClient = HttpClient.newHttpClient(); // Sends a delete request to the DB
                HttpRequest request = HttpRequest.newBuilder()      // to delete the user with the requested id
                        .uri(URI.create(httpDeleteAll))
                        .DELETE()
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());

            } catch (Exception e) {
                System.out.println(e.getMessage());;
            }
        }
    }




    // get All Local KAfka topics / messages

    // integrate to delete kafka messages












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

   /* public CompletableFuture<DbList> getLocalDB(String httpGET) throws MalformedURLException {
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

    }*/






}