package com.khai.notifier.Models.User.UserFactory;

import com.khai.notifier.Models.User.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inokentii on 17.12.16.
 */
public class UserFactory {
    public static User create(String[] record) {
        return new User(record);
    }

    public static List createList(String[][] records) {
        List<User> list = new ArrayList<User>();

        for (String[] record: records) {
            list.add(UserFactory.create(record));
        }

        return list;
    }
}
