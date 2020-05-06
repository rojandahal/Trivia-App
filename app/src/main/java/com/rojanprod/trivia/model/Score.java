package com.rojanprod.trivia.model;

public class Score {

    private int score;
    private int HighScore;

    /**
     * This method gets or returns the score of the playera
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * This method sets the score value to the new value of the player
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * This method is used to return or get the highest score
     * @return
     */
    public int getHighScore() {
        return HighScore;
    }

    /**
     * This method applies to set the high score of the player
     * @param highScore
     */
    public void setHighScore(int highScore) {
        HighScore = highScore;
    }
}

