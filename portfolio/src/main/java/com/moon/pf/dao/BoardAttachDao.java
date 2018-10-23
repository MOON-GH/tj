package com.moon.pf.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface BoardAttachDao {
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
	
	/**
	 * 첨부파일 모든정보 조회
	 * @return
	 */
	public List<HashMap<String, Object>> getAttachAllFileInfo();
	
	/**
	 * 파일이 존재하지 않을시 linked 컬럼 값 수정 (한건)
	 * @param fileIdx
	 * @return
	 */
	public int updateUnlinked(int fileIdx);
	
	/**
	 * 파일이 존재하지 않을시 linked 컬럼 값 수정 (다건)
	 * @param fileIdx
	 * @return
	 */
	public int updateUnlinkeds(ArrayList<Integer> fileIdx);
}
