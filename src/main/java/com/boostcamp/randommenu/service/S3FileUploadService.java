package com.boostcamp.randommenu.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface S3FileUploadService {
    String upload(MultipartFile uploadFile) throws IOException;
    void uploadOnS3(final String fileName, final File file);
}
