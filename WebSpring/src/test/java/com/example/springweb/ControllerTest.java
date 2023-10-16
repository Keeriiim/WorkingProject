package com.example.springweb;

import com.example.springweb.Controller.JsonMessageController;
import com.example.springweb.ErrorHandler.ErrorResponse;
import com.example.springweb.ErrorHandler.UserNotFound;
import com.example.springweb.Kafka.JsonKafkaProducer;
import com.example.springweb.Payload.User;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;


@SpringBootTest
public class ControllerTest {


    @Mock
    private JsonKafkaProducer kafkaProducer; // Mock your KafkaProducer dependency

    @InjectMocks
    private JsonMessageController jsonMessageController; // Inject your KafkaProducer mock into your controller

    @Test
    public void testHandleException() { // Tests the UserNotFound exception handler
        UserNotFound exception = new UserNotFound("User not found");
        ResponseEntity<ErrorResponse> responseEntity = jsonMessageController.handleException(exception);

        System.out.println(" -- Test Handle Exception --");
        System.out.println(HttpStatus.NOT_FOUND.value() + " = " + responseEntity.getStatusCodeValue());
        assertEquals(HttpStatus.NOT_FOUND.value(), responseEntity.getStatusCodeValue()); // Checks if the status code is the same as the one we set in the exception

        System.out.println("User not found" + " = " + responseEntity.getBody().getMessage());
        assertEquals("User not found", responseEntity.getBody().getMessage()); // Checks if the message is the same as the one we set in the exception
        System.out.println(" -- Finished Test --");

    }

    @Test
    public void testHandleGenericException() { // Tests the generic exception handler
        Exception exception = new Exception("Generic exception");
        ResponseEntity<ErrorResponse> responseEntity = jsonMessageController.handleGenericException(exception);

        System.out.println(" -- Test Handle Exception --");
        System.out.println(HttpStatus.BAD_REQUEST.value() + " = " + responseEntity.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue()); // Checks if the status code is the same as the one we set in the exception

        System.out.println("Generic exception" + " = " + responseEntity.getBody().getMessage());
        assertEquals("Generic exception", responseEntity.getBody().getMessage()); // Checks if the message is the same as the one we set in the exception
        System.out.println(" -- Finished Test --");
    }

   /* @Test
    public void testPublish() throws Exception {
        // Arrange
        User user = new User();
        Mockito.doThrow(new Exception("Some error")).when(kafkaProducer).sendMessage(user); // Mocks the kafkaProducer to throw an exception when sendMessage is called

        // Act
        ResponseEntity<String> responseEntity = jsonMessageController.publish(user);

        System.out.println(responseEntity);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity);
    }

    */



}
