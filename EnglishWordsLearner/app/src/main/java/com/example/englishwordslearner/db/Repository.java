package com.example.englishwordslearner.db;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    public Repository(Application application){
        Database db = Database.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getWordsOrderedByEnAlphabet();
    }
    LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }
    void insert(final Word word){
        Database.dbExecutor.execute(()->{
            mWordDao.insert(word);
        });
    }
    void delete(final Word word){
        Database.dbExecutor.execute(()->{
            mWordDao.deleteWord(word);
        });
    }
    LiveData<Word> findWordById(int id){
        return mWordDao.findWordById(id);
    }
    void update(final Word word){
        Database.dbExecutor.execute(()->{
            mWordDao.Update(word);
        });
    }
}
