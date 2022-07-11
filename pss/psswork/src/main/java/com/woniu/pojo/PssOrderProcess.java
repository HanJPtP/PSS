package com.woniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.woniu.fo.Tools;
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
public class PssOrderProcess implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 工单编号
     */
    private String orderno;

    /**
     * 工单类型
     */
    private Integer ordertypeid;

    /**
     * 工单标题
     */
    private String ordertitle;

    /**
     * 开始时间
     */
    private Date starttime;

    /**
     * 结束时间
     */
    private Date endtime;

    /**
     * 处理人编号
     */
    private String handlerno;

    /**
     * 负责的区域编号
     */
    private String arearno;

    /**
     * 遇到的问题（图文）
     */
    private String trouble;

    /**
     * 问题处理说明（图文）
     */
    private String solution;

    /**
     * 所需工具数量
     */
    private String nums;

    /**
     * 所需工具编号
     */
    private String tools;

    /**
     * 问题类型(0正常，1维护维修，2抢修装拆，3未处理)
     */
    private Integer problemtype;

    //处理人编号集合
    @TableField(exist = false)
    private List<PssUsers> handlerList;

    //所需工具对象集合
    @TableField(exist = false)
    private List<PssWhdata> pssWhdataList;


    //每种工具对应使用的数量 对象集合
    @TableField(exist = false)
    private List<PssToolsNums> toolsNumsList;

    /**
     * 工单类型名
     */
    @TableField(exist = false)
    private String typename;

    //借用工具详情集合
    @TableField(exist = false)
    private List<Tools> toolsList;



}
