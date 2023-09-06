package com.example.keshe.service;

import com.example.keshe.pojo.CarCycleData;

import java.util.ArrayList;
import java.util.HashMap;

public interface ICarService {

    HashMap<String, Object> selectCoord();

    ArrayList<CarCycleData> selectCoordByPage(int pageNum);

    default float[] getXYData() {//ArrayList<ArrayList<String>>
        return null;
    }

    ArrayList<ArrayList<String>> getOffCenterData();

    ArrayList<ArrayList<String>> getMotorBiasData();

    void sendPID(String p, String i, String d);

    void sendMPID(String mp, String mi, String md);

    void sendINPID(String inp, String ini, String ind);

    void sendSpeed(String speed);

    void startOrStop(boolean bool);

    void startOrStopSocket(boolean bool);

    void delete();

    void sendOffset(float offsetX, float offsetY);//    void sendOffset(String offsetX, String offsetY);

    ArrayList<Integer> analyse();

    float[] updateCoordinate(String num, String id);//ArrayList<ArrayList<String>>

    void upload(String path);

    HashMap<String, ArrayList<String>> getNumAndId();
}
