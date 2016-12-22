package com.khai.notifier.Managers.Output;

/**
 * Output class responsible for viewing some information
 */
public class Output {

    /**
     * Shows success messages
     * @param message message to show
     */
    public static void success(String message) {
        System.out.println("SUCCESS: " + message);
    }

    /**
     * Shows info messages
     * @param message message to show
     */
    public static void info(String message) {
        System.out.println("INFO: " + message);
    }

    /**
     * Shows warning messages
     * @param message message to show
     */
    public static void warning(String message) {
        System.out.println("WARNING: " + message);
    }

    /**
     * Shows error messages
     * @param message message to show
     */
    public static void error(String message) {
        System.out.println("ERROR: " + message);
    }
}
