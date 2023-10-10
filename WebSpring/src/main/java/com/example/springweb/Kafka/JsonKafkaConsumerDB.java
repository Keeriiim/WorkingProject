package com.example.springweb.Kafka;

import com.example.springweb.Payload.User;
import com.example.springweb.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaConsumerDB {

    @Autowired
    private UserRepository userRepository;

    @KafkaListener(topics = "TestJson", groupId = "otherGroup")
    public void writeToDb(User user) {

        System.out.println(user);

        System.out.println("Skickar data till DB!");
        //Skicka data till DB
        userRepository.save(user);
    }
}
