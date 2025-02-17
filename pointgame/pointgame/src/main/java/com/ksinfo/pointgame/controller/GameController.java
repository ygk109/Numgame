package com.ksinfo.pointgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ksinfo.pointgame.dto.GameDTO;
import com.ksinfo.pointgame.service.GameService;

@Controller
@RequestMapping("/")
public class GameController {
    @Autowired
    GameService gameService;
    
    @GetMapping("/gameplay")
    public String gameplay(@RequestParam("memberId") String memberId, Model model) {
    	GameDTO gameDto = new GameDTO();
    	gameDto.setMemberId(memberId);
    	gameService.gameInfoInit(gameDto);
    	
    	model.addAttribute("point", gameDto.getPoint());
        System.out.println(gameDto.getPoint());
        
    	return "gameplay";
    }
}
