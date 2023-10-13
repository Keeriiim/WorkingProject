package org.example;

import com.example.springweb.Kafka.JsonKafkaConsumer;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.json.simple.JSONObject;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Menu {
    private HttpClass http;
    private int input;
    private Scanner scan;
    private Properties props;
    private  Consumer<String, String> consumer1;
    private  Consumer<String, String> consumer2;

    public Menu() throws MalformedURLException {
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
                  //  consumer2.assign(Collections.singletonList(new TopicPartition("jsondemo", 0)));
                    //consumer2.seekToEnd(consumer2.assignment());
                    // send post
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", null);

                    System.out.println("Enter first name: ");
                    String firstName = scan.nextLine();
                    jsonObject.put("firstName", firstName);

                    System.out.println("Enter last name: ");
                    String lastName = scan.nextLine();
                    jsonObject.put("lastName", lastName);

                    System.out.println("Enter phone number: ");
                    Long phoneNumber = scan.nextLong();
                    jsonObject.put("phoneNumber", phoneNumber);
                    http.sendPost(jsonObject);

                    System.out.println("Your User has been added!");

                    // Now consumer is at the end of the partition
                  /*  ConsumerRecords<String, String> recordss = consumer2.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, String> record : recordss) {
                        System.out.println("Last message: " + record.value());
                    }

                   */

                    //consumer2.close();


                    break;

                case 2:
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
                    break;




                case 3:

                    break;


                case 4:
                     System.out.println("Print all Users from DB");
                     http.countUsers("http://localhost:8080/kafka/ListUsers");



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
