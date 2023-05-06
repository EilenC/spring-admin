package com.eilen.site.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eilen.site.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import com.eilen.site.common.Result;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.eilen.site.service.IArticleService;
import com.eilen.site.entity.Article;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author eilen
 * @since 2023-05-05 04:47:55
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @PostMapping
    public Result save(@RequestBody Article article) {
        if (article.getId() == null || article.getTime() == null || article.getUser().equals("")) {
            article.setTime(DateUtil.now());
            article.setUser(TokenUtils.getCurrentUser().getNickname());
        }
        return Result.success(articleService.saveOrUpdate(article));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(articleService.removeById(id));
    }

    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(articleService.removeBatchByIds(ids));
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(articleService.getById(id));
    }


    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String name) {
        IPage<Article> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Article> qw = new QueryWrapper<>();
        qw.orderByDesc("id");
        if (!name.equals("")) {
            qw.like("name", name);
        }
        return Result.success(articleService.page(page, qw));
    }
}
