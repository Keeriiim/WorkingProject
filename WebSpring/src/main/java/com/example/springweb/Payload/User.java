package com.example.springweb.Payload;

import jakarta.persistence.*;

@Entity
@Table (name = "users")
public class User {

    @Id
    @GeneratedValue (generator = "users_gen", strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "users_gen", sequenceName = "users_seq", initialValue = 50, allocationSize = 1)
    private Long id;
    private String firstName;
    private String lastName;
    private Long phoneNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return String.format("User [id=%s, firstName=%s, lastName=%s, phoneNumber=%s]", id, firstName, lastName, phoneNumber);
    }

}
