package com.woniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

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
 * @since 2022-05-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PssPerf implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Double score;

    private String quotaName;

    private String appname;

    private Double weight;

    private Integer staffsid;

    public PssPerf(Double score, String quotaName, String appname, Double weight, Integer staffsid) {
        this.score = score;
        this.quotaName = quotaName;
        this.appname = appname;
        this.weight = weight;
        this.staffsid = staffsid;
    }
}
