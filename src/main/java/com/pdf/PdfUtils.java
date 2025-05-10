package com.pdf;

import com.codeborne.pdftest.PDF;

import java.util.Calendar;

import static com.pdf.DateUtils.formatCalendarDate;

public class PdfUtils {
    private final PDF pdf;

    public PdfUtils(PDF pdf) {
        this.pdf = pdf;
    }

    public String getAuthor() {
        return pdf.author;
    }

    public String getCreator() {
        return pdf.creator;
    }

    public String getFormattedCreationDate() {
        return formatCalendarDate(pdf.creationDate);
    }

    public String getKeywords() {
        return pdf.keywords;
    }

    public String getProducer() {
        return pdf.producer;
    }

    public String getSubject() {
        return pdf.subject;
    }

    public String getTitle() {
        return pdf.title;
    }

    public boolean isEncrypted() {
        return pdf.encrypted;
    }

    public boolean isSigned() {
        return pdf.signed;
    }

    public String getSignerName() {
        return pdf.signerName;
    }

    public Calendar getSignatureTime() {
        return pdf.signatureTime;
    }

    public String getSignatureTimeToString() {
        return pdf.signatureTime != null ? pdf.signatureTime.toString() : null;
    }

    public String getFormattedSignatureTime() {
        return formatCalendarDate(pdf.signatureTime);
    }
}

//Отличный класс для работы с PDF-документами! Вот несколько идей, что еще можно добавить в ваш класс PdfUtils для расширения его функциональности:
//
//Методы для работы с текстом и содержимым:
//
//Метод для извлечения всего текста: public String extractAllText()
//Метод для поиска текста: public boolean containsText(String searchText)
//Метод для получения количества страниц: public int getNumberOfPages()
//
//
//Работа с метаданными:
//
//Метод для получения всех метаданных одновременно: public Map<String, String> getAllMetadata()
//Метод для проверки наличия конкретного метаданного: public boolean hasMetadata(String key)
//
//
//Информация о безопасности:
//
//Методы для получения уровня шифрования: public String getEncryptionLevel()
//Метод для проверки разрешений: public boolean isEditingAllowed(), isPrintingAllowed() и т.д.
//
//
//Работа с аннотациями и комментариями:
//
//Методы для извлечения комментариев: public List<String> getComments()
//Методы для работы с аннотациями: public List<Annotation> getAnnotations()
//
//
//Утилиты для изображений:
//
//Метод для извлечения всех изображений: public List<BufferedImage> extractImages()
//Метод для подсчета изображений: public int countImages()
//
//
//Работа с формами:
//
//Методы для определения, содержит ли PDF формы: public boolean hasAcroForm()
//Методы для извлечения полей форм: public List<FormField> getFormFields()
//
//
//Валидация и проверки:
//
//Метод для проверки соответствия PDF/A: public boolean isPdfA()
//Метод для проверки цифровой подписи: public boolean isValidSignature()
//
//
//Статистика документа:
//
//Метод для получения размера файла: public long getFileSize()
//Метод для анализа структуры (количество шрифтов и т.д.): public DocumentStatistics getStatistics()
//
//
//Поддержка работы с закладками:
//
//Методы для извлечения закладок: public List<Bookmark> getBookmarks()
//
//
//Вспомогательные методы для строкового представления:
//
//Метод для получения краткой информации о документе: public String getSummary()
//Метод для красивого вывода метаданных: public String prettyPrintMetadata()
//
//
//Методы для сравнения PDF-документов:
//
//public boolean isSameDocument(PDF anotherPdf)
//public DocumentDifference compareTo(PDF anotherPdf)
//
//
//Работа с датами создания/модификации:
//
//public Calendar getModificationDate()
//public String getFormattedModificationDate()
//public long getDocumentAgeInDays()
//
//
//Методы для конвертации и извлечения:
//
//public String extractTextFromPage(int pageNumber)
//public BufferedImage renderPageAsImage(int pageNumber)

//com.pdf
//├── PdfUtils.java (главный фасад)
//├── extractors
//│   ├── PdfMetadataExtractor.java
//│   ├── PdfTextExtractor.java
//│   └── PdfImageExtractor.java
//├── analyzers
//│   ├── PdfSecurityAnalyzer.java
//│   └── PdfStructureAnalyzer.java
//├── handlers
//│   ├── PdfFormHandler.java
//│   ├── PdfBookmarkManager.java
//│   └── PdfAnnotationManager.java
//├── utils
//│   └── DateUtils.java
//└── models
//    ├── PdfStatistics.java
//    ├── FormField.java
//    ├── PdfAnnotation.java
//    ├── PdfBookmark.java
//    └── PdfDifference.java
