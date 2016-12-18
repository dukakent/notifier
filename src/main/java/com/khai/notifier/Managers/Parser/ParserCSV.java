package com.khai.notifier.Managers.Parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.Reader;
import java.util.List;

/**
 * Created by inokentii on 13.12.16.
 */
public class ParserCSV extends Parser {

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

    private void getRecords() {
        try {
            this.parser = new CSVParser(this.file, this.format);
            this.records = parser.getRecords();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String[][] parse() {
        this.getRecords();

        String[][] list = new String[this.records.size()][];

        for (int i = 0; i < list.length; i++) {
            CSVRecord record = this.records.get(i);
            String[] props = new String[record.size()];

            for (int j = 0; j < props.length; j++) {
                props[j] = record.get(j);
            }

            list[i] = props;
        }

        return list;
    }
}
