package com.moon.pf.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.moon.pf.dao.BoardAttachDao;
import com.moon.pf.service.BoardAttachService;

@Service
public class BoardAttachServiceImpl implements BoardAttachService{
	@Autowired private BoardAttachDao bAttachDao;
	
	@Value("#{config['project.file.upload.location']}")
	private String saveLocation;
	
	@Override
	public HashMap<String, Object> getAttachFileInfo(int fileIdx) {
		return bAttachDao.getAttachFileInfo(fileIdx);
	}

	@Override
	public int deleteAttach(int fileIdx) {
		return bAttachDao.deleteAttach(fileIdx);
	}

	@Override
	public int updateUnlinkedInfo() {
		// 1. 첨부파일 정보 다 가져오기
		List<HashMap<String, Object>> targets = bAttachDao.getAttachAllFileInfo();
		// 2. 파일 있는지 없는지 확인
		ArrayList<Integer> fileIdxs = new ArrayList<Integer>();
		for (HashMap<String, Object> target : targets) {
			String fakeFilename = String.valueOf(target.get("fake_filename"));
			File f = new File(saveLocation, fakeFilename);
			
			// 2-1. 없으면, 컬럼(linked) 값(0 or F) 수정
			if(!f.exists()) {
				int fileIdx = Integer.parseInt(String.valueOf(target.get("file_idx")));
				fileIdxs.add(fileIdx);
				// 1건씩
				// int result = bAttachDao.updateUnlinked(fileIdx);
			}
		}
		
		// 한꺼번에 업데이트
		int result = 0;
		if(fileIdxs.size() > 0) {
			result = bAttachDao.updateUnlinkeds(fileIdxs);
		}
		
		return result;
	}
}