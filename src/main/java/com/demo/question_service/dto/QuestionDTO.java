package com.demo.question_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class QuestionDTO {

    private String question;
    private List<String> options;
    private String correctIndex;
    private String category;

}
