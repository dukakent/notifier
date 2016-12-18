package com.khai.notifier.Models.Message;

/**
 * Message model, consists of subject and text
 */
public class Message {

    private String subject;
    private String text;

    /**
     * @param subject message subject (title, header)
     * @param text message main text (body)
     */
    public Message(String subject, String text) {
        this.subject = subject;
        this.text = text;
    }

    /**
     * @return message's subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return message's body
     */
    public String getText() {
        return text;
    }
}
