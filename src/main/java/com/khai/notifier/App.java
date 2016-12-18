package com.khai.notifier;

import com.khai.notifier.Models.Message.Message;
import com.khai.notifier.Managers.Parser.Parser;
import com.khai.notifier.Managers.Parser.ParserCSV;
import com.khai.notifier.Managers.Sender.EmailSender;
import com.khai.notifier.Managers.Sender.SMSSender;
import com.khai.notifier.Managers.Sender.Sender;
import com.khai.notifier.Models.Template.Template;
import com.khai.notifier.Models.User.User;
import com.khai.notifier.Models.User.UserFactory.UserFactory;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;

/**
 * Created by inokentii on 13.12.16.
 */
public class App 
{
    public static void main( String[] args ) {
        Options options = new Options();
        CommandLineParser cmdParser = new DefaultParser();
        CommandLine cmd;

        Reader fileUsers;
        Reader fileTemplate;
        Parser parser;
        String[][] records;

        List<User> userList;
        Template template;
        Sender senderEmail = new EmailSender("notifier123456@gmail.com", "123456abcde!");
        Sender senderSMS = new SMSSender("notifier123456@gmail.com", "123456abcde!");

        options.addOption("u", "users", true, "Path to file with user list");
        options.addOption("t", "template", true, "Path to template file");
        options.addOption("e", "send-email", false, "Send email");
        options.addOption("s", "send-sms", false, "Send SMS");

        try {
            cmd = cmdParser.parse(options, args);

            String pathUsers = cmd.getOptionValue("u");
            String pathTemplate = cmd.getOptionValue("t");
            fileUsers = new FileReader(pathUsers);
            fileTemplate = new FileReader(pathTemplate);

            parser = new ParserCSV(fileUsers);
            records = parser.parse();

            userList = UserFactory.createList(records);
            template = new Template(fileTemplate);

            /*
             * Usage
             */
            User user;
            Message mess;

            for (int i = 0; i < userList.size(); i++) {
                user = userList.get(i);
                mess = new Message("Password restore", template.compile(user));

                if (cmd.hasOption("e")) {
                    senderEmail.send(mess, user);
                }

                if (cmd.hasOption("s")) {
                    senderSMS.send(mess, user);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
      
    }
}
