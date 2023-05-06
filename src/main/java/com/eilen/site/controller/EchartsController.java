package com.eilen.site.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eilen.site.common.Result;
import com.eilen.site.config.AuthAccess;
import com.eilen.site.entity.Files;
import com.eilen.site.entity.User;
import com.eilen.site.mapper.FileMapper;
import com.eilen.site.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private IUserService userService;

    @Resource
    private FileMapper fileMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${files.upload.host}")
    private String Host;

    @GetMapping("/members")
    public Result member() {
        List<User> list = userService.list();
        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        for (User user : list) {
            Date createTime = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter) {
                case Q1:
                    q1 += 1;
                    break;
                case Q2:
                    q2 += 1;
                    break;
                case Q3:
                    q3 += 1;
                    break;
                case Q4:
                    q4 += 1;
                    break;
            }
        }
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }

    @AuthAccess
    @GetMapping("/file/front/all")
    public Result frontAll() {
        // 1. 从缓存获取数据
//        String jsonStr = stringRedisTemplate.opsForValue().get(Constants.FILES_KEY);
        String jsonStr = "";
        List<Files> files;
        if (StrUtil.isBlank(jsonStr)) {  // 2. 取出来的json是空的
            QueryWrapper<Files> qw = new QueryWrapper<>();
            qw.orderByDesc("id");
            qw.eq("is_delete",0);
            files = fileMapper.selectList(qw);  // 3. 从数据库取出数据
            // 4. 再去缓存到redis
//            stringRedisTemplate.opsForValue().set(Constants.FILES_KEY, JSONUtil.toJsonStr(files));
        } else {
            // 减轻数据库的压力
            // 5. 如果有, 从redis缓存中获取数据
            files = JSONUtil.toBean(jsonStr, new TypeReference<List<Files>>() {
            }, true);
        }
        for (Files f : files) {
            if (f != null || f.getUrl() != null || !f.getUrl().equals("")) {
                f.setUrl(Host + f.getUrl());
            }
        }
        return Result.success(files);
    }

}
