package com.moon.pf.service;

import java.util.HashMap;

public interface NoticeAttachService {
	
	/**
	 * 파일정보 조회
	 * @param fileIdx
	 * @return 조회된 파일 정보
	 */
	public HashMap<String, Object> getAttachFileInfo(int fileIdx);
	
	/**
	 * 파일정보 삭제
	 * @param fileIdx
	 * @return 실행된 쿼리결과
	 */
	public int deleteAttach(int fileIdx); 
}
