package com.eilen.site.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eilen.site.common.Result;
import com.eilen.site.controller.dto.UserDTO;
import com.eilen.site.controller.dto.UserPasswordDTO;
import com.eilen.site.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author eilen
 * @since 2023-04-28 05:07:20
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);

    IPage<User> findPage(Page<User> objectPage, String username, String email, String address);

    String updatePassword(UserPasswordDTO user);
}
