package com.boostcamp.randommenu.service.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.boostcamp.randommenu.service.S3FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Slf4j
@Service
public class S3FileUploadServiceImpl implements S3FileUploadService {

    // 버킷 이름 동적 할당
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 버킷 주소 동적 할당
    @Value("${cloud.aws.s3.bucket.url}")
    private String defaultUrl;

    private final AmazonS3Client amazonS3Client;

    public S3FileUploadServiceImpl(final AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    /**
     * 파일 업로드
     * @param uploadFile
     *      업로드할 파일
     * @return 파일이 저장된 url
     * @throws IOException
     */
    @Override
    public String upload(MultipartFile uploadFile) throws IOException {
        String origName = uploadFile.getOriginalFilename();
        String url;
        File file = null;
        try {
            // 확장자
            final String ext = origName.substring(origName.lastIndexOf('.'));
            // 파일 이름 암호화
            final String saveFileName = getUuid() + ext;
            // 파일 객체 생성

            file = new File(FileUtils.getTempDirectory() +"/" +saveFileName);

            // 파일 변환
            uploadFile.transferTo(file);
            // s3 파일 업로드
            uploadOnS3(saveFileName, file);

            // 주소 할당
            url = defaultUrl + saveFileName;
        } catch(StringIndexOutOfBoundsException e) {
            // 파일이 없을 경우 예외 처리
            url = null;
        } finally {
            FileUtils.deleteQuietly(file);
        }
        return url;
    }


    private static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * S3에 파일 업로드
     * @param fileName
     *      업로드할 파일의 이름
     * @param file
     *      저장할 파일
     */
    @Override
    public void uploadOnS3(String fileName, File file) {
        // AWS S3 전송 객체 생성
        final TransferManager transferManager = new TransferManager(this.amazonS3Client);
        // 요청 객체 생성
        final PutObjectRequest request = new PutObjectRequest(bucket, fileName, file);
        // 업로드 시도
        final Upload upload = transferManager.upload(request);

        try {
            // 완료 확인
            upload.waitForCompletion();
        } catch(AmazonClientException e) {
            log.error(e.getMessage());
        } catch(InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
