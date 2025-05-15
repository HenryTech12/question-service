package com.demo.question_service.response;

import lombok.Data;

import java.util.List;

@Data
public class QuestionResponse {

    private Long questionId;
    private List<Integer> answersIndex;
    private String category;
}
