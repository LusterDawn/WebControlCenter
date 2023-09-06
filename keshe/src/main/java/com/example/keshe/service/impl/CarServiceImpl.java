package com.example.keshe.service.impl;

import com.example.keshe.dao.CarCycleDataMapper;
import com.example.keshe.dao.CarOffsetMapper;
import com.example.keshe.pojo.CarCycleData;
import com.example.keshe.pojo.CarOffset;
import com.example.keshe.pojo.CarRunCount;
import com.example.keshe.pojo.Coordinate;
import com.example.keshe.service.ICarService;
import com.example.keshe.utils.Helper;
import com.example.keshe.utils.SocketCommunicate;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service("iCarService")
public class CarServiceImpl implements ICarService {

    @Autowired
    CarCycleDataMapper ccd;
    
    @Autowired
    CarOffsetMapper carOffsetMapper;

    @Autowired
    SocketCommunicate socketCommunicate;

    @Autowired
    // 本项目用到的 5 个redis键值对都是<字符串字段，字符串数值>
    private RedisTemplate redisTemplate;

    @Autowired
    private Helper helper;


    @Override
    @Transactional
    public HashMap<String, Object> selectCoord() {
        HashMap<String, Object> res = new HashMap<>();
        int count = ccd.selectTotal();
        CarCycleData data = ccd.selectLastByNum();
        res.put("totalpage", count);
        res.put("controldata", data);
        return res;
    }

    @Override
    public ArrayList<CarCycleData> selectCoordByPage(int pageNum){
        //设置分页条数
        PageHelper.startPage(pageNum, 1);
        return (ArrayList<CarCycleData>) ccd.select();
    }



    /* 数据传输性能取决于下面的三个函数是如何实现的，尽量传输更少的数据 */
    // 调试的时候，getXYData不会报错，但是类似的getOffCenterData和getMotorBiasData就整天报错“无法将String转化为float”
    // 原因是getXYData的数据源之前经过了预处理，但其它两个就直接由ccd提供，而ccd里面的成员都是String类型的！
    @Override
    public float[] getXYData() {
        String cx = (String) redisTemplate.opsForList().index("FieldCurrentX", -1);
        String cy = (String) redisTemplate.opsForList().index("FieldCurrentY", -1);
        if (cx == null || cy == null){
            System.out.println("getXYData-Null");
            return null;
        }
        return new float[]{Float.parseFloat(cx), Float.parseFloat(cy)};
    }

//    这两个界面不需要实时更新，所以就不重构了
//    @Override
//    public float[] getOffCenterData() {
//        String offCenter = (String) redisTemplate.opsForList().index("FieldOffCenter",  -1);
//        String steeringGearControl = (String) redisTemplate.opsForList().index("FieldSteeringGearControl", -1);
//        if (offCenter == null || steeringGearControl == null){
//            System.out.println("getOffCenterData-Null");
//            return null;
//        }
//        return new float[]{Float.parseFloat(offCenter), Float.parseFloat(steeringGearControl)};
//    }

//    @Override
//    public float[] getMotorBiasData() {
//        String offCenter = (String) redisTemplate.opsForList().index("FieldOffCenter", -1);
//        String motorBias = (String) redisTemplate.opsForList().index("FieldMotorBias", -1);
//        if (offCenter == null || motorBias == null){
//            System.out.println("getMotorBiasData-Null");
//            return null;
//        }
//        return new float[]{Float.parseFloat(offCenter), Float.parseFloat(motorBias)};
//    }

    @Override
    public ArrayList<ArrayList<String>> getOffCenterData() {
        List<String> offCenter = redisTemplate.opsForList().range("FieldOffCenter", 0, -1);
        List<String> steeringGearControl = redisTemplate.opsForList().range("FieldSteeringGearControl", 0, -1);
        return buildCoordinate(offCenter, steeringGearControl);
    }

    @Override
    public ArrayList<ArrayList<String>> getMotorBiasData() {
        List<String> offCenter = redisTemplate.opsForList().range("FieldOffCenter", 0, -1);
        List<String> motorBias = redisTemplate.opsForList().range("FieldMotorBias", 0, -1);
        return buildCoordinate(offCenter, motorBias);
    }
    @Override
    public void sendPID(String p, String i, String d) {
        socketCommunicate.sendPID(p, i, d);
    }

    @Override
    public void sendMPID(String mp, String mi, String md) {
        socketCommunicate.sendMPID(mp, mi, md);
    }

    @Override
    public void sendINPID(String inp, String ini, String ind){
        socketCommunicate.sendINPID(inp, ini, ind);
    }

    @Override
    public void sendSpeed(String speed){
        socketCommunicate.sendMsg("SP="+speed+"<");
    }

    @Override
    public void startOrStop(boolean bool) {
        if (bool){
            System.out.println("启动");
            socketCommunicate.sendMsg("ING<");
        }else{
            socketCommunicate.sendMsg("STOP<");
        }
    }



    @Override
    public void startOrStopSocket(boolean bool) {
        if (bool){
            socketCommunicate.sendFlagFalse();
            Helper.setOrigin(0.0f, 0.0f);
            socketCommunicate.sendINPID(null,null,null);
            socketCommunicate.sendPID(null,null,null);
            socketCommunicate.sendMPID(null,null,null);
            socketCommunicate.sendMsg(null);
            socketCommunicate.communicate();
        }else{
            socketCommunicate.sendFlag();
            socketCommunicate.close();
        }
    }

    @Override
    public void delete() {
        redisTemplate.delete("FieldOffCenter");
        redisTemplate.delete("FieldSteeringGearControl");
        redisTemplate.delete("FieldMotorBias");
        redisTemplate.delete("FieldCurrentX");
        redisTemplate.delete("FieldCurrentY");
        ccd.drop();
    }

    public ArrayList<ArrayList<String>> buildCoordinate(List<String> a, List<String> b){
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        int num = Math.min(a.size(),b.size());
        for(int i = 0; i < num; i++) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(a.get(i));
            temp.add(b.get(i));
            res.add(temp);
        }
        return res;
    }


    @Override
    public void sendOffset(float offsetX, float offsetY){
        CarOffset car = new CarOffset();
        car.setCarId(1);
        car.setOffsetX(offsetX);
        car.setOffsetY(offsetY);
        //System.out.println("offsetX : " + offsetX + " offsetY : " + offsetY );
    }

    @Override
    public ArrayList<Integer> analyse(){

        return null;
    }

    @Override
    public float[] updateCoordinate(String num, String id){//ArrayList<ArrayList<String>>
        redisTemplate.delete("FieldCurrentX");  // 用完redis中的数据后，需要清除，否则内存会被一直占用
        redisTemplate.delete("FieldCurrentY");
        List<CarCycleData> lists = ccd.selectByNumAndId(num, id);
        if (lists.isEmpty()){
            return null;
        }

        Coordinate coordinate = ccd.selectMaxAndMin(num,id);
        Double offset_y = -(Double.parseDouble(coordinate.getXMax()) + Double.parseDouble(coordinate.getXMin()))/2;
        Double offset_x = (Double.parseDouble(coordinate.getYMax()) + Double.parseDouble(coordinate.getYMin()))/2;
        //System.out.println("offset_x: " + offset_x + " offset_y: "+ offset_y);
        for (CarCycleData car : lists){
            helper.updateCoordinate(car.getMagnetometerX(), car.getMagnetometerY(), car.getEncoderLeft(), car.getEncoderRight(), offset_x, offset_y);
        }
        Helper.setOrigin(0.0f,0.0f);
        return getXYData();
    }

    @Override
    public HashMap<String, ArrayList<String>> getNumAndId(){
        HashMap<String, ArrayList<String>> res = new HashMap<>();
        List<CarRunCount> list = ccd.selectCarIdAndNum();
        for (CarRunCount car : list){
            String num = car.getNum();
            ArrayList<String> temp = null;
            if (res.containsKey(car.getNum())){
                temp = res.get(car.getNum());
            }else{
                temp = new ArrayList<>();
            }
            temp.add(car.getId());
            res.put(car.getNum(), temp);
        }
        return res;
    }

    @Override
    public void upload(String fileName){
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;

            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                socketCommunicate.sendMsg(tempString);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}





