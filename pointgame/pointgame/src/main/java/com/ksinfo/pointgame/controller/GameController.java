package com.ksinfo.pointgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ksinfo.pointgame.dto.GameDTO;
import com.ksinfo.pointgame.service.GameService;

@Controller
@RequestMapping("/")
public class GameController {
    @Autowired
    GameService gameService;
    
    @GetMapping("/gameinit")
    public String gameInit(@RequestParam("memberId") String memberId, Model model) {
    	GameDTO gameDto = new GameDTO();
    	gameDto.setMemberId(memberId);
    	gameService.gameInfoInit(gameDto);
    	//point information viewing
    	model.addAttribute("point", gameDto.getPoint());
        System.out.println("Controller Point: "+ gameDto.getPoint());
        model.addAttribute("hiddenNum", gameDto.getHiddenNum());
        
        //game result viewing
        model.addAttribute("gameResult", gameDto.getGameResult());
        System.out.println("gameResult List: "+ gameDto.getGameResult());
        
    	return "gameplay";
    }
    
    @PostMapping("/gameplay")
    public String gamePlay(@RequestParam("memberId") String memberId, Model model) {
    	
    	return "gameplay";
    }
}
