package com.pdf;

import com.codeborne.pdftest.PDF;
import com.pdf.extractors.PdfTextExtractor;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public final class PdfUtils {
    private final PDF document;

    private PdfUtils(PDF document) {
        this.document = document;
    }

    public static void main(String[] args) {
        File file = null;
        PdfUtils pdf = PdfUtils.fromFile(file);
    }

    public static @NotNull PdfUtils fromFile(@NotNull File file) {
        try {
            PDF pdf = new PDF(file);
            return new PdfUtils(pdf);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать PDF из файла: " + file.getName(), e);
        }
    }

    public static @NotNull PdfUtils fromInputStream(@NotNull InputStream inputStream) {
        try {
            PDF pdf = new PDF(inputStream);
            return new PdfUtils(pdf);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать PDF из потока", e);
        }
    }

    public PdfTextExtractor getTextExtractor() {
        return new PdfTextExtractor(document);
    }
}