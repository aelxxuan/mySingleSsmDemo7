/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.common.PictureTool;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.List;

/**
 * 阿里云OSS存储基础工具类
 *
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/18
 */
public class OssUtilityBase {
    /**
     * 获取文件键值获取文件全路径
     *
     * @param client
     * @param bucket
     * @param ossFileKey
     * @return
     */
    public static String getImgUrlByBucket(OSSClient client, String bucket, String ossFileKey) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, 10);
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, ossFileKey);
        request.setExpiration(instance.getTime());
        URL generatePresignedUrl = client.generatePresignedUrl(request);
        return generatePresignedUrl.toString();
    }

    /**
     * 下载OSS文件到本地
     * @param client
     * @param bucket
     * @param ossFileKey
     * @param localFilePath
     * @throws IOException
     */
    public static void downloadToFile(OSSClient client, String bucket, String ossFileKey, String localFilePath) throws IOException {

        OSSObject ossObject = client.getObject(bucket, ossFileKey);
        InputStream content = ossObject.getObjectContent();
        OutputStream os = null;
        File file = new File(localFilePath);

        try {
            if (content != null) {
                os = new FileOutputStream(file);
                FileCopyUtils.copy(content, os);
            }
        } finally {
            content.close();
            if (os != null){
                os.close();
            }
        }
    }

    /**
     * 删除OSS文件
     * @param client
     * @param bucket
     * @param prefix
     */
    public static void deletePrefixObject(OSSClient client, String bucket, String prefix) {
        ObjectListing listing =
                client.listObjects(new ListObjectsRequest(bucket, prefix, null, null, 1000));

        //oss listObjects每次取有条数限制，需要循环取到没有
        while (listing.getObjectSummaries().size() > 0) {
            for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
                client.deleteObject(bucket, objectSummary.getKey());
            }
            listing =
                    client.listObjects(new ListObjectsRequest(bucket, prefix, null, null, 1000));
        }
        client.deleteObject(bucket, prefix);
    }

    /**
     * 删除Oss文件，excludeKeys的文件除外
     * @param client
     * @param bucket
     * @param prefix
     * @param excludeKeys
     */
    public static void deletePrefixObject(OSSClient client, String bucket, String prefix, List<String> excludeKeys) {
        if (CollectionUtils.isEmpty(excludeKeys)) {
            deletePrefixObject(client,bucket,prefix);
            return;
        }
        ObjectListing listing =
                client.listObjects(new ListObjectsRequest(bucket, prefix, null, null, 1000));

        boolean deletePrefixSelf = true;
        while (listing.getObjectSummaries().size() > 0) {
            for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
                if (!excludeKeys.stream().anyMatch(i -> i.equals(objectSummary.getKey()))) {
                    deletePrefixSelf = false;
                    client.deleteObject(bucket, objectSummary.getKey());
                }
            }
            listing =
                    client.listObjects(new ListObjectsRequest(bucket, prefix, null, null, 1000));
            if (listing.getObjectSummaries().stream().allMatch(i -> excludeKeys.contains(i))) {
                break;
            }
        }
        if (deletePrefixSelf) {
            client.deleteObject(bucket, prefix);
        }
    }

    /**
     * 上传文件到OSS的BUCKET
     * @param client
     * @param bucket
     * @param ossFileKey
     * @param fileStream
     * @return
     */
    public static PutObjectResult putObjectToOss(OSSClient client,String bucket, String ossFileKey, InputStream fileStream) {
        return client.putObject(new PutObjectRequest(bucket, ossFileKey, fileStream));
    }

    /**
     * 获取 OSS指定Bucket的Url，以"/"结尾
     *
     * @return
     */
    public static String getIdCardImagesBucketUrl(OSSClient client,String bucket) {
        URI endpoint = client.getEndpoint();
        return endpoint.getScheme() + "://" + bucket + "." + endpoint.getHost() + "/";
    }
}
