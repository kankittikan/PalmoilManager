package ku.cs.palmoilmnger.service;

import ku.cs.palmoilmnger.exception.StorageException;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageService {
    public String addImg(MultipartFile multipartFile) throws StorageException, IOException {
        if (multipartFile == null) throw new StorageException("File is empty");
        if (!multipartFile.getContentType().contains("image")) throw new StorageException("อัปโหลดรูปได้เท่านั้น");

        File file = new File("storage/transactionImg");
        file.mkdirs();

        BufferedImage resized = resizeImage(ImageIO.read(multipartFile.getInputStream()), 1280, 720);

        String imageName = String.valueOf(UUID.randomUUID()).replace("-", "");
        //Path path = Path.of(file.getPath() + "/" + imageName + contentType.replace("image/", "."));
        //Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        File out = new File(file.getPath() + "/" + imageName + ".jpg");
        FileSystemUtils.deleteRecursively(out);
        ImageIO.write(resized, "JPG", out);
        return imageName + ".jpg";
    }

    public void deleteImg(String imgName) {
        FileSystemUtils.deleteRecursively(new File("storage/transactionImg/" + imgName));
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
}
