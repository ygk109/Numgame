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

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
@SessionAttributes("gameDto")
public class GameController {
    @Autowired
    GameService gameService;
    
    @GetMapping("/gameinit")
    public String gameInit(@RequestParam("memberId") String memberId, Model model, HttpSession session) {
    	GameDTO gameDto = new GameDTO();
    	gameDto.setMemberId(memberId);
    	gameService.gameInfoInit(gameDto);
    	
    	model.addAttribute("gameDto", gameDto);

        session.setAttribute("gameDto", gameDto);
        
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
    public String gamePlay(@RequestParam("memberId") String memberId, @RequestParam("inputNum") String inputNum, Model model, HttpSession session) {
    	GameDTO gameDto = (GameDTO) session.getAttribute("gameDto");
    	gameDto.setInputNum(inputNum);
    	
    	model.addAttribute("point", gameDto.getPoint());
    	model.addAttribute("hiddenNum", gameDto.getHiddenNum());
    	
    	System.out.println("gameDto값 확인:" + gameDto);
    	System.out.println("gameDto memberId 값" + gameDto.getMemberId());
    	System.out.println("Hidden Number of gameDTO: "+ gameDto.getHiddenNum());
    	System.out.println("User input Number of gameDTO: " + gameDto.getInputNum());
    	
    	//game result viewing
        model.addAttribute("gameResult", gameDto.getGameResult());
        System.out.println("gameResult List: "+ gameDto.getGameResult());
    	
    	//result check service
    	gameService.gamePlay(gameDto);
    	
    	session.setAttribute("gameDto", gameDto);
    	
    	//pointInfo, gameResult List view
    	model.addAttribute("point", gameDto.getPoint());
    	model.addAttribute("gameResult", gameDto.getGameResult());
    	
    	//game finish Msg
    	if(gameDto.getGameActFlg() == 1 && gameDto.getRewardPoint() > 0) {
    		System.out.println("Controller gameActFlg: "+ gameDto.getGameActFlg());
    		String msg = "挑戦に成功しました。" + gameDto.getRewardPoint() + "ポイント支給";
    		model.addAttribute("msg", "1");
    		model.addAttribute("finishMsg", msg);
    		System.out.println(msg);
    	}else if(gameDto.getGameActFlg() == 1 && gameDto.getRewardPoint() == 0) {
    		String msg = "挑戦に失敗しました";
    		model.addAttribute("msg", "1");
    		model.addAttribute("finishMsg", msg);
    		System.out.println(msg);
    	}
    	
    	return "gameplay";
    }
    
    
}
