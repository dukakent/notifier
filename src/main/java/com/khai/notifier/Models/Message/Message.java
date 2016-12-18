package com.khai.notifier.Models.Message;

/**
 * Created by Yermakov Vladislav on 12/17/2016.
 */
public class Message {

    private String subject;
    private String text;

    public Message(String subject, String text)
    {
        this.subject = subject;
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }
}
