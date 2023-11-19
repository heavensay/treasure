package com.helix.demo.oss.minio;

import com.alibaba.fastjson.JSON;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;

/**
 * @author lijianyu
 * @date 2023/11/13 17:20
 */
@Slf4j
public class TestMinio {

    @Test
    public void uploadMinio() {
        String localFilePath = "/Users/liyu/Downloads/555.png";
        String cloudPath = "cloudpath";

        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                //http访问，否则就是https
//                .withClientConfiguration(new ClientConfiguration().withProtocol(Protocol.HTTP))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(MinioConfig.endpoint, null))
                .withPathStyleAccessEnabled(true)
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicSessionCredentials(MinioConfig.ak, MinioConfig.sk,/* ossInfo.getToken()*/null)
//                            new BasicAWSCredentials(MinioConfig.ak, MinioConfig.sk)
                        )
                )
                .withChunkedEncodingDisabled(true)
                .build();
        File file = new File(localFilePath);

        // 上传文件
        String cloudFile = new File(cloudPath, file.getName()).getPath();
        System.out.println("cloudFile:"+cloudFile);
        final PutObjectResult putObjectResult = s3Client.putObject(
                new PutObjectRequest(MinioConfig.bucketName, cloudFile, file));
        System.out.println("putObjectResult：" + JSON.toJSONString(putObjectResult));
    }

    @Test
    public void download() {
        String localFilePath = "/Users/liyu/Downloads/555.png";
        String cloudPath = "cloudpath";

        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                //http访问，否则就是https
                .withClientConfiguration(new ClientConfiguration().withProtocol(Protocol.HTTP))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(MinioConfig.endpoint, null))
                .withPathStyleAccessEnabled(true)
                .withCredentials(new AWSStaticCredentialsProvider(
                                new BasicSessionCredentials(MinioConfig.ak, MinioConfig.sk,/* ossInfo.getToken()*/null)
//                            new BasicAWSCredentials(MinioConfig.ak, MinioConfig.sk)
                        )
                )
                .withChunkedEncodingDisabled(true)
                .build();
        File file = new File(localFilePath);

        // 上传文件
        String key = new File(cloudPath, file.getName()).getPath();
        System.out.println("cloudFile:"+key);
        S3Object object = s3Client.getObject(MinioConfig.bucketName, key);

        System.out.println("S3Object：" + JSON.toJSONString(object));
    }
}
