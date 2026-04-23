package com.classroom.cse_a_classroom.service;

import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    private final String STORAGE_DIR = "storage/";

    public FileStorageService() {
        File dir = new File(STORAGE_DIR);
        if (!dir.exists()) dir.mkdirs();
    }

    public String storeFile(File sourceFile) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + sourceFile.getName();
        File targetFile = new File(STORAGE_DIR + fileName);
        Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    public void deleteFile(String fileName) {
        File file = new File(STORAGE_DIR + fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
