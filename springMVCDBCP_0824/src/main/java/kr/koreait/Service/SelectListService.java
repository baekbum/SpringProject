package kr.koreait.Service;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import kr.koreait.DAO.MvcBoardDAO;
import kr.koreait.VO.MvcBoardList;

public class SelectListService implements MvcBoardService {

	@Override
	public void execute(Model model) {
		System.out.println("SelectListService execute()");		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");		
		MvcBoardDAO dao = ctx.getBean("dao", MvcBoardDAO.class);
//		테이블에서 글 목록 전체를 얻어와서 리턴할 클래스의 객체를 선언한다.
		MvcBoardList list = ctx.getBean("mvcBoardList", MvcBoardList.class);
		list.setList(dao.SelectListService());
		model.addAttribute("list", list);		
	}
}
