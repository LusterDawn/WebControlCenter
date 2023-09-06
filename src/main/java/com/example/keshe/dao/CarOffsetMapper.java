package com.example.keshe.dao;

import com.example.keshe.pojo.CarOffset;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface CarOffsetMapper {

    @Insert("insert into car_offset (car_id, offset_x, offset_y) values (#{carId,jdbcType=INTEGER}, #{offsetX,jdbcType=VARCHAR}, #{offsetY,jdbcType=VARCHAR})")
    int insert(CarOffset car);

    @Update("update car_offset set offset_x = {offsetX}, offset_y = #{offsetY} where car_id = #{car_id}")
    int update(CarOffset car);

    @Select("select * from car_offset where car_id = #{carId}")
    CarOffset select(@Param("carId")int carId);

    @Select("select count(*) from car_offset WHERE car_id = #{carId} limit 1")
    int selectIfExit(@Param("carId")int carId);
}
