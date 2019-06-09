package com.example.android.flash_card;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class CardContent {
    private String mWord;
    private String mMeaning;
    private List<String> mCategory;
    private int mDifficulty;
    private int mScore;

    public CardContent() {
    }

    public CardContent(String word, String meaning, List<String> category, int difficulty, int score) {
        mWord = word;
        mMeaning = meaning;
        mCategory = category;
        mDifficulty = difficulty;
        mScore = score;
    }

    public String getWord() {
        return mWord;
    }

    public void setWord(String word) {
        mWord = word;
    }

    public String getMeaning() {
        return mMeaning;
    }

    public void setMeaning(String meaning) {
        mMeaning = meaning;
    }

    public List<String> getCategory() {
        return mCategory;
    }

    public void setCategory(List<String> category) {
        mCategory = new ArrayList<String>();
        for(String i: category) {
            mCategory.add(i);
        }
    }

    public int getDifficulty() {
        return mDifficulty;
    }

    public void setDifficulty(int difficulty) {
        mDifficulty = difficulty;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }
}
