package com.rojanprod.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

        /* This method is used to get the Question Array list from the Json request and
         *  its address is copied to another array list variable and some initial operations are performed here
         */
        questionList = new QuestionBank().getQuestions(new AsyncQuestionList() {
            @SuppressLint("DefaultLocale")
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {

                questionText.setText(questionArrayList.get(questionIndexCounter).getQuestionName());
                queCounter.setText(String.format("%d/%d", questionIndexCounter + 1, questionArrayList.size()-1));
                correctNumText.setText(String.format("Correct: %d",correctCounter));

                //Log.d("Inside Finish", "processFinished: " + questionArrayList);

            }
        });

    }

    /**
     * This method defines the behaviour of the buttons
     * When the buttons are clicked different methods are called and different behaviour is performed
     */
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

    /**
     * This method is used to check whether the answer is correct or not
     * If the answer is wrong then certain operations are performed and else it is correct then different
     * operations are performed like changing question and updating the correct answer parameter
     */
    @SuppressLint("DefaultLocale")
    private void answerUpdate(boolean ans){
        if(questionList.get(questionIndexCounter).isAnswer()==ans)
        {
            Toast.makeText(MainActivity.this,"Correct!",Toast.LENGTH_SHORT).show();
            correctCounter++;
            correctNumText.setText(String.format("Correct: %d",correctCounter));
            updateQuestion();
        }else{
            Toast.makeText(MainActivity.this,"Wrong!",Toast.LENGTH_SHORT).show();
            shakeAnimation();
            updateQuestion();
        }

    }

    /**
     * nextQueUpdate() is a method used to change the question when the next button is pressed.
     * This method changes the question to next counter and makes Toast if the question list reaches the last question
     */
    private void nextQueUpdate(){

        if (questionIndexCounter != (questionList.size())) {
            updateQuestion();
        }else
            Toast.makeText(MainActivity.this, "No more Questions!", Toast.LENGTH_SHORT).show();
    }

    /**
     * prevQueUpdate() method is used to change the question to previous question.
     * When the previous button is clicked then the previous question is shown and the question counter is decreased
     * When the question index reaches to 0 then there are no previous question
     */
    @SuppressLint("DefaultLocale")
    private void prevQueUpdate(){
        if(questionIndexCounter>0){
            questionIndexCounter--;
            questionText.setText(questionList.get(questionIndexCounter).getQuestionName());
            queCounter.setText(String.format("%d/%d", questionIndexCounter+1, questionList.size()));
        }else
            Toast.makeText(MainActivity.this,"No previous Questions!",Toast.LENGTH_SHORT).show();
    }

    /**
     * This method is used to update question i.e this method is used for increment of the question Index counter
     * and the text is set to questionText field using the Index counter to follow question number
     */
    @SuppressLint("DefaultLocale")
    private void updateQuestion()
    {
        questionIndexCounter = (questionIndexCounter+1) % questionList.size();
        questionText.setText(questionList.get(questionIndexCounter).getQuestionName());
        queCounter.setText(String.format("%d/%d", questionIndexCounter+1, questionList.size()));
    }

    /**
     * This method is used to set shaking animation to the card view of the question displaying text
     */
    private void shakeAnimation()
    {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.shake_animation);
        CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);

    }


}
