package com.englishTest.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.englishTest.main.model.Question;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {

}