package com.eilen.site.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author eilen
 * @since 2023-05-06 10:46:40
 */
@Getter
@Setter
@TableName("t_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 内容
     */
    private String content;

    /**
     * 评论人id
     */
    private Integer userId;

    /**
     * 评论时间
     */
    private String time;

    /**
     * 父id
     */
    private Integer pid;

    /**
     * 最上级评论id
     */
    private Integer originId;

    /**
     * 关联文章的id
     */
    private Integer articleId;

    @TableField(exist = false)
    private String nickname;
    @TableField(exist = false)
    private String avatarUrl;

    @TableField(exist = false)
    private List<Comment> children;

    @TableField(exist = false)
    private String pNickname;  // 父节点的用户昵称
    @TableField(exist = false)
    private Integer pUserId;  // 父节点的用户id
}
