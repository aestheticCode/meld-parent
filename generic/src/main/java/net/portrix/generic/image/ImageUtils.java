package net.portrix.generic.image;

import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.gif.GIFImageReaderSpi;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public static void saveToWorkingDirectory(UUID id, String fileName, byte[] image, byte[] thumbnail) throws IOException {
        File imageWorkingDir = workingDirectory(id);
        String extension = FilenameUtils.getExtension(fileName);
        File imageFile = new File(imageWorkingDir.getCanonicalPath() + File.separator + "image." + extension);
        File thumbnailFile = new File(imageWorkingDir.getCanonicalPath() + File.separator + "thumbnail." + extension);
        FileUtils.writeByteArrayToFile(imageFile, image);
        FileUtils.writeByteArrayToFile(thumbnailFile, thumbnail);
    }

    public static byte[] loadImageFromWorkingDirectory(UUID id, String fileName) throws IOException {
        File imageWorkingDir = workingDirectory(id);
        String extension = FilenameUtils.getExtension(fileName);
        File imageFile = new File(imageWorkingDir.getCanonicalPath() + File.separator + "image." + extension);
        return IOUtils.toByteArray(imageFile.toURI());
    }

    public static byte[] loadThumbnailFromWorkingDirectory(UUID id, String fileName) throws IOException {
        File imageWorkingDir = workingDirectory(id);
        String extension = FilenameUtils.getExtension(fileName);
        File thumbnailFile = new File(imageWorkingDir.getCanonicalPath() + File.separator + "thumbnail." + extension);
        return IOUtils.toByteArray(thumbnailFile.toURI());
    }

    public static void deleteFromWorkingDirectory(UUID id) throws IOException {
        File workingDirectory = workingDirectory(id);
        FileUtils.deleteDirectory(workingDirectory);
    }

    public static File workingDirectory(UUID id) throws IOException {
        String home = System.getProperty("user.home");
        File meld = new File(home + File.separator + ".meld");
        FileUtils.forceMkdir(meld);
        File imageWorkingDir = new File(meld.getCanonicalPath() + File.separator + id.toString());
        FileUtils.forceMkdir(imageWorkingDir);
        return imageWorkingDir;
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
