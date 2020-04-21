package com.rojanprod.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.rojanprod.trivia.data.AsyncQuestionList;
import com.rojanprod.trivia.data.QuestionBank;
import com.rojanprod.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new QuestionBank().getQuestions(new AsyncQuestionList() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {

                Log.d("Inside Finish", "processFinished: " + questionArrayList);

            }
        });

        //Log.d("Main", "onCreate: " + questionList);
    }
}
