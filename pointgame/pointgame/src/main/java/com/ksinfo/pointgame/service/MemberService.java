package com.ksinfo.pointgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksinfo.pointgame.dao.MemberDAO;
import com.ksinfo.pointgame.dto.MemberDTO;

@Service
public class MemberService {
	@Autowired
	private MemberDAO memberDao;
	
	public boolean login(MemberDTO memberDto) {
		String memberId = memberDto.getMemberId();
		String memberPassword = memberDto.getMemberPassword();
		System.out.println(memberId); //check
		System.out.println(memberPassword); //check
		
		Integer loginResult = memberDao.getResultById(memberId, memberPassword);
		System.out.println(loginResult); //check
		
		return loginResult > 0;
	};
}
