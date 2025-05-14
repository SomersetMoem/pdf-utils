# 📄 pdf-utils

Утилитная Java-библиотека для извлечения текста, метаданных и другой информации из PDF-документов с использованием Apache PDFBox.

## 📦 Установка

Добавьте зависимость в ваш `pom.xml`:

```xml
<dependency>
    <groupId>com.pdf</groupId>
    <artifactId>pdf-utils</artifactId>
    <version>1.0</version>
</dependency>
```

🚀 Быстрый старт
```
File file = new File("example.pdf");
PdfUtils pdfUtils = PdfUtils.fromFile(file);

// Извлечение текста
String text = pdfUtils.getTextExtractor().getAllText();
List<String> parts = pdfUtils.getTextExtractor().getSplitPdfText("Chapter");

// Извлечение метаданных
PdfMetaDataExtractor meta = new PdfMetaDataExtractor(pdfUtils.getDocument());
String title = meta.getTitle();
boolean encrypted = meta.isEncrypted();
```
