package com.eilen.site.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import com.eilen.site.common.Result;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.eilen.site.service.IRoleMenuService;
import com.eilen.site.entity.RoleMenu;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色菜单关系表 前端控制器
 * </p>
 *
 * @author eilen
 * @since 2023-05-04 02:06:24
 */
@RestController
@RequestMapping("/role-menu")
public class RoleMenuController {
    
    @Autowired
    private IRoleMenuService roleMenuService;

    @PostMapping
    public Result save(@RequestBody RoleMenu roleMenu) {
        return Result.success(roleMenuService.saveOrUpdate(roleMenu));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(roleMenuService.removeById(id));
    }

    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(roleMenuService.removeBatchByIds(ids));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String username) {
        IPage<RoleMenu> page = new Page<>(pageNum, pageSize);
        QueryWrapper<RoleMenu> qw = new QueryWrapper<>();
        qw.orderByDesc("id");
        if (!username.equals("")) {
            qw.like("username", username);
        }
        return Result.success(roleMenuService.page(page, qw));
    }
}
