package com.pdf;

import com.pdf.extractors.PdfImageExtractor;
import com.pdf.extractors.PdfMetaDataExtractor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PdfImageExtractorTest {
    private final static String PDF_PATH = "src/test/resources/test.data/testPdf.pdf";
    private PdfImageExtractor imageExtractor;

    @BeforeEach
    public void setUp() {
        PdfUtils pdfUtils = PdfUtils.fromPath(PDF_PATH);
        imageExtractor = new PdfImageExtractor(pdfUtils.getDocument());
    }

    @Test
    public void getTotalImageCountTest() {
        int totalImageCount = imageExtractor.getTotalImageCount();
        Assertions.assertEquals(2, totalImageCount);
    }
}
