package com.khai.notifier;

import com.khai.notifier.Managers.Output.Output;
import com.khai.notifier.Models.Message.Message;
import com.khai.notifier.Managers.Parser.Parser;
import com.khai.notifier.Managers.Parser.ParserCSV;
import com.khai.notifier.Managers.Sender.EmailSender;
import com.khai.notifier.Managers.Sender.SMSSender;
import com.khai.notifier.Managers.Sender.Sender;
import com.khai.notifier.Models.Template.Template;
import com.khai.notifier.Models.User.User;
import com.khai.notifier.Models.User.UserFactory;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Properties;

public class App {

    private static Options options = new Options();
    private static CommandLineParser cmdParser = new DefaultParser();

    private static String EMAIL_NAME = "i.duka@student.csn.khai.edu";
    private static String EMAIL_PASSWORD = "42935304";
    private static String EMAIL_HOST = "smtp-relay.gmail.com";
    private static String EMAIL_PORT = "465";
    private static String EMAIL_MAXSIZE = "25000000";

    public static void main(String[] args) {
        options.addOption("d", "data", true, "Path to file with data");
        options.addOption("e", "send-email", true, "Path to email template");
        options.addOption("s", "send-sms", true, "Path to SMS template");

        Properties propsEmail = new Properties();

        propsEmail.setProperty("email.text.maxSize", EMAIL_MAXSIZE);

        propsEmail.put("mail.smtp.host", EMAIL_HOST);
        propsEmail.put("mail.smtp.socketFactory.port", EMAIL_PORT);
        propsEmail.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        propsEmail.put("mail.smtp.auth", "true");
        propsEmail.put("mail.smtp.port", EMAIL_PORT);

        EmailSender senderEmail = new EmailSender(propsEmail);
        Sender senderSMS = new SMSSender("notifier123456@gmail.com", "123456abcde!");


        try {
            CommandLine cmd = cmdParser.parse(options, args);
            List<User> userList;

            /*
                Options handling
             */
            if (cmd.hasOption("d")) {
                String pathUsers = cmd.getOptionValue("d");

                Reader fileUsers = new FileReader(pathUsers);
                Parser parser = new ParserCSV(fileUsers);

                String[][] records = parser.parse();
                userList = UserFactory.createList(records);
            } else {
                Output.error("-d or --data option is required");
                throw new RuntimeException();
            }

            if (cmd.hasOption("e")) {
                String pathTemplateEmail = cmd.getOptionValue("e");
                Reader fileTemplateEmail = new FileReader(pathTemplateEmail);
                Template templateEmail = new Template(fileTemplateEmail);

                for (User user : userList) {
                    senderEmail.openSMTP(EMAIL_NAME, EMAIL_PASSWORD);
                    senderEmail.from(EMAIL_NAME);

                    Message mess = new Message("Password restore", templateEmail.compile(user));
                    senderEmail.to(user.getEmail());
                    senderEmail.send(mess);
                }
            }

            if (cmd.hasOption("s")) {
                for (User user : userList) {
                    String pathTemplateSMS = cmd.getOptionValue("s");
                    Reader fileTemplateSMS = new FileReader(pathTemplateSMS);
                    Template templateSMS = new Template(fileTemplateSMS);
                    Message mess = new Message("", templateSMS.compile(user));
                    senderSMS.send(mess, user.getPhone());
                }
            }


        } catch (Exception e) {
            Output.error(e.getMessage());
        }

    }
}
