package kr.koreait.springMVCRedirect;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	@RequestMapping("/confirm")
	public String confirm(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		model.addAttribute("id", id);
//		redirect : 뒤에 적어주는 내용은 뷰 페이지 이름이 아니고 컨트롤러에게 요청을 보낸다.
//		@RequestMapping("/confirmOK") 컨트롤러의 어노테이션이 붙어있는 메소드를 실행한다.
		if(id.equals("abc")) {
			return "redirect:confirmOK";
		}
		else {
			return "redirect:confirmNG";
		}		
	}
	
	@RequestMapping("/confirmOK")
	public String confirmOK(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		model.addAttribute("id", id);
		return "confirmOK";
	}	
	
	@RequestMapping("/confirmNG")
	public String confirmNG(HttpServletRequest request, Model model) {
		return "confirmNG";
	}
}
