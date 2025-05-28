package com.example;

import com.pdf.PdfUtils;
import com.pdf.extractors.PdfTextExtractor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PdfTest {
    private PdfTextExtractor textExtractor;

    @BeforeEach
    public void setUp() {
        PdfUtils pdfUtils = PdfUtils.fromPath("src/test/resources/test.data/testPdf.pdf");
        textExtractor = new PdfTextExtractor(pdfUtils.getDocument());
    }


    @Test
    public void getAllText_shouldContainTextFromAllPages() {
        String allText = textExtractor.getAllText();
        Assertions.assertTrue(allText.contains("Этапы. Угрозы. Стратегии"));
        Assertions.assertTrue(allText.contains("Арт-директор Алексей Богомолов"));
    }
}