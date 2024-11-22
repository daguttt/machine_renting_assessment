package org.example.entities;

public class Client {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;

    public Client() {
    }

    public Client(String fullName, String email, String phoneNumber, String address) {
        this(null, fullName, email, phoneNumber, address);
    }

    public Client(Long id, String fullName, String email, String phoneNumber, String address) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}
