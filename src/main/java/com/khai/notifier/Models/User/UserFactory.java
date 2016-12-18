package com.khai.notifier.Models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Users' factory responsible for creating instances of Users
 */
public class UserFactory {
    /**
     * Creates single User instance
     * @param record String array of user attributes
     * @return new User instance
     */
    private static User create(String[] record) {
        return new User(record);
    }

    /**
     * Creates array of User instances
     * @param records 2D Array contains many users' attributes
     * @return List of user instances
     */
    public static List<User> createList(String[][] records) {
        List<User> list = new ArrayList<User>();

        for (String[] record : records) {
            list.add(UserFactory.create(record));
        }

        return list;
    }
}
