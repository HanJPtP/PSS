package com.woniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@EqualsAndHashCode(callSuper = false)
public class PssWhInout implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)


    private Integer id;

    private String orderno;


//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

    private Date intime;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

    private Date outtime;

    private Integer inouttype;


    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private Integer nums;
    @TableField(exist = false)
    private String tools;
    @TableField(exist = false)
    private String borrowtime;
    @TableField(exist = false)
    private String returntime;
    @TableField(exist = false)
    private String dataname;
    @TableField(exist = false)
    private String standard;
    @TableField(exist = false)
    private String typename;






}
