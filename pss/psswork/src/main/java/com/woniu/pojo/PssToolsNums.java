package com.woniu.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2022-05-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PssToolsNums implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String orderno;

    private Integer ordertypeid;

    private String userno;

    private String username;

    private String tools;
    private Integer nums;

    private Date borrowtime;

    private Date returntime;

    private Integer status;

    @TableField(exist = false)
    private  String typename;//类别名

    @TableField(exist = false)
    private  String dataname;//部门名

    @TableField(exist = false)
    private  String standard;//部门名

    @TableField(exist = false)
    private Integer whhouseid;
}
