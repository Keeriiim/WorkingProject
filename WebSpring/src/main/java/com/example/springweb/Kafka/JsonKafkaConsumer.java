package com.example.springweb.Kafka;


import com.example.springweb.Payload.User;
import com.example.springweb.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaConsumer.class);

    @Autowired
    private UserRepository userRepository;

    @KafkaListener(topics = "TestJson", groupId = "myGroup")
    public void consume(User user) {

        LOGGER.info(String.format("Json message recieved -> %s", user.toString()));
    }
}
