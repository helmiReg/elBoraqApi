package sbz.padel.backend.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

public class RestServerResponseException extends RuntimeException {

    private HttpStatus statusCode;
    private List<String> errors = new ArrayList<>();

    public RestServerResponseException(HttpStatus statusCode, String message) {
        super();
        this.errors.add(message);
        this.statusCode = statusCode;
    }

    public RestServerResponseException(HttpStatus statusCode, List<String> messages) {
        super();
        this.errors.addAll(messages);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String getMessage() {
        return StringUtils.hasText(super.getMessage()) ? super.getMessage() : this.getErrors().toString();
    }
}
