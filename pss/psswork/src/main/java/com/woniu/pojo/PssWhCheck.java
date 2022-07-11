package com.woniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@EqualsAndHashCode(callSuper = false)
public class PssWhCheck implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer adminid;

    private LocalDateTime checktime;

    private String checkdata;

    @TableField("CHECKstatus")
    private Integer CHECKstatus;

    private String orderno;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String tools;

    @TableField(exist = false)
    private Integer nums;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(exist = false)
    private Date borrowtime;
    @TableField(exist = false)
    private String account;
    @TableField(exist = false)
    private String dataname;

    @TableField(exist = false)
    private String standard;

}
