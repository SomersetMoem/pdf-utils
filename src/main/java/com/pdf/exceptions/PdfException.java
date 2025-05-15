package com.pdf.exceptions;

/**
 * Исключение, выбрасываемое при ошибках обработки PDF документов.
 * Является непроверяемым (RuntimeException), так как большинство ошибок
 * при работе с PDF не могут быть обработаны в прикладном коде.
 */
public class PdfException extends RuntimeException {
    /**
     * Создает новый экземпляр исключения с указанным сообщением и причиной.
     *
     * @param message сообщение об ошибке
     * @param cause причина исключения
     */
    public PdfException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Создает новый экземпляр исключения с указанным сообщением.
     *
     * @param message сообщение об ошибке
     */
    public PdfException(String message) {
        super(message);
    }
}