package fr.nasser.mvp.coursesmanager.exception;

import org.springframework.http.HttpStatus;

public enum ErrorMessages {
    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "La course n'existe pas");

    private final HttpStatus status;
    private final String message;

    // Constructor
    ErrorMessages(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters
    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
