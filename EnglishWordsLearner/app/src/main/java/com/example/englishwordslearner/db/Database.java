package com.example.englishwordslearner.db;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {Word.class}, version = 1)
public abstract class Database extends RoomDatabase {

    public abstract WordDao wordDao();

    private static volatile Database Instance;
    private static final int THREADSCONST = 4;
    static final ExecutorService dbExecutor =
            Executors.newFixedThreadPool(THREADSCONST);
    static Database getDatabase(final Context context){
        if(Instance == null){
            synchronized (Database.class){
                if(Instance == null){
                    Instance = Room.databaseBuilder(context.getApplicationContext(),
                                                    Database.class,
                                                    "word_db")
                            .addCallback(sRoomDbCallback)
                            .build();
                }
            }
        }
        return Instance;
    }
    private static RoomDatabase.Callback sRoomDbCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            dbExecutor.execute(()->{
                Word word = new Word("Hi", "Привет");
                Word word1 = new Word("Got", "Получать");
                WordDao dao = Instance.wordDao();
                dao.insert(word);
                dao.insert(word1);
            });
        }
    };
}
