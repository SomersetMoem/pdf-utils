# 📄 pdf-utils

Лёгкая и удобная Java-библиотека для работы с PDF-документами на основе Apache PDFBox. Поддерживает извлечение текста, метаданных и изображений из PDF.

## 🚀 Возможности

### 🔍 Извлечение текста
- Получение всего текста документа
- Извлечение текста по одной или нескольким страницам
- Поддержка предобработки текста и разделения по символам

### 🖼 Извлечение изображений
- Извлечение всех изображений из документа
- Получение изображений с конкретной страницы
- Сохранение изображений в PNG-файлы

### 📑 Метаданные
- Получение заголовка, автора, ключевых слов, дат создания и модификации
- Извлечение всех доступных полей метаданных
- Проверка на наличие шифрования и прав доступа

## 📦 Установка

Пока библиотека не размещена в центральном репозитории Maven, её можно подключить напрямую из GitHub с помощью JitPack:

### 1. Добавьте JitPack в `pom.xml`

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

### 2. Добавьте зависимость

```xml
<dependency>
    <groupId>com.github.SomersetMoem</groupId>
    <artifactId>pdf-utils</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 📘 Примеры использования

### Извлечение текста

```java
PDDocument document = PDDocument.load(new File("sample.pdf"));
PdfTextExtractor extractor = new PdfTextExtractor(document);

String text = extractor.getAllText();
String firstPageText = extractor.getTextByPages(1);
```

### Извлечение изображений

```java
PDDocument document = PDDocument.load(new File("sample.pdf"));
PdfImageExtractor extractor = new PdfImageExtractor(document);

List<BufferedImage> images = extractor.extractAllImages();
extractor.saveAllImagesToDirectory(new File("output/images"));
```

### Получение метаданных

```java
PDDocument document = PDDocument.load(new File("sample.pdf"));
PdfMetaDataExtractor extractor = new PdfMetaDataExtractor(document);

String title = extractor.getTitle();
Map<String, String> allMeta = extractor.getAllMetadata();
```