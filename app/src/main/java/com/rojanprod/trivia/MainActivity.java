package com.rojanprod.trivia;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rojanprod.trivia.data.AsyncQuestionList;
import com.rojanprod.trivia.data.QuestionBank;
import com.rojanprod.trivia.model.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView questionText;
    private Button trueButton;
    private Button falseButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private TextView queCounter;
    private TextView correctNumText;
    private int correctCounter = 0;
    private int questionIndexCounter = 0;
    List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionText = findViewById(R.id.questionText);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        queCounter = findViewById(R.id.queCounter);
        correctNumText = findViewById(R.id.correctAnswers);

        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);

        questionList = new QuestionBank().getQuestions(new AsyncQuestionList() {
            @SuppressLint("DefaultLocale")
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {

                questionText.setText(questionArrayList.get(questionIndexCounter).getQuestionName());
                queCounter.setText(String.format("%d/%d", questionIndexCounter + 1, questionArrayList.size()));
                correctNumText.setText(String.format("Correct: %d",correctCounter));

                //Log.d("Inside Finish", "processFinished: " + questionArrayList);

            }
        });

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.true_button:
                answerUpdate(true);
                break;
            case R.id.false_button:
                answerUpdate(false);
                break;
            case R.id.nextButton:
                nextQueUpdate();
                break;
            case R.id.prevButton:
                prevQueUpdate();
                break;

        }
    }

    @SuppressLint("DefaultLocale")
    public void answerUpdate(boolean ans){
        if(questionList.get(questionIndexCounter).isAnswer()!=ans)
        {
            Toast.makeText(MainActivity.this,"Wrong Answer!",Toast.LENGTH_SHORT).show();
            questionIndexCounter = questionIndexCounter % questionList.size() + 1;
            questionText.setText(questionList.get(questionIndexCounter).getQuestionName());

        }else{

            Toast.makeText(MainActivity.this,"Correct Answer!",Toast.LENGTH_SHORT).show();
            questionIndexCounter = questionIndexCounter % questionList.size() + 1;
            correctCounter++;
            correctNumText.setText(String.format("Correct: %d", correctCounter));
            questionText.setText(questionList.get(questionIndexCounter).getQuestionName());
        }

    }

    @SuppressLint("DefaultLocale")
    public void nextQueUpdate(){

        if(questionIndexCounter!=questionList.size()){
            questionIndexCounter++;
            questionText.setText(questionList.get(questionIndexCounter).getQuestionName());
            queCounter.setText(String.format("%d/%d", questionIndexCounter+1, questionList.size()));
        }else
            Toast.makeText(MainActivity.this,"No more Questions!",Toast.LENGTH_SHORT).show();

    }

    @SuppressLint("DefaultLocale")
    public void prevQueUpdate(){
        if(questionIndexCounter>0){
            questionIndexCounter--;
            questionText.setText(questionList.get(questionIndexCounter).getQuestionName());
            queCounter.setText(String.format("%d/%d", questionIndexCounter+1, questionList.size()));
        }else
            Toast.makeText(MainActivity.this,"No previous Questions!",Toast.LENGTH_SHORT).show();
    }

}
