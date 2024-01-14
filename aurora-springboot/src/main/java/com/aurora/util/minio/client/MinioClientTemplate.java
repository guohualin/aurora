package com.aurora.util.minio.client;

import com.aurora.config.properties.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author DaLin
 * @Date: 2024/01/12 16:48
 * @Description: minio-client
 * @Version 1.0
 **/
@Component
public class MinioClientTemplate {
    private static final Logger logger = LoggerFactory.getLogger(MinioClientTemplate.class);

    @Autowired
    public MinioProperties minioConfigProperties;

    private MinioClient minioClient = null;

    private String File_BUCKET = "file";

    /**
     * 创建minio连接
     */
    private void buildMinioClient() {
        try {
            minioClient = MinioClient.builder().
                    endpoint(
                            minioConfigProperties.getEndpoint(),
                            minioConfigProperties.getPort(),
                            minioConfigProperties.isSecure()).
                    credentials(
                            minioConfigProperties.getAccessKey(),
                            minioConfigProperties.getSecretKey()).
                    build();

            minioClient.setTimeout(
                    minioConfigProperties.getConnectTimeout(),
                    minioConfigProperties.getWriteTimeout(),
                    minioConfigProperties.getReadTimeout()
            );
        } catch (Exception e) {
            logger.info("====minio连接失败==Endpoint：{};Port：{}=",minioConfigProperties.getEndpoint(), minioConfigProperties.getPort());
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 校验minio是否连接
     */
    private void checkMinioConnect() {
        try {
            boolean found_default = minioClient.bucketExists(BucketExistsArgs.builder().bucket(File_BUCKET).build());
            if(!found_default){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(File_BUCKET).build());
                logger.info(File_BUCKET+" is created successfully");
            }

        } catch (Exception e) {
            logger.error("====minio创建桶异常=="+e.getMessage(), e);
        }
    }

    public MinioClient getMinioClient(){
        if(minioClient == null){
            long start = new Date().getTime();
            logger.info("创建连接开始：MinioClientTemplate===getInstance");
            buildMinioClient();
            checkMinioConnect();
            long end = new Date().getTime();
            logger.info("创建连接结束：MinioClientTemplate===getInstance====={}",end-start);
        }
        return minioClient;
    }
}