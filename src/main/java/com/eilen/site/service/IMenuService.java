package com.eilen.site.service;

import com.eilen.site.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author eilen
 * @since 2023-05-04 02:05:12
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findMenus(String name);
}
