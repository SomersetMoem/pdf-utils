package com.pdf.extractors;

import com.pdf.exceptions.PdfException;
import org.apache.pdfbox.pdmodel.PDDocument;

public class BaseExtractor {
    protected final PDDocument document;

    public BaseExtractor(PDDocument document) {
        this.document = document;
    }

    /**
     * Проверяет корректность номера страницы
     *
     * @param pageNumber номер страницы для проверки
     * @throws PdfException если номер страницы некорректный
     */
    protected void validatePageNumber(int pageNumber) {
        int pageCount = getPageCount();
        if (pageNumber < 0 || pageNumber > pageCount) {
            throw new PdfException(
                    String.format("Некорректный номер страницы: %d. Документ содержит %d страниц",
                            pageNumber, pageCount));
        }
    }

    /**
     * Проверяет корректность диапазона страниц
     *
     * @param startPage начальная страница
     * @param endPage   конечная страница
     * @throws PdfException если диапазон страниц некорректный
     */
    protected void validatePageRange(int startPage, int endPage) {
        int pageCount = getPageCount();
        if (startPage < 1 || startPage > pageCount) {
            throw new PdfException(
                    String.format("Некорректный номер начальной страницы: %d. Документ содержит %d страниц",
                            startPage, pageCount));
        }
        if (endPage < 1 || endPage > pageCount) {
            throw new PdfException(
                    String.format("Некорректный номер конечной страницы: %d. Документ содержит %d страниц",
                            endPage, pageCount));
        }
        if (startPage > endPage) {
            throw new PdfException(
                    String.format("Некорректный диапазон страниц: от %d до %d", startPage, endPage));
        }
    }

    /**
     * Получить количество страниц в PDF-документе
     *
     * @return количество страниц
     */
    public int getPageCount() {
        return document.getNumberOfPages();
    }
}
