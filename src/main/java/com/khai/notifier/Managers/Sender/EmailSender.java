package com.khai.notifier.Managers.Sender;

import com.khai.notifier.Models.User.User;
import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Email sender use smtp email protocol.
 * Using ssl secured connection.
 * Connected to port 465.
 * EmailSender must have username and password of existing user on gmail.com
 */
public class EmailSender extends Sender {

    private String     username;
    private String     password;
    private Properties props;

    public EmailSender(String username, String password) {
        this.username = username;
        this.password = password;

        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
    }

    @Override
    public void send(com.khai.notifier.Models.Message.Message message, User user) {
        //создаем сессию
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message mimeMessage = new MimeMessage(session);
            //от кого
            mimeMessage.setFrom(new InternetAddress(username));
            //кому
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            //тема сообщения
            mimeMessage.setSubject(message.getSubject());
            //текст
            mimeMessage.setText(message.getText());
            //отправляем сообщение
            Transport.send(mimeMessage);
            System.out.println("Sending email to: " + user.getEmail());
            System.out.println("Email subject: " + message.getSubject());
            System.out.println("Email body: " + message.getText());


        } catch (MessagingException e) {
            System.out.println("Error sending email to user with email: " + user.getEmail());
            throw new RuntimeException(e);
        }
    }
}
