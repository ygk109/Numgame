package com.ksinfo.pointgame.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ksinfo.pointgame.dao.PointDAO;
import com.ksinfo.pointgame.dto.GameDTO;

@Service
public class GameService {
	@Autowired
	private PointDAO pointDao;
	
	public void gameInfoInit(GameDTO gameDto) {
		String memberId= gameDto.getMemberId();
		int point = gameDto.getPoint();
		int gameCount = gameDto.getGameCount();
		int gameActFlag = gameDto.getGameActFlg();
		String hiddenNum = gameDto.getHiddenNum();
		LocalDateTime updateDate = gameDto.getUpdateDate();
		System.out.println("dto에 저장된 id: "+ memberId);
		
		GameDTO pointSearchResult = pointDao.getPointById(memberId);
		System.out.println("pointSearch result: "+pointSearchResult);
		//포인트정보테이블 조회 후 당일 날짜 비교 로직
		if(pointSearchResult != null) {
			point = pointSearchResult.getPoint();
			gameCount = pointSearchResult.getGameCount();
			gameActFlag = pointSearchResult.getGameActFlg();
			hiddenNum = pointSearchResult.getHiddenNum();
			
			gameDto.setPoint(point);
			gameDto.setGameCount(gameCount);
			gameDto.setGameCount(gameCount);
			gameDto.setHiddenNum(hiddenNum);
			
			
//			if(!updateDate.toLocalDate().equals(LocalDate.now())) {
//				gameCount = 0;
//				gameActFlag = 0;
//				hiddenNum = createNum();
//				updateDate	= LocalDateTime.now();
//				
//				gameDto.setGameCount(gameCount);
//				gameDto.setGameActFlg(gameActFlag);
//				gameDto.setHiddenNum(hiddenNum);
//				gameDto.setUpdateDate(updateDate);
//			}
//			else {
//				
//			}
//			
//			
		}
		else {
		String errorMsg = "システムエラーが発生しました。";
		System.out.println(errorMsg);
		}
		
		
	}
    private String createNum() {
        Random random = new Random();
        int[] digits = new int[3];
        boolean[] used = new boolean[10]; // 숫자 중복 방지

        for (int i = 0; i < 3; i++) {
            int num;
            do {
                num = random.nextInt(9); 
            } while (used[num]); // 중복 검사

            digits[i] = num;
            used[num] = true;
        }
        System.out.println("생성된 난수번호:" + digits[0]*100 + digits[1]*10 + digits[2]*1);
        return "" + digits[0] + digits[1] + digits[2]; // String으로 변환하여 저장
    }
}
