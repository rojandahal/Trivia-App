package com.rojanprod.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rojanprod.trivia.data.AsyncQuestionList;
import com.rojanprod.trivia.data.QuestionBank;
import com.rojanprod.trivia.model.Prefs;
import com.rojanprod.trivia.model.Question;
import com.rojanprod.trivia.model.Score;

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
    private int scoreCounter = 0;
    private int questionIndexCounter;
    private TextView highestScore;
    private Score score;
    private Prefs prefs;
    List<Question> questionList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score = new Score();
        prefs = new Prefs(this);

        questionText = findViewById(R.id.questionText);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        queCounter = findViewById(R.id.queCounter);
        correctNumText = findViewById(R.id.correctAnswers);
        highestScore = findViewById(R.id.highest_score);

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

                questionIndexCounter = prefs.getPreferences().getInt("current_que", 0);
                questionText.setText(questionArrayList.get(questionIndexCounter).getQuestionName());
                queCounter.setText(String.format("%d/%d", questionIndexCounter + 1, questionArrayList.size() - 1));
                correctNumText.setText(String.format("Correct: %d", score.getScore()));

                int value = prefs.getPreferences().getInt("high_score", 0);
                highestScore.setText(String.format("Highest: %s", value));
                score.setHighScore(value);

                /* These are the logs for debugging whether the values are properly called or not
                 * Log.d("Inside Finish", "processFinished: " + questionArrayList);
                 * Log.d("ActivityTracker", "onCreate: HighScore: " + score.getHighScore());
                 * Log.d("ActivityTracker", "onCreate: QuestionIndex: " + questionIndexCounter);
                 */
            }
        });
    }

    /**
     * This method defines the behaviour of the buttons
     * When the buttons are clicked different methods are called and different behaviour is performed
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
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
     *
     * @param ans
     */
    @SuppressLint("DefaultLocale")
    private void answerUpdate(boolean ans) {
        if (questionList.get(questionIndexCounter).isAnswer() == ans) {
            increaseScore();
            correctNumText.setText(String.format("Correct: %d", score.getScore()));
            fadeAnimation();
            updateQuestion();
        } else {
            shakeAnimation();
            updateQuestion();
        }

    }

    /**
     * nextQueUpdate() is a method used to change the question when the next button is pressed.
     * This method changes the question to next counter and makes Toast if the question list reaches the last question
     */
    private void nextQueUpdate() {

        if (questionIndexCounter != (questionList.size() - 1)) {
            questionIndexCounter++;
            updateQuestion();
        } else
            Toast.makeText(MainActivity.this, "No more Questions!", Toast.LENGTH_SHORT).show();
    }

    /**
     * prevQueUpdate() method is used to change the question to previous question.
     * When the previous button is clicked then the previous question is shown and the question counter is decreased
     * When the question index reaches to 0 then there are no previous question
     */
    @SuppressLint("DefaultLocale")
    private void prevQueUpdate() {
        if (questionIndexCounter > 0) {
            questionIndexCounter--;
            questionText.setText(questionList.get(questionIndexCounter).getQuestionName());
            queCounter.setText(String.format("%d/%d", questionIndexCounter + 1, questionList.size()));
        } else
            Toast.makeText(MainActivity.this, "No previous Questions!", Toast.LENGTH_SHORT).show();
    }

    /**
     * This method is used to update question i.e this method is used for increment of the question Index counter
     * and the text is set to questionText field using the Index counter to follow question number
     */
    @SuppressLint("DefaultLocale")
    private void updateQuestion() {
        questionText.setText(questionList.get(questionIndexCounter).getQuestionName());
        queCounter.setText(String.format("%d/%d", questionIndexCounter + 1, questionList.size()));
        if (updateHighScore()) {
            highestScore.setText(String.format("Highest: %s", score.getHighScore()));
        }
    }

    /**
     * This method is used to set shaking animation to the card view of the question displaying text
     */
    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.shake_animation);
        final CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * This method is used to set fadeAnimation which uses AlphaAnimation class of java
     * Currently this method sets the cardView to green for 200ms when correct answer is pressed
     */

    private void fadeAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        final CardView cardView = findViewById(R.id.cardView);

        alphaAnimation.setDuration(200);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setRepeatCount(1);
        cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * This method is used to update the highest score if the current score exceeds it and
     * it returns a boolean to confirm the action is completed or not
     *
     * @return
     */
    private boolean updateHighScore() {

        if (score.getScore() > score.getHighScore()) {
            score.setHighScore(scoreCounter);
            return true;
        }
        return false;
    }

    /**
     * This method is used to increase the score in the score.java class where it is saved
     */
    private void increaseScore() {
        scoreCounter++;
        score.setScore(scoreCounter);
    }

    /**
     * This method saves the high score to the preference when the app applies onPause() state
     * This method saves the current question index when the program stops
     */
    @Override
    protected void onPause() {
        super.onPause();
        prefs.saveHighScore(score.getScore());
        prefs.saveCurrentState(questionIndexCounter);

//        Log.d("ActivityTracker", "onPause: High Score: " + score.getHighScore());
//        Log.d("ActivityTracker", "onPause: Question Index: " + questionIndexCounter);
        //Logs for debugging the highScore and question index
    }




}
