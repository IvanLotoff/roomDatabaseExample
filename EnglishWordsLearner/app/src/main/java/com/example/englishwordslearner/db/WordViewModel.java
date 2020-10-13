package com.example.englishwordslearner.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private Repository mRepository;

    public LiveData<List<Word>> getmAllWords() {
        return mAllWords;
    }

    private LiveData<List<Word>> mAllWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        mAllWords = mRepository.getAllWords();
    }
    public void delete(Word word) {mRepository.delete(word);}
    public void insert(Word word){
        mRepository.insert(word);
    }
    public void update(Word word) {mRepository.update(word);}
}
