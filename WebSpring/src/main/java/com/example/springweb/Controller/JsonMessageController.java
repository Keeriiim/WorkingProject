package com.example.springweb.Controller;

import com.example.springweb.ErrorHandler.ErrorResponse;
import com.example.springweb.ErrorHandler.UserNotFound;
import com.example.springweb.Kafka.JsonKafkaProducer;
import com.example.springweb.Payload.User;
import com.example.springweb.Repository.UserRepository;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/kafka")
public class JsonMessageController {
    private JsonKafkaProducer kafkaProducer;

    @Autowired
    private UserRepository userRepository;

    public JsonMessageController(JsonKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void userRepo(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    // Error Handlers
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(UserNotFound exc){ // Sends a response string to the webbrowser if the user is not found & helps avoid spring to crash
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exc){ // Unlike the previous handler, this one Catches all other exceptions and sends a response string to the webbrowser
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exc.getMessage());
        errorResponse.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }


    // Post Requests
    @PostMapping("/publish") // http://localhost:8080/kafka/publish
    public ResponseEntity<String> publish(@RequestBody User user) {
        try{
            kafkaProducer.sendMessage(user);
            return ResponseEntity.ok("Added user: " + user.toString() + " to kafka & DB");
        }catch (Exception e){
            throw e; // Throws the exception to the error handler handleGenericException
        }
    }


    // Get Requests
    @GetMapping("/CountUsers") // http://localhost:8080/kafka/CountUsers
    public ResponseEntity<String> CountUsers() {
        long count = userRepository.count();
        System.out.println("Number of users in the DB: " + count);
        return ResponseEntity.ok(String.valueOf(count));
    }

    @GetMapping("/ListUsers") // http://localhost:8080/kafka/ListUsers
    public ResponseEntity<String> ListUsers() {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            System.out.println(user.toString());
        }
        return ResponseEntity.ok(users.toString());
    }

    @GetMapping("/ListUser/{id}") // http://localhost:8080/kafka/ListUser/1
    public ResponseEntity<String> ListUser(@PathVariable Long id) {

        if(id > userRepository.count() || id <= 0){ // If the id is out of bounds, throw my custom exception
            throw new UserNotFound("User with id: " + id + " not found");
        }
        else{ // If the id is within bounds, return the user
                User user = userRepository.findById(id).orElseThrow();
                System.out.println(user.toString());
                return ResponseEntity.ok(user.toString());
            }
    }

    // PUT Requests
    @PutMapping("UpdateUser/{id}")
    // http://localhost:8080/kafka/UpdateUser/1  Vill du verkligen söka på id och ändra om hela den? ,
    public ResponseEntity<String> UpdateUser(@PathVariable Long id, @RequestBody User user) {
        User user1 = userRepository.findById(id).orElseThrow();
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(user1);
        kafkaProducer.sendMessage(user1);
        return ResponseEntity.ok("Updated user with id: " + id);
    }


    // Delete Requests
    @DeleteMapping("/DeleteUser/{id}") // http://localhost:8080/kafka/DeleteUser/1
    public ResponseEntity<String> DeleteUser(@PathVariable Long id) {
        if(id > userRepository.count() || id <= 0){ // If the id is out of bounds, throw my custom exception
            throw new UserNotFound("User with id: " + id + " not found");
        }
        else{ // If the id is within bounds, return the user
            userRepository.deleteById(id);
            return ResponseEntity.ok("Deleted user with id: " + id);
        }
    }

    @DeleteMapping("/DeleteAllUsers") // http://localhost:8080/kafka/DeleteAllUsers
    public ResponseEntity<String> DeleteAllUsers() {
        userRepository.deleteAll();
        return ResponseEntity.ok("Deleted all users");
    }






}



