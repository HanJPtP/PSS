package com.woniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

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
public class PssOrderElec implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 抄写人编号
     */
    private String userno;

    /**
     * 区域编号
     */
    private String arearno;

    /**
     * 电表编号
     */
    private String electicno;

    /**
     * 电表起始码
     */
    private String startnumber;

    /**
     * 电表当前码
     */
    private String endnumber;

    /**
     * 用电量
     */
    private Double electicsum;

    /**
     * 抄写时间
     */
    private Date edittime;

    /**
     * 用电有无异常（0无，1有）
     */
    private Integer isexception;

    /**
     * 异常情况描述
     */
    private String exception;

    /**
     * 备注
     */
    private String other;


}
