package com.example.springweb;


import com.example.springweb.Payload.User;
import com.example.springweb.Repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DbTest {

    @Autowired
    UserRepository userRepository;

    static User testUser;

    @BeforeEach
    void setUp() {
        System.out.println("\n\nBefore Test");

        // Creates a test user
        this.testUser= new User();
        this.testUser.setFirstName("A");
        this.testUser.setLastName("B");
        this.testUser.setPhoneNumber(123456789L);
        System.out.println("TestUser created!");
        System.out.println("Printing Values from the created User before saving it to the DB");
        System.out.println(testUser.toString()+"\n");
    }

    @AfterEach
    void tearDown() {
        System.out.println("\n\nAfter Test");
       // userRepository.deleteById(this.testUser.getId()); // Deletes the latest test user from the local database
        System.out.println("TestUser deleted from the DB!");
        List<User> users = userRepository.findAll();

        if(users == null || users.isEmpty()) {
            System.out.println(" --- No testUsers in the DB! --- ");
        } else {
            System.out.println("Users in the DB:");
            for (User user : users) {
                System.out.println(user.toString());
            }
        }


    }

    @AfterAll
    static void afterAll() {
        System.out.println("\n All tests are finished!\n\n");
    }

    @Test
    @Order(1)
    void createUser() {
        // A Test User is automatically created from the setUp() method
        System.out.println("\n\n --- Create User Test ---");

        userRepository.save(testUser); // Saves the testUser to the local database

        // Checks if the latest User -> testUser was saved to the local database
        assertNotNull(userRepository.findById(testUser.getId()).get());


    }

    @Test
    @Order(2)
    void updateUser() {
        // A Test User is automatically created from the setUp() method
        System.out.println("\n\n --- Update User Test ---");

        // Get the latest user from the database
        List<User> users = userRepository.findAll();
        System.out.println("You have " + users.size() + " users in the DB, userTest being the latest one.");
        User fetchedUser = users.get(users.size()-1);
        System.out.println("Fetched User: \n" + fetchedUser.toString() + "\n");

       // System.out.println(testUser.toString()); // This will always be null because the DB user needs to be overwritten to this Object!

        assertNotNull(fetchedUser);
        System.out.println("Testing local fetchedUser against the newly saved fetchedUser in DB");

        //Updatera värdet i fetchedUser
        fetchedUser.setFirstName("Niklas");
        userRepository.save(fetchedUser);
        System.out.println("Local Fetched User after change: \n" + fetchedUser.toString());

        //Kontrollera att värdet i DB har ändrats
        assertEquals("Niklas", userRepository.findById(fetchedUser.getId()).get().getFirstName());


    }

    @Test
    @Order(3)
    void removeUser() {

        System.out.println("\n\n --- Delete User Test ---");

        User user2 = new User();
        user2.setFirstName("deletedUser");
        user2.setLastName("deletedUser");
        user2.setPhoneNumber(00000L);
        userRepository.save(user2);

        // Check if the latest User -> user2 was saved to the local database
        assertNotNull(userRepository.findById(user2.getId()).get());

        System.out.println("User2 created!");
        System.out.println(userRepository.findById(user2.getId()).get().toString()); // Prints the user2 object that was stored in the DB

        // Check if the latest User -> user2 was saved to the local database


        userRepository.deleteById(user2.getId()); // Deletes the latest test user from the local database

        // Checks if the latest User -> user2 was deleted from the local database
        assertTrue(userRepository.findById(user2.getId()).isEmpty());


    }
}

