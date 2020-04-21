package com.rojanprod.trivia.data;

import com.rojanprod.trivia.model.Question;

import java.util.ArrayList;

public interface AsyncQuestionList {

    /** This process gets ArrayList of the Question array fetched from the Json request
     * and this method is override in the MainActivity class when the getQuestion() method is called
     * from the MainActivity
     * */
    void processFinished(ArrayList<Question> questionArrayList);
}
