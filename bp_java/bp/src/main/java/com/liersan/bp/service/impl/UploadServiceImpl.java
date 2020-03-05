package com.liersan.bp.service.impl;

import com.liersan.bp.service.UploadService;
import com.liersan.bp.utils.utils.OSSUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
/**
 * 上传阿里云文件的服务层
 */
@Service
public class UploadServiceImpl implements UploadService {
    /**
     * 上传到阿里云的文件 , 最后返回拼接后的地址
     * @param file 图片
     * @return 返回拼接后的url地址
     */
    @Override
    public String upload(MultipartFile file) {
        String url = OSSUtils.upload(file);
        return url;
    }
}
