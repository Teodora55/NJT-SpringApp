package rs.ac.bg.fon.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class FileUploadConfig {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostConstruct
    public void init() {
        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }
    }
}
