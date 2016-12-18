package com.khai.notifier.Managers.Parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.Reader;
import java.util.List;

/**
 * Parser responsible for getting data from CSV file
 */
public class ParserCSV extends Parser {

    private Reader file;
    private List<CSVRecord> records;
    private CSVFormat format;

    /**
     * @param file CSV file where data saved
     * @param headers columns names
     */
    private ParserCSV(Reader file, String[] headers) {
        this.file = file;
        String[] headers1 = headers;
        this.format = CSVFormat.DEFAULT;

        if (headers1 != null) {
            this.format.withHeader(headers1);
        }
    }

    /**
     * @param file Stream of file where data saved
     */
    public ParserCSV(Reader file) {
        this(file, null);
    }

    /**
     * Parses rows (records)
     */
    private void getRecords() {
        try {
            CSVParser parser = new CSVParser(this.file, this.format);
            this.records = parser.getRecords();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Converts rows to arrays of strings
     * @return 2D string array of data
     */
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
