package com.example.truecitizenquiz;

public class Question {
    private int AnswerResid;
    private Boolean AnswerTrue;

    public Question(int answerResid, Boolean answerTrue) {
        AnswerResid = answerResid;
        AnswerTrue = answerTrue;
    }

    public void setAnswerResid(int answerResid) {
        AnswerResid = answerResid;
    }

    public void setAnswerTrue(Boolean answerTrue) {
        AnswerTrue = answerTrue;
    }

    public int getAnswerResid() {
        return AnswerResid;
    }

    public Boolean getAnswerTrue() {
        return AnswerTrue;
    }
}
