package com.demo.question_service.service;

import com.demo.question_service.dto.QuestionDTO;
import com.demo.question_service.mapper.QuestionMapper;
import com.demo.question_service.model.Question;
import com.demo.question_service.repository.QuestionRepository;
import com.demo.question_service.response.QuestionResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionRepository questionRepository;

    private Logger logger = LoggerFactory.getLogger(QuestionService.class);

    public void createQuestion(QuestionDTO questionDTO) {
        if(!Objects.isNull(questionDTO)) {
            Question question = new Question();
            question = questionMapper.convertToModel(questionDTO);
            questionRepository.save(question);
            logger.info("question added to database.");
        }
    }

    public void updateQuestionById(Long id, QuestionDTO questionDTO) {

    }

    public QuestionDTO getQuestionById(Long id) {
        Optional<Question> optionalQuestion = questionRepository
                .findById(id);
        if(optionalQuestion.isPresent()) {
            Question question = optionalQuestion.orElseThrow(() -> {
                throw new NullPointerException("question object can't be null");
            });
            return questionMapper.convertToDto(question);
        }
        else
        {
            return null;
        }
    }

    public List<QuestionDTO> getQuestionByCategory(String category) {
        Optional<List<Question>> optionalQuestion = questionRepository
                .findByCategory(category);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        if(optionalQuestion.isPresent()) {
            List<Question> questionList = optionalQuestion.orElseThrow(() -> {
                throw new NullPointerException("question object can't be null");
            });
            for(Question question : questionList) {
                questionDTOList.add(questionMapper.convertToDto(question));
            }
            return questionDTOList;
        }
        else
        {
            return null;
        }
    }

    public List<QuestionDTO> getQuestionByCategoryAndNumOfQuestion(String category, int numOfQuestion) {
        Optional<List<Question>> optionalQuestionList = questionRepository.
                findByCategoryAndNumOfQuestion(category,numOfQuestion);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        if(optionalQuestionList.isPresent()) {
            List<Question> questionList = optionalQuestionList.orElse(new ArrayList<>());
            for(Question question : questionList) {
                questionDTOList.add(questionMapper.convertToDto(question));
            }
            logger.info(numOfQuestion+" question successfully fetched from db.");
        }
        return questionDTOList;
    }

    public List<QuestionDTO> getQuestions() {
        List<Question> questionList = questionRepository.findAll();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        if(!questionList.isEmpty()) {
            for(Question question: questionList) {
                System.out.println(question.getQuestion());
                questionDTOList.add(questionMapper.convertToDto(question));
                logger.info("all question fetched successfully from db.");
            }
            return questionDTOList;
        }
        return null;
    }

    public void deleteQuestionById(Long id) {
        questionRepository.deleteById(id);
        logger.info("question with id: {} removed from db.", id);
    }

    public void deleteQuestionByCategory(String category) {
        Optional<List<Question>> optionalQuestion = questionRepository
                .findByCategory(category);
        if(optionalQuestion.isPresent()) {
            List<Question> questions = optionalQuestion.orElseThrow(() -> {
                throw new NullPointerException("question object can't be null");
            });
            for(Question question : questions) {
                questionRepository.deleteById(question.getId());
                logger.info("question with id : {} removed from db.", question.getId());
            }
        }
    }

    public Integer calculateScore(QuestionResponse questionResponse) {
        Integer score = 0;
        long result = 0;
        Optional<List<Question>> optionalQuestionList = questionRepository.
                findByCategory(questionResponse.getCategory());
        List<Question> questionList = optionalQuestionList.orElseThrow(() -> {
            throw new NullPointerException("question object can't be null");
        });

        List<Integer> answersIndex = questionResponse.getAnswersIndex();

        for(int i = 0; i < answersIndex.size(); i++) {
           Integer correctIndex = Integer.parseInt(questionList.get(i).getCorrectIndex());
           Integer answerIndex = answersIndex.get(i);

           if(Objects.equals(answerIndex, correctIndex))
               score++;
        }
        logger.info("total number of score for question is : {}", score);
        return score;
    }
}
