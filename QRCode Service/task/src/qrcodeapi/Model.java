package qrcodeapi;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;

@Component
class Model {
    private final int width;
    private final int height;

    public Model() {
        width = 250;
        height = 250;
    }

    public BufferedImage getQrcode() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        return image;
    }
}
