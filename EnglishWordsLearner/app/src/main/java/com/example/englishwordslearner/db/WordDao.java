package com.example.englishwordslearner.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {

    @Query("SELECT * FROM words ORDER BY word_en ASC")
    LiveData<List<Word>> getWordsOrderedByEnAlphabet();

    @Insert
    void insert(Word word);

    @Delete
    void deleteWord(Word word);

    @Query("SELECT * FROM words WHERE PrimaryKey=:id")
    LiveData<Word> findWordById(int id);

    @Update
    void Update(Word word);
}