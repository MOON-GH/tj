package com.moon.pf.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	@Value("#{config['project.file.upload.location']}")
	private String saveLocation;
	
	public void copyFile(MultipartFile mf, String fakename) throws IOException {
		// 폴더 지정
		File destDir = new File(this.saveLocation);
		
		if(!destDir.exists()) {
			// 없으면 만든다.
			destDir.mkdirs();
		}
		// 파일 지정
		File destFile = new File(destDir, fakename);
		FileCopyUtils.copy(mf.getBytes(), destFile);
	}
	
	public byte[] readFile(HashMap<String, Object> fileInfo) {
		// 파일 찾기
		File f = new File(saveLocation, String.valueOf(fileInfo.get("fake_filename")));
		byte[] b = null;
		if(f.exists()) {	// 물리적 위치에 존재하면
			try {
				// byte단위로 파일을 읽어온다.
				b = FileUtils.readFileToByteArray(f);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else {	// 물리적 위치에 파일 없음
			
		}
		
		return b;
	}
	
	public boolean deleteFile(HashMap<String, Object> fileInfo) {
		// 파일 찾기
		File f = new File(saveLocation, String.valueOf(fileInfo.get("fake_filename")));
		
		if(f.exists()) {	// 물리적 위치에 존재하면
			// 파일 삭제
			return f.delete();
		}
		
		return false;
	}
}
