package com.eilen.site.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.eilen.site.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author eilen
 * @since 2023-05-02 04:26:52
 */
public interface IRoleService extends IService<Role> {
    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getRoleMenu(Integer roleId);

    List<Role> getAllRole();
}
