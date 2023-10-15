package com.example.springweb.ErrorHandler;

public class UserNotFound extends RuntimeException{ // Customized class to handle errors when you try to find a user that does not exist

    public UserNotFound(String message) { //
        super(message);
    }

    public UserNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFound(Throwable cause) {
        super(cause);
    }
}
