package sbz.padel.backend.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiErrorDto {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiErrorDto(final HttpStatus status,
            final String message,
            final List<String> errors,
            Exception ex) {

        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiErrorDto(final HttpStatus status, final String message, final String error, Exception ex) {
        this(status, message, Arrays.asList(error), ex);
    }

    public ApiErrorDto(final HttpStatus status, final String message, Exception ex) {
        this(status, message, Arrays.asList(""), ex);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}
