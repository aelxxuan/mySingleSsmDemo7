/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.common.PictureTool;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.xuan.mysingle.console.support.SsmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/18
 */
public class OssUtilityUserFaceImg {

    protected final static Logger log = LoggerFactory.getLogger(OssUtilityUserFaceImg.class);
    protected static Properties properties;
    static {
        // 加载services.properties中的配置
        InputStream is = OssUtilityUserFaceImg.class.getResourceAsStream("/config-oss-userface.properties");
        properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            log.error("读取OSS配置文件失败！");
            throw new SsmException("读取OSS配置失败！", e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                log.error("关闭Oss配置文件输入流失败！");
            }
        }
    }

    protected static String ACCESS_KEY_ID = properties.getProperty("ACCESS_KEY_ID");
    protected static String ACCESS_KEY_SECRET = properties.getProperty("ACCESS_KEY_SECRET");
    protected static String ENDPOINT = properties.getProperty("ENDPOINT");
    protected static String BUCKET_NAME = properties.getProperty("BUCKET_ACCOUNT_IMG");
    protected static String DIR_PHASE = properties.getProperty("DIR_PHASE");
    public static String IMG_DOMAIN = properties.getProperty("IMG_DOMAIN");
    protected static OSSClient client = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);


    /**
     * 根据oss文件key获取路径
     *
     * @param key 不包括"userface/"前缀
     * @return
     */
    public static String getUserFaceImgUrl(String key) {
        String ossUrl = OssUtilityBase.getImgUrlByBucket(client, BUCKET_NAME, DIR_PHASE+"/"+key);
        String url = ossUrl;
        int pos = ossUrl.indexOf("?");
        if (pos > 0) {
            url =  ossUrl.substring(0, pos);
        }
        try {
            URL u = new URL(url);
            return IMG_DOMAIN + u.getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传文件到OSS的idCard-images BUCKET
     * @param key 自动加上"userface/"前缀
     * @param fileStream
     * @return
     */
    public static PutObjectResult putObjectToOss(String key, InputStream fileStream) {
        return OssUtilityBase.putObjectToOss(client, BUCKET_NAME,DIR_PHASE+"/"+key,fileStream);
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
