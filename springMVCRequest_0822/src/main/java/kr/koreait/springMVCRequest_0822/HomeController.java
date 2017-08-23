package kr.koreait.springMVCRequest_0822;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping("/memberView")
//	HttpServletRequest 인터페이스는 뷰 페이지에서 컨트롤러로 넘어오는 데이터를 받아서 저장한다.
//	Model 인터페이스 객체는 컨트롤러에서 뷰 페이지로 넘겨주는 데이터를 저장한다.
	public String memberView(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		model.addAttribute("id", id);
		model.addAttribute("pw", pw);
		return "memberView";
	}
	
	@RequestMapping("/memberConfirm")
//	@RequestMapping("뷰 페이지에서 값이 저장되서 넘어오는 변수명")
//	@RequestMapping 어노테이션을 사용해 뷰 페이지에서 넘어오는 데이터를 받아서 변수에 저장시킨다.
//	HttpServletRequest 인터페이스 객체는 뷰 페이지에서 데이터가 넘어오지 않더라도 에러가 발생되지 않지만
//	@RequestParam 어노테이션을 사용하는 경우에는 뷰 페이지에서 데이터가 넘어오지 않으면 400에러가 발생된다.
	public String memberConfirm(@RequestParam("id") String id, @RequestParam("pw") String pw, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("pw", pw);
		return "memberConfirm";
	}
	
	@RequestMapping("/insert.nhn")
	public String insert(HttpServletRequest request, Model model) {
		return "insert";		
	}
	
	@RequestMapping("/insertOK.nhn")
	/*
	public String insertOK(HttpServletRequest request, Model model) {
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		//MemberVO vo = new MemberVO();
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MemberVO vo = ctx.getBean("vo", MemberVO.class);
		vo.setName(name);
		vo.setId(id);
		vo.setPassword(password);
		vo.setEmail(email);
		model.addAttribute("memberVO", vo);
		return "insertOK";		
	}
	*/
	
// 	뷰 페이지에서 컨트롤러로 넘어온 데이터를 클래스의 객체에 저장하려 할 경우 데이터(커멘드) 객체를 사용하면 편리하다.
//	HttpServletRequest 인터페이스의 객체나 @RequestParam 어노테이션으로 받아서 넘겨받은 데이터를
//	저장할 클래스의 객체를 생성하고 setter 메소드를 실행해 Model 인터페이스의 객체에 넣어주는 동작이 일괄적으로 처리된다.
//	데이터(커멘드) 객체가 정상적으로 사용하려면 객체 이름을 클래스 이름과 같게 만들어야 한다는 것이다.
//	=> 첫 문자는 소문자로 한다.
//	커멘드 객체의 이름이 너무 길어서 사용하기 불편하다면 @ModelAttribute를 사용한다.
	public String insertOK(@ModelAttribute("vo") MemberVO memberVO) {
		return "insertOK";
	}
	
}
