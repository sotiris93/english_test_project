package com.englishTest.main.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.englishTest.main.model.Question;
import com.englishTest.main.model.QuestionForm;
import com.englishTest.main.model.Result;
import com.englishTest.main.repository.QuestionRepo;
import com.englishTest.main.repository.ResultRepo;

@Service
public class TestService {
	
	@Autowired
	Question question;
	@Autowired
	QuestionForm questionForm;
	@Autowired
	QuestionRepo questionRepo;
	@Autowired
	Result result;
	@Autowired
	ResultRepo resultRepo;
	
	public QuestionForm getQuestions() {
		List<Question> allQues = questionRepo.findAll();
		List<Question> qList = new ArrayList<>();
		
		Random random = new Random();
		
		for(int i=0; i<25; i++) {
			int rand = random.nextInt(allQues.size());
			qList.add(allQues.get(rand));
			allQues.remove(rand);
		}

		questionForm.setQuestions(qList);
		
		return questionForm;
	}
	
	public int getCorrectAnswers(QuestionForm questionForm) {
		int correct = 0;
		
		for(Question question: questionForm.getQuestions())
			if(question.getAnswer() == question.getSelection())
				correct++;
		
		return correct;
	}

	public int getFalseAnswers(QuestionForm questionForm) {
		int falseAnswers = 0;

		for(Question question: questionForm.getQuestions())
			if(question.getAnswer() != question.getSelection())
				falseAnswers++;

		return falseAnswers;
	}

	public int getTotalQuestions(QuestionForm questionForm) {
		int total= 0;

		for(Question question: questionForm.getQuestions()) {
			total++;
		}
		return total;
	}


	
	public void saveScore(Result result) {
		Result saveResult = new Result();
		saveResult.setUsername(result.getUsername());
		saveResult.setTotalCorrect(result.getTotalCorrect());
		resultRepo.save(saveResult);
	}
	
	public List<Result> getTopScore() {
		List<Result> sList = resultRepo.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));
		
		return sList;
	}
}