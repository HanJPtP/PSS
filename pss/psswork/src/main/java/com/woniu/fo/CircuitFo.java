package com.woniu.fo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CircuitFo {
    private Integer id;
    private String circuit;
    private Integer courtsid;
    private String village;
}
