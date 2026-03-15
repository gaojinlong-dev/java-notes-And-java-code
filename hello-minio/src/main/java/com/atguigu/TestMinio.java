package com.atguigu;

import io.minio.*;
import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * ClassName: TestMinio
 * Package: com.atguigu
 * Description:
 *
 * @Author 高金龙
 * @Create 2024/10/8 11:07
 * @Version 1.0
 */
public class TestMinio {
    public static void main(String[] args) {
        String endpoint = "http://192.168.136.101:9000";
        String accessKey= "minioadmin";
        String secretKey= "minioadmin";
        String bucketName = "hello-minio";

        MinioClient minioClient = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();

        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                String poicy = """
                        {
                          "Statement" : [ {
                            "Action" : "s3:GetObject",
                            "Effect" : "Allow",
                            "Principal" : "*",
                            "Resource" : "arn:aws:s3:::%s/*"
                          } ],
                          "Version" : "2012-10-17"
                        }
                        """.formatted(bucketName);
                minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucketName).config(poicy).build());
            }
            minioClient.uploadObject(UploadObjectArgs.builder().filename("C:\\DProgram Files/公寓-外观.jpg").bucket(bucketName).object("公寓-外观.jpg").build());
            System.out.println("上传成功");
        } catch ( Exception e) {
             e.printStackTrace();
        }

    }
}
