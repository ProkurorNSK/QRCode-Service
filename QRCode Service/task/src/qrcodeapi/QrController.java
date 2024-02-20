package qrcodeapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Object> getQrcode() {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(model.getQrcode());
    }
}
