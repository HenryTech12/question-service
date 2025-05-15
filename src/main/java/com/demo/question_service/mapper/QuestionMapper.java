package com.demo.question_service.mapper;

import com.demo.question_service.dto.QuestionDTO;
import com.demo.question_service.model.Question;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class QuestionMapper {

    @Autowired
    private ModelMapper mapper;

    public QuestionDTO convertToDto(Question question) {
        if(!Objects.isNull(question))
            return mapper.map(question, QuestionDTO.class);
        else
            return null;
    }

    public Question convertToModel(QuestionDTO questionDTO) {
        if(!Objects.isNull(questionDTO))
            return mapper.map(questionDTO,Question.class);
        else
            return null;
    }
}
