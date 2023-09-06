package com.example.keshe.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarOffset {
    /**
     *车辆编号
     */
    private int carId;

    /**
     *车辆X轴的offset
     */
    private float offsetX;

    /**
     *车辆Y轴的offset
     */
    private float offsetY;
}
