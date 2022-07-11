package com.woniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class PssCourts implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String cname;

    private Integer usersid;

    private String transformernum;

    private String transformercap;

    private Integer vnumber;

    private Integer vlength;


}
