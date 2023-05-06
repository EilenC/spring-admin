package com.eilen.site.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eilen.site.common.Constants;
import com.eilen.site.common.Result;
import com.eilen.site.controller.dto.UserDTO;
import com.eilen.site.controller.dto.UserPasswordDTO;
import com.eilen.site.entity.Menu;
import com.eilen.site.entity.Role;
import com.eilen.site.entity.User;
import com.eilen.site.exception.ServiceException;
import com.eilen.site.mapper.RoleMapper;
import com.eilen.site.mapper.RoleMenuMapper;
import com.eilen.site.mapper.UserMapper;
import com.eilen.site.service.IMenuService;
import com.eilen.site.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eilen.site.utils.TokenUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author eilen
 * @since 2023-04-28 05:07:20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    @Value("${files.upload.host}")
    private String Host;

    @Override
    public UserDTO login(UserDTO userDTO) {
        // 用户密码 md5加密
        userDTO.setPassword(SecureUtil.md5(userDTO.getPassword()));
        User one = getUserInfo(userDTO);
        if (one != null) {
            BeanUtil.copyProperties(one, userDTO, true);
            // 设置token
            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);

            String role = one.getRole(); // ROLE_ADMIN
            // 设置用户的菜单列表
            List<Menu> roleMenus = getRoleMenus(role);
            userDTO.setMenus(roleMenus);
            userDTO.setAvatarUrl(Host+userDTO.getAvatarUrl());
            return userDTO;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    @Override
    public User register(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one == null) {//账户不存在,添加
            one = new User();
            BeanUtil.copyProperties(userDTO, one, true);
            save(one);
        } else {
            throw new ServiceException(Constants.CODE_600, "用户已存在");
        }
        return one;
    }

    @Override
    public IPage<User> findPage(Page<User> page, String username, String email, String address) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.orderByDesc("id");
        if (!username.equals("")) {
            qw.like("username", username);
        }
        if (!email.equals("")) {
            qw.like("email", email);
        }
        if (!address.equals("")) {
            qw.like("address", address);
        }
        IPage<User> list = userMapper.selectPage(page, qw);

        for (User user : list.getRecords()) {
            if ( user != null || user.getAvatarUrl() != null || !user.getAvatarUrl().equals(""))
            {
                user.setAvatarUrl(Host+user.getAvatarUrl());
            }
            if (user.getRole() != null && !user.getRole().equals("")) {
                QueryWrapper<Role> qwo = new QueryWrapper<>();
                String na = roleMapper.selectOne(qwo.eq("flag", user.getRole())).getName();
                user.setRoleName(na);
            }
        }
        return list;
    }

    @Override
    public String updatePassword(UserPasswordDTO user) {
        if (user.getId() == 0) {
            return "帐号异常" ;
        }
        //先验证旧密码
        UserDTO rqU = new UserDTO();
        rqU.setId(user.getId());
        rqU.setUsername(user.getUsername());
        rqU.setPassword(user.getPassword());
        User oldUser = getUserInfo(rqU);
        if (oldUser == null || oldUser.getId() == 0) {
            return "旧密码错误" ;
        }
        if (userMapper.updatePassword(user)) {
            return "" ;
        }
        return "修改异常!" ;
    }

    private User getUserInfo(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper); // 从数据库查询用户信息
        } catch (Exception e) {
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }

    /**
     * 获取当前角色的菜单列表
     *
     * @param roleFlag
     * @return
     */
    private List<Menu> getRoleMenus(String roleFlag) {
        Integer roleId = roleMapper.selectByFlag(roleFlag);
        // 当前角色的所有菜单id集合
        List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);

        // 查出系统所有的菜单(树形)
        List<Menu> menus = menuService.findMenus("");
        // new一个最后筛选完成之后的list
        List<Menu> roleMenus = new ArrayList<>();
        // 筛选当前用户角色的菜单
        for (Menu menu : menus) {
            if (menuIds.contains(menu.getId())) {
                roleMenus.add(menu);
            }
            List<Menu> children = menu.getChildren();
            // removeIf()  移除 children 里面不在 menuIds集合中的 元素
            children.removeIf(child -> !menuIds.contains(child.getId()));
        }
        return roleMenus;
    }

}

