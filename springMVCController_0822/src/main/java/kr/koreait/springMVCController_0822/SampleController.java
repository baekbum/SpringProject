package kr.koreait.springMVCController_0822;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/board")
public class SampleController {
	
	@RequestMapping("/board/board")
	public String view() {
		return "board/board";
	}

}
