package com.example.global.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String message;
    private List<FieldError> errors;

    public ErrorResponse(String message) {
        this.message = message;
        this.errors = new ArrayList<>();
    }

    public ErrorResponse(String message, List<FieldError> errors) {
        this.message = message;
        this.errors = errors;
    }

    public static ErrorResponse of(String message) {
        return new ErrorResponse(message);
    }

    public static ErrorResponse of(String message, BindingResult bindingResult) {
        return new ErrorResponse(message, FieldError.of(bindingResult));
    }

    public static ErrorResponse of(String message, List<FieldError> errors) {
        return new ErrorResponse(message, errors);
    }

    public static ErrorResponse of (MethodArgumentTypeMismatchException e) {
        String value = e.getValue() == null ? "" : e.getValue().toString();
        List<FieldError> fieldErrors = FieldError.of(e.getName(), value, e.getErrorCode());
        return new ErrorResponse("Invalid Type Value", fieldErrors);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        public FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of (String field, String value, String reason) {
            return List.of(new FieldError(field, value, reason));
        }

        private static List<FieldError> of (BindingResult bindingResult) {
            List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage())
                    )
                    .toList();
        }
    }
}
