package com.pdf;

import com.pdf.extractors.PdfMetaDataExtractor;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PdfMetaDataExtractorTest {
    private final static String PDF_PATH = "src/test/resources/test.data/testPdf.pdf";
    private PdfMetaDataExtractor metaDataExtractor;

    @BeforeEach
    public void setUp() {
        PdfUtils pdfUtils = PdfUtils.fromPath(PDF_PATH);
        metaDataExtractor = new PdfMetaDataExtractor(pdfUtils.getDocument());
    }

    @Test
    public void testMetadataFields() {
        assertEquals("Ожидаемый заголовок", metaDataExtractor.getTitle());
        assertEquals("Ожидаемый автор", metaDataExtractor.getAuthor());
        assertEquals("Ожидаемая тема", metaDataExtractor.getSubject());
        assertEquals("ключевое1, ключевое2", metaDataExtractor.getKeywords());
        assertEquals("Ожидаемый creator", metaDataExtractor.getCreator());
        assertEquals("Ожидаемый producer", metaDataExtractor.getProducer());

        assertEquals("2024-12-01T12:00:00", metaDataExtractor.getCreationDate());       // подставь ожидаемую дату
        assertEquals("2025-01-15T08:30:00", metaDataExtractor.getModificationDate());   // подставь ожидаемую дату
    }

    @Test
    public void testHasAndGetCustomMetadata() {
        assertTrue(metaDataExtractor.hasMetadata("Author"));
        assertEquals("Ожидаемый автор", metaDataExtractor.getCustomMetadata("Author"));

        assertFalse(metaDataExtractor.hasMetadata("NonExistentKey"));
        assertNull(metaDataExtractor.getCustomMetadata("NonExistentKey"));
    }

    @Test
    public void testAllMetadata() {
        Map<String, String> metadata = metaDataExtractor.getAllMetadata();
        assertNotNull(metadata);
        assertTrue(metadata.containsKey("Title"));
        assertEquals("Ожидаемый заголовок", metadata.get("Title"));
    }

    @Test
    public void testEncryptionStatus() {
        assertFalse(metaDataExtractor.isEncrypted());
        assertFalse(metaDataExtractor.isPasswordProtected());
    }

    @Test
    public void testAccessPermissions() {
        AccessPermission permission = metaDataExtractor.getAccessPermissions();
        assertNotNull(permission);
        assertTrue(permission.canPrint());
        assertTrue(permission.canExtractContent());
    }
}

