package kr.koreait.springMVCDBCP_0824;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
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
import kr.koreait.DAO.MybatisDAO;
import kr.koreait.Service.ContentViewService;
import kr.koreait.Service.DeleteService;
import kr.koreait.Service.IncrementService;
import kr.koreait.Service.InsertService;
import kr.koreait.Service.MvcBoardService;
import kr.koreait.Service.ReplyService;
import kr.koreait.Service.SelectListService;
import kr.koreait.Service.UpdateService;
import kr.koreait.VO.MvcBoardList;
import kr.koreait.VO.MvcBoardVO;

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
	
//	mybstis를 사용하기 위해 SqlSession 객체를 선언한다.
	@Autowired
	private SqlSession sqlSession;
	
	public JdbcTemplate template;
	
	public JdbcTemplate getTemplate() {
		return template;
	}
//	servlet-context.xml에서 생성된 JdbcTemplate 객체를 template에 자동으로 setter를 통해서 저장되게 하기 위해
//	template의 setter에 @Autowired 어노테이션을 추가한다.
	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		Constant.template = this.template;
	}

	MvcBoardService service;
	AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	
//	게시글을 입력하는 폼을 띄우는 메소드
	@RequestMapping("/insert")
	public String insert(HttpServletRequest request, Model model) {
		System.out.println("insert()");
		return "insert";
	}
	
//	입력 폼에 입력된 게시글을 테이블에 저장하는 메소드
	@RequestMapping("/insertOK")
	public String insertOK(HttpServletRequest request, Model model) {
		System.out.println("insertOK()");
		/*
		model.addAttribute("request", request);
		service = ctx.getBean("insert", InsertService.class);
		service.execute(model);
		*/
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
//		mybatis를 사용하기 위해 SqlSession 객체에서 mapper를 얻어온다.
		MybatisDAO mybatisDAO = sqlSession.getMapper(MybatisDAO.class);
//		mybatisDAO.insert(name, subject, content);
		/*
		MvcBoardVO vo = ctx.getBean("vo", MvcBoardVO.class);
		vo.setName(name);
		vo.setSubject(subject);
		vo.setContent(content);
		mybatisDAO.insert(vo);
		*/
		mybatisDAO.insert(new MvcBoardVO(name, subject, content));
		return "redirect:list";
	}
	
//	테이블에 저장된 글 전체를 얻어오는 메소드
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		System.out.println("list()");
		/*
		service = ctx.getBean("selectList", SelectListService.class);
		service.execute(model);
		*/
		MybatisDAO mybatisDAO = sqlSession.getMapper(MybatisDAO.class);
		MvcBoardList list = ctx.getBean("mvcBoardList", MvcBoardList.class);
		list.setList(mybatisDAO.selectList());
		model.addAttribute("list", list) ;
		return "list";
	}
	
//	조회수를 증가시키는 메소드
	@RequestMapping("/increment")
	public String increment(HttpServletRequest request, Model model) {
		System.out.println("increment()");
		/*
		model.addAttribute("request", request);
		service = ctx.getBean("increment", IncrementService.class);
		service.execute(model);
		model.addAttribute("idx", Integer.parseInt(request.getParameter("idx")));
		*/
		int idx = Integer.parseInt(request.getParameter("idx"));
		MybatisDAO mybatisDAO = sqlSession.getMapper(MybatisDAO.class);
		mybatisDAO.increment(idx);
		model.addAttribute("idx", idx);
		return "redirect:contentView";
	}
	
//	게시글 한 건을 얻어오는 메소드
	@RequestMapping("/contentView")
	public String contentView(HttpServletRequest request, Model model) {
		System.out.println("contentView()");
		/*
		model.addAttribute("request", request);
		service = ctx.getBean("contentView", ContentViewService.class);
		service.execute(model);
		*/
		int idx = Integer.parseInt(request.getParameter("idx"));
		MybatisDAO mybatisDAO = sqlSession.getMapper(MybatisDAO.class);
		MvcBoardVO vo = mybatisDAO.contentView(idx);
		model.addAttribute("vo", vo);
		return "contentView";
	}
	
//	게시글 한 건을 수정하는 메소드
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Model model) {
		System.out.println("update()");
		/*
		model.addAttribute("request", request);
		service = ctx.getBean("update", UpdateService.class);
		service.execute(model);
		*/
		int idx = Integer.parseInt(request.getParameter("idx"));
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		MybatisDAO mybatisDAO = sqlSession.getMapper(MybatisDAO.class);
		MvcBoardVO vo = ctx.getBean("vo", MvcBoardVO.class);
		vo.setIdx(idx);
		vo.setSubject(subject);
		vo.setContent(content);
		mybatisDAO.update(vo);
		return "redirect:list";
	}
	
//	게시글 한 건을 삭제하는 메소드
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("delete()");
		/*
		model.addAttribute("request", request);
		service = ctx.getBean("delete", DeleteService.class);
		service.execute(model);
		*/
		int idx = Integer.parseInt(request.getParameter("idx"));
		MybatisDAO mybatisDAO = sqlSession.getMapper(MybatisDAO.class);
		mybatisDAO.delete(idx);
		return "redirect:list";
	}
	
//	게시글에 답글을 입력하는 메소드
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request, Model model) {
		System.out.println("reply()");
		/*
		model.addAttribute("request", request);
		service = ctx.getBean("contentView", ContentViewService.class);
		service.execute(model);
		*/
		int idx = Integer.parseInt(request.getParameter("idx"));
		MybatisDAO mybatisDAO = sqlSession.getMapper(MybatisDAO.class);
		MvcBoardVO vo = mybatisDAO.contentView(idx);
		model.addAttribute("vo", vo);
		return "reply";
	}
	
//	답글을 저장하는 메소드
	@RequestMapping("/replyOK")
	public String replyOK(HttpServletRequest request, Model model) {
		System.out.println("replyOK()");
		/*
		model.addAttribute("request", request);
		service = ctx.getBean("reply", ReplyService.class);
		service.execute(model);
		*/
		int idx = Integer.parseInt(request.getParameter("idx"));
		int ref = Integer.parseInt(request.getParameter("ref"));
		int lev = Integer.parseInt(request.getParameter("lev")) + 1;
		int seq = Integer.parseInt(request.getParameter("seq")) + 1;
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		MybatisDAO mybatisDAO = sqlSession.getMapper(MybatisDAO.class);
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("ref", ref);
		hmap.put("seq", seq);
		mybatisDAO.replyIncrement(hmap);
		
		MvcBoardVO vo = ctx.getBean("vo", MvcBoardVO.class);
		vo.setIdx(idx);
		vo.setRef(ref);
		vo.setLev(lev);
		vo.setSeq(seq);
		vo.setName(name);
		vo.setSubject(subject);
		vo.setContent(content);
		mybatisDAO.replyInsert(vo);
		
		return "redirect:list";
	}
	
}








