package kr.koreait.Service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import kr.koreait.DAO.MvcBoardDAO;
import kr.koreait.VO.MvcBoardVO;

public class contentViewService implements MvcBoardService {

	@Override
	public void execute(Model model) {
		System.out.println("contentViewService execute()");
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");		
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");		
		MvcBoardDAO dao = ctx.getBean("dao", MvcBoardDAO.class);
		
		int idx = Integer.parseInt(request.getParameter("idx"));
//		테이블에서 글 한 건을 얻어와서 리턴할 클래스의 객체를 선언한다.
		MvcBoardVO vo = ctx.getBean("vo", MvcBoardVO.class);
		vo = dao.contentView(idx);
		model.addAttribute("vo", vo);		
	}
}
