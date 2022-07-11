package com.woniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
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
public class PssOrderTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 工单编号
     */
    private String orderno;

    /**
     * 工单类型(外键)
     */
    private Integer ordertypeid;

    /**
     * 发起人（外键）
     */
    private String userno;

    /**
     * 工作任务
     */
    private String worktask;

    /**
     * 开始时间
     */
    private Date starttime;

    /**
     * 结束时间
     */
    private Date endtime;

    /**
     * 0未发放，1未接收，2未完成，3已完成
     */
    private Integer orderstatus;

    /**
     * 工单备注
     */
    private String ordermsg;

    /**
     * -5 --10分工单评分
     */
    private Integer grade;

    //工单类型对象
    @TableField(exist = false)
    private PssOrderType pssOrderType;


}
