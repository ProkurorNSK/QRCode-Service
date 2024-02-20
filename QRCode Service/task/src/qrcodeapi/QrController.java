package qrcodeapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SuppressWarnings("unused")
@RestController
public class QrController {

    private final Model model;
    @Autowired
    private QrController(Model model) {
        this.model = model;
    }

    @GetMapping("/health")
    public ResponseEntity<Object> getHealth() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/qrcode")
    public ResponseEntity<Object> getQrcode(@RequestParam int size, @RequestParam String type) {
        if (size < 150 || size > 350) {
            throw new QrErrorException("Image size must be between 150 and 350 pixels");
        } else {
            model.setWidth(size);
            model.setHeight(size);
        }

        MediaType mediaType = switch (type) {
            case "png" -> MediaType.IMAGE_PNG;
            case "jpeg" -> MediaType.IMAGE_JPEG;
            case "gif" -> MediaType.IMAGE_GIF;
            default -> throw new QrErrorException("Only png, jpeg and gif image types are supported");
        };

        return ResponseEntity.ok().contentType(mediaType).body(model.getQrcode());
    }

    @ExceptionHandler(QrErrorException.class)
    ResponseEntity<Object> handleTicketException(QrErrorException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
