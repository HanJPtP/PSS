package com.woniu.pojo;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("pss_perms")
@EqualsAndHashCode(callSuper = false)
public class PssPerms implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 权限名
     */
    private String name;

    /**
     * 权限编号
     */
    private String code;

    /**
     * 链接跳转路径
     */
    private String link;

    /**
     * 主菜单
     */
    private Integer parentid;

    /**
     * m：菜单；p：数据
     */
    private String type;

    /**
     * 1：正常；2：锁定
     */
    private String status;

    /**
     * 用户权限
     */
    private String percode;

    /**
     * 按钮格式
     */
    private String icon;

    @TableField(exist = false)
    private List<PssPerms> childList;

    @Override
    public String toString() {
        return "PssPerms{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", link='" + link + '\'' +
                ", parentid=" + parentid +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", percode='" + percode + '\'' +
                ", icon='" + icon + '\'' +
                '}'+'\n';
    }
}
