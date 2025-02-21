package com.ksinfo.pointgame.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ksinfo.pointgame.dto.GameDTO;

@Mapper
public interface PointDAO {
	GameDTO getPointById(@Param("memberId") String memberId);

	int setPointById(@Param("memberId") String memberId, @Param("gameCount")int gameCount, @Param("gameActFlg")int gameActFlg, @Param("hiddenNum")String hiddenNum);

	int setAddPointById(@Param("memberId") String memberId, @Param("point")int point, @Param("gameCount")int gameCount, @Param("gameActFlg")int gameActFlg);
}
