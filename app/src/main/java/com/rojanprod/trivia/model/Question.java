package com.rojanprod.trivia.model;

/**
 * This class is used to represent the single question of the trivia quiz questions
 * When an array of questions are extracted from Json request then the individual question are the
 * instance of this class where the individual question contains a questionName and an answer as 'true' or 'false'
 * This class is used to set the individual question and to extract the individual question and answer
 * for a single element in the question list array
 */
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

    @Override
    public String toString() {
        return "Question{" +
                "questionName='" + questionName + '\'' +
                ", answer=" + answer +
                '}';
    }
}
