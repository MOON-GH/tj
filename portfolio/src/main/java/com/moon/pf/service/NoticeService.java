package com.moon.pf.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface NoticeService {
	/**
	 * 전체 게시글 개수 조회
	 * @param params
	 * @return 전체 게시글 개수
	 */
	public int getBoardCnt(HashMap<String, String> params); 
	
	/**
	 * 자유게시판 게시글 전부 조회
	 * @return 조회된 게시글 리스트
	 */
	public List<HashMap<String, Object>> getBoardList(HashMap<String, String> params);
	
	/**
	 * 게시글 정보 조회
	 * @param typeSeq
	 * @param boardSeq
	 * @return 한개의 게시글 정보 
	 */
	public HashMap<String, Object> read(int typeSeq, int boardSeq);
	
	/**
	 * 글작성 insert
	 * @param params
	 * @param mFile
	 * @return 실행된 쿼리결과
	 */
	public int write(HashMap<String, Object> params, List<MultipartFile> mFiles);
	
	/**
	 * 글 수정 update
	 * @param params
	 * @param mFiles
	 * @return 실행된 쿼리결과
	 */
	public int update(HashMap<String, Object> params, List<MultipartFile> mFiles);
	
	/**
	 * 글 삭제 delete
	 * @param getBoard
	 * @return 실행된 쿼리결과
	 */
	public int delete(HashMap<String, Object> getBoard);
	
	/**
	 * has_file 이 1 이면 파일 정보 조회
	 * @param typeSeq
	 * @param boardSeq
	 * @return 실행된 쿼리결과 여러건
	 */
	public List<HashMap<String, Object>> fileRead(int typeSeq, int boardSeq);
	
	/**
	 * 파일 정보 삭제
	 * @param fileIdx
	 * @param typeSeq
	 * @param boardSeq
	 * @return 실행된 쿼리결과
	 */
	public boolean deleteAttach(int fileIdx, int typeSeq, int boardSeq);
}
