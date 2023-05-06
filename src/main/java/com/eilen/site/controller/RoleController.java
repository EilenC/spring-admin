package com.eilen.site.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import com.eilen.site.common.Result;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.eilen.site.service.IRoleService;
import com.eilen.site.entity.Role;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author eilen
 * @since 2023-05-02 04:50:49
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    
    @Autowired
    private IRoleService roleService;

    @PostMapping
    public Result save(@RequestBody Role role) {
        return Result.success(roleService.saveOrUpdate(role));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(roleService.removeById(id));
    }

    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(roleService.removeBatchByIds(ids));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String name) {
        IPage<Role> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Role> qw = new QueryWrapper<>();
        qw.orderByDesc("id");
        if (!name.equals("")) {
            qw.like("name", name);
        }
        return Result.success(roleService.page(page, qw));
    }

    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> menuIds) {
        roleService.setRoleMenu(roleId, menuIds);
        return Result.success();
    }

    @GetMapping("/roleMenu/{roleId}")
    public Result getRoleMenu(@PathVariable Integer roleId) {
        return Result.success( roleService.getRoleMenu(roleId));
    }


    @GetMapping("/allRole")
    public Result allRole() {
        return Result.success(roleService.getAllRole());
    }
}
