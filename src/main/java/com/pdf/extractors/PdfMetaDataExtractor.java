package com.pdf.extractors;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pdf.utils.DateUtils.formatCalendarDate;

/**
 * Класс для извлечения метаданных из PDF-документа.
 */
public class PdfMetaDataExtractor extends BaseExtractor {

    public PdfMetaDataExtractor(PDDocument document) {
        super(document);
    }

    /**
     * @return Заголовок документа, если указан
     */
    public String getTitle() {
        return document.getDocumentInformation().getTitle();
    }

    /**
     * @return Автор документа
     */
    public String getAuthor() {
        return document.getDocumentInformation().getAuthor();
    }

    /**
     * @return Тема документа
     */
    public String getSubject() {
        return document.getDocumentInformation().getSubject();
    }

    /**
     * @return Ключевые слова документа
     */
    public String getKeywords() {
        return document.getDocumentInformation().getKeywords();
    }

    /**
     * @return Программа/система, сгенерировавшая PDF
     */
    public String getCreator() {
        return document.getDocumentInformation().getCreator();
    }

    /**
     * @return Программа, использованная для генерации PDF
     */
    public String getProducer() {
        return document.getDocumentInformation().getProducer();
    }

    /**
     * @return Дата создания документа (форматированная)
     */
    public String getCreationDate() {
        return formatCalendarDate(document.getDocumentInformation().getCreationDate());
    }

    /**
     * @return Дата последнего изменения документа (форматированная)
     */
    public String getModificationDate() {
        return formatCalendarDate(document.getDocumentInformation().getModificationDate());
    }

    /**
     * @return Все доступные метаданные в виде Map
     */
    public Map<String, String> getAllMetadata() {
        return document.getDocumentInformation().getCOSObject().entrySet().stream()
                .filter(e -> e.getKey() != null && e.getValue() instanceof COSString)
                .collect(Collectors.toMap(
                        e -> e.getKey().getName(),
                        e -> ((COSString) e.getValue()).getString(),
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
        return document.getDocumentInformation().getCOSObject().containsKey(COSName.getPDFName(key));
    }

    /**
     * Получить пользовательские метаданные по ключу
     *
     * @param key имя поля
     * @return значение или null
     */
    public String getCustomMetadata(String key) {
        return Optional.ofNullable(document.getDocumentInformation().getCOSObject().getDictionaryObject(COSName.getPDFName(key)))
                .map(Object::toString)
                .orElse(null);
    }

    /**
     * @return true, если документ зашифрован
     */
    public boolean isEncrypted() {
        return document.isEncrypted();
    }

    /**
     * @return true, если документ защищён паролем (ограничения на чтение)
     */
    public boolean isPasswordProtected() {
        if (!document.isEncrypted()) return false;
        AccessPermission permission = document.getCurrentAccessPermission();
        return !permission.canExtractContent();
    }

    /**
     * @return Объект с разрешениями доступа (печать, копирование и т.п.)
     */
    public AccessPermission getAccessPermissions() {
        return document.getCurrentAccessPermission();
    }
}
