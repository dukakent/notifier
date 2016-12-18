package com.khai.notifier.Managers.File;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * Utility for easy reading text files
 */
public class File {

    /**
     * @param file Open file stream
     * @return string of text in the file
     */
    public static String read(Reader file) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br;
        try {
            br = new BufferedReader(file);
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
                line = br.readLine();
            }

            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return sb.toString();
    }


    /**
     * @param path Path of file to open
     * @return String of text in the file
     * @throws FileNotFoundException
     */
    public static String read(String path) throws FileNotFoundException {
        Reader file = new FileReader(path);
        return File.read(file);
    }
}
