package com.ksinfo.pointgame.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberDAO {
	//会員IDとPWを持って一致する会員有無を確認
	Integer getResultById(@Param("memberId") String memberId, @Param("memberPassword") String memberPassword);
	
}
