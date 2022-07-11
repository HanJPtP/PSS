package com.woniu.fo;

import com.woniu.pojo.PssTestType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
/**
 * 用来存值
 *  上传的数据,传递下来的数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCountsFo {
    private PssTestType pssTestType;
    private Integer counts;
}
