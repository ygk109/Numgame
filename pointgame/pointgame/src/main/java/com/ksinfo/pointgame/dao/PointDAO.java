package com.ksinfo.pointgame.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ksinfo.pointgame.dto.GameDTO;

@Mapper
public interface PointDAO {
	GameDTO getPointById(@Param("memberId") String memberId);

	int setPointById(GameDTO gameDto);
}
