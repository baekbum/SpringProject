package kr.koreait.springMVCSample_0821;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//	servlet-context.xml ==> spring 환경 설정 파일
//	servlet-context.xml 파일에서 base-package로 지정된 패키지의 클래스 중에서 @Controller 어노테이션이 붙은
//	클래스가 컨트롤러로 사용된다.
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
//	@RequestMapping 어노테이션의 method 속성에 RequestMethod.POST라 쓰면 post 방식일때만 실행되고 
//	RequestMethod.GET이라 쓰면 get 방식일때만 실행된다. 
//	post 방식이나 get 방식에 관계없이 모두 실행되게 하려면 method 속성을 사용하지 않으면 된다.
//	method 속성을 사용하지 않을 경우 @RequestMapping 어노테이션의 괄호 안에는 요청 경로만 적어주면 된다.
	@RequestMapping("/")
//	url 창에 context 이름 다음에 "/"라고 요청이 들어오면 home() 이라는 메소드를 실행한다.
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
//		작업이 완료되면 화면에 표시할 뷰 페이지 이름을 리턴시킨다.
//		뷰 페이지 이름만 리턴시키면 servlet-context.xml의 prefix 속성에 적은 내용이 뷰 페이지 이름 앞에
//		suffix 속성에 적은 내용이 뷰 페이지 이름 뒤에 자동으로 붙어서 뷰 페이지의 전체 경로를 나타낸다.
		return "home";
	}
	
}



