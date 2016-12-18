package com.khai.notifier.Managers.Sender;

import com.khai.notifier.Models.Message.Message;
import com.khai.notifier.Models.User.User;

/**
 * Abstract class of sender.
 * Children of this class can send messages of different types.
 */
public abstract class Sender {

    /**
     * Send message to user
     *
     * @param message
     * @param user
     */
    public abstract void send(Message message, User user);
}
