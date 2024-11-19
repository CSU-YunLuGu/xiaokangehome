package com.csuyunlugu.ehome.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * @ClassName FileUploadUtil
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/7 17:19
 * @Version 1.0
 */
@Component
public class FileUploadUtil {
    private static String URL_PREFIX;
    private static String LOCATION;

    public FileUploadUtil(
            @Value("${static.url.prefix}") String urlPrefix,
            @Value("${static.location}") String location) {
        FileUploadUtil.URL_PREFIX = urlPrefix;
        FileUploadUtil.LOCATION = location;
    }

    public static String storeFile(MultipartFile file, String type) {
        // 根据当前日期生成文件夹
        long time = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(time);
        String formatDate = TimeUtil.formatDate(timestamp);

        // 拼接文件名
        String dir = String.format("/%s/%s/", type, formatDate);
        String uploadDir = URL_PREFIX + dir;
        String fileDir = LOCATION + dir;
        String originalFileName = file.getOriginalFilename();
        String fileName = time + originalFileName;
        String filePath = fileDir + fileName;
        String url = uploadDir + fileName;

        System.out.println("filePath: " + filePath);
        System.out.println("url: " + url);

        // 确保目录存在
        new File(fileDir).mkdirs();

        // 保存文件
        File destFile = new File(filePath);
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return url;
    }
}
