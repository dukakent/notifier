package com.khai.notifier.Models.User;

import java.util.ArrayList;
import java.util.List;

public class UserFactory {
    private static User create(String[] record) {
        return new User(record);
    }

    public static List<User> createList(String[][] records) {
        List<User> list = new ArrayList<User>();

        for (String[] record : records) {
            list.add(UserFactory.create(record));
        }

        return list;
    }
}
