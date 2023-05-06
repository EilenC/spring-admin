package com.eilen.site.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        generator();
    }

    public static void generator() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/learnjava?serverTimezone=GMT%2b8", "root", "root")
                .globalConfig(builder -> {
                    builder.author("eilen") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
                            .commentDate("yyyy-MM-dd hh:mm:ss")   //注释日期
                            .disableOpenDir() //禁止打开输出目录，默认打开
                            .outputDir("E:\\Mycode\\spring_vue_project\\backend\\site\\src\\main\\java\\"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.eilen.site") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "E:\\Mycode\\spring_vue_project\\backend\\site\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
//                    builder.controllerBuilder().enableFileOverride();
//                    builder.entityBuilder().enableFileOverride();
//                    builder.mapperBuilder().enableFileOverride();
//                    builder.serviceBuilder().enableFileOverride();
                    builder.entityBuilder().enableLombok();
                    builder.controllerBuilder().enableHyphenStyle().enableRestStyle();
                    builder.serviceBuilder();
                    builder.mapperBuilder();
                    builder.addInclude("t_comment") // 设置需要生成的表名
                            .addTablePrefix("t_", "sys_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateConfig(builder -> {
                    builder.controller("templates/controller.java").build();
                    builder.entity("templates/entity.java").build();
                })
                .execute();
    }
}
