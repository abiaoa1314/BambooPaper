package com.liersan.bp.controller;

import com.liersan.bp.dto.UserDTO;
import com.liersan.bp.entity.User;
import com.liersan.bp.service.UserService;
import com.liersan.bp.utils.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "根据手机号和密码进行用户登录(注册)")
    @ApiImplicitParam(name = "user" , value = "用户实体类 , 其中数据包含手机号和密码" , required = true , dataType = "User")
    @PostMapping("/login")
    public Result userLogin(@RequestBody User user){
        UserDTO userDTO = userService.userLogin(user);
        return Result.createSuccess(userDTO);
    }

    @ApiOperation(value = "重设用户密码")
    @ApiImplicitParam(name = "map" , value = "其中数据包含手机号和新密码和旧密码" , required = true , dataType = "Map")
    @PutMapping("/reset")
    public Result userReset(@RequestBody Map<String,String> map){
        userService.userReset(map);
        return Result.createSuccess();
    }

    @ApiOperation(value = "重设用户昵称")
    @ApiImplicitParam(name = "map" , value = "其中包括用户id和新昵称" , required = true , dataType = "Map")
    @PutMapping("/resetUsername")
    public Result resetUsername(@RequestBody Map<String,String> map){
        UserDTO userDTO = userService.resetUsername(map);
        return Result.createSuccess(userDTO);
    }

    @ApiOperation(value = "修改用户头像")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "uId" , value = "用户id" , required = true , dataType = "String"),
            @ApiImplicitParam(name = "file" , value = "图片文件" , required = true , dataType = "MultipartFile"),
    })
    @PostMapping("/resetImgUrl/{uId}")
    public Result resetImgUrl(@PathVariable(value = "uId") String uId ,
                              @RequestParam MultipartFile file){
        UserDTO userDTO = userService.resetImgUrl(uId, file);
        return Result.createSuccess(userDTO);
    }

    @ApiOperation(value = "获得全部用户")
    @GetMapping("/getAllUser")
    public Result getAllUser(){
        List<UserDTO> userDTOList = userService.getAllUser();
        return Result.createSuccess(userDTOList);
    }

    @ApiOperation(value = "通过用户名模糊查找用户信息")
    @ApiImplicitParam(name = "username" , value = "用户名" , required = false , dataType = "String")
    @GetMapping("/getUserByUsername/{username}")
    public Result getUserByUsername(@PathVariable("username") String username){
        List<UserDTO> userDTOList = userService.getUserByUsername(username);
        return Result.createSuccess(userDTOList);
    }

    @ApiOperation(value = "通过用户id查找对应用户信息")
    @ApiImplicitParam(name = "uId" , value = "用户id" , required = true , dataType = "String")
    @GetMapping("/getUserByUId/{uId}")
    public Result getUserByUId(@PathVariable("uId") String uId){
        UserDTO userDTO = userService.getUserByUId(uId);
        return Result.createSuccess(userDTO);
    }

    @ApiOperation(value = "通过用户id设置用户为博主")
    @ApiImplicitParam(name = "uId" , value = "用户id" , required = true , dataType = "String")
    @PutMapping("/putUserZero/{uId}")
    public Result putUserZero(@PathVariable("uId") String uId){
        userService.putUserZero(uId);
        return Result.createSuccess();
    }

    @ApiOperation(value = "通过用户id设置用户为用户")
    @ApiImplicitParam(name = "uId" , value = "用户id" , required = true , dataType = "String")
    @PutMapping("/putUserOne/{uId}")
    public Result putUserOne(@PathVariable("uId") String uId){
        userService.putUserOne(uId);
        return Result.createSuccess();
    }
}
