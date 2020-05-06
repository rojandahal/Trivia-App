package com.rojanprod.trivia.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Activity activity) {
        this.preferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    /**
     * This method returns the current preferences
     * @return
     */
    public SharedPreferences getPreferences() {
        return preferences;
    }

    /**
     * This method saves the current high score to a key and its default value is 0
     * @param currentScore
     */
    public void saveHighScore(int currentScore) {

        int lastScore = preferences.getInt("high_score",0);

        if(currentScore>lastScore){
            preferences.edit().putInt("high_score",currentScore).apply();
            //This is used to save the current preference of the highest score
        }

    }

    /**
     * This method saves the current question index counter int the key map
     * @param currentQueIndex
     */
    public void saveCurrentState(int currentQueIndex){
        preferences.edit().putInt("current_que",currentQueIndex).apply();
    }
}
