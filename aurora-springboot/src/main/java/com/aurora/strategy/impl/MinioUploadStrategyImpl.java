package com.aurora.strategy.impl;

import com.aurora.config.properties.MinioProperties;
import com.aurora.util.minio.client.MinioClientTemplate;
import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;

@Service("minioUploadStrategyImpl")
public class MinioUploadStrategyImpl extends AbstractUploadStrategyImpl {
    private Logger logger = LoggerFactory.getLogger(MinioUploadStrategyImpl.class);

    @Autowired
    private MinioProperties minioProperties;

    @Autowired
    private MinioClientTemplate minioClientTemplate;

    /**
     * 校验文件是否存在
     * @param filePath
     * @return
     */
    @Override
    public Boolean exists(String filePath) {
        boolean exist = true;
        try {

            MinioClient minioClient = minioClientTemplate.getMinioClient();

            minioClient.statObject(StatObjectArgs.builder().bucket(minioProperties.getBucketName()).object(filePath).build());

        } catch (Exception e) {
            exist = false;
        }
        return exist;
    }

    /**
     * 上传文件
     * @param path
     * @param fileName
     * @param inputStream
     */
    @SneakyThrows
    @Override
    public void upload(String path, String fileName, InputStream inputStream) {
        try{
            MinioClient minioClient = minioClientTemplate.getMinioClient();

            minioClient.putObject(
                    PutObjectArgs.builder().bucket(minioProperties.getBucketName()).object(path + fileName).stream(
                                    inputStream, inputStream.available(), -1)
                            .build());
        }catch (Exception e){
            logger.error("上传文件---出现系统异常e",e);
        }

    }

    @Override
    public String getFileAccessUrl(String filePath) {
        return minioProperties.getUrl() + filePath;
    }


}
