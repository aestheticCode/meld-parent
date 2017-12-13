package net.portrix.generic.image;

import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.gif.GIFImageReaderSpi;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick Bittner on 26.07.17.
 */
public class ImageUtils {

    private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);

    public static byte[] thumnail(String fileName, byte[] image, int resolution)  {
        byte[] imageInByte = new byte[0];
        try {
            final BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image));
            final int width = bufferedImage.getWidth();
            final int height = bufferedImage.getHeight();
            BufferedImage crop;
            if (width > height) {
                crop = bufferedImage.getSubimage((width - height) / 2, 0, height, height);
            } else {
                crop = bufferedImage.getSubimage(0, (height - width) / 2, width, width);
            }
            BufferedImage scaledImg = Scalr.resize(crop, resolution, resolution);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( scaledImg, FilenameUtils.getExtension(fileName), baos ); // if your image is a jpg
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return imageInByte;
    }

    public List<BufferedImage> frames(InputStream stream) throws IOException{
        List<BufferedImage> frames = new ArrayList<>();
        GIFImageReader imageReader = new GIFImageReader(new GIFImageReaderSpi());
        imageReader.setInput(ImageIO.createImageInputStream(stream));
        for(int i = 0; i < imageReader.getNumImages(true); i++)
            frames.add(imageReader.getRawImageType(i).createBufferedImage(imageReader.getWidth(i), imageReader.getHeight(i)));
        return frames;
    }

}
