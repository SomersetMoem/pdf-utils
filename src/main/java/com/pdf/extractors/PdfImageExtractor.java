package com.pdf.extractors;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PdfImageExtractor {
    private final PDDocument document;

    public PdfImageExtractor(PDDocument document) {
        this.document = document;
    }

    public List<BufferedImage> extractAllImages() {
        // TODO: Реализация через PDFRenderer и/или PDResources
        return new ArrayList<>();
    }

    public List<BufferedImage> extractImagesFromPage(int pageNumber) {
        // TODO: Извлечь изображения только с указанной страницы
        return new ArrayList<>();
    }

    public Map<Integer, List<BufferedImage>> extractImagesByPages() {
        // TODO: Перебрать страницы и сохранить результат в Map
        return new HashMap<>();
    }

    public int getTotalImageCount() {
        // TODO: Подсчитать изображения во всем документе
        return 0;
    }

    public void saveAllImagesToDirectory(File outputDir) {
        // TODO: Сохранение изображений с нумерацией
    }
}
