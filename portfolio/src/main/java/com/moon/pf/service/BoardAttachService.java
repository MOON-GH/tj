package com.moon.pf.service;

import java.util.HashMap;

public interface BoardAttachService {
	
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
	
	/**
	 * 첨부파일 테이블에 있는 정보와 물리적 파일 간의
	 * 연결이 끊긴 데이터를 찾아서 특정 컬럼(linked)에 표시
	 * @return
	 */
	public int updateUnlinkedInfo();
}
