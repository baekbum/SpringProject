package kr.koreait.Service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import kr.koreait.DAO.MvcBoardDAO;
import kr.koreait.VO.MvcBoardVO;

public class ReplyService implements MvcBoardService {

	@Override
	public void execute(Model model) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");		
		MvcBoardDAO dao = ctx.getBean("dao", MvcBoardDAO.class);
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		int ref = Integer.parseInt(request.getParameter("ref"));
		int lev = Integer.parseInt(request.getParameter("lev"));
		int seq = Integer.parseInt(request.getParameter("seq"));

		MvcBoardVO vo = ctx.getBean("vo", MvcBoardVO.class);
		
		vo.setIdx(idx);
		vo.setName(name);
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setRef(ref);
//		답글이므로 lev와 seq을 1씩 증가시켜 저장한다
		vo.setLev(lev + 1);
		vo.setSeq(seq + 1);
		
//		글이 출력되는 순서를 조정하기 위해 조건에 만족하는 seq과 req 1씩 증가시키는 메소드를 실행한다.
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("seq", vo.getSeq());
		hmap.put("ref", vo.getRef());
		
		dao.replyIncrement(hmap);
		dao.replyInsert(vo);		
	}

}
