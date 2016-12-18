package com.khai.notifier.Managers.Parser;

/**
 * Abstract parser entity responsible for getting values from files with different format (.csv, .xlsx, .json)
 */
public abstract class Parser {
    public abstract String[][] parse();
}
