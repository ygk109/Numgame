package com.ksinfo.pointgame.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public class GameController {
    @GetMapping("/gameplay")
    public String gameplay() {
        return "gameplay";
    }
}
