package com.moon.pf.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.moon.pf.dao.NoticeAttachDao;
import com.moon.pf.dao.NoticeDao;
import com.moon.pf.service.NoticeService;
import com.moon.pf.util.FileUtil;

@Service
public class NoticeServiceImpl implements NoticeService{
	@Autowired private NoticeDao nDao;
	@Autowired private NoticeAttachDao nAttachDao;
	@Autowired private FileUtil fUtil;
	
	@Override
	public int getBoardCnt(HashMap<String, String> params) {
		return nDao.getBoardCnt(params);
	}
	
	@Override
	public List<HashMap<String, Object>> getBoardList(HashMap<String, String> param) {
		return nDao.getBoardList(param);
	}

	@Override
	public HashMap<String, Object> read(int typeSeq, int boardSeq) {
		nDao.updateHits(typeSeq, boardSeq);
		return nDao.getBoard(typeSeq, boardSeq);
	}

	@Override
	public int write(HashMap<String, Object> params, List<MultipartFile> mFiles) {
		// 1. 글등록
		nDao.write(params);
		
		// 2. 첨부파일이 있으면 board_attach 테이블에 등록
		if(params.get("hasFile").equals("1")) {
			for(MultipartFile mf : mFiles) {
				if(!mf.getOriginalFilename().equals("")) {
					// 난수를 만들어 가짜이름으로 사용
					String fakename = UUID.randomUUID().toString().replaceAll("-", "");
					params.put("mFile", mFiles);
					params.put("filename", mf.getOriginalFilename());
					params.put("fakeFilename", fakename);
					params.put("fileSize", mf.getSize());
					params.put("fileType", mf.getContentType());
					
					nAttachDao.insertAttachInfo(params);
					
					try {
						fUtil.copyFile(mf, fakename);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return 0;
	}

	@Override
	public int update(HashMap<String, Object> params, List<MultipartFile> mFiles) {
		// 2. 첨부파일이 있으면 board_attach 테이블에 등록
		for(MultipartFile mf : mFiles) {
			if(!mf.getOriginalFilename().equals("")) {
				// 난수를 만들어 가짜 이름으로 사용..
				String fakename = UUID.randomUUID().toString().replace("-", "");
				params.put("mFile", mFiles);
				params.put("filename", mf.getOriginalFilename());
				params.put("fakeFilename", fakename);
				params.put("fileSize", mf.getSize());
				params.put("fileType", mf.getContentType());
				
				nAttachDao.insertAttachInfo(params);
				
				try {
					fUtil.copyFile(mf, fakename);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return nDao.update(params);
	}

	@Override
	public int delete(HashMap<String, Object> getBoard) {
		int typeSeq = Integer.parseInt(String.valueOf(getBoard.get("type_seq")));
		int boardSeq = Integer.parseInt(String.valueOf(getBoard.get("board_seq")));
		
		int result = 0;
		
		if(getBoard.get("has_file").equals("1")) {	// 첨부 파일이 있으면
			
			// 1. 삭제할 첨부파일 정보를 모두 가지고 온다.
			List<HashMap<String, Object>> files = nAttachDao.getFile(typeSeq, boardSeq);
			
			// 글번호, 타입으로 첨부파일 정보를 삭제하는 DAO 메서드 호출
			result = nAttachDao.deleteAttachBoard(typeSeq, boardSeq);
			
			// 글 삭제
			result = nDao.delete(typeSeq, boardSeq);
			
			// 물리적 위치에서 삭제
			for(Map<String, Object> file : files) {
				// Map 은 interface 고 HashMap 은 class이다. -> 즉, Map은 HashMap으로 cast 가능하다.
				fUtil.deleteFile((HashMap<String, Object>) file);
			}
			
			return result;
		}
		return nDao.delete(typeSeq, boardSeq);
	}

	@Override
	public List<HashMap<String, Object>> fileRead(int typeSeq, int boardSeq) {
		return nAttachDao.getFile(typeSeq, boardSeq);
	}

	@Override
	public boolean deleteAttach(int fileIdx, int typeSeq, int boardSeq) {
		boolean result = false;
		// 첨부파일 정보를 가져온다.
		HashMap<String, Object> fileInfo = nAttachDao.getAttachFileInfo(fileIdx);
		// DB에서 삭제한다.
		result = (nAttachDao.deleteAttach(fileIdx) == 1);
		// 원 게시글과 첨부파일 정보의 관계를 확인한다. (첨부파일 전체 가져오기)
		List<HashMap<String, Object>> files = nAttachDao.getFile(typeSeq, boardSeq);
		// 가져온 첨부파일이 없으면 (더이상 첨부파일이 없으면)
		if(files == null || files.size() == 0) {
			// 게시글의 has_file을 0으로 바꾼다.
			result = (nDao.hasFileUpdate(typeSeq, boardSeq) == 1 && result);
		}
		// 물리 디스크에서 삭제한다.
		result = (fUtil.deleteFile(fileInfo) == result);
		
		return result;
	}
}
