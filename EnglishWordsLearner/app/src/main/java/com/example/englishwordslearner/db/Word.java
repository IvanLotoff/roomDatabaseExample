package com.example.englishwordslearner.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "words")
public class Word {
    String word_en;
    String word_ru;
    @PrimaryKey(autoGenerate = true)
    int PrimaryKey;

    public Word(String word_en, String word_ru) {
        this.word_en = word_en;
        this.word_ru = word_ru;
    }

    public String getWord_en() {
        return word_en;
    }

    public void setWord_en(String word_en) {
        this.word_en = word_en;
    }

    public String getWord_ru() {
        return word_ru;
    }

    public void setWord_ru(String word_ru) {
        this.word_ru = word_ru;
    }

    public int getPrimaryKey() {
        return PrimaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        PrimaryKey = primaryKey;
    }
}