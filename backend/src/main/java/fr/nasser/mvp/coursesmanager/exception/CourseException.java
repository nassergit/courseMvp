package fr.nasser.mvp.coursesmanager.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CourseException extends RuntimeException{

    private final ErrorMessages error;
    public CourseException(ErrorMessages error) {
        this.error=error;
    }

    public CourseException(ErrorMessages error, Throwable cause) {
        super( cause);
        this.error=error;
    }

}
