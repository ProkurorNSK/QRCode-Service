package qrcodeapi;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus()
public class QrErrorException extends RuntimeException{
    public QrErrorException(String message) {
        super(message);
    }
}
