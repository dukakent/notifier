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

public class App {
    public static void main(String[] args) {
        Options options = new Options();
        CommandLineParser cmdParser = new DefaultParser();

        Sender senderEmail = new EmailSender("notifier123456@gmail.com", "123456abcde!");
        Sender senderSMS = new SMSSender("notifier123456@gmail.com", "123456abcde!");

        options.addOption("d", "data", true, "Path to file with data");
        options.addOption("e", "send-email", true, "Path to email template");
        options.addOption("s", "send-sms", true, "Path to SMS template");

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
                Output.error("--data option is required");
                throw new RuntimeException();
            }

            if (cmd.hasOption("e")) {
                for (User user : userList) {
                    String pathTemplateEmail = cmd.getOptionValue("e");
                    Reader fileTemplateEmail = new FileReader(pathTemplateEmail);
                    Template templateEmail = new Template(fileTemplateEmail);
                    Message mess = new Message("Password restore", templateEmail.compile(user));
                    senderEmail.send(mess, user);
                }
            }

            if (cmd.hasOption("s")) {
                for (User user : userList) {
                    String pathTemplateSMS = cmd.getOptionValue("s");
                    Reader fileTemplateSMS = new FileReader(pathTemplateSMS);
                    Template templateSMS = new Template(fileTemplateSMS);
                    Message mess = new Message("", templateSMS.compile(user));
                    senderSMS.send(mess, user);
                }
            }


        } catch (Exception e) {
            Output.error(e.getMessage());
        }

    }
}
