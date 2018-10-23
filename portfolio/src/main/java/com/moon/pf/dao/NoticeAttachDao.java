package com.moon.pf.dao;

import java.util.HashMap;
import java.util.List;

public interface NoticeAttachDao {
	/**
	 * 첨부파일 정보 insert
	 * @param params
	 * @return 실행된 쿼리 결과
	 */
	public int insertAttachInfo(HashMap<String, Object> params);
	
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
	 * 파일 정보 조회
	 * @param typeSeq
	 * @param boardSeq
	 * @return 실행된 쿼리결과 여러건
	 */
	public List<HashMap<String, Object>> getFile(int typeSeq, int boardSeq);
	
	/**
	 * 글번호로 파일 삭제하는 메서드
	 * @param typeSeq
	 * @param boardSeq
	 * @return 실행된 쿼리결과
	 */
	public int deleteAttachBoard(int typeSeq, int boardSeq);
}
