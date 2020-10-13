package com.example.englishwordslearner.RecView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishwordslearner.R;

public class RecViewItemDecorator extends RecyclerView.ItemDecoration {
    public RecViewItemDecorator(Context context, @DrawableRes int drawableId, int heigh) {
        this.context = context;
        mDrawable = ContextCompat.getDrawable(context, drawableId);
        this.heigh = heigh;
    }
    public RecViewItemDecorator(Context context, @DrawableRes int drawableId)  {
        this(context,drawableId,3);
    }

    public void setDrawable(Drawable mDrawable) {
        this.mDrawable = mDrawable;
    }

    Drawable mDrawable;
    Context context;
    int heigh;
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = heigh;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        for(int i = 0; i < parent.getChildCount(); i++){
            mDrawable.setBounds(0, parent.getChildAt(i).getBottom(), c.getWidth(),parent.getChildAt(i).getBottom()+heigh);
            mDrawable.draw(c);
        }
       // drawable.setBounds(0,0,100,100);

        //drawable.draw(c);
    }
}
