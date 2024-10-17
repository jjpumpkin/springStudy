package com.future.my.member.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.future.my.common.exception.BizException;
import com.future.my.common.service.CodeService;
import com.future.my.common.valid.Login;
import com.future.my.common.valid.Regist;
import com.future.my.common.vo.CodeVO;
import com.future.my.common.vo.MessageVO;
import com.future.my.member.service.MemberService;
import com.future.my.member.vo.MemberVO;
import com.future.my.member.vo.QuestionVO;

@Controller
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	private  BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	CodeService codeService;
	
	//codeList라는 key로 MemberController 에서 연결되는 모든 뷰에서 사용 가능
	@ModelAttribute("comList")
	public ArrayList<CodeVO> getCodeList(){
		return codeService.getCodeList(null);
	}

	@Value("#{util['file.upload.path']}")
	private String CURR_IMAGE_PATH;
	
	@Value("#{util['file.download.path']}")
	private String WEB_PATH;
	
	
	@RequestMapping("/registView")
	public String registView( @ModelAttribute("member") MemberVO member) {
		return "member/registView";
	}
	@RequestMapping("/registDo")
	   //2024-10-17 2.model 추가, RedirectAttri 추가
	   // redirect 기본적으로 재요청을 하는것 이기에 modal 객체를 전달 할 수 없음 : forward 를 해줘여ㅑ함
   public String registDo(@Validated(Regist.class) @ModelAttribute("member")
   MemberVO member, BindingResult result  ,Model model) {
		if(result.hasErrors()) {
			// @validated의
			return "member/registView";
		}
		

	      //2024-10-17 1.기존 하나의 try-catch 를  3개짜리로 수정
	         try {
	        	   member.setMemPw(passwordEncoder.encode(member.getMemPw()));
	        	   memberService.registMember(member);
	         } catch (DuplicateKeyException e) {
	            MessageVO messageVO = new MessageVO(false,"회원가입","중복 아이디!","/registView","회원가입");
	            model.addAttribute("messageVO",messageVO);
	            return "member/registView";
	         
	         } catch (DataAccessException e) {
	            MessageVO messageVO = new MessageVO(false,"회원가입","잘못된 입력입니다!","/registView","회원가입");
	            model.addAttribute("messageVO",messageVO);
	            return "member/registView";
	         } catch (BizException e) {
	            MessageVO messageVO = new MessageVO(false,"회원가입","회원가입 안됨!","/registView","회원가입");
	            model.addAttribute("messageVO",messageVO);
	            return "member/registView";
	         }
	         MessageVO messageVO =
	         new MessageVO(true,"회원가입","회원가입 성공!","/loginView","로그인");
	         //2024-10-17 3. 리 다이렉트시 데이터 전달
	         model.addAttribute("messageVO",messageVO);
	         // 포워드는 지금 가지고 있는 값을 가지고 이동
	         return "forward:/";
	   }
	

	@RequestMapping("/loginView")
	public String loginView(@ModelAttribute("member") MemberVO member) {
		return "member/loginView";
	}

	@RequestMapping("/loginDo")
	public String loginDo(MemberVO vo, boolean remember
			            ,HttpSession session
			            ,HttpServletResponse response
			            ,@Validated(Login.class)@ModelAttribute("member") MemberVO member 
			            ,BindingResult result)throws Exception {
		if(result.hasErrors()) {
			return "member/loginView";
		}
		
		System.out.println(vo);
		MemberVO login = memberService.loginMember(vo);
		
		//입력한 비밀번호와 db의 암호화된 비번 비교 일치하면 true 그렇지 않으면 false
		boolean match = passwordEncoder.matches(vo.getMemPw(),login.getMemPw());
		System.out.println(match);
		if(login == null || !match) {
			return "redirect:/loginView";
		}
		
		session.setAttribute("login", login);
		
		if(remember) {
			// 쿠키생성
			Cookie cookie = new Cookie("rememberId", login.getMemId());
			response.addCookie(cookie);
		}else {
			//쿠키 삭제
			//동일한 key값을 가지는 쿠키의 유효시간을 0으로
			Cookie cookie = new Cookie("rememberId", "");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		
		return "redirect:/";
	}
	
	@RequestMapping("/logoutDo")
	public String logout(HttpSession session) throws Exception {
		
		session.invalidate();
		
		return "redirect:/";
	}
	
	@RequestMapping("/mypage")
	public String mypage(HttpSession session, Model model) {
		System.out.println(CURR_IMAGE_PATH);
		System.out.println(WEB_PATH);
		
		
		if(session.getAttribute("login")==null) {
			return "redirect:/loginView";
		}
		return "member/mypage";
	}
	@ResponseBody
	@PostMapping("/files/upload")
	public Map<String, Object>uploadFiles(
			HttpSession session, @RequestParam("uploadImage") MultipartFile uploadImage) throws Exception{
		MemberVO vo =(MemberVO) session.getAttribute("login");
		String imgPath = memberService.profileUpload(vo, CURR_IMAGE_PATH, WEB_PATH, uploadImage);
		
		Map<String,Object> map = new HashMap<>();
		//
		map.put("message","success");
		map.put("imagePath",imgPath);
		return map;
	}
	
	@RequestMapping("/test")
	public String test(Model model) {
		
//		ArrayList<CodeVO> comList = codeService.getCodeList(null);
//		model.addAttribute("comList", comList);
		
		return "member/test";
	}
	
	@RequestMapping("/survey")
	public String survey(Model model) {
		
		
		ArrayList<QuestionVO> qList= memberService.getSurvey();
		System.out.println(qList);
		model.addAttribute("qList", qList);
	     return "member/survey";	
		
	}
		
	
}
