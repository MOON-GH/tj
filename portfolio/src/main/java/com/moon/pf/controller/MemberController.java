package com.moon.pf.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.moon.pf.dto.Member;
import com.moon.pf.exception.MemberNotFoundException;
import com.moon.pf.exception.PasswordMissMatchException;
import com.moon.pf.service.MemberService;

@Controller
public class MemberController {
	@Autowired MemberService mService;
	@Value("#{config['project.context.path']}")
	private String contextRoot;
	
	Logger logger = Logger.getLogger(MemberController.class);
	
	@RequestMapping("/member/goLoginPage.do")
	public ModelAndView goLoginPage(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		if(session.getAttribute("memberId") != null) {
			RedirectView rv = new RedirectView("/index.do");
			mv.setView(rv);
		} else {
			mv.setViewName("login");
		}
		
		return mv;
	}
	
	@RequestMapping("/member/checkId.do")
	@ResponseBody
	public int checkId(@RequestParam String memberId) {
		return mService.checkId(memberId);
	}
	
	@RequestMapping("/member/join.do")
	public ModelAndView join(@RequestParam HashMap<String, Object> param) {
		ModelAndView mv = new ModelAndView();
		
		String msg = (mService.join(param) == 1) ? "회원가입 성공!!!" : "회원가입 실패!!!";
		RedirectView rv = new RedirectView("/member/goLoginPage.do");
		mv.addObject("msg", msg);
		mv.setView(rv);
		
		return mv;
	}
	
	@RequestMapping("/member/login.do")
	public ModelAndView login(@RequestParam String memberId, @RequestParam String memberPw, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();
		String msg = "시스템 에러";
		String page = "login";
		
		if(session.getAttribute("memberId") == null) {
			try {
				Member member = mService.login(memberId, memberPw);
				session.setAttribute("memberIdx", member.getMemberIdx());
				session.setAttribute("memberId", member.getMemberId());
				session.setAttribute("memberName", member.getMemberName());
				session.setAttribute("memberNick", member.getMemberNick());
				session.setAttribute("email", member.getEmail());
				session.setAttribute("birthDate", member.getBirthDate());
				session.setAttribute("typeSeq", member.getTypeSeq());
				
				RedirectView rv = new RedirectView(contextRoot + "/index.do");
				mv.setView(rv);
				
				return mv;
				
			} catch (MemberNotFoundException mnfe) {
				msg = mnfe.getMessage();
			} catch (PasswordMissMatchException pme) {
				msg = pme.getMessage();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			mv.setViewName(page);
			mv.addObject("msg", msg);
			
		} else {
			RedirectView rv = new RedirectView(contextRoot + "/index.do");
			mv.setView(rv);
		}
		
		return mv;
	}
	
	@RequestMapping("/member/logout.do")
	public ModelAndView logout(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		session.invalidate();
		RedirectView rv = new RedirectView(contextRoot + "/index.do");
		mv.setView(rv);
		
		return mv;
	}
	
	@RequestMapping("/member/mList.do")
	public String mList() {
		return "mList";
	}
	
	@RequestMapping("/member/getMemberList.do")
	@ResponseBody
	public HashMap<String, Object> getMemberList(@RequestParam HashMap<String, String> params) {
		ModelAndView mv = new ModelAndView();
		logger.debug("/member/mList.do params :" + params);
		// 한 페이지에 보여줄 게시글 수
		int rows = Integer.parseInt(params.get("rows"));
		// 현재 페이지
		int currentPage = Integer.parseInt(params.get("page"));
		// 시작 인덱스
		int startIdx = (currentPage - 1) * rows;
		params.put("startIdx", String.valueOf(startIdx));
		
		// 전체 회원 수 구하기
		int totalMember = mService.getMemberListCnt(params);
		// 총 페이지 수
		int totalPageCnt = (int) Math.ceil((double) totalMember / rows);
		// 끝 인덱스가 필요없는 이유
		//  : mySql 은 몇번째 부터 몇개를 가져와라 이기 때문에 필요없다.
		//  : 오라클은 몇번째 부터 몇번째 이기 때문에 끝 인덱스가 필요하다.
		
		// 블럭 수
		int pageBlockSize = 10;
		// 시작 페이지
		//int pageBlockStart = (currentPage-1) / pageBlockSize * pageBlockSize + 1;
		// 끝 페이지
		int pageBlockEnd = (currentPage-1) / pageBlockSize * pageBlockSize + pageBlockSize;
		pageBlockEnd = (pageBlockEnd >= totalPageCnt) ? totalPageCnt : pageBlockEnd;
		
		ArrayList<HashMap<String, Object>> mList = mService.getMemberList(params);
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("page", params.get("page")); // 현재페이지
		result.put("total", totalPageCnt); // 총 페이지 수
		result.put("rows", mList); // 데이터(목록)
		result.put("records", totalMember); // 총 회원 수
		
		return result;
	}
	
	@RequestMapping("/member/delMember.do")
	@ResponseBody
	public HashMap<String, String> delMember(@RequestParam HashMap<String, String> params) {
		HashMap<String, String> map = new HashMap<String, String>();
		int cnt = mService.deleteMember(params);
		
		map.put("msg", (cnt==1) ? "삭제 되었습니다." : "삭제 실패!");
		map.put("result",  String.valueOf(cnt));
		
		return map;
	}
}
