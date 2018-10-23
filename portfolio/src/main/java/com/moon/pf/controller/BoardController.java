package com.moon.pf.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.moon.pf.service.BoardAttachService;
import com.moon.pf.service.BoardService;
import com.moon.pf.util.FileUtil;

@Controller
public class BoardController {
	@Autowired private BoardService bService;
	@Autowired private BoardAttachService bAttachService;
	@Autowired private FileUtil fUtil;
	@Value("#{config['project.context.path']}")
	private String contextRoot;
	Logger logger = Logger.getLogger(BoardController.class);
	// 자유게시판 typeSeq = 2
	private int typeSeq = 2;
	
	@RequestMapping("/board/list.do")
	public ModelAndView list(@RequestParam HashMap<String, String> params, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		session.setAttribute("useGrid", 0);
		
		params.put("typeSeq", String.valueOf(this.typeSeq));

		// 총 게시글 수
		int totalArticleCnt = bService.getBoardCnt(params);
		// 한 페이지에 보여줄 게시글 수
		int pageArticleSize = (params.containsKey("pageArticleSize")) ? Integer.parseInt(params.get("pageArticleSize")) : 10;
		// 현재 페이지
		int currentPage = (params.containsKey("currentPage")) ? Integer.parseInt(params.get("currentPage")) : 1;
		// 총 페이지 수
		int totalPageCnt = (int) Math.ceil((double) totalArticleCnt / pageArticleSize);
		// 시작 인덱스
		int startIdx = (currentPage - 1) * pageArticleSize;

		// 블럭 수
		int pageBlockSize = 10;
		// 시작페이지
		int pageBlockStart = (currentPage-1) / pageBlockSize * pageBlockSize + 1;
		// 끝 페이지
		int pageBlockEnd = (currentPage-1) / pageBlockSize * pageBlockSize + pageBlockSize;
		pageBlockEnd = (pageBlockEnd >= totalPageCnt) ? totalPageCnt : pageBlockEnd;
		
		params.put("startIdx", String.valueOf(startIdx));
		params.put("pageArticleSize", String.valueOf(pageArticleSize));
		
		List<HashMap<String, Object>> getBoardList = bService.getBoardList(params);
		
		mv.addObject("boardList", getBoardList);
		mv.addObject("totalPageCnt", totalPageCnt);
		mv.addObject("currentPage", currentPage);
		mv.addObject("pageBlockSize", pageBlockSize);
		mv.addObject("pageBlockStart", pageBlockStart);
		mv.addObject("pageBlockEnd", pageBlockEnd);
		mv.addObject("searchType", params.get("searchType"));
		mv.addObject("searchText", params.get("searchText"));
		
		if(params.containsKey("msg")) {
			mv.addObject("msg", params.get("msg"));
		}
		mv.setViewName("/board/list");
		
		return mv;
	}
	
	@RequestMapping("/board/gridList.do")
	public String gridList() {
		return "grid/list";
	}
	
	@RequestMapping("/board/getGridList.do")
	@ResponseBody
	public HashMap<String, Object> getGridList(@RequestParam HashMap<String, String> params, HttpSession session) {
		session.setAttribute("useGrid", 1);
		
		params.put("typeSeq", String.valueOf(this.typeSeq));

		// 한 페이지에 보여줄 게시글 수
		int rows = Integer.parseInt(params.get("rows"));
		// 현재 페이지
		int currentPage = Integer.parseInt(params.get("page"));
		// 시작 인덱스
		int startIdx = (currentPage - 1) * rows;
		params.put("startIdx", String.valueOf(startIdx));
		
		// 총 게시글 수
		int totalGridList = bService.getBoardCnt(params);
		// 총 페이지 수
		int totalPageCnt = (int) Math.ceil((double) totalGridList / rows);
		// 끝 인덱스가 필요없는 이유
		//  : mySql 은 몇번째 부터 몇개를 가져와라 이기 때문에 필요없다.
		//  : 오라클은 몇번째 부터 몇번째 이기 때문에 끝 인덱스가 필요하다.
		
		// 블럭 수
		int pageBlockSize = 10;
		// 시작페이지
		// int pageBlockStart = (currentPage-1) / pageBlockSize * pageBlockSize + 1;
		// 끝 페이지
		int pageBlockEnd = (currentPage-1) / pageBlockSize * pageBlockSize + pageBlockSize;
		pageBlockEnd = (pageBlockEnd >= totalPageCnt) ? totalPageCnt : pageBlockEnd;
		
		params.put("startIdx", String.valueOf(startIdx));
		
		List<HashMap<String, Object>> getGridList = bService.getBoardList(params);
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("page", params.get("page")); // 현재 페이지
		result.put("total", totalPageCnt); // 총 페이지 수
		result.put("rows", getGridList); // 데이터 (글 리스트)
		result.put("records", totalGridList); // 총 글 개수
		
		return result;
	}
	
	@RequestMapping("/board/read.do")
	public ModelAndView read(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		
		int boardSeq = Integer.parseInt(params.get("boardSeq"));
		
		if(params.containsKey("msg")) {
			mv.addObject("msg", params.get("msg"));
		}
		
		HashMap<String, Object> getBoard = bService.read(this.typeSeq, boardSeq);
		// 첨부파일 (hasFile) 이 1이면
		if(getBoard.get("has_file").equals("1")) {
			List<HashMap<String, Object>> getFile = bService.fileRead(typeSeq, boardSeq);
			mv.addObject("files", getFile);
		}
		
		mv.addObject("getBoard", getBoard);
		mv.setViewName("/board/read");
		
		return mv;
	}
	
	@RequestMapping("/board/goWritePage.do")
	public ModelAndView goWritePage(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		if(session.getAttribute("memberId") != null) {
			mv.setViewName("/board/write");
		} else {
			RedirectView rv = new RedirectView(contextRoot + "/index.do");
			mv.setView(rv);
		}
		
		return mv;
	}
	
	@RequestMapping("/board/write.do")
	public ModelAndView write(@RequestParam HashMap<String, Object> params, HttpSession session, MultipartHttpServletRequest mReq) {
		ModelAndView mv = new ModelAndView();
		params.put("typeSeq", this.typeSeq);

		List<MultipartFile> mFiles = mReq.getFiles("attFile");

		for(MultipartFile mf : mFiles) {
			if(!mf.getOriginalFilename().equals("")) {
				params.put("hasFile", "1");
				break;
			} else {
				params.put("hasFile", "0");
			}
		}
		
		bService.write(params, mFiles);
		
		String url = (session.getAttribute("useGrid").toString().equals("0"))? "/board/list.do" : "/board/gridList.do";
				
		RedirectView rv = new RedirectView(contextRoot + url);
		
		mv.setView(rv);
		
		return mv;
	}
	
	@RequestMapping("/board/goUpdatePage.do")
	public ModelAndView goUpdatePage(@RequestParam HashMap<String, String> params, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		if(session.getAttribute("memberId") != null) {
			
			int typeSeq = Integer.parseInt(params.get("typeSeq"));
			int boardSeq = Integer.parseInt(params.get("boardSeq"));
			
			HashMap<String, Object> getBoard = bService.read(typeSeq, boardSeq);
			
			if(session.getAttribute("memberId").equals(getBoard.get("member_id"))) {
				// 첨부파일 (hasFile) 이  1이면
				if(getBoard.get("has_file").equals("1")) {
					List<HashMap<String, Object>> getFile = bService.fileRead(typeSeq, boardSeq);
					mv.addObject("files", getFile);
				}
				
				mv.addObject("getBoard", getBoard);
				mv.setViewName("/board/update");
			} else {
				mv.addObject("msg", "본인이 작성한 글이 아닙니다.");
				
				RedirectView rv = new RedirectView(contextRoot + "/board/read.do?typeSeq="+getBoard.get("type_seq")+"&boardSeq="+getBoard.get("board_seq"));
				mv.setView(rv);
			}
		} else {
			mv.addObject("msg", "로그인 하세요.");
			mv.addObject("nextLocation", "/index.do");
			mv.setViewName("/common/error");
		}
		
		return mv;
	}
	
	@RequestMapping("/board/update.do")
	public ModelAndView update(@RequestParam HashMap<String, Object> params, HttpSession session, MultipartHttpServletRequest mReq) {
		ModelAndView mv = new ModelAndView();
		String msg = "";
		String url = "";
		
		if(session.getAttribute("memberId") != null) {
			
			if(session.getAttribute("memberId").equals(params.get("memberId"))) {
				
				List<MultipartFile> mFiles = mReq.getFiles("attFile");

				if(params.get("hasFile").equals("0")) {
					
					for (MultipartFile mf : mFiles) {
						if(!mf.getOriginalFilename().equals("")) {
							params.put("hasFile", "1");
							break;
						} else {
							params.put("hasFile", "0");
						}
					}
				}
				
				int result = bService.update(params, mFiles);
				
				if(result == 1) {
					msg = "글이 수정되었습니다.";
					url = "/board/read.do?typeSeq="+params.get("typeSeq")+"&boardSeq="+params.get("boardSeq");
				} else {
					msg = "글 수정에 실패하였습니다.";
					url = "/board/goUpdatePage.do?typeSeq="+params.get("typeSeq")+"&boardSeq="+params.get("boardSeq");
				}
			} else {
				msg = "본인이 작성한 글이 아닙니다.";
				url = "/board/goUpdatePage.do?typeSeq="+params.get("typeSeq")+"&boardSeq="+params.get("boardSeq");
			}
			
			RedirectView rv = new RedirectView(contextRoot + url);
			mv.setView(rv);
		} else {
			msg = "로그인 하세요.";
			mv.addObject("nextLocation", "/index.do");
			mv.setViewName("/common/error");
		}
		
		mv.addObject("msg", msg);
		return mv;
	}
	
	@RequestMapping("/board/delete.do")
	public ModelAndView delete(@RequestParam HashMap<String, String> params, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		String msg = "";
		String url = "";
		
		if(session.getAttribute("memberId") != null) {
			
			int typeSeq = Integer.parseInt(params.get("typeSeq"));
			int boardSeq = Integer.parseInt(params.get("boardSeq"));
			HashMap<String, Object> getBoard = bService.read(typeSeq, boardSeq);
			
			if(session.getAttribute("memberId").equals(getBoard.get("member_id"))) {
				int result = bService.delete(getBoard);
				
				if(result == 1) {
					msg = "삭제 되었습니다.";
					url = (session.getAttribute("useGrid").toString().equals("0"))? "/board/list.do" : "/board/gridList.do";
				} else {
					msg = "삭제에 실패했습니다.";
					url = "/board/read.do?typeSeq="+typeSeq+"&boardSeq="+boardSeq;
				}
			} else {
				msg = "본인이 작성한 글이 아닙니다.";
				url = "/board/read.do?typeSeq="+typeSeq+"&boardSeq="+boardSeq;
			}
			
			RedirectView rv = new RedirectView(contextRoot + url);
			mv.setView(rv);
			
		} else {
			msg = "로그인 하세요.";
			mv.addObject("nextLocation", "/index.do");
			mv.setViewName("/common/error");
		}
		
		mv.addObject("msg", msg);
		
		return mv;
	}
	
	@RequestMapping("/board/download.do")
	@ResponseBody
	public byte[] download(@RequestParam int fileIdx, HttpServletResponse rep) {
		// PK를 이용해 첨부파일 정보를 가져온다.
		HashMap<String, Object> fileInfo = bAttachService.getAttachFileInfo(fileIdx);
		byte[] b = null;
		
		// 첨부파일 정보를 토대로 파일을 읽어온다.
		b = fUtil.readFile(fileInfo);
		
		String encodingName = null;
		try {
			// 한글 파일명 인코딩
			encodingName = java.net.URLEncoder.encode(fileInfo.get("filename").toString(), "UTF-8");
		} catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}
		
		// 돌려보내기 위해 응답(HttpServletResponse)에 정보를 입력한다.
		// 파일 다운로드를 할 수 있는 정보들을 브라우저에 알려주는 역할(정보전달)
		rep.setHeader("Content-Disposition", "attachment; filename=\"" + encodingName + "\"");
		// 파일 타입에 맞춰서 다운로드 시켜준다.
		rep.setContentType(String.valueOf(fileInfo.get("file_type")));
		rep.setHeader("Pragma", "no-cache");
		rep.setHeader("Cache-Control", "no-cache");
		String tmp = String.valueOf(fileInfo.get("file_size"));
		rep.setContentLength(Integer.parseInt(tmp));
		
		return b;
	}
	
	@RequestMapping("/board/deleteAttach.do")
	public ModelAndView deleteAttach(@RequestParam int fileIdx, @RequestParam int typeSeq, @RequestParam int boardSeq) {
		ModelAndView mv = new ModelAndView();
		bService.deleteAttach(fileIdx, typeSeq, boardSeq);
		
		// 삭제가 되든 안되든 /board/update.do 로 리다이렉트 한다.
		RedirectView rv = new RedirectView(contextRoot + "/board/goUpdatePage.do?typeSeq="+typeSeq+"&boardSeq="+boardSeq);
		mv.setView(rv);
		
		return mv;
	}
}
