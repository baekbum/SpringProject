package kr.koreait.springMVCDBCP_0824;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.koreait.DAO.Constant;
import kr.koreait.Service.DeleteService;
import kr.koreait.Service.IncrementService;
import kr.koreait.Service.InsertService;
import kr.koreait.Service.MvcBoardService;
import kr.koreait.Service.ReplyService;
import kr.koreait.Service.contentViewService;
import kr.koreait.Service.SelectListService;
import kr.koreait.Service.UpdateService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	public JdbcTemplate template;
	
	public JdbcTemplate getTemplate() {
		return template;
	}
//	servlet-context.xml에서 생성된 JdbcTemplate 객체를 template에 자동으로 setter를 통해서 저장되게 하기 위해
//	template의 setter @Autowired 어노테이션을 추가한다. 
	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		Constant.template = this.template;
	}
	
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
		
		return "redirect:list";
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
		System.out.println(request + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		model.addAttribute("request", request);
		service = ctx.getBean("contentView", contentViewService.class);
		service.execute(model);
		return "contentView";
	}
	
//	게시글 한 건을 수정하는 메소드
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Model model) {
		System.out.println("update()");
		model.addAttribute("request", request);
		service = ctx.getBean("update", UpdateService.class);
		service.execute(model);
		return "redirect:list";
	}
	
//	게시글 한 건을 삭제하는 메소드
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("delete()");
		model.addAttribute("request", request);
		service = ctx.getBean("delete", DeleteService.class);
		service.execute(model);
		return "redirect:list";
	}
	
//	답글을 하나를 얻어오는 메소드
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request, Model model) {
		System.out.println("reply()");
		model.addAttribute("request", request);
		service = ctx.getBean("contentView", contentViewService.class);
		service.execute(model);
		return "reply";
	}
	
//	답글을 저장하는 메소드
	@RequestMapping("/replyOK")
	public String replyOK(HttpServletRequest request, Model model) {
		System.out.println("replyOK()");
		model.addAttribute("request", request);
		service = ctx.getBean("reply", ReplyService.class);
		service.execute(model);
		return "redirect:list";
	}
}

