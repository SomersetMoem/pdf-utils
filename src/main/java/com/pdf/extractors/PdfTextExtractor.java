package com.pdf.extractors;

import com.codeborne.pdftest.PDF;

public class PdfTextExtractor {
    private final PDF pdf;

    public PdfTextExtractor(PDF pdf) {
        this.pdf = pdf;
    }

   public String getAllText() {
        return pdf.text;
   }

   public String getAllTextFormated() {
        return pdf.text.
   }
}
