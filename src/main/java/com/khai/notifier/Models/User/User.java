package com.khai.notifier.Models.User;

/**
 * User model
 */
public class User {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String login;
    private String password;

    /**
     * @param attrs Array of stringified attributes
     */
    User(String[] attrs) {
        this.firstName = attrs[0];
        this.lastName = attrs[1];
        this.phone = attrs[2];
        this.email = attrs[3];
        this.login = attrs[4];
        this.password = attrs[5];
    }

    /**
     * @return users's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return users's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return users's login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @return user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return user's phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone new user's phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email new user's email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
