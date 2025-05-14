package com.pdf.extractors;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pdf.utils.DateUtils.formatCalendarDate;

/**
 * Утилита для извлечения метаданных из PDF-документа.
 */
public class PdfMetaDataExtractor {
    private final PDDocument pdf;

    public PdfMetaDataExtractor(PDDocument pdf) {
        this.pdf = pdf;
    }

    /**
     * @return Заголовок документа, если указан
     */
    public String getTitle() {
        return pdf.getDocumentInformation().getTitle();
    }

    /**
     * @return Автор документа
     */
    public String getAuthor() {
        return pdf.getDocumentInformation().getAuthor();
    }

    /**
     * @return Тема документа
     */
    public String getSubject() {
        return pdf.getDocumentInformation().getSubject();
    }

    /**
     * @return Ключевые слова документа
     */
    public String getKeywords() {
        return pdf.getDocumentInformation().getKeywords();
    }

    /**
     * @return Программа/система, сгенерировавшая PDF
     */
    public String getCreator() {
        return pdf.getDocumentInformation().getCreator();
    }

    /**
     * @return Программа, использованная для генерации PDF
     */
    public String getProducer() {
        return pdf.getDocumentInformation().getProducer();
    }

    /**
     * @return Дата создания документа (форматированная)
     */
    public String getCreationDate() {
        return formatCalendarDate(pdf.getDocumentInformation().getCreationDate());
    }

    /**
     * @return Дата последнего изменения документа (форматированная)
     */
    public String getModificationDate() {
        return formatCalendarDate(pdf.getDocumentInformation().getModificationDate());
    }

    /**
     * @return Все доступные метаданные в виде Map
     */
    public Map<String, String> getAllMetadata() {
        return pdf.getDocumentInformation().getCOSObject().entrySet().stream()
                .filter(e -> e.getKey() != null && e.getValue() != null)
                .collect(Collectors.toMap(
                        e -> e.getKey().getName(),
                        e -> e.getValue().toString(),
                        (a, b) -> b,
                        LinkedHashMap::new
                ));
    }

    /**
     * Проверка наличия метаданных по ключу
     *
     * @param key Ключ (например, "Author")
     * @return true, если значение присутствует
     */
    public boolean hasMetadata(String key) {
        return pdf.getDocumentInformation().getCOSObject().containsKey(COSName.getPDFName(key));
    }

    /**
     * Получить пользовательские метаданные по ключу
     *
     * @param key имя поля
     * @return значение или null
     */
    public String getCustomMetadata(String key) {
        return Optional.ofNullable(pdf.getDocumentInformation().getCOSObject().getDictionaryObject(COSName.getPDFName(key)))
                .map(Object::toString)
                .orElse(null);
    }

    /**
     * @return true, если документ зашифрован
     */
    public boolean isEncrypted() {
        return pdf.isEncrypted();
    }

    /**
     * @return true, если документ защищён паролем (ограничения на чтение)
     */
    public boolean isPasswordProtected() {
        if (!pdf.isEncrypted()) return false;
        AccessPermission permission = pdf.getCurrentAccessPermission();
        return !permission.canExtractContent();
    }

    /**
     * @return Объект с разрешениями доступа (печать, копирование и т.п.)
     */
    public AccessPermission getAccessPermissions() {
        return pdf.getCurrentAccessPermission();
    }
}
