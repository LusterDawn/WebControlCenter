package com.example.keshe.controller;

import com.example.keshe.service.IDirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/dirction/")
public class dirctionController {


    @Autowired
    private IDirectionService iDirectionService;

    //方向控制
    @GetMapping("front")
    public void front(){
        iDirectionService.front();
    }

    @GetMapping("leftFront")
    public void leftFront(){
        iDirectionService.leftFront();
    }

    @GetMapping("rightFront")
    public void rightFront(){
        iDirectionService.rightFront();
    }

    @GetMapping("comeBack")
    public void comeBack(){
        iDirectionService.comeBack();
    }

    @GetMapping("leftBack")
    public void leftBack(){
        iDirectionService.leftBack();
    }

    @GetMapping("rightBack")
    public void rightBack(){
        iDirectionService.rightBack();
    }
}
