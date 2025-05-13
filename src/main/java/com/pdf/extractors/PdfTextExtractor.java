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

    public List<String> getTextByPages() {
        pdf.
    }
}
