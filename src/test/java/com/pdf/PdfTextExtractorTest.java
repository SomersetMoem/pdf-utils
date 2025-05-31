package com.pdf;

import com.pdf.extractors.PdfTextExtractor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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

    @Test
    public void getTextByPagesTest() {
        String text = textExtractor.getTextByPages(3);
        Assertions.assertTrue(text.contains("Эту книгу хорошо дополняют:"));
        Assertions.assertFalse(text.contains("Арт-директор Алексей Богомолов"));
    }

    @Test
    public void getTextByBetweenPagesTest() {
        String text = textExtractor.getTextByPages(3, 4);
        Assertions.assertTrue(text.contains("Эту книгу хорошо дополняют:"));
        Assertions.assertTrue(text.contains("это невероятно подробный гид,"));
        Assertions.assertFalse(text.contains("Арт-директор Алексей Богомолов"));
    }

    @Test
    public void getTextMapPagesTest() {
        Map<Integer, String> textByAllPages = textExtractor.getTextByAllPages();
        Assertions.assertTrue(textByAllPages.get(3).contains("Эту книгу хорошо дополняют:"));
        Assertions.assertFalse(textByAllPages.get(3).contains("это невероятно подробный гид,"));
        Assertions.assertTrue(textByAllPages.get(4).contains("это невероятно подробный гид,"));
    }

    @Test
    public void isContainsTextTest() {
        Assertions.assertTrue(textExtractor.containsText("Эту книгу хорошо дополняют:"));
        Assertions.assertFalse(textExtractor.containsText("Этого текста в файле точно нет"));

        Assertions.assertTrue(textExtractor.containsText("Эту книгу хорошо дополняют:", 3));
        Assertions.assertFalse(textExtractor.containsText("Эту книгу хорошо дополняют:", 4));

        Assertions.assertTrue(textExtractor.containsTextIgnoreCase("ЭТУ КНИГУ ХОРОШО ДОПОЛНЯЮТ"));
        Assertions.assertFalse(textExtractor.containsText("ЭТУ КНИГУ ХОРОШО ДОПОЛНЯЮТ"));
    }


    @Test
    public void containsPatternTest() {
        Pattern patternExists = Pattern.compile("Эту\\s+книгу\\s+хорошо\\s+дополняют");
        Pattern patternNotExists = Pattern.compile("Этого\\s+текста\\s+точно\\s+нет");

        Assertions.assertTrue(textExtractor.containsPattern(patternExists));
        Assertions.assertFalse(textExtractor.containsPattern(patternNotExists));
        Assertions.assertTrue(textExtractor.containsPattern(patternExists, 3));
        Assertions.assertFalse(textExtractor.containsPattern(patternExists, 4));

        Pattern caseSensitive = Pattern.compile("ЭТУ КНИГУ ХОРОШО ДОПОЛНЯЮТ");
        Assertions.assertFalse(textExtractor.containsPattern(caseSensitive));

        Pattern caseInsensitive = Pattern.compile("Эту книгу хорошо дополняют", Pattern.CASE_INSENSITIVE);
        Assertions.assertTrue(textExtractor.containsPattern(caseInsensitive));
    }
}
