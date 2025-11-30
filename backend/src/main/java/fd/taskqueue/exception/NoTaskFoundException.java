package fd.taskqueue.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoTaskFoundException extends RuntimeException {

    public NoTaskFoundException(String message) {
        super(message);
    }
}
