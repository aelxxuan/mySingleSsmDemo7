/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */
package com.xuan.mysingle.console.common.PictureTool;

import com.xuan.mysingle.console.common.AvatarData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author xuanzongjun
 * @since 1.0
 * Date: 2018/5/18
 */
public class ImageUtils {
    private final static Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    /**
     * 剪切压缩上传图片
     *
     * @param file
     * @param ad
     * @param suffix
     * @param ossKeyFileName
     * @param w
     * @param h
     * @throws IOException
     */
    public static byte[] uploadCutImg(MultipartFile file, AvatarData ad, String suffix, String ossKeyFileName, int w, int h) {

        try (
                ImageInputStream iis = ImageIO.createImageInputStream(file.getInputStream());
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                ImageOutputStream imagOut = ImageIO.createImageOutputStream(bs);
        ) {
            // 根据图片类型获取该种类型的ImageReader
            ImageReader reader = ImageIO.getImageReaders(iis).next();
            reader.setInput(iis, true);
            //处理截图位置x,y为负数的情况
            calculateRect(ad, reader);
            //裁剪图片
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(ad.getX(), ad.getY(), ad.getWidth(), ad.getHeight());
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
            int width = bi.getWidth();
            int height = bi.getHeight();
            if ((width * 1.0) / w < (height * 1.0) / h) {
                w = h * width / height;
            } else {
                h = w * height / width;
            }
            BufferedImage newBufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bi, 0, 0, w, h, Color.WHITE, null);

            ImageIO.write(newBufferedImage, suffix, imagOut);
            return bs.toByteArray();
            //保存图像到Oss
            //InputStream byteArrayInput = new ByteArrayInputStream(bs.toByteArray());
            //OssUtilityUserFaceImg.putObjectToOss(ossKeyFileName, byteArrayInput);

            //return Result.SUCCESS;
        } catch (IOException ex) {
            //logger.error("CUT-UPLOAD-IMAGE", ex);
           // return new Result(false, ex.getMessage());
            return null;
        }
    }

    /**
     * 处理截取矩形的宽高和位置
     *
     * @param ad
     * @param reader
     * @throws IOException
     */
    private static void calculateRect(AvatarData ad, ImageReader reader) throws IOException {
        if (ad.getX() < 0) {
            ad.setWidth(ad.getWidth() + ad.getX());
            ad.setX(0);
        }
        if (ad.getY() < 0) {
            ad.setHeight(ad.getHeight() + ad.getY());
            ad.setY(0);
        }
        if (reader.getHeight(0) < ad.getHeight()) {
            ad.setHeight(reader.getHeight(0));
        }
        if (reader.getWidth(0) < ad.getWidth()) {
            ad.setWidth(reader.getWidth(0));
        }
    }
}
