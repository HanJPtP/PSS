package com.woniu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

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
public class PssApproval implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String approvalno;

    private Integer userid;

    private Integer deptid;
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(shape = JsonFormat.Shape.STRING,timezone ="GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime starttime;
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(shape = JsonFormat.Shape.STRING,timezone ="GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endtime;

    private Integer leavetypeid;

    private Integer approvalid;
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(shape = JsonFormat.Shape.STRING,timezone ="GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime leavetime;
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(shape = JsonFormat.Shape.STRING,timezone ="GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime caremakeuptime;

    private Double leavealltime;

    private Integer status;

    private String notes;
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    @JsonFormat(shape = JsonFormat.Shape.STRING,timezone ="GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approvaltime;

    private String leaveresoult;
    @TableField(exist = false)
    private PssUsers users;
    @TableField(exist = false)
    private PssLeavetype leavetype;
    @TableField(exist = false)
    private PssApprovaltype approvaltype;
}
