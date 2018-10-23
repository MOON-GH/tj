package com.moon.pf.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moon.pf.service.BoardAttachService;

@RunWith(SpringJUnit4ClassRunner.class)		// Spring 기반 JUnit TEST
@ContextConfiguration(locations = {			// WAS 를 사용하지 않고 TEST 를 진행하기 때문에 Context를 불러온다.
		"file:src/main/resources/applicationContext.xml"
})
public class AttachTestApp {
	@Autowired
	BoardAttachService baService;
	
	@Test
	public void test01() {
		baService.updateUnlinkedInfo();  
	}
}