package com.example;

import com.pdf.PdfUtils;
import com.pdf.extractors.PdfTextExtractor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PdfTest {
    private PdfTextExtractor textExtractor;
    private static final Path PDF_PATH_CLASS = Path.of("src/test/resources/test.data/testPdf.pdf");
    private static final String PDF_PATH = "src/test/resources/test.data/testPdf.pdf";

    @Test
    public void fromInputStream_shouldParsePdfCorrectly() throws IOException {
        byte[] pdfBytes = Files.readAllBytes(PDF_PATH_CLASS);
        try (InputStream inputStream = new ByteArrayInputStream(pdfBytes)) {
            PdfUtils pdfUtils = PdfUtils.fromInputStream(inputStream);
            PdfTextExtractor extractor = new PdfTextExtractor(pdfUtils.getDocument());
            String text = extractor.getAllText();
            Assertions.assertTrue(text.contains("Этапы. Угрозы. Стратегии"));
            Assertions.assertTrue(text.contains("Арт-директор Алексей Богомолов"));
        }
    }

    @Test
    public void fromBytes_shouldParsePdfCorrectly() throws IOException {
        byte[] pdfBytes = Files.readAllBytes(PDF_PATH_CLASS);
        PdfUtils pdfUtils = PdfUtils.fromBytes(pdfBytes);
        PdfTextExtractor extractor = new PdfTextExtractor(pdfUtils.getDocument());
        String text = extractor.getAllText();
        Assertions.assertTrue(text.contains("Этапы. Угрозы. Стратегии"));
        Assertions.assertTrue(text.contains("Арт-директор Алексей Богомолов"));
    }

    @Test
    public void fromPathTest() {
        PdfUtils pdfUtils = PdfUtils.fromPath(PDF_PATH);
        textExtractor = new PdfTextExtractor(pdfUtils.getDocument());
        String allText = textExtractor.getAllText();
        Assertions.assertTrue(allText.contains("Этапы. Угрозы. Стратегии"));
        Assertions.assertTrue(allText.contains("Арт-директор Алексей Богомолов"));
    }

    @Test
    public void fromPathClassPathTest() {
        Path pdfPath = Paths.get(PDF_PATH);
        PdfUtils pdfUtils = PdfUtils.fromPath(pdfPath);
        PdfTextExtractor textExtractor = new PdfTextExtractor(pdfUtils.getDocument());
        String allText = textExtractor.getAllText();
        Assertions.assertTrue(allText.contains("Этапы. Угрозы. Стратегии"));
        Assertions.assertTrue(allText.contains("Арт-директор Алексей Богомолов"));
    }

    @Test
    public void fromFileTest() {
        PdfUtils pdfUtils = PdfUtils.fromFile(new File(PDF_PATH));
        textExtractor = new PdfTextExtractor(pdfUtils.getDocument());
        String allText = textExtractor.getAllText();
        Assertions.assertTrue(allText.contains("Этапы. Угрозы. Стратегии"));
        Assertions.assertTrue(allText.contains("Арт-директор Алексей Богомолов"));
    }
}