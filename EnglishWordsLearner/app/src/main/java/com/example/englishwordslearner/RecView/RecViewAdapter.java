package com.example.englishwordslearner.RecView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishwordslearner.R;
import com.example.englishwordslearner.db.Word;



import java.util.List;

public class RecViewAdapter extends RecyclerView.Adapter<RecViewAdapter.ViewHolder> {
    private final LayoutInflater mInflater;
    private List<Word> words;

    private ItemClickedCallback itemClickedCallback;

    public void setItemClickedCallback(ItemClickedCallback itemClickedCallback) {
        this.itemClickedCallback = itemClickedCallback;
    }

    public RecViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("TAGGING", "onCreateViewHolder: ");
        return new ViewHolder(mInflater.inflate(R.layout.recyclerview_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTo(words.get(position));
        Log.d("TAGGING", "onBindViewHolder: position = " + position);
    }

    @Override
    public int getItemCount() {
        if(words == null){
            return 0;
        }
        Log.d("TAGGING" , "getItemCount: " + words.size());
        return words.size();
    }

    public void setWords(List<Word> words){
        this.words = words;
        notifyDataSetChanged();
    }
    public Word wordAt(int position){
        return words.get(position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView word_ru;
        private final TextView word_en;
//        private final View itemscreen;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.findViewById(R.id.item_screen).setOnClickListener(view -> {
                Log.d("TAGGING", "ViewHolder: getAdaptetPosition = " + getAdapterPosition());
                RecViewAdapter.this.itemClickedCallback.OnItemClicked(getAdapterPosition());
            });
            word_en = itemView.findViewById(R.id.word_en);
            word_ru = itemView.findViewById(R.id.word_ru);
        }
        public void bindTo(Word word){
            word_ru.setText(word.getWord_ru());
            word_en.setText(word.getWord_en());
        }
    }
}
