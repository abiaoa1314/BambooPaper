package com.liersan.bp.controller;

import com.liersan.bp.dto.NoteDTO;
import com.liersan.bp.dto.UserDTO;
import com.liersan.bp.service.NoteService;
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
@RequestMapping("/note")
@Api(tags = "随笔接口")
@CrossOrigin
public class NoteController {
    @Autowired
    private NoteService noteService;

    @ApiOperation(value = "获取当前用户的全部随笔")
    @ApiImplicitParam(name = "uId" , value = "用户id" , required = false , dataType = "String")
    @GetMapping("/allNotes/{uId}")
    public Result getAllNotesByUId(@PathVariable("uId") String uId){
        List<NoteDTO> noteDTOList = noteService.getAllNotesByUId(uId);
        return Result.createSuccess(noteDTOList);
    }

    @ApiOperation(value = "根据前端传过来的关键字进行检索笔记")
    @ApiImplicitParam(name = "title" , value = "检索关键字" , required = false , dataType = "String")
    @GetMapping("/search/{title}")
    public Result searchByTitle(@PathVariable(name = "title" , required = false) String title){
        List<NoteDTO> noteDTOList = noteService.searchByTitle(title);
        return Result.createSuccess(noteDTOList);
    }

    @ApiOperation(value = "通过笔记id检索笔记内容")
    @ApiImplicitParam(name = "nId" , value = "笔记id" , required = true , dataType = "String")
    @GetMapping("/noteContent/{nId}")
    public Result noteContentByUId(@PathVariable(name = "nId" , required = true) String nId){
        String content = noteService.noteContentByUId(nId);
        return Result.createSuccess(content);
    }

    @ApiOperation(value = "通过用户id来插入笔记")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "uId" , value = "用户id" , required = true , dataType = "String"),
            @ApiImplicitParam(name = "map" , value = "包含笔记的标题,内容,简介" , required = true , dataType = "Map")
    })
    @PostMapping("/saveNote/{uId}")
    public Result saveNote(@PathVariable("uId") String uId, @RequestBody Map<String,String> map){
        UserDTO userDTO = noteService.saveNote(uId,map);
        return Result.createSuccess(userDTO);
    }

    @ApiOperation(value = "通过笔记id来修改笔记")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "nId" , value = "笔记id" , required = true , dataType = "String"),
            @ApiImplicitParam(name = "map" , value = "包含笔记的标题,内容,简介" , required = true , dataType = "Map")
    })
    @PutMapping("/compileNote/{nId}")
    public Result compileNote(@PathVariable("nId") String nId, @RequestBody Map<String,String> map){
        noteService.compileNote(nId,map);
        return Result.createSuccess();
    }

    @ApiOperation(value = "通过随笔id来删除随笔")
    @ApiImplicitParam(name = "nId" , value = "笔记id" , required = true , dataType = "String")
    @DeleteMapping("/deleteNote/{nId}")
    public Result deleteNote(@PathVariable("nId") String nId){
        UserDTO userDTO = noteService.deleteNote(nId);
        return Result.createSuccess(userDTO);
    }

    @ApiOperation(value = "通过笔记id来检索笔记")
    @ApiImplicitParam(name = "nId" , value = "笔记id" , required = true , dataType = "String")
    @GetMapping("/getNote/{nId}")
    public Result getNote(@PathVariable("nId") String nId){
        NoteDTO noteDTO = noteService.getNote(nId);
        return Result.createSuccess(noteDTO);
    }
}
