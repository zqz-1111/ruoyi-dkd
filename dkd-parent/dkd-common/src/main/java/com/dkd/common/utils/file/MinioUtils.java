package com.dkd.common.utils.file;

import java.io.InputStream;
import java.util.UUID;
import com.dkd.common.config.MinioConfig;
import com.dkd.common.utils.DateUtils;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * MinIO工具类
 *
 * @author itheima
 */
@Component
public class MinioUtils
{
    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioConfig minioConfig;

    /**
     * 上传文件到MinIO
     *
     * @param file 文件
     * @return 文件访问路径
     */
    public String upload(MultipartFile file) throws Exception
    {
        // 生成文件名：yyyy/MM/dd/UUID.后缀
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = DateUtils.datePath() + "/" + UUID.randomUUID().toString().replace("-", "") + extension;

        // 上传文件到MinIO
        try (InputStream inputStream = file.getInputStream())
        {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .object(fileName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }

        // 返回文件访问路径
        return minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/" + fileName;
    }
}
