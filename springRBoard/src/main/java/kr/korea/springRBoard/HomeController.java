package kr.korea.springRBoard;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.koreait.DAO.MybatisCommentDAO;
import kr.koreait.DAO.MybatisDAO;
import kr.koreait.VO.RBoardCommentList;
import kr.koreait.VO.RBoardCommentVO;
import kr.koreait.VO.RBoardList;
import kr.koreait.VO.RBoardVO;

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
	
	@Autowired
	private SqlSession sqlSession;
	
	AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	
	@RequestMapping("/insert")
	public String insert(HttpServletRequest request, Model model) {
		System.out.println("insert()");
		return "insertform";
	}
	
	@RequestMapping("/insertOK")
	public String insertOK(HttpServletRequest request, Model model) {
		System.out.println("insertOK()");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String ip = request.getRemoteAddr();
		
		MybatisDAO mybatisDAO = sqlSession.getMapper(MybatisDAO.class);
		RBoardVO vo = ctx.getBean("RBoardVO", RBoardVO.class);
		vo.setName(name);
		vo.setPassword(password);
		vo.setTitle(title);
		vo.setContent(content);
		vo.setIp(ip);
		
		mybatisDAO.insert(vo);
		
		return "redirect:list";
	}
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		System.out.println("list()");
		String t = request.getParameter("page");
		int pageNo = 1;
		if(t != null && !t.equals("") && t.trim().length() != 0) {
			try {
				pageNo = Integer.parseInt(t);
			} catch(Exception e) { }
		}
		MybatisDAO mybatisDAO = sqlSession.getMapper(MybatisDAO.class);
		RBoardList list = ctx.getBean("RBoardList", RBoardList.class);
		int pageSize = 10;
		int totalCount = mybatisDAO.selectCount();
		int currentPage = pageNo;
		list = new RBoardList(pageSize, totalCount, currentPage);
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("startNo", list.getStartNo());
		hmap.put("endNo", list.getEndNo());		
		list.setList(mybatisDAO.selectList(hmap));		
		model.addAttribute("board", list);	
		return "listView";
	}
	
	@RequestMapping("/increment")
	public String increment(HttpServletRequest request, Model model) {
		System.out.println("increment()");
		int idx = Integer.parseInt(request.getParameter("idx"));
		String t = request.getParameter("page");
		int pageNo = 1;
		if(t != null && !t.equals("") && t.trim().length() != 0) {
			try {
				pageNo = Integer.parseInt(t);
			} catch(Exception e) { }
		}	
		MybatisDAO mybatisDAO = sqlSession.getMapper(MybatisDAO.class);
		mybatisDAO.increment(idx);
		model.addAttribute("page", pageNo);
		model.addAttribute("idx", idx);
		return "redirect:view";
	}
	
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Model model) {
		System.out.println("view()");
		int idx = Integer.parseInt(request.getParameter("idx"));
		String t = request.getParameter("page");
		int pageNo = 1;
		if(t != null && !t.equals("") && t.trim().length() != 0) {
			try {
				pageNo = Integer.parseInt(t);
			} catch(Exception e) { }
		}	
		
		MybatisDAO mybatisDAO = sqlSession.getMapper(MybatisDAO.class);
		MybatisCommentDAO mybatisCommentDAO = sqlSession.getMapper(MybatisCommentDAO.class);
		
		RBoardVO vo = mybatisDAO.selectByIdx(idx);
		
		int cnt = mybatisCommentDAO.selectCommentCount(vo.getIdx());		
		
		if(cnt > 0) {
			List<RBoardCommentVO> comment = mybatisCommentDAO.selectCommentList(vo.getIdx());
			RBoardCommentList list = ctx.getBean("RBoardCommentList", RBoardCommentList.class);
			list.setList(comment);
			model.addAttribute("list", list);
		}		
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("vo", vo);
		return "view";
	}
	
	@RequestMapping("/edit")
	public String edit(HttpServletRequest request, Model model) {
		System.out.println("edit()");
		int idx = Integer.parseInt(request.getParameter("idx"));
		String t = request.getParameter("page");		
		int pageNo = 1;
		if(t != null && !t.equals("") && t.trim().length() != 0) {
			try {
				pageNo = Integer.parseInt(t);
			} catch(Exception e) { }
		}
		MybatisDAO mybatisDAO = sqlSession.getMapper(MybatisDAO.class);
		RBoardVO vo = mybatisDAO.selectByIdx(idx);
		model.addAttribute("vo", vo);
		model.addAttribute("pageNo", pageNo);
		return "edit";
	}
	
	@RequestMapping("/editOK")
	public String editOK(HttpServletRequest request, Model model) {
		System.out.println("editOK()");
		int idx = Integer.parseInt(request.getParameter("idx"));
		String t = request.getParameter("page");		
		int pageNo = 1;
		if(t != null && !t.equals("") && t.trim().length() != 0) {
			try {
				pageNo = Integer.parseInt(t);
			} catch(Exception e) { }
		}
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		MybatisDAO mybatisDAO = sqlSession.getMapper(MybatisDAO.class);
		RBoardVO compareVO = ctx.getBean("RBoardVO", RBoardVO.class);
		
		compareVO = mybatisDAO.selectByIdx(idx);		
		
		RBoardVO vo = ctx.getBean("RBoardVO", RBoardVO.class);
		vo.setIdx(idx);
		vo.setName(name);
		vo.setPassword(password);
		vo.setTitle(title);
		vo.setContent(content);
		
		if(compareVO.getPassword().trim().equals(password)){
			mybatisDAO.edit(vo);
			model.addAttribute("page", pageNo);
			return "redirect:list";
		}
		else {
			System.out.println("비밀번호 오류");
			model.addAttribute("idx", idx);
			model.addAttribute("page", pageNo);
			return "redirect:view";
		}	
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("delete()");
		int idx = Integer.parseInt(request.getParameter("idx"));
		String t = request.getParameter("page");
		int pageNo = 1;
		if(t != null && !t.equals("") && t.trim().length() != 0) {
			try {
				pageNo = Integer.parseInt(t);
			} catch(Exception e) { }
		}
		MybatisDAO mybatisDAO = sqlSession.getMapper(MybatisDAO.class);
		RBoardVO vo = mybatisDAO.selectByIdx(idx);
		model.addAttribute("vo", vo);
		model.addAttribute("pageNo", pageNo);		
		return "delete";
	}
	
	@RequestMapping("/deleteok")
	public String deleteOK(HttpServletRequest request, Model model) {
		System.out.println("deleteOK()");
		String t = request.getParameter("page");
		int pageNo = 1;
		if(t != null && !t.equals("") && t.trim().length() != 0) {
			try {
				pageNo = Integer.parseInt(t);
			} catch(Exception e) { }
		}
		int idx = Integer.parseInt(request.getParameter("idx"));
		String password = request.getParameter("password");
		
		MybatisDAO mybatisDAO = sqlSession.getMapper(MybatisDAO.class);
		MybatisCommentDAO mybatisCommentDAO = sqlSession.getMapper(MybatisCommentDAO.class);
		RBoardVO compareVO = ctx.getBean("RBoardVO", RBoardVO.class);
		
		compareVO = mybatisDAO.selectByIdx(idx);
		
		System.out.println(compareVO.getPassword());
		System.out.println(password);
		
		if(compareVO.getPassword().trim().equals(password)) {
			mybatisDAO.delete(idx);
			mybatisCommentDAO.cascadeDelete(idx);
			model.addAttribute("page", pageNo);
			return "redirect:list";
		}
		else {
			System.out.println("비밀번호 오류");
			model.addAttribute("idx", idx);
			model.addAttribute("page", pageNo);
			return "redirect:view";
		}		
	}
	
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request, Model model) {
		System.out.println("reply()");
		int idx = Integer.parseInt(request.getParameter("idx"));
		String t = request.getParameter("page");
		int pageNo = 1;
		if(t != null && !t.equals("") && t.trim().length() != 0) {
			try {
				pageNo = Integer.parseInt(t);
			} catch(Exception e) { }
		}
		MybatisDAO mybatisDAO = sqlSession.getMapper(MybatisDAO.class);
		RBoardVO vo = mybatisDAO.selectByIdx(idx);
		model.addAttribute("vo", vo);
		model.addAttribute("pageNo", pageNo);
		return "reply";
	}
	
	@RequestMapping("/replyok")
	public String replyok(HttpServletRequest request, Model model) {
		System.out.println("replyok()");
		int idx = Integer.parseInt(request.getParameter("idx"));
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String content = request.getParameter("content");
		String t = request.getParameter("page");
		int pageNo = 1;
		if(t != null && !t.equals("") && t.trim().length() != 0) {
			try {
				pageNo = Integer.parseInt(t);
			} catch(Exception e) { }
		}
		
		MybatisCommentDAO mybatisCommentDAO = sqlSession.getMapper(MybatisCommentDAO.class);
		RBoardCommentVO commentVO = ctx.getBean("RBoardCommentVO", RBoardCommentVO.class);
		
		commentVO.setIdx(idx);
		commentVO.setName(name);
		commentVO.setPassword(password);
		commentVO.setContent(content);
		commentVO.setIp(request.getRemoteAddr());
		
		mybatisCommentDAO.reply(commentVO);
		
		model.addAttribute("page", pageNo);
		return "redirect:list";		
	}
	
	@RequestMapping("/replyEdit")
	public String replyEdit(HttpServletRequest request, Model model) {
		System.out.println("replyEdit()");
		int idx = Integer.parseInt(request.getParameter("idx"));
		MybatisCommentDAO mybatisCommentDAO = sqlSession.getMapper(MybatisCommentDAO.class);
		RBoardCommentVO commentVO = mybatisCommentDAO.replyEdit(idx);
		
		model.addAttribute("vo", commentVO);
		
		return "replyEdit";
	}
	
	@RequestMapping("/replyEditOK")
	public String replyEditOK(HttpServletRequest request, Model model) {
		System.out.println("replyEditOK()");
		int idx = Integer.parseInt(request.getParameter("idx"));
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String content = request.getParameter("content");
		return "listView";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
