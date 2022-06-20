package sbz.padel.backend.config;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
@Order(1)

public class GeneralBackendExceptionHandler {

    /**
     * Backend checked Exception Handling
     * 
     * @see DefaultResponseErrorHandler
     */
    @ExceptionHandler({ RestServerResponseException.class })
    public ResponseEntity<Object> handleAll(RestServerResponseException ex) {

        ApiErrorDto apiError = new ApiErrorDto(ex.getStatusCode(),
                ex.getMessage(),
                ex.getErrors(),
                ex);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}