package com.rojanprod.trivia.model;

public class Question {
    private String questionName;
    private boolean answer;

    public Question() {
    }

    public Question(String questionName, boolean answer) {
        this.questionName = questionName;
        this.answer = answer;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
