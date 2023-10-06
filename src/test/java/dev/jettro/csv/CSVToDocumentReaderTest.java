package dev.jettro.csv;

import dev.langchain4j.data.document.Document;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CSVToDocumentReaderTest {

    @Test
    void readCSV() {
        String pathToCsv = "./data/all_brickheadz.csv";
        String fieldName = "name";
        List<Document> documents = CSVToDocumentReader.readCSV(fieldName, pathToCsv);

        assertEquals(124, documents.size());
    }

    @Test
    void readCSV_non_existing() {
        assertThrows(ParseCSVException.class, () ->
                CSVToDocumentReader.readCSV("name", "nonexistent.csv"));
    }

    @Test
    void readCSV_no_header() {
        assertThrows(IllegalArgumentException.class, () ->
                CSVToDocumentReader.readCSV("name", "./no_header.csv"));
    }
}