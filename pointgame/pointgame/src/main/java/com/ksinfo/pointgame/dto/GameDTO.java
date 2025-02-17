package com.ksinfo.pointgame.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {
	//PointInfo table resource management
	private String memberId;     
    private int point;           
    private String hiddenNum;    
    private int gameCount;       
    private int gameActFlg;      
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    
    //ResultInfo table resource management
    private int gameNum;       
    private String inputNum;
    private String result;
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getHiddenNum() {
		return hiddenNum;
	}
	public void setHiddenNum(String hiddenNum) {
		this.hiddenNum = hiddenNum;
	}
	public int getGameCount() {
		return gameCount;
	}
	public void setGameCount(int gameCount) {
		this.gameCount = gameCount;
	}
	public int getGameActFlg() {
		return gameActFlg;
	}
	public void setGameActFlg(int gameActFlg) {
		this.gameActFlg = gameActFlg;
	}
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}
	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}
	public int getGameNum() {
		return gameNum;
	}
	public void setGameNum(int gameNum) {
		this.gameNum = gameNum;
	}
	public String getInputNum() {
		return inputNum;
	}
	public void setInputNum(String inputNum) {
		this.inputNum = inputNum;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}       
    
    
}
