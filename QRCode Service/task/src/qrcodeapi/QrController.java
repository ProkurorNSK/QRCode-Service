package qrcodeapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@RestController
public class QrController {
    @GetMapping("/health")
    public ResponseEntity<Object> getHealth() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/qrcode")
    public ResponseEntity<Object> getQrcode() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
