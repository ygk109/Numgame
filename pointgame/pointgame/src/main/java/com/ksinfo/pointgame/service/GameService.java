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
		System.out.println("dto에 저장된 id: "+ memberId);
		
		//1.1)ポイント情報テーブル検索
		GameDTO pointSearchResult = pointDao.getPointById(memberId);
		System.out.println("pointSearch result: "+pointSearchResult);
		point = pointSearchResult.getPoint();
		gameDto.setPoint(point);
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
				gameDto.setGameCount(gameActFlag);
				gameDto.setHiddenNum(hiddenNum);
				
				int updateResult = pointDao.setPointById(gameDto);
				//1.4) SQL状態が >0(正常)でない場合、メッセージ出力
				if(updateResult > 0) {
					System.out.println("Update Success");
				}
				else {
					String errorMsg = "システムエラーが発生しました。";
					System.out.println(errorMsg);
				}
				
			}
			//1.5)レコード更新日が一致する場合の処理
			else {
				gameCount = pointSearchResult.getGameCount();
				gameActFlag = pointSearchResult.getGameActFlg();
				hiddenNum = pointSearchResult.getHiddenNum();
				
				gameDto.setGameCount(gameCount);
				gameDto.setGameCount(gameActFlag);
				gameDto.setHiddenNum(hiddenNum);
			}
			
			//2.1)当日のゲーム入力数、判定結果を取得するため、パラメータ04をして以下の機能を呼び出す。
			List<GameDTO> gameResult = resultDao.getResultById(memberId);
			gameDto.setGameResult(gameResult);
		}
		//1.2)SQL状態が00(正常)でない場合、メッセージ出力	
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
                num = random.nextInt(10); 
            } while (used[num]); // 중복 검사
            digits[i] = num;
            used[num] = true;
        }
        System.out.println("생성된 난수번호:" + digits[0]*100 + digits[1]*10 + digits[2]*1);
        return "" + digits[0] + digits[1] + digits[2]; // String으로 변환하여 저장
    }
}
