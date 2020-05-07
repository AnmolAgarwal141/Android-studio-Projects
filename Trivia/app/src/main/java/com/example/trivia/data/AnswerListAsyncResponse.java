package com.example.trivia.data;

import com.example.trivia.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void processfinished(ArrayList<Question> questionArrayList);
}
