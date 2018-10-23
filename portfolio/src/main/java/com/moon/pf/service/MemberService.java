package com.moon.pf.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.moon.pf.dto.Member;

public interface MemberService {
	/**
	 * 사용자가 입력한 ID로 검색
	 * @param memberId
	 * @return 검색된 ID 개수
	 */
	public int checkId(String memberId);
	
	/**
	 * 회원가입 메서드
	 * @param param
	 * @return 실행된 쿼리
	 */
	public int join(HashMap<String, Object> param);
	
	/**
	 * 로그인 메서드
	 * @param param
	 * @return 성공 1 실패 0
	 * @throws Exception 
	 */
	public Member login(String memberId, String memberPw) throws Exception;
	
	/**
	 * 회원정보 전체 조회
	 * @param params
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getMemberList(HashMap<String, String> params);
	
	/**
	 * 회원정보 전체 개수 조회
	 * @param params
	 * @return
	 */
	public int getMemberListCnt(HashMap<String, String> params);
	
	
	/**
	 * 회원삭제
	 * @param params
	 * @return
	 */
	public int deleteMember(HashMap<String, String> params);
}
