package com.moon.pf.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface NoticeDao {
	/**
	 * 전체 게시글 개수 조회
	 * @return 전체 게시글 개수
	 */
	public int getBoardCnt(HashMap<String, String> params);
	
	/**
	 * 자유게시판 게시글 전부 조회
	 * @return 조회된 게시글
	 */
	public List<HashMap<String, Object>> getBoardList(HashMap<String, String> param);
	
	/**
	 * 조회수 업데이트
	 * @param typeSeq
	 * @param boardSeq
	 * @return 실행된 쿼리결과
	 */
	public int updateHits(int typeSeq, int boardSeq);
	
	/**
	 * 게시글 정보 조회
	 * @param typeSeq
	 * @param boardSeq
	 * @return 한개의 게시글 정보
	 */
	public HashMap<String, Object> getBoard(int typeSeq, int boardSeq);
	
	/**
	 * 글작성 insert
	 * @param params
	 * @param mFiles
	 * @return 실행된 쿼리결과
	 */
	public int write(HashMap<String, Object> params);
	
	/**
	 * 글 수정 update
	 * @param params
	 * @return 실행된 쿼리결과
	 */
	public int update(HashMap<String, Object> params);
	
	/**
	 * 글 삭제 delete
	 * @param typeSeq
	 * @param boardSeq
	 * @return 실행된 쿼리결과
	 */
	public int delete(int typeSeq, int boardSeq);
	
	/**
	 * 게시글 수정시 첨부파일여부 업데이트(has_file)
	 * @param typeSeq
	 * @param boardSeq
	 * @return 실행된 쿼리결과
	 */
	public int hasFileUpdate(int typeSeq, int boardSeq);
}
