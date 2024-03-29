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
import java.util.Objects;

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
    public ResponseEntity<Object> getQrcode(@RequestParam String contents, @RequestParam(required = false, defaultValue = "250") int size, @RequestParam(required = false, defaultValue = "png") String type, @RequestParam(required = false, defaultValue = "L") String correction) {

        if (Objects.isNull(contents) || contents.isBlank()) {
            throw new QrErrorException("Contents cannot be null or blank");
        }

        if (size < 150 || size > 350) {
            throw new QrErrorException("Image size must be between 150 and 350 pixels");
        } else {
            model.setWidth(size);
            model.setHeight(size);
        }

        if (!Objects.equals(correction, "L") && !Objects.equals(correction, "M")
        && !Objects.equals(correction, "Q") && !Objects.equals(correction, "H")) {
            throw new QrErrorException("Permitted error correction levels are L, M, Q, H");
        }

        MediaType mediaType = switch (type) {
            case "png" -> MediaType.IMAGE_PNG;
            case "jpeg" -> MediaType.IMAGE_JPEG;
            case "gif" -> MediaType.IMAGE_GIF;
            default -> throw new QrErrorException("Only png, jpeg and gif image types are supported");
        };

        return ResponseEntity.ok().contentType(mediaType).body(model.getQrcode(contents, correction));
    }

    @ExceptionHandler(QrErrorException.class)
    ResponseEntity<Object> handleTicketException(QrErrorException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
