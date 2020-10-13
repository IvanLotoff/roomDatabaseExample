package com.example.englishwordslearner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishwordslearner.RecView.ItemClickedCallback;
import com.example.englishwordslearner.RecView.RecViewAdapter;
import com.example.englishwordslearner.RecView.RecViewItemDecorator;
import com.example.englishwordslearner.db.Word;
import com.example.englishwordslearner.db.WordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WordViewModel viewModel;
    private static final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View MainScreen = findViewById(R.id.main_layout);
        viewModel = new ViewModelProvider(this).get(WordViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final RecViewAdapter adapter = new RecViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecViewItemDecorator(this,R.drawable.divider_line));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Word cahcedWord = adapter.wordAt(position);
                viewModel.delete(cahcedWord);
                Snackbar snackbar = Snackbar.make(MainScreen,"Отменить удаление?",Snackbar.LENGTH_SHORT);
                snackbar.setAction("ДА!", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewModel.insert(cahcedWord);
                    }
                });
                snackbar.show();
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adapter.setItemClickedCallback(wordId -> {
            Log.d("TAGGING", "onCreate: wordID = "  + wordId);
            Toast.makeText(MainActivity.this, "You clicked on " + adapter.wordAt(wordId).getWord_en(), Toast.LENGTH_SHORT).show();
            Word word = adapter.wordAt(wordId);
            Intent intent = new Intent(this, SingleWordActivity.class);
            intent.putExtra("en", word.getWord_en());
            intent.putExtra("ru", word.getWord_ru());
            intent.putExtra("id", word.getPrimaryKey());

            startActivity(intent);
        });
        viewModel.getmAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                adapter.setWords(words);
            }
        });
        FloatingActionButton fab = findViewById(R.id.action_button);
        fab.setOnClickListener(view -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
            View _view = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_word_popup_window, null);
            AlertDialog Alertdialog = dialogBuilder
                    .setView(_view)
                    .create();
            Alertdialog.show();
            _view.findViewById(R.id.add_button_popup).setOnClickListener(view12 -> {
                String en_word = ((TextView) _view.findViewById(R.id.word_en_popup)).getText().toString();
                String ru_word = ((TextView) _view.findViewById(R.id.word_ru_popup)).getText().toString();
                Word word = new Word(en_word, ru_word);
                viewModel.insert(word);
                Alertdialog.dismiss();
            });
            _view.findViewById(R.id.cancell_button_popup).setOnClickListener(view1 -> Alertdialog.dismiss());
        });
    }
}