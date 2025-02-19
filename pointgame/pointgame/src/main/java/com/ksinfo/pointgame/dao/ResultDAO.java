package com.ksinfo.pointgame.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ksinfo.pointgame.dto.GameDTO;

@Mapper
public interface ResultDAO {
	List<GameDTO> getResultById(@Param("memberId")String memberId);
	
	int setResultById(@Param("memberId")String memberId, @Param("gameCount")int gameCount,@Param("inputNum")String inputNum, @Param("result")String result);
}
