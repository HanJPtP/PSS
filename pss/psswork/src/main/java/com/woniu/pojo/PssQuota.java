package com.woniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuqi
 * @since 2022-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PssQuota implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("quotaName")
    private String quotaName;

    @TableField("quotaStand")
    private String quotaStand;

    @TableField("quotaContex")
    private String quotaContex;

    @TableField("totalSco")
    private Double totalSco;

    private Double weight;

    private Double score;

}
