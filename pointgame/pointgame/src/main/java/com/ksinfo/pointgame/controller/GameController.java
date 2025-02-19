package com.ksinfo.pointgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ksinfo.pointgame.dto.GameDTO;
import com.ksinfo.pointgame.service.GameService;

@Controller
@RequestMapping("/")
@SessionAttributes("gameDto")
public class GameController {
    @Autowired
    GameService gameService;
    
    @GetMapping("/gameinit")
    public String gameInit(@RequestParam("memberId") String memberId, Model model) {
    	GameDTO gameDto = new GameDTO();
    	gameDto.setMemberId(memberId);
    	gameService.gameInfoInit(gameDto);
    	
    	model.addAttribute("gameDto", gameDto);
    	
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
    public String gamePlay(@SessionAttribute("gameDto")GameDTO sessionGameDto,@ModelAttribute GameDTO gameDto, @RequestParam("memberId") String memberId, Model model) {
    	gameDto.setMemberId(memberId);
    	model.addAttribute("point", sessionGameDto.getPoint());
    	model.addAttribute("gameResult", sessionGameDto.getGameResult());
    	model.addAttribute("hiddenNum", sessionGameDto.getHiddenNum());
    	
    	gameDto.setGameCount(sessionGameDto.getGameCount());
    	gameDto.setGameActFlg(sessionGameDto.getGameActFlg());
    	gameDto.setHiddenNum(sessionGameDto.getHiddenNum());
    	String inputNum = gameDto.getInputNum();
    	
    	System.out.println("Hidden Number: "+ sessionGameDto.getHiddenNum());
    	System.out.println("User input Number: " + inputNum);
    	
    	//game result viewing
        model.addAttribute("gameResult", gameDto.getGameResult());
        System.out.println("gameResult List: "+ gameDto.getGameResult());
    	
    	//result check service
    	gameService.gamePlay(gameDto);
    	
    	model.addAttribute("gameResult", gameDto.getGameResult());
    	model.addAttribute("point", gameDto.getPoint());
    	
    	return "gameplay";
    }
    
    
}
