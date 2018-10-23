package com.moon.pf.app;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moon.pf.service.BoardService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
})
public class BoardTestApp {		// 컨트롤러를 대신...
	@Autowired private BoardService bService;
	
	@Test // 단위(기능) 테스트
	public void writeTest() {	// ~~.do 에 대응되는 메서드
		// 웹에서 전송될 데이터를 유추해서 파라미터 생성
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeSeq", "2");
		params.put("memeberNick", "1");
		params.put("memeberIdx", "99");
		params.put("memeberId", "test");
		params.put("title", "이건 테스트");
		params.put("contents", "이건 테스트");
		params.put("hasFile", "0");
		
		// try-catch 필요 X
		Assert.assertEquals(1, bService.writeTestDrivenDevelopment(params));
		
		/* try-catch 필요
		 * try-catch 주의 할 것.
		 * Member member = null;
		 * try {
		 *	 member = mService.login("aa", "bb");
		 * } catch (Exception e1) {
		 *	 e1.printStackTrace();
		 * }
		 * 
		 * try-catch 가 필요하다면 try catch 밖에서 판정을 해줘야한다.
		 * Assert.assertNotNull(member);
		 */
	}
}
