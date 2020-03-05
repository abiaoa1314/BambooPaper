package com.liersan.bp.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传阿里云文件的服务层接口
 */
public interface UploadService {
    /**
     * 上传到阿里云的文件 , 最后返回拼接后的地址
     * @param file 图片
     * @return 返回拼接后的url地址
     */
    String upload(MultipartFile file);
}
