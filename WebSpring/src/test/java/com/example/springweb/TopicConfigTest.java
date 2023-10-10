package com.example.springweb;

import com.example.springweb.Config.TopicConfig;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@EmbeddedKafka
@ActiveProfiles("test")
public class TopicConfigTest {
    private AdminClient adminClient;


    @BeforeAll
    public void setUp() {
        Properties adminClientProperties = new Properties(); // Creates a new Properties object
        adminClientProperties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Sets the bootstrap server to 9092 which is the default port for Kafka

        this.adminClient = AdminClient.create(adminClientProperties); // Creates a new AdminClient object and sets it to the adminClient variable
    }


    @AfterAll
    public void tearDown() {
        adminClient.close(); // Closes the adminClient for testing
    }


    @Test
    public void testmyJsonTopic() throws Exception {
        TopicConfig topicConfig = new TopicConfig();
        NewTopic topic = topicConfig.myJsonTopic();

        assertEquals("TestJson", topic.name()); // Checks if the topic name is set to "TestJson"
        assertNotEquals("TestJson1", topic.name()); // Double checks if any other NOT set topic name passes

    }



}
