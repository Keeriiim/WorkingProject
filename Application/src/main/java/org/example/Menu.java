package org.example;

import com.example.springweb.Kafka.JsonKafkaConsumer;
import com.example.springweb.Payload.User;
import com.example.springweb.Repository.UserRepository;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Menu {
    private HttpClass http;
    private int input;
    private Scanner scan;

    private Properties props;

    private UserRepository userRepository;

    private List<User> users;
    private  Consumer<String, String> consumer1;
    private  Consumer<String, String> consumer2;

    public Menu() throws IOException, InterruptedException {
        this.http = new HttpClass();
        this.scan = new Scanner(System.in);

        this.props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "fetchingGroup");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer1 = new KafkaConsumer<>(props);
        this.consumer2 = new KafkaConsumer<>(props);



        run();
    }

    public void run() throws IOException, InterruptedException {
        boolean running = true;

        while (running) {

            System.out.println("\nWelcome to the menu!");
            System.out.println("1. Add user to local kafka & DB");
            System.out.println("2. Count all users from db");
            System.out.println("3. Print a user from DB");
            System.out.println("4. Print all Users from DB");
            System.out.println("5. Update user from DB");
            System.out.println("6. Delete user from DB");
            System.out.println("7. Delete all users from DB");
            System.out.println("Anything else -> Exit");

            this.input = scan.nextInt();

            switch (input) {
                case 1:
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", 0);

                    System.out.println("Enter first name: ");
                    String firstName = scan.next();
                    jsonObject.put("firstName", firstName);

                    System.out.println("Enter last name: ");
                    String lastName = scan.next();
                    jsonObject.put("lastName", lastName);

                    System.out.println("Enter phone number: ");
                    while (!scan.hasNextLong()) { // Checks if the input is a number
                        System.out.println("Invalid input, please enter a number for phone number: ");
                        scan.next(); // Clear invalid input
                    }
                        Long phoneNumber = scan.nextLong();
                        jsonObject.put("phoneNumber", phoneNumber);
                        http.sendPostAddUser(jsonObject);


                        System.out.println("Your User has been added!");


                    break;

                case 2:
                    System.out.println("Count all users from db");
                    http.countUsers("http://localhost:8080/kafka/CountUsers");
                    break;



                    /*
                    // Send Payload to WebAPI and recieve it -> request
                    System.out.println("Recive all posts from your local kafka server - TestJson topic");

                    consumer1.assign(Collections.singletonList(new TopicPartition("TestJson", 0))); // Assigns the consumer to a specific partition
                    consumer1.seekToBeginning(consumer1.assignment()); // Sets the consumer to the beginning of the partition

                   boolean printTopic = true;

                   while (printTopic) {
                       ConsumerRecords<String, String> records = consumer1.poll(Duration.ofMillis(100)); // Sets the time to wait for data if there is no data in the buffer
                       if(!records.isEmpty()){
                           for (ConsumerRecord<String, String> record : records){ // Prints all the records in the topic
                               System.out.println(record.value());
                           }
                            printTopic = false;
                       }
                   }

                     */

                case 3:
                    System.out.println("Enter id: ");
                    String input = scan.next();
                    http.getUser("http://localhost:8080/kafka/ListUser/" + input);
                    break;


                case 4:
                    System.out.println("Print all Users from DB");
                    http.ListUsers("http://localhost:8080/kafka/ListUsers");
                    break;

                case 5:
                    System.out.println("Update an user from DB");
                    http://localhost:8080/kafka/DeleteUser/1
                    break;

                case 6:
                    http.deleteUser();
                    break;

                case 7:
                    System.out.println("Delete user from DB");
                    http://localhost:8080/kafka/DeleteUser/1
                    break;

                default:
                    System.out.println("Exit");
                    running = false;
                    break;
            }
        }
    }

}
