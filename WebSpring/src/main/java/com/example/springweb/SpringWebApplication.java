package com.example.springweb;

import com.example.springweb.Payload.Menu;
import com.example.springweb.Payload.User;
import com.example.springweb.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.MalformedURLException;

@SpringBootApplication
public class SpringWebApplication {

    @Autowired
    private static UserRepository userRepository;

    public static void main(String[] args) throws MalformedURLException, InstantiationException, IllegalAccessException {
        SpringApplication.run(SpringWebApplication.class, args);









    }

}
