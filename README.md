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
String text = pdfUtils.getTextExtractor().getAllText();
String parts = pdfUtils.getMetaDataExtractor().getTitle();
```
