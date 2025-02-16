package com.ksinfo.pointgame.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

	    @GetMapping("/status")
	    public String pointGamePage() {
	        return "동작중";
	    }
	    
	    @Autowired
	    private JdbcTemplate jdbcTemplate;

	    @GetMapping("/db")
	    public List<Map<String, Object>> getTables() {
	        String sql = "SELECT * FROM memberinfo";
	        return jdbcTemplate.queryForList(sql);
	    }
	}
