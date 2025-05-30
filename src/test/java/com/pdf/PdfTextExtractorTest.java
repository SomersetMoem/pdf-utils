package com.pdf;

import com.pdf.extractors.PdfTextExtractor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PdfTextExtractorTest {
    private final static String PDF_PATH = "src/test/resources/test.data/testPdf.pdf";
    private PdfTextExtractor textExtractor;

    @BeforeEach
    public void setUp() {
        PdfUtils pdfUtils = PdfUtils.fromPath(PDF_PATH);
        textExtractor = new PdfTextExtractor(pdfUtils.getDocument());
    }


    @Test
    public void getAllTextTest() {
        String allText = textExtractor.getAllText();
        Assertions.assertTrue(allText.contains("Этапы. Угрозы. Стратегии"));
        Assertions.assertTrue(allText.contains("Арт-директор Алексей Богомолов"));
    }

    @Test
    public void getPreparedPdfTextTest() {
        String allText = textExtractor.getAllText();
        Assertions.assertTrue(allText.contains("\r\nЭту книгу хорошо дополняют:\r\n"));
        String allTestPrepared = textExtractor.getPreparedPdfText();
        Assertions.assertFalse(allTestPrepared.contains("\r\nЭту книгу хорошо дополняют:\r\n"));
        Assertions.assertTrue(allTestPrepared.contains("Эту книгу хорошо дополняют:"));
    }

    @Test
    public void getSplitPdfTextTest() {
        List<String> texts = textExtractor.getSplitPdfText("Эту книгу хорошо дополняют:");
        Assertions.assertEquals(2, texts.size());
    }


}
