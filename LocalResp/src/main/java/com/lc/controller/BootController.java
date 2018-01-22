package com.lc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boot")
public class BootController {

    @GetMapping("/getSuccess")
    public String boot(){
        return "boot success!";
    }

}
