package tobyspring.splearn.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tobyspring.splearn.domain.member.DuplicateEmailException;
import tobyspring.splearn.domain.member.DuplicateProfileException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler({DuplicateEmailException.class, DuplicateProfileException.class})
    public ProblemDetail emailExceptionHandler(Exception exception) {
        return getProblemDetail(HttpStatus.CONFLICT, exception);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception exception) {
        return getProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    private static ProblemDetail getProblemDetail(HttpStatus httpStatus, Exception exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage());

        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("exception", exception.getClass().getName());

        return problemDetail;
    }
}
