package pl.pt.put.poznan.webscraperdb;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URL;

public class ImageConventer {

    public static byte[] imageToBytes(String urlToImage) {
        URL url = null;
        BufferedImage image = null;
        byte[] data = null;
        try {
            url = new URL(urlToImage);
            image = ImageIO.read(url);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", bos);
            data = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
