package com.example.keshe.dao;

import com.example.keshe.pojo.CarCycleData;
import com.example.keshe.pojo.CarRunCount;
import com.example.keshe.pojo.Coordinate;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarCycleDataMapper {
    @Insert("insert into car_cycle_data (num, id, control_cycle, encoder_left, encoder_right, motor_control_left, motor_control_right, motor_target_left, motor_target_right, steering_gear_control, off_center, electromagnetic_left, electromagnetic_mid, electromagnetic_right, acceleration_x, acceleration_y, acceleration_z, gyroscope_x, gyroscope_y, gyroscope_z, magnetometer_x, magnetometer_y, magnetometer_z, steering_KP, steering_KI, steering_KD, servo_KP, servo_KI, servo_KD, speed, extra_one, extra_two) values (#{num,jdbcType=VARCHAR}, #{id,jdbcType=VARCHAR}, #{controlCycle,jdbcType=VARCHAR}, #{encoderLeft,jdbcType=VARCHAR}, #{encoderRight,jdbcType=VARCHAR}, #{motorControlLeft,jdbcType=VARCHAR}, #{motorControlRight,jdbcType=VARCHAR}, #{motorTargetLeft,jdbcType=VARCHAR}, #{motorTargetRight,jdbcType=VARCHAR}, #{steeringGearControl,jdbcType=VARCHAR}, #{offCenter,jdbcType=VARCHAR}, #{electromagneticLeft,jdbcType=VARCHAR}, #{electromagneticMid,jdbcType=VARCHAR}, #{electromagneticRight,jdbcType=VARCHAR}, #{accelerationX,jdbcType=VARCHAR}, #{accelerationY,jdbcType=VARCHAR}, #{accelerationZ,jdbcType=VARCHAR}, #{gyroscopeX,jdbcType=VARCHAR}, #{gyroscopeY,jdbcType=VARCHAR}, #{gyroscopeZ,jdbcType=VARCHAR}, #{magnetometerX,jdbcType=VARCHAR}, #{magnetometerY,jdbcType=VARCHAR}, #{magnetometerZ,jdbcType=VARCHAR}, #{steeringKp,jdbcType=VARCHAR}, #{steeringKi,jdbcType=VARCHAR}, #{steeringKd,jdbcType=VARCHAR}, #{servoKp,jdbcType=VARCHAR}, #{servoKi,jdbcType=VARCHAR}, #{servoKd,jdbcType=VARCHAR}, #{speed,jdbcType=VARCHAR}, #{extraOne,jdbcType=VARCHAR}, #{extraTwo,jdbcType=VARCHAR})")
    int insert(CarCycleData record);

    @Select("select * from car_cycle_data")
    List<CarCycleData> select();

    @Delete("delete from car_cycle_data")
    void drop();


    @Select("select * from car_cycle_data order by control_cycle desc limit 1")
    CarCycleData selectLastByNum();

    @Select("select count(id) from car_cycle_data")
    int selectTotal();

    @Select("select * from car_cycle_data where num = #{num} and id = #{id}")
    List<CarCycleData> selectByNumAndId(@Param("num")String num, @Param("id")String id);

    @Select("select distinct num, id from car_cycle_data order by num, id")
    List<CarRunCount> selectCarIdAndNum();

    @Select("select max(CAST(magnetometer_x AS SIGNED)) as xMax, min(CAST(magnetometer_x AS SIGNED)) as xMin, max(CAST(magnetometer_y AS SIGNED)) as yMax, min(CAST(magnetometer_y AS SIGNED)) as yMin from car_cycle_data where num = #{num} and id = #{id}")
    Coordinate selectMaxAndMin(@Param("num")String num, @Param("id")String id);
}
