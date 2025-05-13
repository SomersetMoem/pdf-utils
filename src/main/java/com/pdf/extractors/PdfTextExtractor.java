package com.pdf.extractors;

import com.pdf.exceptions.PdfException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PdfTextExtractor {
    private final PDDocument pdf;
    private final String allText;

    public PdfTextExtractor(@NotNull PDDocument pdf) {
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        this.pdf = pdf;
        try {
            allText = pdfTextStripper.getText(pdf);
        } catch (IOException e) {
            throw new PdfException("", new IOException());
        }
    }

    public String getAllText() {
        return this.allText;
    }

    public String getPreparedPdfText() {
        return this.allText.replaceAll("\r\n", " ")
                .replaceAll("\r", " ")
                .replaceAll("\n", " ")
                .replaceAll(" ", "");
    }

    public List<String> getSplitPdfText(String splitSymbol) {
        return Arrays.stream(this.allText.split(splitSymbol))
                .collect(Collectors.toList());
    }

    public String getTextByPages(int page) {
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setStartPage(page);
        stripper.setEndPage(page);
        try {
            return stripper.getText(pdf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получить текст с нескольких страниц (включительно).
     */
    public String getTextByPages(int startPage, int endPage);

    /**
     * Получить количество страниц в PDF-документе.
     */
    public int getPageCount();

    /**
     * Получить текст с указанной страницы, построчно.
     */
    public List<String> getLinesByPage(int page);

    /**
     * Получить текст со всех страниц, построчно.
     */
    public List<String> getAllLines();

    /**
     * Получить текст в виде Map<Номер страницы, Текст этой страницы>.
     */
    public Map<Integer, String> getTextByAllPages();

    /**
     * Получить список всех слов в документе.
     */
    public List<String> getWords();

    /**
     * Получить все абзацы (если разделение возможно по структуре документа).
     */
    public List<String> getParagraphs();

    /**
     * Поиск и извлечение всех предложений, содержащих заданную строку.
     */
    public List<String> findSentencesContaining(String text);

    /**
     * Проверка наличия текста в документе (удобно для assert'ов в тестах).
     */
    public boolean containsText(String text);

    /**
     * Получить текст по регулярному выражению (например, все email'ы).
     */
    public List<String> getTextByRegex(String regex);

    /**
     * Получить текст только с чётных/нечётных страниц.
     */
    public String getTextFromEvenPages();

    /**
     * Удалить спецсимволы (например, невидимые символы, табы и прочее).
     */
    public String getCleanedText();

    /**
     * Получить оригинальный PDDocument, если нужно использовать напрямую.
     */
    public PDDocument getRawDocument();
}
