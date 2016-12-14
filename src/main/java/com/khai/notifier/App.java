package com.khai.notifier;

import com.khai.notifier.Parser.ParserCSV;

import java.io.FileReader;
import java.io.Reader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String[] header = {"firstname", "lastname", "phone", "email", "login", "password"};
        ParserCSV parser;
        Reader file;


        try {
            file = new FileReader("/home/inokentii/IdeaProjects/notifier/mocks/users.csv");
            parser = new ParserCSV(file, header);
            parser.get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
