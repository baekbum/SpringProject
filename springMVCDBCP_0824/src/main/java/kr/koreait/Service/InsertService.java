package kr.koreait.Service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import kr.koreait.DAO.MvcBoardDAO;

public class InsertService implements MvcBoardService {

	@Override
	public void execute(Model model) {
		System.out.println("InsertService execute()");
//		컨트롤러에서 Model 인터페이스 객체에 저장시켜 넘겨주는 입력받은 내용을 읽어낸다.
//		Model 인터페이스 객체는 key와 value로 구성된 Map<String, Object> 타입의 객체이다.
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
//		넘거받은 내용으로 DAO 클래스의 insert SQL 명령을 실행하는 메소드를 실행한다.
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		System.out.println(name);
		System.out.println(subject);
		System.out.println(content);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");		
		MvcBoardDAO dao = ctx.getBean("dao", MvcBoardDAO.class);
		
		dao.insert(name, subject, content);
	}
}
