package com.pdf.extractors;

import com.pdf.exceptions.PdfException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Утилита для извлечения текста из PDF-документа.
 */
public class PdfTextExtractor {
    private final PDDocument pdf;
    private final String allText;

    /**
     * Создает экстрактор текста для PDF документа
     *
     * @param pdf PDF документ для обработки
     * @throws PdfException если возникла ошибка при извлечении текста
     */
    public PdfTextExtractor(@NotNull PDDocument pdf) {
        this.pdf = pdf;
        try {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            allText = pdfTextStripper.getText(pdf);
        } catch (IOException e) {
            throw new PdfException("Не удалось извлечь текст из PDF", e);
        }
    }

    /**
     * Получить весь текст PDF документа
     *
     * @return строка, содержащая весь текст документа
     */
    public String getAllText() {
        return this.allText;
    }

    /**
     * Получить подготовленный текст PDF с удалением переносов строк и лишних пробелов
     *
     * @return подготовленный текст
     */
    public String getPreparedPdfText() {
        return this.allText.replaceAll("\r\n", " ")
                .replaceAll("\r", " ")
                .replaceAll("\n", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    /**
     * Разделить текст PDF по указанному разделителю
     *
     * @param splitSymbol символ-разделитель
     * @return список строк после разделения
     */
    public List<String> getSplitPdfText(String splitSymbol) {
        return Arrays.stream(this.allText.split(splitSymbol))
                .collect(Collectors.toList());
    }

    /**
     * Получить текст с указанной страницы
     *
     * @param page номер страницы (начиная с 1)
     * @return текст страницы
     * @throws PdfException если номер страницы некорректный или возникла ошибка доступа
     */
    public String getTextByPages(int page) {
        validatePageNumber(page);

        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setStartPage(page);
        stripper.setEndPage(page);
        try {
            return stripper.getText(pdf);
        } catch (IOException e) {
            throw new PdfException("Ошибка при извлечении текста со страницы " + page, e);
        }
    }

    /**
     * Получить текст с нескольких страниц (включительно)
     *
     * @param startPage начальная страница (начиная с 1)
     * @param endPage   конечная страница (включительно)
     * @return текст указанного диапазона страниц
     * @throws PdfException если номера страниц некорректны или возникла ошибка доступа
     */
    public String getTextByPages(int startPage, int endPage) {
        validatePageRange(startPage, endPage);

        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setStartPage(startPage);
        stripper.setEndPage(endPage);
        try {
            return stripper.getText(pdf);
        } catch (IOException e) {
            throw new PdfException("Ошибка при извлечении текста со страниц " + startPage + "-" + endPage, e);
        }
    }

    /**
     * Получить количество страниц в PDF-документе
     *
     * @return количество страниц
     */
    public int getPageCount() {
        return pdf.getNumberOfPages();
    }

    /**
     * Получить текст в виде Map<Номер страницы, Текст этой страницы>
     *
     * @return карта с текстом каждой страницы
     */
    public Map<Integer, String> getTextByAllPages() {
        Map<Integer, String> pageTextMap = new HashMap<>();
        int pageCount = getPageCount();

        try {
            PDFTextStripper stripper = new PDFTextStripper();
            for (int i = 1; i <= pageCount; i++) {
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                pageTextMap.put(i, stripper.getText(pdf));
            }
        } catch (IOException e) {
            throw new PdfException("Ошибка при извлечении текста со всех страниц", e);
        }

        return pageTextMap;
    }

    /**
     * Проверка наличия текста в документе
     *
     * @param text искомый текст
     * @return true, если текст найден
     */
    public boolean containsText(String text) {
        return allText.contains(text);
    }

    /**
     * Проверка наличия текста в документе без учета регистра
     *
     * @param text искомый текст
     * @return true, если текст найден
     */
    public boolean containsTextIgnoreCase(String text) {
        return allText.toLowerCase().contains(text.toLowerCase());
    }

    /**
     * Проверка наличия текста в документе на конкретной странице
     *
     * @param text       искомый текст
     * @param pageNumber номер страницы (начиная с 1)
     * @return true, если текст найден на указанной странице
     * @throws PdfException если номер страницы некорректный
     */
    public boolean containsText(String text, int pageNumber) {
        validatePageNumber(pageNumber);
        String pageText = getTextByPages(pageNumber);
        return pageText.contains(text);
    }

    /**
     * Поиск текста по регулярному выражению
     *
     * @param pattern регулярное выражение
     * @return true, если найдено совпадение
     */
    public boolean containsPattern(Pattern pattern) {
        return pattern.matcher(allText).find();
    }

    /**
     * Поиск текста по регулярному выражению на конкретной странице
     *
     * @param pattern    регулярное выражение
     * @param pageNumber номер страницы (начиная с 1)
     * @return true, если найдено совпадение на указанной странице
     * @throws PdfException если номер страницы некорректный
     */
    public boolean containsPattern(Pattern pattern, int pageNumber) {
        validatePageNumber(pageNumber);
        String pageText = getTextByPages(pageNumber);
        return pattern.matcher(pageText).find();
    }

    /**
     * Проверяет корректность номера страницы
     *
     * @param pageNumber номер страницы для проверки
     * @throws PdfException если номер страницы некорректный
     */
    private void validatePageNumber(int pageNumber) {
        int pageCount = getPageCount();
        if (pageNumber < 1 || pageNumber > pageCount) {
            throw new PdfException(
                    String.format("Некорректный номер страницы: %d. Документ содержит %d страниц",
                            pageNumber, pageCount), new IOException());
        }
    }

    /**
     * Проверяет корректность диапазона страниц
     *
     * @param startPage начальная страница
     * @param endPage   конечная страница
     * @throws PdfException если диапазон страниц некорректный
     */
    private void validatePageRange(int startPage, int endPage) {
        int pageCount = getPageCount();
        if (startPage < 1 || startPage > pageCount) {
            throw new PdfException(
                    String.format("Некорректный номер начальной страницы: %d. Документ содержит %d страниц",
                            startPage, pageCount), new IOException());
        }
        if (endPage < 1 || endPage > pageCount) {
            throw new PdfException(
                    String.format("Некорректный номер конечной страницы: %d. Документ содержит %d страниц",
                            endPage, pageCount), new IOException());
        }
        if (startPage > endPage) {
            throw new PdfException(
                    String.format("Некорректный диапазон страниц: от %d до %d", startPage, endPage), new IOException());
        }
    }
}
