package com.moon.pf.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moon.pf.dao.NoticeAttachDao;
import com.moon.pf.service.NoticeAttachService;

@Service
public class NoticeAttachServiceImpl implements NoticeAttachService{
	@Autowired private NoticeAttachDao nAttachDao;
	
	@Override
	public HashMap<String, Object> getAttachFileInfo(int fileIdx) {
		return nAttachDao.getAttachFileInfo(fileIdx);
	}

	@Override
	public int deleteAttach(int fileIdx) {
		return nAttachDao.deleteAttach(fileIdx);
	}
	
	
}
