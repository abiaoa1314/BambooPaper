package com.liersan.bp.controller;

import com.liersan.bp.dto.ArticleDTO;
import com.liersan.bp.dto.UserDTO;
import com.liersan.bp.service.ArticleService;
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
@RequestMapping("/article")
@Api(tags = "文章接口")
@CrossOrigin
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "通过专栏id查找此专栏下的所有文章")
    @ApiImplicitParam(name = "cId" , value = "专栏id" , required = true , dataType = "String")
    @GetMapping("/getAllArticle/{cId}")
    public Result getAllArticleByCId(@PathVariable("cId") String cId){
        List<ArticleDTO> articleDTOList = articleService.getAllArticleByCId(cId);
        return Result.createSuccess(articleDTOList);
    }

    @ApiOperation(value = "通过文章id获取一个文章信息")
    @ApiImplicitParam(name = "aId" , value = "文章id" , required = true , dataType = "String")
    @GetMapping("/getArticle/{aId}")
    public Result getArticleByAId(@PathVariable("aId") String aId){
        ArticleDTO articleDTO = articleService.getArticleByAId(aId);
        return Result.createSuccess(articleDTO);
    }

    @ApiOperation(value = "通过文章id获取文章的内容")
    @ApiImplicitParam(name = "aId" , value = "文章id" , required = true , dataType = "String")
    @GetMapping("/getContent/{aId}")
    public Result getContentByAId(@PathVariable("aId") String aId){
        String content = articleService.getContentByAId(aId);
        return Result.createSuccess(content);
    }

    @ApiOperation(value = "通过专栏id在此专栏下新增一个文章")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "cId" , value = "专栏id" , required = true , dataType = "String"),
            @ApiImplicitParam(name = "map" , value = "包含文章的标题,简介,内容" , required = true , dataType = "Map")
    })
    @PostMapping("/saveArticle/{cId}")
    public Result saveArticleByCId(@PathVariable("cId") String cId,
                                   @RequestBody Map<String , String> map){
        UserDTO userDTO = articleService.saveArticleByCId(cId,map);
        return Result.createSuccess(userDTO);
    }

    @ApiOperation(value = "通过文章id修改一个文章内容")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "aId" , value = "文章id" , required = true , dataType = "String"),
            @ApiImplicitParam(name = "map" , value = "包含文章的标题,简介,内容" , required = true , dataType = "Map")
    })
    @PutMapping("/alterArticle/{aId}")
    public Result alterArticleByAId(@PathVariable("aId") String aId,
                                    @RequestBody Map<String,String> map){
        articleService.alterArticleByAId(aId,map);
        return Result.createSuccess();
    }

    @ApiOperation(value = "通过文章id删除一篇文章")
    @ApiImplicitParam(name = "aId" , value = "文章id" , required = true , dataType = "String")
    @DeleteMapping("/deleteArticle/{aId}")
    public Result deleteArticleByAId(@PathVariable("aId") String aId){
        UserDTO userDTO = articleService.deleteArticleByAId(aId);
        return Result.createSuccess(userDTO);
    }

    @ApiOperation(value = "通过专栏id和用户输入的关键字检索文章")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "cId" , value = "专栏id" , required = true , dataType = "String"),
            @ApiImplicitParam(name = "title" , value = "文章检索的关键字" , required = false , dataType = "String")
    })
    @GetMapping("/searchArticle")
    public Result searchArticle(@RequestParam("cId") String cId,
                                @RequestParam(required = false) String title){
        List<ArticleDTO> articleDTOList = articleService.searchArticle(cId,title);
        return Result.createSuccess(articleDTOList);
    }
}
