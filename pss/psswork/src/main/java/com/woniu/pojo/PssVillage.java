package com.woniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuqi
 * @since 2022-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PssVillage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer circuitid;

    private String village;

    @TableField(exist = false)
    private PssCourts pssCourts;
    @TableField(exist = false)
    private PssCircuit pssCircuit;
    @TableField(exist = false)
    private PssUsers pssUsers;

//    @TableField(exist = false)
//    private String circuit;
//    @TableField(exist = false)
//    private String cname;
//    @TableField(exist = false)
//    private String transformernum;
//    @TableField(exist = false)
//    private String transformercap;
//    @TableField(exist = false)
//    private Integer vnumber;
//    @TableField(exist = false)
//    private Integer vlength;
//    @TableField(exist = false)
//    private String username;


}
