package ku.cs.palmoilmnger.service;

import ku.cs.palmoilmnger.exception.StorageException;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

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
        String contentType = multipartFile.getContentType();
        if (!contentType.endsWith("jpeg") &&
                !contentType.endsWith("png") &&
                !contentType.endsWith("jpg")) throw new StorageException("สกุลไฟล์ไม่ถูกต้อง");

        File file = new File("storage/transactionImg");
        file.mkdirs();

        String imageName = String.valueOf(UUID.randomUUID()).replace("-", "");
        Path path = Path.of(file.getPath() + "/" + imageName + contentType.replace("image/", "."));
        Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return imageName + contentType.replace("image/", ".");
    }

    public void deleteImg(String imgName) {
        FileSystemUtils.deleteRecursively(new File("storage/transactionImg/" + imgName));
    }
}
