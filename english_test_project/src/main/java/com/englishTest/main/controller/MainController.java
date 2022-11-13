package com.englishTest.main.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.englishTest.main.model.QuestionForm;
import com.englishTest.main.model.Result;
import com.englishTest.main.service.TestService;

@Controller
public class MainController {
	
	@Autowired
	Result result;
	@Autowired
	TestService quizService;
	
	Boolean submitted = false;
	
	@ModelAttribute("result")
	public Result getResult() {
		return result;
	}
	
	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/levels")
	public String levels() {
		return "levels";
	}
	
	@PostMapping("/test")
	public String test(@RequestParam String username, Model m, RedirectAttributes redirectAttributes) {
		if(username.equals("")) {
			redirectAttributes.addFlashAttribute("warning", "Please enter your name");
			return "redirect:/";
		}
		
		submitted = false;
		result.setUsername(username);
		
		QuestionForm qForm = quizService.getQuestions();
		m.addAttribute("qForm", qForm);
		
		return "test";
	}
	
	@PostMapping("/submit")
	public String submit(@ModelAttribute QuestionForm questionForm) {
		if(!submitted) {
			result.setTotalCorrect(quizService.getCorrectAnswers(questionForm));
			result.setTotalFalse(quizService.getFalseAnswers(questionForm));
			result.setTotalQuestions(quizService.getTotalQuestions(questionForm));
			quizService.saveScore(result);
			submitted = true;
		}
		
		return "result";
	}
	
	@GetMapping("/score")
	public String score(Model model) {
		List<Result> scoreList = quizService.getTopScore();
		model.addAttribute("sList", scoreList);
		
		return "scoreboard";
	}

}
