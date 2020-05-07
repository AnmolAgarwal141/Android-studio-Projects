package com.example.trivia.model;

public class Question {
    private String Answer;
    private Boolean AnswerTrue;

    public Question(){

    }
    public Question(String answer,Boolean answerTrue){
        this.Answer=answer;
        this.AnswerTrue=answerTrue;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public void setAnswerTrue(Boolean answerTrue) {
        AnswerTrue = answerTrue;
    }

    public String getAnswer() {
        return Answer;
    }

    public Boolean getAnswerTrue() {
        return AnswerTrue;
    }
}
