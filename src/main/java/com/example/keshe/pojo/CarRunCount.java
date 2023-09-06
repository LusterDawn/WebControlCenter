package com.example.keshe.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarRunCount {
    /**
     *车辆编号
     */
    private String num;
    /**
     *运行次数
     */
    private String id;
}
