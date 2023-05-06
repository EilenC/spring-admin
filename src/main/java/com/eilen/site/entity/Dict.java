package com.eilen.site.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author eilen
 * @since 2023-05-04 02:14:48
 */
@TableName("sys_dict")
@Data
public class Dict {

  private String name;
  private String value;
  private String type;

}
