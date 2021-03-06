package kr.koreait.Service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import kr.koreait.DAO.MvcBoardDAO;

public class IncrementService implements MvcBoardService {

	@Override
	public void execute(Model model) {		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardDAO dao = ctx.getBean("dao", MvcBoardDAO.class);
		
		dao.increment(idx);
	}
}
