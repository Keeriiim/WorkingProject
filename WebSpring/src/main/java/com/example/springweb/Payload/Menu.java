package com.example.springweb.Payload;

import com.example.springweb.Repository.UserRepository;

import java.net.MalformedURLException;

import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;


public class Menu {  private HttpClass http;
    private int input;
    private Scanner scan;

    private Properties props;

    private UserRepository userRepository;


    public Menu(Class<UserRepository> userRepositoryClass) throws MalformedURLException, InstantiationException, IllegalAccessException {
        this.http = new HttpClass();
        this.scan = new Scanner(System.in);
        this.userRepository = userRepositoryClass.newInstance();


      /*  this.props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "fetchingGroup");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer1 = new KafkaConsumer<>(props);
        this.consumer2 = new KafkaConsumer<>(props);
*/
        run();
    }

    public void run() throws MalformedURLException {
        boolean running = true;

        while (running) {

            System.out.println("\nWelcome to the menu!");
            System.out.println("1. Add user to local kafka & DB");
            System.out.println("2. Recieve all posts from local kafka server - jsondemo topic");
            System.out.println("3. Print all Local kafka Topics");
            System.out.println("4. Print all Users from DB");
            System.out.println("5. Count all users from db");
            System.out.println(". Exit");

            this.input = scan.nextInt();


            switch (input) {
                case 1:

                    System.out.println("Send post");
                    // send post

                    // Get the latest user from the database
                    List<User> users = userRepository.findAll();
                    System.out.println("You have " + users.size() + " users in the DB, userTest being the latest one.");

                    User user = new User();
                    user.setFirstName("Kerim");
                    user.setLastName("Kerimov");
                    user.setPhoneNumber(123456789L);
                    user.setId(users.size()+1);


                    http.sendPost(user);

                    break;

                case 2:
                    // Send Payload to WebAPI and recieve it -> request
                    System.out.println("Recive all posts from your local kafka server - jsondemo topic");


                    boolean printTopic = true;

                 /*   while (printTopic) {
                     //   ConsumerRecords<String, String> records = consumer1.poll(Duration.ofMillis(100)); // Sets the time to wait for data if there is no data in the buffer
                        if(!records.isEmpty()){
                            for (ConsumerRecord<String, String> record : records){
                                System.out.println(record.value());
                            }
                            printTopic = false;
                        }
                    } */

                    break;




                case 3:
                    System.out.println("Print all local kafka topics");
                    http.getLocalKafka("http://localhost:8080/dynamicapp/listAllKafkaTopics");
                    break;
                case 4:
                    /*  System.out.println("Print all Users from DB");
                      http.countUsers("http://localhost:8080/dynamicapp/listAllUsersFromDB");
                    DbList dbList = new DbList();
                    dbList.printAllUsers();
                     */

                    System.out.println("Print all Users from DB");
                    CompletableFuture<User> futureDbList = http.getLocalDB("http://localhost:8080/dynamicapp/listAllUsersFromDB");

                  /*  // Handle the future result asynchronously
                    futureDbList.thenAccept(dbList -> {
                        if (dbList != null) {
                            dbList.printAllUsers();
                        } else {
                            System.out.println("Failed to fetch user data.");
                        }
                    }).join(); // Wait for the CompletableFuture to complete

                   */




                    break;

                case 5:
                    System.out.println("Count all users from db");
                    http.countUsers("http://localhost:8080/dynamicapp/CountAllUsersFromDB");
                    break;
                default:
                    System.out.println("Exit");
                    running = false;
                    break;
            }



            // recieve post
        }
    }

}