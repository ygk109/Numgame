package com.ksinfo.pointgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ksinfo.pointgame.dto.MemberDTO;
import com.ksinfo.pointgame.service.MemberService;

@Controller
@RequestMapping("/")
public class MemberController {

		@Autowired
		MemberService memberService;
		
	    @GetMapping("/login")
	    public String loginPage() {
	        return "login";
	    }
	    
	    @PostMapping("/cert")
	    public String login(@ModelAttribute MemberDTO memberDto, Model model) {
	        boolean dbCheck = memberService.login(memberDto);

	    	if(dbCheck) {
	        	model.addAttribute("memberId", memberDto.getMemberId());
	        	model.addAttribute("memberPassword", memberDto.getMemberPassword());
	        	return "redirect:/gameinit?memberId=" + memberDto.getMemberId();
	        	
	        }else {
	        	String errorMsg = "入力したID, PWが一致しません。";

	        	model.addAttribute("error", errorMsg);
	        	return "login";
	        }
	        
	    }
	}
