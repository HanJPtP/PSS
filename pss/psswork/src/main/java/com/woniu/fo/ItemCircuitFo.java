package com.woniu.fo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCircuitFo {
    List<CircuitFo> circuitItem;
}
