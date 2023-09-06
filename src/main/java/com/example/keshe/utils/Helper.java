package com.example.keshe.utils;


import com.example.keshe.dao.CarCycleDataMapper;
import com.example.keshe.dao.CarOffsetMapper;
import com.example.keshe.pojo.CarCycleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class Helper {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    CarCycleDataMapper carCycleDataMapper;

    @Autowired
    CarOffsetMapper carOffsetMapper;

    private static float origin_x = 0.0f;

    private static float origin_y = 0.0f;


    public static void setOrigin(float originX, float originY){
        origin_x = originX;
        origin_y = originY;
    }

    public void help(String msg){

        String[] data = msg.split(",");
        CarCycleData car = buildCarCycleData(data);

        //写入redis缓存
        redisTemplate.opsForList().leftPush("FieldOffCenter", car.getOffCenter());
        redisTemplate.opsForList().leftPush("FieldSteeringGearControl", car.getSteeringGearControl());
        redisTemplate.opsForList().leftPush("FieldMotorBias", String.valueOf(Integer.parseInt(car.getMotorControlLeft()) - Integer.parseInt(car.getMotorControlRight())));
        updateCoordinate(car.getMagnetometerX(), car.getMagnetometerY(), car.getEncoderLeft(), car.getEncoderRight(), 0.0, 0.0);

        //写入mysql
        carCycleDataMapper.insert(car);
    }

    public void updateCoordinate(String magnetometerX, String magnetometerY, String encoderLeft, String encoderRight, Double offset_x, Double offset_y){
        //System.out.println("magnetometerX："+ magnetometerX + "  magnetometerY:" + magnetometerY +" encoderLeft："+ encoderLeft + "  encoderRight: " + encoderRight);
        // 简单的坐标解算（精度不高）
        float megaY = -Float.parseFloat(magnetometerX);
        float megaX = Float.parseFloat(magnetometerY);
        float leftEncoder = Float.parseFloat(encoderLeft);
        float rightEncoder = Float.parseFloat(encoderRight);
        float dist = (float) ((leftEncoder + rightEncoder)*2.5);
        float angle = (float) Math.atan2(megaX - offset_x, megaY - offset_y);
        float x = (float) (origin_x + dist * Math.cos(angle)*(0.207/1040.0));
        float y = (float) (origin_y + dist * Math.sin(angle)*(0.207/1040.0));
        origin_x = x;   // 给下一个要解算的点用的数据
        origin_y = y;   // 给下一个要解算的点用的数据
        redisTemplate.opsForList().rightPush("FieldCurrentX", String.valueOf(x));
        redisTemplate.opsForList().rightPush("FieldCurrentY", String.valueOf(y));
        System.out.println("x轴坐标："+ x + "  y轴坐标: " + y);
    }

    private CarCycleData buildCarCycleData(String[] data) {
        CarCycleData car = new CarCycleData();
        car.setNum(data[0]);
        car.setId(data[1]);
        car.setControlCycle(data[2]);
        car.setEncoderLeft(data[3]);
        car.setEncoderRight(data[4]);
        car.setMotorControlLeft(data[5]);
        car.setMotorControlRight(data[6]);
        car.setMotorTargetLeft(data[7]);
        car.setMotorTargetRight(data[8]);
        car.setSteeringGearControl(data[9]);
        car.setOffCenter(data[10]);
        car.setElectromagneticLeft(data[11]);
        car.setElectromagneticMid(data[12]);
        car.setElectromagneticRight(data[13]);
        car.setAccelerationX(data[14]);
        car.setAccelerationY(data[15]);
        car.setAccelerationZ(data[16]);
        car.setGyroscopeX(data[17]);
        car.setGyroscopeY(data[18]);
        car.setGyroscopeZ(data[19]);
        car.setMagnetometerX(data[20]);
        car.setMagnetometerY(data[21]);
        car.setMagnetometerZ(data[22]);
        car.setSteeringKp(data[23]);
        car.setSteeringKi(data[24]);
        car.setSteeringKd(data[25]);
        car.setServoKp(data[26]);
        car.setServoKi(data[27]);
        car.setServoKd(data[28]);
        car.setSpeed(data[29]);
        car.setExtraOne(data[30]);
        car.setExtraTwo(data[31]);
        return car;
    }
}


