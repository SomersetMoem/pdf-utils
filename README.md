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

### Maven

#### 1. Добавьте JitPack в `pom.xml`
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

#### 2. Добавьте зависимость
```xml
<dependency>
    <groupId>com.github.SomersetMoem</groupId>
    <artifactId>pdf-utils</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

#### 1. Добавьте JitPack в `build.gradle`
```gradle
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
```

#### 2. Добавьте зависимость
```gradle
dependencies {
    implementation 'com.github.SomersetMoem:pdf-utils:1.0.0'
}
```

#### Для Gradle Kotlin DSL (`build.gradle.kts`)
```kotlin
repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.SomersetMoem:pdf-utils:1.0.0")
}
```

## 📘 Примеры использования

### Извлечение текста

```java
PdfUtils pdfUtils = PdfUtils.fromPath(PDF_PATH);
PdfTextExtractor extractor = new PdfTextExtractor(pdfUtils.getDocument());

String text = extractor.getAllText();
String firstPageText = extractor.getTextByPages(1);
```

### Извлечение изображений

```java
PdfUtils pdfUtils = PdfUtils.fromPath(PDF_PATH);
PdfImageExtractor extractor = new PdfImageExtractor(pdfUtils.getDocument());

List<BufferedImage> images = extractor.extractAllImages();
extractor.saveAllImagesToDirectory(new File("output/images"));
```

### Получение метаданных

```java
PdfUtils pdfUtils = PdfUtils.fromPath(PDF_PATH);
PdfMetaDataExtractor extractor = new PdfMetaDataExtractor(pdfUtils.getDocument());

String title = extractor.getTitle();
Map<String, String> allMeta = extractor.getAllMetadata();
```
