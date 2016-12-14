package com.khai.notifier.Parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.Reader;
import java.util.List;

/**
 * Created by inokentii on 13.12.16.
 */
public class ParserCSV {

    private Reader file;
    private String[] headers;
    private List<CSVRecord> records;
    private CSVFormat format;
    private CSVParser parser;

    public ParserCSV(Reader file, String[] headers) {
        this.file = file;
        this.headers = headers;
        this.format = CSVFormat.DEFAULT;

        if (this.headers != null) {
            this.format.withHeader(this.headers);
        }
    }

    public ParserCSV(Reader file) {
        this(file, null);
    }

    public List<CSVRecord> get() {
        try {
            this.parser = new CSVParser(this.file, this.format);
            this.records = parser.getRecords();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return this.records;
    }
}
