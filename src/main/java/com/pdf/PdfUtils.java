package com.pdf;

import com.codeborne.pdftest.PDF;
import com.pdf.exception.PdfException;
import com.pdf.extractors.PdfTextExtractor;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public final class PdfUtils {
    private final PDF document;

    private PdfUtils(PDF document) {
        this.document = document;
    }

    /**
     * Создаёт экземпляр PdfUtils из файла.
     *
     * @param file Файл PDF.
     * @return PdfUtils экземпляр.
     * @throws PdfException если не удалось создать PDF из файла.
     */
    public static @NotNull PdfUtils fromFile(@NotNull File file) {
        try {
            PDF pdf = new PDF(file);
            return new PdfUtils(pdf);
        } catch (FileNotFoundException e) {
            throw new PdfException("Файл не найден: " + file.getName(), e);
        } catch (IOException e) {
            throw new PdfException("Ошибка при чтении файла: " + file.getName(), e);
        } catch (Exception e) {
            throw new PdfException("Не удалось создать PDF из файла: " + file.getName(), e);
        }
    }

    /**
     * Создаёт экземпляр PdfUtils из потока.
     *
     * @param inputStream Входящий поток.
     * @return PdfUtils экземпляр.
     * @throws PdfException если не удалось создать PDF из потока.
     */
    public static @NotNull PdfUtils fromInputStream(@NotNull InputStream inputStream) {
        try {
            PDF pdf = new PDF(inputStream);
            return new PdfUtils(pdf);
        } catch (IOException e) {
            throw new PdfException("Ошибка при чтении из потока", e);
        } catch (Exception e) {
            throw new PdfException("Не удалось создать PDF из потока", e);
        }
    }

    /**
     * Получить текстовый экстрактор для работы с PDF.
     *
     * @return Экстрактор текста.
     */
    public PdfTextExtractor getTextExtractor() {
        return new PdfTextExtractor(document);
    }
}
