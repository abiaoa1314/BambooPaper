package com.liersan.bp.utils.utils;

import com.liersan.bp.utils.entity.StatusCode;
import com.liersan.bp.utils.exception.BpException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 本地图片处理
 * 处理图片上传,最后返回拼接后的图片地址
 */
public class ImageUtils {
    public static String upload(MultipartFile file){
        //可以支持文件类型
        List<String> suffixes = Arrays.asList("image/png", "image/jpeg", "image/jpg");

        //图片信息校验
        //校验文件类型
        String contentType = file.getContentType();
        if (!suffixes.contains(contentType))
            throw new BpException(StatusCode.InvalidImageType);

        //检验图片内容
        //若是提取不到文件流 则直接抛出异常
        BufferedImage image = null;
        try {
            image = ImageIO.read(file.getInputStream());
        }catch (IOException e){
            throw new BpException(StatusCode.InvalidImageType);
        }
        if (image == null)
            throw new BpException(StatusCode.InvalidImageType);

        //保存图片
        //保存的自己的文件所在目录,可以随意保存 , 这里我保存在靠近目录下
        File dir = new File("E:/OSS/Java/BambooPaper/file/image");
        if (!dir.exists())
            dir.mkdirs();
        try{
            //保存图片
            file.transferTo(new File(dir,file.getOriginalFilename()));
            //拼接图片地址返回给前端
            return "E:/OSS/Java/BambooPaper/file/image/" + file.getOriginalFilename();
        } catch (IOException e) {
            throw new BpException(StatusCode.FileUploadError);
        }
    }
}
