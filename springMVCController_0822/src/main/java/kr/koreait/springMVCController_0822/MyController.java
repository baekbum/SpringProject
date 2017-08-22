package kr.koreait.springMVCController_0822;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
//	@Controller 어노테이션을 붙여 스프링에게 컨트롤러 클래스임을 알려준다
public class MyController {
//	컨트롤러에서 뷰 페이지를 찾아갈 메소드를 만들려면 @RequestMapping 어노테이션을 붙여준다.
//	@RequestMapping("요청경로")
	@RequestMapping("/view.nhn")	// url 창의 요청경로
	public String view() {
//		메소드가 리턴시켜주는 값이 컨트롤러가 되돌려주는 jsp 페이지의 파일 이름이 된다.
		return "view";		// 뷰 페이지 이름
	}
	
	@RequestMapping("/read.do")
	public String read() {
		return "read";
	}
	
	@RequestMapping("/content.nhn")
	public String content() {
		return "content/content";
	}
	
	@RequestMapping("/model.nhn")
	public String model(Model model) {
//		@RequestMapping 어노테이션이 설정된 메소드는 Model 인터페이스의 객체를 인수로 받을 수 있다.
//		Model 인터페이스 객체는 컨트롤러에서 뷰 페이지로 넘겨줄 데이터를 보관한다.
//		model.addAttribute("변수명", 값) : Model 인터페이스 객체에 값을 넣어준다.
		model.addAttribute("id1", "abc");
		model.addAttribute("id2", "xyz");
		return "model/model";
	}
	
	@RequestMapping("/modelandview.nhn")
	public ModelAndView modelAndView() {
//		ModelAndView 클래스는 컨트롤러에서 뷰 페이지로 넘겨줄 데이터와 뷰 페이지 이름을 보관한다.
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("id1", "abc");
		modelAndView.addObject("id2", "xyz");
//		setViewName("뷰 페이지 이름") : ModelAndView 클래스 객체에 뷰 페이지 이름을 넣어준다.
		modelAndView.setViewName("model/modelandview");
		return modelAndView;
		
		
	}
}
