package com.pdf.extractors;

import com.codeborne.pdftest.PDF;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PdfTextExtractor {
    private final PDF pdf;

    public PdfTextExtractor(@NotNull PDF pdf) {
        this.pdf = pdf;
    }

    public String getAllText() {
        return pdf.text;
    }

    public String getPreparedPdfText() {
        return this.pdf.text.replaceAll("\r\n", " ")
                .replaceAll("\r", " ")
                .replaceAll("\n", " ")
                .replaceAll(" ", "");
    }

    public List<String> getSplitPdfText(String splitSymbol) {
        return Arrays.stream(this.pdf.text.split(splitSymbol))
                .collect(Collectors.toList());
    }
}
