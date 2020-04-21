package com.rojanprod.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rojanprod.trivia.data.QuestionBank;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new QuestionBank().getQuestions();
    }
}
