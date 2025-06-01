package com.pdf;

import com.pdf.extractors.PdfMetaDataExtractor;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class PdfMetaDataExtractorTest {
    private final static String PDF_PATH = "src/test/resources/test.data/testPdf.pdf";
    private PdfMetaDataExtractor metaDataExtractor;

    @BeforeEach
    public void setUp() {
        PdfUtils pdfUtils = PdfUtils.fromPath(PDF_PATH);
        metaDataExtractor = new PdfMetaDataExtractor(pdfUtils.getDocument());
    }

    @Test
    public void getTitleTest() {
        String title = metaDataExtractor.getTitle();
        Assertions.assertEquals("Человек + машина. Новые принципы работы в эпоху искусственного интеллекта", title);
    }

    @Test
    public void getAuthorTest() {
        String author = metaDataExtractor.getAuthor();
        Assertions.assertEquals("Пол Доэрти", author);
    }

    @Test
    public void getSubjectTest() {
        String subject = metaDataExtractor.getSubject();
        Assertions.assertEquals("Руководители компании Accenture Пол Доэрти и Джеймс Уилсон много лет посвятили изучению искусственного интеллекта и влияния технологий на бизнес и общество. Итогом их тридцатилетней работы стало это руководство по переосмыслению бизнес-процессов и созданию инновационных рабочих мест.\n" +
                "Революция искусственного интеллекта не грядет, она уже здесь. Многие проиграют. А в выигрыше окажутся компании, которые стремятся расширить человеческие возможности с помощью машин. Они станут лидерами своей отрасли.", subject);
    }

    @Test
    public void getKeywordsTest() {
        String keywords = metaDataExtractor.getKeywords();
        Assertions.assertEquals("мозг и интеллект; предпринимателю; управление людьми; руководителю; управление компанией; стратегия; инновации; бизнес-процессы", keywords);
    }

    @Test
    public void getCreatorTest() {
        String creator = metaDataExtractor.getCreator();
        Assertions.assertEquals("Adobe InDesign CC 13.1 (Windows)", creator);
    }

    @Test
    public void getProducerTest() {
        String producer = metaDataExtractor.getProducer();
        Assertions.assertEquals("Acrobat Distiller 19.0 (Windows)", producer);
    }

    @Test
    public void getCreationDateTest() {
        String date = metaDataExtractor.getCreationDate();
        Assertions.assertEquals("2019-04-11 09:08:10", date);
    }

    @Test
    public void getModificationDateTest() {
        String date = metaDataExtractor.getModificationDate();
        Assertions.assertEquals("2019-05-28 17:24:43", date);
    }

    @Test
    public void getAllMetadataTest() {
        Map<String, String> allMetadata = metaDataExtractor.getAllMetadata();
        String author = allMetadata.get("Author");
        Assertions.assertEquals("Пол Доэрти", author);
    }

    @Test
    public void hasMetadataTest() {
        boolean hasMD = metaDataExtractor.hasMetadata("Author");
        Assertions.assertTrue(hasMD);
    }

    @Test
    public void isEncryptedTest() {
        boolean isEncrypt = metaDataExtractor.isEncrypted();
        Assertions.assertTrue(isEncrypt);
    }

    @Test
    public void isPasswordProtectedTest() {
        boolean isPassword = metaDataExtractor.isPasswordProtected();
        Assertions.assertFalse(isPassword);
    }

    @Test
    public void getAccessPermissionsTest() {
        AccessPermission accessPermissions = metaDataExtractor.getAccessPermissions();
        Assertions.assertTrue(accessPermissions.isReadOnly());
    }
}