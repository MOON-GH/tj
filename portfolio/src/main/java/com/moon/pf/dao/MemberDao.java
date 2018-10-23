package com.moon.pf.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.moon.pf.dto.Member;

public interface MemberDao {
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
	 * 로그인 메서드 사용자가 입력한 ID로 회원정보를 조회한다.
	 * @param memberId
	 * @return 회원정보
	 */
	public Member findMemberId(String memberId);
	
	/**
	 * 사용자가 입력한 비밀번호 암호화 메서드
	 * @param memberPw
	 * @return 암호화된 비밀번호
	 */
	public String makeCipherText(String memberPw);
	
	/**
	 * 회원정보 전체 조회
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
