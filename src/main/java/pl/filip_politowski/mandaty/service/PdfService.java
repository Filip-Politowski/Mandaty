package pl.filip_politowski.mandaty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PdfService {

    @Value("${upload.directory}")
    private String uploadDir;

    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "";
        }
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName != null ? originalFileName.substring(originalFileName.lastIndexOf(".")) : "";
        String uniqueFileName = UUID.randomUUID() + extension;


        Path path = Paths.get(uploadDir, uniqueFileName);
        Files.copy(file.getInputStream(), path);

        return path.toString();
    }

    public void deleteFile(String pdfPath) {
        Path path = Paths.get(pdfPath);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}