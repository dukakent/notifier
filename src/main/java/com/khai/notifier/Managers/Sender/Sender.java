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
     * @param message message to send
     * @param where some contacts of user which receives the message
     */
    public abstract void send(Message message, String where);
}
