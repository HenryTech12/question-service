package com.demo.question_service.controller;

import com.demo.question_service.dto.QuestionDTO;
import com.demo.question_service.response.QuestionResponse;
import com.demo.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody QuestionDTO questionDTO) {
          questionService.createQuestion(questionDTO);
        System.out.println(questionDTO.getQuestion());
          return new ResponseEntity<>("question added to database", HttpStatus.OK);
    }

    @GetMapping("/questions")
    public ResponseEntity<List<QuestionDTO>> getQuestions() {
        return new ResponseEntity<>(questionService.getQuestions(), HttpStatus.OK);
    }

    @GetMapping("/get/category/{category}")
    public ResponseEntity<List<QuestionDTO>> getByCategory(@PathVariable String category) {
        return new ResponseEntity<>(questionService.getQuestionByCategory(category), HttpStatus.OK);
    }

    @GetMapping("/get/{category}/{numOfQuestion}")
    public ResponseEntity<List<QuestionDTO>> getByCategoryAndNumOfQuestion(@PathVariable String category, @PathVariable int numOfQuestion) {
        return new ResponseEntity<>(questionService.
                getQuestionByCategoryAndNumOfQuestion(category,numOfQuestion),
                HttpStatus.OK);
    }

    @GetMapping("/get/ID/{id}")
    public ResponseEntity<QuestionDTO> getByID(@PathVariable Long id) {
        return new ResponseEntity<>(questionService.getQuestionById(id), HttpStatus.OK);
    }

    @GetMapping("/remove/{id}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable Long id) {
        questionService.deleteQuestionById(id);
        return new ResponseEntity<>("question with id : "+id+" removed from db",
                HttpStatus.OK);
    }

    @GetMapping("/remove/{category}")
    public ResponseEntity<String> deleteQuestionByCategory(@PathVariable String category) {
        questionService.deleteQuestionByCategory(category);
        return new ResponseEntity<>("question under category : "+category+" removed from db",
                HttpStatus.OK);
    }

    @PostMapping("/check/grade")
    public ResponseEntity<String> checkScore(@RequestBody QuestionResponse questionResponse) {
        int result = questionService.calculateScore(questionResponse);
        questionResponse.getAnswersIndex().forEach(System.out::println);
        return new ResponseEntity<>("you got "+result+" question",
                HttpStatus.OK);
    }



}
