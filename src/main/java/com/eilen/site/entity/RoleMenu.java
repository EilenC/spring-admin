package com.eilen.site.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色菜单关系表
 * </p>
 *
 * @author eilen
 * @since 2023-05-04 02:06:24
 */
@TableName("sys_role_menu")
@Data
public class RoleMenu {

  private Integer roleId;
  private Integer menuId;

}
