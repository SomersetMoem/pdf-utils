package com.pdf;

import com.pdf.exceptions.PdfException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public final class PdfUtils implements AutoCloseable {
    private final PDDocument document;

    private PdfUtils(PDDocument document) {
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
        if (!file.exists()) {
            throw new PdfException("Файл не найден: " + file.getAbsolutePath(), new IOException());
        }

        if (!file.canRead()) {
            throw new PdfException("Нет доступа к файлу: " + file.getAbsolutePath(), new SecurityException());
        }

        try {
            PDDocument document = Loader.loadPDF(file);
            return new PdfUtils(document);
        } catch (IOException e) {
            throw new PdfException("Ошибка при чтении файла: " + file.getAbsolutePath(), e);
        }
    }


    /**
     * Создаёт экземпляр PdfUtils из пути к файлу.
     *
     * @param path Путь к файлу PDF.
     * @return PdfUtils экземпляр.
     * @throws PdfException если не удалось создать PDF из файла.
     */
    public static @NotNull PdfUtils fromPath(@NotNull Path path) {
        return fromFile(path.toFile());
    }

    /**
     * Создаёт экземпляр PdfUtils из пути к файлу.
     *
     * @param path Путь к файлу PDF.
     * @return PdfUtils экземпляр.
     * @throws PdfException если не удалось создать PDF из файла.
     */
    public static @NotNull PdfUtils fromPath(@NotNull String path) {
        return fromFile(new File(path));
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
            PDDocument document = Loader.loadPDF(inputStream.readAllBytes());
            return new PdfUtils(document);
        } catch (IOException e) {
            throw new PdfException("Ошибка при чтении из потока", e);
        }
    }


    /**
     * Создаёт экземпляр PdfUtils из массива байтов.
     *
     * @param bytes Массив байтов PDF.
     * @return PdfUtils экземпляр.
     * @throws PdfException если не удалось создать PDF из массива байтов.
     */
    public static @NotNull PdfUtils fromBytes(byte[] bytes) {
        try {
            PDDocument document = Loader.loadPDF(bytes);
            return new PdfUtils(document);
        } catch (IOException e) {
            throw new PdfException("Ошибка при чтении из массива байтов", e);
        }
    }

    /**
     * Закрыть документ и освободить ресурсы.
     * Этот метод должен быть вызван после завершения работы с PDF.
     *
     * @throws IOException если произошла ошибка при закрытии документа.
     */
    @Override
    public void close() throws IOException {
        document.close();
    }

    public PDDocument getDocument() {
        return document;
    }
}