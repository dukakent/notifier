package com.khai.notifier.Managers.Sender;

import com.khai.notifier.Models.Message.Message;
import com.khai.notifier.Models.User.User;

/**
 * Created by Yermakov Vladislav on 12/17/2016.
 */
public abstract class Sender {

        public abstract void send(Message message, User user);
}
