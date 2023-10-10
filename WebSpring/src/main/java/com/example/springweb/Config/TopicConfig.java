package com.example.springweb.Config;

import com.example.springweb.Payload.Menu;
import com.example.springweb.Repository.UserRepository;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {

   /* @Bean
    public NewTopic myTopic() {
        return TopicBuilder.name("javaGuides").build();
    }

    */

    @Bean
    public NewTopic myJsonTopic() { return TopicBuilder.name("TestJson").build(); }



    }

