package com.moon.pf.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moon.pf.dao.MemberDao;
import com.moon.pf.dto.Member;
import com.moon.pf.exception.MemberNotFoundException;
import com.moon.pf.exception.PasswordMissMatchException;
import com.moon.pf.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired MemberDao mDao;
	
	@Override
	public int checkId(String memberId) {
		return mDao.checkId(memberId);
	}

	@Override
	public int join(HashMap<String, Object> param) {
		return mDao.join(param);
	}

	@Override
	public Member login(String memberId, String memberPw) throws Exception {
		Member member = mDao.findMemberId(memberId);
		
		if(member != null) {
			if(member.getMemberPw().equals(mDao.makeCipherText(memberPw))) {
				return member;
			} else {
				throw new PasswordMissMatchException(); 
			}
		} else {
			throw new MemberNotFoundException();
		}
	}

	@Override
	public ArrayList<HashMap<String, Object>> getMemberList(HashMap<String, String> params) {
		return mDao.getMemberList(params);
	}

	@Override
	public int getMemberListCnt(HashMap<String, String> params) {
		return mDao.getMemberListCnt(params);
	}

	@Override
	public int deleteMember(HashMap<String, String> params) {
		return mDao.deleteMember(params);
	}
}