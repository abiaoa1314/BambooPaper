package com.liersan.bp.utils.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.liersan.bp.utils.entity.StatusCode;
import com.liersan.bp.utils.exception.BpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 阿里云图片处理
 * 处理图片上传到阿里云 , 最后返回图片地址
 */
public class OSSUtils {
    //阿里云所需要的配置信息
    private static String endpoint = "";
    private static String accessKeyId = "";
    private static String accessKeySecret = "";
    private static String bucketName = "";

    private static OSSClient ossClientStatic;
    //调用本工具类 就会初始化oss服务
    static {
        ossClientStatic = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 上传到OSS服务器 如果同名文件会覆盖服务器上的
     * @param file 文件
     * @return 返回url地址
     */
    public static String upload(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            InputStream inputstream = file.getInputStream();
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 上传文件 (上传文件流的形式)
            PutObjectResult putResult = ossClientStatic.putObject(bucketName, fileName, inputstream, objectMetadata);
            //关闭文件流
            inputstream.close();
            //拼接url地址信息
            return "http://" + bucketName + "." + endpoint + "/" + fileName;
        } catch (Exception e) {
            throw new BpException(StatusCode.FileUploadError);
        }
    }
}