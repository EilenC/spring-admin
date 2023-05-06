package com.eilen.site.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eilen.site.controller.dto.UserPasswordDTO;
import com.eilen.site.entity.User;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author eilen
 * @since 2023-04-28 05:07:20
 */
public interface UserMapper extends BaseMapper<User> {
    @Update("update sys_user set password = #{newPassword} where username = #{username} and password = #{password}")
    boolean updatePassword(UserPasswordDTO dto);
}
