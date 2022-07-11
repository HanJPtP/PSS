package com.woniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuqi
 * @since 2022-05-05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("pssUser")
@EqualsAndHashCode(callSuper = false)
public class PssUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    @Id
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户名
     */
    private String username;

    /**
     * 工号
     */
    private String jobno;

    /**
     * 头像
     */
    private String image;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 状态：y.正常；n.锁住
     */
    private String status;

    /**
     * 状态：1.在职；2.离职
     */
    private String state;

    /**
     * 最近登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime loginTime;

    /**
     * 最近登录IP
     */
    private String loginIp;


    /**
     * 职位id
     */
    private Integer pId;


}
