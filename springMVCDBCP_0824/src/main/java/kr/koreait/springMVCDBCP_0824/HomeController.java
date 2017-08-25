package kr.koreait.springMVCDBCP_0824;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.koreait.Service.IncrementService;
import kr.koreait.Service.InsertService;
import kr.koreait.Service.MvcBoardService;
import kr.koreait.Service.contentViewService;
import kr.koreait.Service.SelectListService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	MvcBoardService service;
	AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	
//	게시글을 입력하는 폼을 띄우는 메소드
	@RequestMapping("/insert")
	public String insert(HttpServletRequest request, Model model) {
		return "insert";
	}	
	
	@RequestMapping("/insertOK")
	public String insertOK(HttpServletRequest request, Model model) {
//		입력 폼에서 넘어온 내용을 받아서 model 객체에 저정한다.
		model.addAttribute("request", request);
//		입력 폼에서 넘어온 내용을 저장한 model 객체로 Service 클래스의 글을 저장하는 메소드를 실행한다.
		
		service = ctx.getBean("insert", InsertService.class);		
		service.execute(model);
		
		return "insert";
	}
	
//	테이블에 저장된 글 전체를 얻어오는 메소드
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		System.out.println("list()");		
		service = ctx.getBean("selectList", SelectListService.class);
		service.execute(model);
		return "list";
	}
	
//	조회수를 증가시키는 메소드
	@RequestMapping("/increment")
	public String increment(HttpServletRequest request, Model model) {
		System.out.println("increment");
		model.addAttribute("request", request);
		service = ctx.getBean("increment", IncrementService.class);
		service.execute(model);
		model.addAttribute("idx", Integer.parseInt(request.getParameter("idx")));				
		return "redirect:contentView";
	}
	
//	게시글 한 건을 얻어오는 메소드
	@RequestMapping("/contentView")
	public String contextView(HttpServletRequest request, Model model) {
		model.addAttribute("request", request);
		service = ctx.getBean("contentView", contentViewService.class);
		service.execute(model);
		return "contentView";
	}
}

