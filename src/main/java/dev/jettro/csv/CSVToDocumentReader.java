package dev.jettro.csv;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for reading a CSV file and returning a list of documents. The CSV file should have a
 * header and a column with the provided field name. The field name is used to create the document.
 */
public class CSVToDocumentReader {
    private static final Logger logger = LoggerFactory.getLogger(CSVToDocumentReader.class);

    /**
     * Read the provided CSV file and return a list of documents. The field name is used to create the document.
     * @param fieldName name of the field to use for the document
     * @param csvFilePath path to the CSV file
     * @return list of documents
     */
    public static List<Document> readCSV(String fieldName, String csvFilePath) {
        List<Document> legoPuppets = new ArrayList<>();

        try (InputStream is = CSVToDocumentReader.class.getClassLoader().getResourceAsStream(csvFilePath)) {
            if (is == null) {
                throw new ParseCSVException("Cannot find the file: %s".formatted(csvFilePath));
            }

            try (Reader reader = new InputStreamReader(is)) {
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

                for (CSVRecord record : csvParser.getRecords()) {
                    legoPuppets.add(Document.from(record.get(fieldName), Metadata.from(record.toMap())));
                }

            }
        } catch (IOException e) {
            logger.error("Problem during parsing of provided CSV file", e);
        }

        return legoPuppets;
    }
}
