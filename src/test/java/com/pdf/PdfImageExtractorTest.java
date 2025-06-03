package com.pdf;

import com.pdf.extractors.PdfImageExtractor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Test
    public void extractAllImagesTest() {
        List<BufferedImage> bufferedImages = imageExtractor.extractAllImages();
        Assertions.assertTrue(bufferedImages.get(0).getHeight() != 0);
    }

    @Test
    public void extractImagesFromPageTest() {
        List<BufferedImage> integerListMap = imageExtractor.extractImagesFromPage(301);
        Assertions.assertFalse(integerListMap.isEmpty());
    }

    @Test
    public void extractImagesByPagesTest() {
        Map<Integer, List<BufferedImage>> integerListMap = imageExtractor.extractImagesByPages();
        Assertions.assertFalse(integerListMap.get(301).isEmpty());
    }

    @Test
    public void saveAllImagesToDirectoryTest() {
        File outputDir = new File("target/test-output/pdf-images");

        if (outputDir.exists()) {
            deleteDirectory(outputDir);
        }

        imageExtractor.saveAllImagesToDirectory(outputDir);

        if (outputDir.exists() && outputDir.isDirectory()) {
            File[] files = outputDir.listFiles();

            if (files != null && files.length > 0) {
                Assertions.assertEquals(2, files.length, "Должно быть создано 2 изображения");

            } else {
                Assertions.fail("Файлы не были созданы!");
            }
        } else {
            Assertions.fail("Директория не была создана!");
        }
    }

    private void deleteDirectory(File dir) {
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            dir.delete();
        }
    }
}
