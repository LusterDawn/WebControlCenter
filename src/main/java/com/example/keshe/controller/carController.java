package com.example.keshe.controller;

import com.example.keshe.pojo.CarCycleData;
import com.example.keshe.service.ICarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/manage/")
public class carController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ICarService iCarService;

    @GetMapping("selectCoord")
    public HashMap<String, Object> selectCoord(){
        return iCarService.selectCoord();
    }

    //获取小车的数据（输入页数）
    @PostMapping("selectCoordByToPage")
    public ArrayList<CarCycleData> selectCoordByToPage(@RequestBody Map<String, Object> map){
        int pageNum = Integer.parseInt(map.get("topage").toString());
        return iCarService.selectCoordByPage(pageNum);
    }

    //获取小车的数据
    @PostMapping("selectCoordByPage")
    public ArrayList<CarCycleData> selectCoordByPage(@RequestBody Map<String, Object> map){
        int pageNum = Integer.parseInt(map.get("nowpage").toString());
        return iCarService.selectCoordByPage(pageNum);
    }


    //获取小车轨迹坐标
    @GetMapping("getXYData")
    public float[] getXYData(){//ArrayList<ArrayList<String>>
        return iCarService.getXYData();
    }


    //数据相关分析1
    @GetMapping("getOffCenterData")
    public ArrayList<ArrayList<String>> getOffCenterData(){
        return iCarService.getOffCenterData();
    }


    //数据相关分析2
    @GetMapping("getMotorBiasData")
    public ArrayList<ArrayList<String>> getMotorBiasData(){
        return iCarService.getMotorBiasData();
    }

    @PostMapping("sendPID")
    public String sendPID(@RequestBody Map<String, Object> map){

        String P = (String) map.get("p");
        String I = (String) map.get("i");
        String D = (String) map.get("d");
        System.out.println("p :" + P + " i :" + I + " D :" + D);
        iCarService.sendPID(P, I, D);
        return "success";
    }

    @PostMapping("sendMPID")
    public String sendMPID(@RequestBody Map<String, Object> map){
        String MP = (String) map.get("p");
        String MI = (String) map.get("i");
        String MD = (String) map.get("d");
        logger.info("Mp :" + MP + " Mi :" + MI + " MD :" + MD);
        iCarService.sendMPID(MP, MI, MD);
        return "success";
    }

    @PostMapping("sendINPID")
    public String sendINPID(@RequestBody Map<String, Object> map){
        String INP = (String) map.get("p");
        String INI = (String) map.get("i");
        String IND = (String) map.get("d");
        System.out.println("IN_p :" + INP + " In_i :" + INI + " IN_D :" + IND);
        iCarService.sendINPID(INP, INI, IND);
        return "success";
    }

    //设置速度
    @PostMapping("sendSP")
    public String sendSpeed(@RequestBody Map<String, Object> map){
        String speed = (String) map.get("speednum");
        logger.info("写入的速度为： " + speed);
        iCarService.sendSpeed(speed);
        return "success";
    }

    //启动小车
    @GetMapping("start")
    public String start(){
        iCarService.startOrStop(true);
        return "success";
    }

    //停止小车
    @GetMapping("stop")
    public String stop(){
        iCarService.startOrStop(false);
        return "success";
    }

    //打开socket
    @GetMapping("startSocket")
    public String startSocket(){
        iCarService.startOrStopSocket(true);
        return "success";
    }

    //关闭socket
    @GetMapping("stopSocket")
    public String stopSocket(){
        iCarService.startOrStopSocket(false);
        return "success";
    }

    //清空数据
    @GetMapping("delete")
    public String deleteData(){
        iCarService.delete();
        return "success";
    }

    @PostMapping("sendOffset")
    public String sendOffset(@RequestBody Map<String, Object> map){
        float offsetX = Float.parseFloat(map.get("offset_x").toString());
        float offsetY = Float.parseFloat(map.get("offset_y").toString());
        iCarService.sendOffset(offsetX, offsetY);
        return "success";
    }

    //数据分析
    @RequestMapping("analyse")
    public ArrayList<Integer> analyse(){


        return null;
    }


    //获取车对应的次数
    @GetMapping("getNumAndId")
    public HashMap<String, ArrayList<String>> getNumAndId(){
        return iCarService.getNumAndId();
    }

    //修正轨迹
    @PostMapping("updateCoordinate")
    public float[] updateCoordinate(@RequestBody Map<String, Object> map){//ArrayList<ArrayList<String>>
        String carId = (String)map.get("carvalue");
        String runId = (String)map.get("runvalue");
        return iCarService.updateCoordinate(carId, runId);
    }

    
    //文件上传
    @PostMapping("upload")
    public String uploadFile(MultipartFile file){
        try {
            if (file.isEmpty()){
                return "文件为空";
            }
            //获取文件名
            String fileName = file.getOriginalFilename();
            //获取文件后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            //设置文件存储路径
            String filePath = "/www/keshe/";
            String path = filePath + fileName;
            File dest = new File(path);
            //检测是否存在该目录
            if (!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            //写入文件
            file.transferTo(dest);
            iCarService.upload(path);

            dest.delete();
            return "上传成功！";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }
}
