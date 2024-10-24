package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	// トップ画面へ遷移
	@GetMapping("/top")
	public String displayTop() {
		return "forward:/top.html";
	}

	// Q&A画面へ遷移
	@GetMapping("/question")
	public String displayQA() {
		return "forward:/question.html";
	}

	// 個人ナレッジ画面へ遷移
	@GetMapping("/personal")
	public String displayPersonal() {
		return "forward:/personal.html";
	}
}
