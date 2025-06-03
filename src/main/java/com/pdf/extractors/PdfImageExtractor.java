package com.pdf.extractors;

import com.pdf.exceptions.PdfException;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для извлечения изображений из PDF-документа.
 */
public class PdfImageExtractor extends BaseExtractor {

    /**
     * Конструктор.
     *
     * @param document объект PDF-документа
     */
    public PdfImageExtractor(PDDocument document) {
        super(document);
    }

    /**
     * Извлечь все изображения из всех страниц документа.
     *
     * @return список всех изображений
     */
    public List<BufferedImage> extractAllImages() {
        List<BufferedImage> images = new ArrayList<>();
        for (PDPage page : document.getPages()) {
            images.addAll(extractImagesFromResources(page.getResources()));
        }
        return images;
    }

    /**
     * Извлечь изображения с определённой страницы.
     *
     * @param pageNumber номер страницы (начиная с 0)
     * @return список изображений на странице
     * @throws PdfException если номер страницы некорректен или произошла ошибка при извлечении
     */
    public List<BufferedImage> extractImagesFromPage(int pageNumber) {
        validatePageNumber(pageNumber);
        PDPage page = document.getPage(pageNumber);
        return extractImagesFromResources(page.getResources());
    }

    /**
     * Извлечь изображения, сгруппированные по страницам.
     *
     * @return карта, где ключ — номер страницы, значение — список изображений на ней
     */
    public Map<Integer, List<BufferedImage>> extractImagesByPages() {
        Map<Integer, List<BufferedImage>> pageImages = new HashMap<>();
        PDPageTree pages = document.getPages();
        int index = 0;
        for (PDPage page : pages) {
            List<BufferedImage> images = extractImagesFromResources(page.getResources());
            if (!images.isEmpty()) {
                pageImages.put(index, images);
            }
            index++;
        }
        return pageImages;
    }

    /**
     * Получить общее количество изображений в документе.
     *
     * @return количество изображений
     */
    public int getTotalImageCount() {
        return extractAllImages().size();
    }

    /**
     * Сохранить все изображения в указанную директорию.
     * Названия файлов будут иметь вид page_X_image_Y.png
     *
     * @param outputDir директория, куда сохраняются изображения (полный путь)
     * @throws PdfException если не удалось сохранить изображение
     */
    public void saveAllImagesToDirectory(File outputDir) {
        Map<Integer, List<BufferedImage>> imagesByPages = extractImagesByPages();

        if (!outputDir.isDirectory()) {
            boolean created = outputDir.mkdirs();
            if (!created) {
                throw new PdfException("Не удалось создать директорию: " + outputDir.getAbsolutePath());
            }
        }

        for (Map.Entry<Integer, List<BufferedImage>> entry : imagesByPages.entrySet()) {
            int page = entry.getKey();
            List<BufferedImage> images = entry.getValue();
            for (int i = 0; i < images.size(); i++) {
                BufferedImage img = images.get(i);
                File outFile = new File(outputDir, String.format("page_%d_image_%d.png", page + 1, i + 1));
                try {
                    ImageIO.write(img, "png", outFile);
                } catch (IOException e) {
                    throw new PdfException("Ошибка при сохранении изображения страницы " + (page + 1) + ": " + e.getMessage(), e);
                }
            }
        }
    }

    /**
     * Вспомогательный метод для извлечения изображений из ресурсов страницы.
     *
     * @param resources ресурсы страницы
     * @return список изображений
     * @throws PdfException если произошла ошибка при извлечении
     */
    private List<BufferedImage> extractImagesFromResources(PDResources resources) {
        List<BufferedImage> images = new ArrayList<>();
        if (resources == null) {
            return images;
        }

        try {
            for (COSName xObjectName : resources.getXObjectNames()) {
                PDXObject xObject = resources.getXObject(xObjectName);
                if (xObject instanceof PDImageXObject) {
                    images.add(((PDImageXObject) xObject).getImage());
                }
            }
        } catch (IOException e) {
            throw new PdfException("Ошибка при извлечении изображений из ресурсов страницы", e);
        }

        return images;
    }
}
