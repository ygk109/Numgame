package com.ksinfo.pointgame.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksinfo.pointgame.dao.PointDAO;
import com.ksinfo.pointgame.dao.ResultDAO;
import com.ksinfo.pointgame.dto.GameDTO;

import jakarta.transaction.Transactional;

@Service
public class GameService {
	@Autowired
	private PointDAO pointDao;
	
	@Autowired
	private ResultDAO resultDao;
	
	public void gameInfoInit(GameDTO gameDto) {
		String memberId= gameDto.getMemberId();
		int point = gameDto.getPoint();
		int gameCount = gameDto.getGameCount();
		int gameActFlag = gameDto.getGameActFlg();
		String hiddenNum = gameDto.getHiddenNum();
		LocalDateTime updateDate = gameDto.getUpdateDate();
		String errorMsg = "システムエラーが発生しました。";
		
		System.out.println("Saved dto id: "+ memberId);
		
		//1.1)ポイント情報テーブル検索
		try {
			GameDTO pointSearchResult = pointDao.getPointById(memberId);
		//Search point
		point = pointSearchResult.getPoint();
		System.out.println("DB Search point: " + point);
		
		gameDto.setPoint(point);
		System.out.println("Saving DTO point: " + gameDto.getPoint());
		
		//Search gameCount
		gameCount = pointSearchResult.getGameCount();
		System.out.println("DB Search gameCount: " + gameCount);
		
		gameDto.setGameCount(gameCount);
		System.out.println("Saving DTO gameCount: " + gameDto.getPoint());
		
		//Search gameActFlg
		gameActFlag = pointSearchResult.getGameActFlg();
		System.out.println("DB Search gameActFlag: " + gameActFlag);
		
		gameDto.setGameActFlg(gameActFlag);
		System.out.println("Saving DTO gameActFlag: " + gameDto.getGameActFlg());
		
		//Search hiddenNum
		hiddenNum = pointSearchResult.getHiddenNum();
		gameDto.setHiddenNum(hiddenNum);
		System.out.println("Saving DTO hiddenNum: " + gameDto.getHiddenNum());
		
		//Search updateDate
		updateDate = pointSearchResult.getUpdateDate();
		gameDto.setUpdateDate(updateDate);		
		
		//1.3)レコード更新日が一致しない場合の処理	
		if(pointSearchResult != null) {
			if(!updateDate.toLocalDate().equals(LocalDate.now())) {
				gameCount = 0;
				gameActFlag = 0;
				hiddenNum = createNum();
				updateDate	= LocalDateTime.now();
				
				gameDto.setGameCount(gameCount);
				gameDto.setGameActFlg(gameActFlag);
				gameDto.setHiddenNum(hiddenNum);
				System.out.println("Created hiddenNum:" + hiddenNum);
				System.out.println("Initialized gameActFlg: " + gameDto.getGameActFlg());
				
				int updateResult = pointDao.setPointById(memberId, gameCount, gameActFlag, hiddenNum);
				
				//1.4) SQL状態が >0(正常)でない場合、メッセージ出力
				if(updateResult > 0) {
					System.out.println("Initial PointInfo Update Success");
				}
				else {
					System.out.println(errorMsg);
				}
				
			}
			//1.5)レコード更新日が一致する場合の処理
			else {
				gameCount = pointSearchResult.getGameCount(); gameActFlag =
				pointSearchResult.getGameActFlg(); hiddenNum =
				pointSearchResult.getHiddenNum();
				 
				gameDto.setGameCount(gameCount); gameDto.setGameActFlg(gameActFlag);
				gameDto.setHiddenNum(hiddenNum);
			}
			//2.1)当日のゲーム入力数、判定結果を取得するため、パラメータ04をして以下の機能を呼び出す。
			List<GameDTO> gameResult = resultDao.getResultById(memberId);
			gameDto.setGameResult(gameResult);
		}
		
	//1.2)SQL状態が(正常)でない場合、メッセージ出力	
	}catch (Exception e) {
		System.out.println(errorMsg);
		}
	}
	
	//Create random number method
    private String createNum() {
        Random random = new Random();
        int[] digits = new int[3];
        boolean[] used = new boolean[10]; // 숫자 중복 방지

        for (int i = 0; i < 3; i++) {
            int num;
            do {
                num = random.nextInt(10); 
            } while (used[num]); // 중복 검사
            digits[i] = num;
            used[num] = true;
        }
        System.out.println("Creating hiddenNum:" + digits[0] + digits[1] + digits[2]);
        return "" + digits[0] + digits[1] + digits[2];
    }

    //gamePlay 
    @Transactional
    public void gamePlay(GameDTO gameDto) {
   		String errorMsg = "システムエラーが発生しました。";
    	try {
    	String memberId = gameDto.getMemberId();
    	String inputNum = gameDto.getInputNum();
    	String hiddenNum = gameDto.getHiddenNum();
    	int currentPoint = gameDto.getPoint();
   		int gameCount = gameDto.getGameCount()+1;
   		int gameActFlg = gameDto.getGameActFlg();
   		List<GameDTO> gameResult = gameDto.getGameResult();
   		
   		
   		System.out.println("checkNum 처리 전 currentPoint: " + currentPoint);
    	//game result check method
    	checkNum(gameDto, inputNum, hiddenNum, gameCount, gameActFlg);
   		String result = gameDto.getResult();
    	int updatedPoint = gameDto.getPoint();
    	
    	System.out.println("GameService updatedPoint: "+ updatedPoint);
        System.out.println("----Inserting game result into DB:----");
        System.out.println("Member ID: " + memberId);
        System.out.println("Game Count: " + gameCount);
        System.out.println("Input Number: " + inputNum);
        System.out.println("Result: " + result);
    	
    	//2. ゲーム結果履歴情報テーブル追加
    	int updateResultInfo = resultDao.setResultById(memberId, gameCount, inputNum, result);
    	if(updateResultInfo > 0) {
    		System.out.println("(Save game)SQL Update Success");
    		gameDto.setResult(result);
    	}else {
    		System.out.println("(Save game)システムエラーが発生しました。");
    	}
    	
    	//3. ポイント情報テーブル更新
    	int updatedGameActFlg = gameDto.getGameActFlg();
    	int updatePointInfo = pointDao.setAddPointById(memberId, updatedPoint, gameCount, updatedGameActFlg);
    	System.out.println("updatePointInfo point: " + updatedPoint + "gameCount: " + gameCount + "gameActFlg: " + updatedGameActFlg);
    	if(updatePointInfo > 0) {
    		System.out.println("(Update Point)SQL Update Success");
    		gameDto.setPoint(updatedPoint);
    	}else {
    		System.out.println("(Update Point)システムエラーが発生しました。");
    	}
    	//4. 取得したデータを返却し処理を終了する。
    	gameResult = resultDao.getResultById(memberId);
		gameDto.setGameResult(gameResult);
    	
		    if(gameDto.getGameActFlg() == 1 ) {
		    	System.out.println("game result pop-up msg");
		    	
		    }
	    
	    }catch(Exception e) {
	    	System.out.println(errorMsg);
	    }
    }
  
  //game result check method
   	public void checkNum(GameDTO gameDto, String inputNum, String hiddenNum, int gameCount, int gameActFlg){
   		int i;
   		int j;
   		int s= 0;
   		int b= 0;
   		String result;

   		int finalResult;
   		int currentPoint = gameDto.getPoint();
   		System.out.println("Current Point: "+ currentPoint);
   		int point= 0;
   		
   		char[] input = new char[3];
   		char[] hidden = new char[3];
   		
   		//game judge
   		for(i=0; i<3; i++) {
   			input[i]= inputNum.charAt(i);
   		}
   		
   		for(j=0; j<3; j++) {
   			hidden[j]= hiddenNum.charAt(j);
   		}
   		
   		
   		for(i=0; i<3; i++) {
   			
   			for(j=0; j<3; j++) {
   				if(input[i] == hidden[j]) {
   					if(i==j) {
   						s++;
   					}else {
   						b++;
   					}
   				}
   			}
   		}
   		//はずれ=0, 当たり=1, 0s0b = 2
   		if(s == 0 && b == 0) {
   			System.out.println("result: はずれ");
   			result= "はずれ";
   			finalResult = 0;
   			gameDto.setResult(result);
   			if(gameCount == 10) {
   				//포인트 지급 메소드
   				point= givePoint(finalResult, point, gameCount);
   			}
   		}else if(s == 3){
   			System.out.println("result: 当たり");
   			result= "当たり";
   			finalResult = 1;
   			gameDto.setResult(result);
   			//포인트 지급 메소드
   			point= givePoint(finalResult, point, gameCount);
   		}else {
   			System.out.println(s+ "S" + b + "B");
   			result= s+ "S" + b + "B";
   			finalResult = 2;
   			gameDto.setResult(result);
   			if(gameCount == 10) {
   				//포인트 지급 메소드
   				point= givePoint(finalResult, point, gameCount);
   			}
   		}

   		if(point >= 0 && gameCount == 10) {
   			gameActFlg = 1;
   			System.out.println("gameCount: " + gameCount + "gameActFlg: " + gameActFlg);

   		}else if(finalResult == 1) {
   			gameActFlg = 1;
   			System.out.println("finalResult: "+ finalResult + "gameActFlg: " + gameActFlg);
   		}
   		gameDto.setRewardPoint(point);
   		gameDto.setResult(result);
   		gameDto.setPoint(currentPoint + point);
   		
   		System.out.println("--------After checkNum method result----------");
   		System.out.println("checkNum 메소드 실행 후 currentPoint: " + currentPoint + "지급 point: " + point);
   		System.out.println("checkNum 메소드 실행 후 합산point: "+ gameDto.getPoint());
   		
   		gameDto.setGameCount(gameCount);
   		System.out.println("checkNum 메소드 실행 후 gameCount: "+ gameDto.getGameCount());
   		gameDto.setGameActFlg(gameActFlg);
   		System.out.println("checkNum 메소드 실행 후 gameActFlg: "+ gameDto.getGameActFlg());
	}

   	//point give method
   	private int givePoint(int finalResult, int point, int gameCount) {
   		if(finalResult == 0) {
   			point = 0;
   		}else if(finalResult == 1) {
   			if(gameCount <= 5) {
   				point = 1000;
   			}else if(gameCount <= 7) {
   				point = 500;
   			}else if(gameCount <= 10)point = 200;
   		}else{
   			point = 0;
   		}
   		System.out.println("givePoint method point: " + point);
   		return point;
   	}
}
