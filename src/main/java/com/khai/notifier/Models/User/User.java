package com.khai.notifier.Models.User;

public class User {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String login;
    private String password;

    User(String[] attrs) {
        this.firstName = attrs[0];
        this.lastName = attrs[1];
        this.phone = attrs[2];
        this.email = attrs[3];
        this.login = attrs[4];
        this.password = attrs[5];
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
