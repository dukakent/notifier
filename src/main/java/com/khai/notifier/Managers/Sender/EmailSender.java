package com.khai.notifier.Managers.Sender;

import com.khai.notifier.Managers.Output.Output;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Properties;

/**
 * Email sender use smtp email protocol.
 * Using ssl secured connection.
 * Connected to port 465.
 * EmailSender must have username and password of existing user on gmail.com
 */
public class EmailSender {

    private Properties props;
    private Session session;
    private Message mimeMessage;

    public EmailSender(Properties props) {
        this.props = props;
    }

    private boolean validateMaxSize(String text) {
        int maxLength = Integer.parseInt(props.getProperty("email.text.maxSize"));
        return text.getBytes().length <= maxLength;
    }

    public void openSMTP() {
        session = Session.getDefaultInstance(props);
        mimeMessage = new MimeMessage(session);
    }

    public void openSMTP(final String username, final String password) {
        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        mimeMessage = new MimeMessage(session);
    }

    /* TODO: implement POP3 protocol */
    public void openPOP() {

    }

    public void from(String email) {
        try {
            mimeMessage.setFrom(new InternetAddress(email));
        } catch(Exception e) {
            Output.error(e.getMessage());
        }
    }

    public void to(String email) {
        try {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        } catch (Exception e) {
            Output.error(e.getMessage());
        }
    }

    public void addRecipient(String email) {
        try {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        } catch(Exception e) {
            Output.error(e.getMessage());
        }
    }

    public void addCC(String email) {
        try {
            mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(email));
        } catch(Exception e) {
            Output.error(e.getMessage());
        }
    }

    public void send(com.khai.notifier.Models.Message.Message message) {

        boolean isSizeValid = validateMaxSize(message.getText());

        if (!isSizeValid) {
            Output.error("The message size is more then " + props.getProperty("email.text.maxSize") + "B");
        }

        try {
            mimeMessage.setSubject(message.getSubject());
            mimeMessage.setContent(message.getText(), "text/html");

            Transport.send(mimeMessage);

            Output.info("Sending email...");
            Output.info("From: " + Arrays.toString(mimeMessage.getFrom()));
            Output.info("To: " + Arrays.toString(mimeMessage.getAllRecipients()));
            Output.info("Subject: " + message.getSubject());
            Output.info("Body: " + message.getText());


        } catch (MessagingException e) {
            Output.error("Sending email has failed");
            throw new RuntimeException(e);
        }

        Output.success("Message was sent");
    }
}
