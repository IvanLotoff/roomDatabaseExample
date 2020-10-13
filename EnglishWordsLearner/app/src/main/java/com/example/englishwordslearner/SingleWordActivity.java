package com.example.englishwordslearner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.englishwordslearner.db.Word;
import com.example.englishwordslearner.db.WordViewModel;

public class SingleWordActivity extends AppCompatActivity {
    private EditText word_en;
    private EditText word_ru;
    private WordViewModel wordViewModel;
    Word word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_word);
        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        word_en = findViewById(R.id.word_en_changed);
        word_ru = findViewById(R.id.word_ru_changed);
        Intent intent = getIntent();
        String ru = intent.getStringExtra("ru");
        String en = intent.getStringExtra("en");
        word_ru.setText(ru);
        word_en.setText(en);
        word_en.setOnKeyListener((view, i, keyEvent) -> {
            // KeyEvent.ACTION_DOWN means the button has been pressed
            if(keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                word_en.clearFocus();
                InputMethodManager imn = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imn.hideSoftInputFromWindow(word_en.getWindowToken(),0);
                Log.d("TAGGING", "onKey: ");
                return true;
            }
            return false;
        });
        word_ru.setOnKeyListener((view, i, keyEvent) -> {
            if(keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                word_ru.clearFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(word_ru.getWindowToken(),0);
                return true;
            }
            return false;
        });
        word = new Word(en,ru);
        word.setPrimaryKey(intent.getIntExtra("id",0));
        findViewById(R.id.update_button).setOnClickListener(view -> {
            word.setWord_en(word_en.getText().toString());
            word.setWord_ru(word_ru.getText().toString());
            wordViewModel.update(word);
            finish();
        });

    }
}