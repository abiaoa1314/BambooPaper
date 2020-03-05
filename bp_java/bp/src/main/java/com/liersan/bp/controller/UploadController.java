package com.liersan.bp.controller;

import com.liersan.bp.service.UploadService;
import com.liersan.bp.utils.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@Api(tags = "图片上传接口")
@CrossOrigin
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @ApiOperation(value = "进行图片上传的api")
    @ApiImplicitParam(name = "file" , value = "图片的文件" , required = true , dataType = "MultipartFile")
    @PostMapping
    public Result upload(MultipartFile file){
        String url = uploadService.upload(file);
        return Result.createSuccess(url);
    }
}
