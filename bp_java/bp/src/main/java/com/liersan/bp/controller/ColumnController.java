package com.liersan.bp.controller;

import com.liersan.bp.dto.ColumnDTO;
import com.liersan.bp.dto.UserDTO;
import com.liersan.bp.service.ColumnService;
import com.liersan.bp.utils.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/column")
@Api(tags = "专栏接口")
@CrossOrigin
public class ColumnController {
    @Autowired
    private ColumnService columnService;

    @ApiOperation(value = "获取全部专栏信息")
    @GetMapping("/allColumn")
    public Result getAllColumn(){
        List<ColumnDTO> columnDTOList = columnService.getAllColumn();
        return Result.createSuccess(columnDTOList);
    }

    @ApiOperation(value = "通过关键字查找对应的专栏信息")
    @ApiImplicitParam(name = "title" , value = "专栏标题" , required = false , dataType = "String")
    @GetMapping("/search/{title}")
    public Result searchByTitle(@PathVariable(name = "title" , required = false)String title){
        List<ColumnDTO> columnDTOList = columnService.searchByTitle(title);
        return Result.createSuccess(columnDTOList);
    }

    @ApiOperation(value = "新增一个专栏信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "uId" , value = "用户id" , required = true , dataType = "String"),
            @ApiImplicitParam(name = "map", value = "专栏信息,其中包括标题和简介", required = true, dataType = "Map")
    })
    @PostMapping("/saveColumn/{uId}")
    public Result saveColumn(@PathVariable("uId")String uId,
                             @RequestBody Map<String,String> map){
        UserDTO userDTO = columnService.saveColumn(uId,map);
        return Result.createSuccess(userDTO);
    }

    @ApiOperation(value = "通过专栏id修改一个专栏信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "cId" , value = "专栏id" , required = true , dataType = "String"),
            @ApiImplicitParam(name = "map", value = "专栏信息,其中包括标题和简介", required = true, dataType = "Map")
    })
    @PutMapping("/alterColumn/{cId}")
    public Result alterColumn(@PathVariable("cId")String cId ,
                              @RequestBody Map<String , String> map){
        columnService.alterColumn(cId,map);
        return Result.createSuccess();
    }

    @ApiOperation(value = "通过专栏id删除专栏")
    @ApiImplicitParam(name = "cId" , value = "专栏id" , required = true , dataType = "String")
    @DeleteMapping("/deleteColumn/{cId}")
    public Result deleteColumn(@PathVariable("cId") String cId){
        UserDTO userDTO = columnService.deleteColumn(cId);
        return Result.createSuccess(userDTO);
    }
}
