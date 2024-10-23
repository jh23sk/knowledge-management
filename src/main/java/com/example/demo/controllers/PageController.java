package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * VueのSPAを返す。存在しないURLへのアクセス時はHtml5HistoryModeResourceConfigで返す。
 * https://sun-blog.site/vue-js-spring-boot-heroku%EF%BC%88%E3%83%97%E3%83%AD%E3%82%B8%E3%82%A7%E3%82%AF%E3%83%88%E4%BD%9C%E6%88%90%E3%83%93%E3%83%AB%E3%83%89%EF%BC%89/
 */
@Controller
public class PageController {
	@GetMapping("/top")
	public String displayTop() {
		return "forward:/top.html";
		// return "index"; //templates配下にindex.htmlがあればこのように省略OK
	}

	@GetMapping("/question")
	public String displayQA() {
		return "forward:/question.html";
	}

	@GetMapping("/personal")
	public String displayPersonal() {
		// 登録内容をDB検索
		// 検索結果のデータを画面に渡して初期表示する
		return "forward:/personal.html";
	}

	//	↑↓どっちでもOK	
	//	@GetMapping("/")
	//	public ModelAndView response() {
	//		ModelAndView mav = new ModelAndView();
	//		mav.setViewName("forward:/index.html");
	//		return mav;
	//	}

}
