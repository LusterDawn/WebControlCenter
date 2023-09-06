package com.example.keshe.service.impl;

import com.example.keshe.service.IDirectionService;
import com.example.keshe.utils.SocketCommunicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iDirctionService")
public class DirectionServiceImpl implements IDirectionService {

    @Autowired
    SocketCommunicate socketCommunicate;

    @Override
    public void front(){
        socketCommunicate.sendMsg("CON_FW<");
    }

    @Override
    public void leftFront(){
        socketCommunicate.sendMsg("F_LEFT<");
    }

    @Override
    public void rightFront(){
        socketCommunicate.sendMsg("F_RIGHT<");
    }

    @Override
    public void comeBack(){
        socketCommunicate.sendMsg("CON_BK<");
    }

    @Override
    public void leftBack(){
        socketCommunicate.sendMsg("B_LEFT<");
    }

    @Override
    public void rightBack(){
        socketCommunicate.sendMsg("B_RIGHT<");
    }
}
