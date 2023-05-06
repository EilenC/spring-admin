package com.eilen.site.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author eilen
 * @since 2023-05-05 04:47:55
 */
@Getter
@Setter
  public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 标题
     */
      private String name;

      /**
     * 内容
     */
      private String content;

      /**
     * 发布人
     */
      private String user;

      /**
     * 发布时间
     */
      private String time;


}
