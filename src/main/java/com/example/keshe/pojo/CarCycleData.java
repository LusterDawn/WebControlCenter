package com.example.keshe.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarCycleData {
    /**
     *车辆编号
     */
    private String num;
    /**
     *运行次数
     */
    private String id;
    /**
     *控制周期
     */
    private String controlCycle;
    /**
     *左编码器
     */
    private String encoderLeft;
    /**
     * 右编码器
     */
    private String encoderRight;
    /**
     * 左电机控制量
     */
    private String motorControlLeft;
    /**
     * 右电机控制量
     */
    private String motorControlRight;
    /**
     * 左电机目标
     */
    private String motorTargetLeft;
    /**
     * 右电机目标
     */
    private String motorTargetRight;
    /**
     * 舵机控制量
     */
    private String steeringGearControl;
    /**
     *偏移中心量
     */
    private String offCenter;
    /**
     *左电感值
     */
    private String electromagneticLeft;
    /**
     *中电感值
     */
    private String electromagneticMid;
    /**
     *右电感值
     */
    private String electromagneticRight;
    /**
     *加速度X
     */
    private String accelerationX;
    /**
     *加速度Y
     */
    private String accelerationY;
    /**
     *加速度Y
     */
    private String accelerationZ;
    /**
     *陀螺仪X
     */
    private String gyroscopeX;
    /**
     *陀螺仪Y
     */
    private String gyroscopeY;
    /**
     *陀螺仪Z
     */
    private String gyroscopeZ;
    /**
     *磁力计X
     */
    private String magnetometerX;
    /**
     *磁力计Y
     */
    private String magnetometerY;
    /**
     *磁力计Z
     */
    private String magnetometerZ;
    /**
     *舵机P
     */
    private String steeringKp;
    /**
     *舵机I
     */
    private String steeringKi;
    /**
     *舵机D
     */
    private String steeringKd;
    /**
     *电机P
     */
    private String servoKp;
    /**
     *电机I
     */
    private String servoKi;
    /**
     *电机D
     */
    private String servoKd;
    /**
     *速度
     */
    private String speed;
    /**
     *预留字段1
     */
    private String extraOne;
    /**
     *预留字段2
     */
    private String extraTwo;
}