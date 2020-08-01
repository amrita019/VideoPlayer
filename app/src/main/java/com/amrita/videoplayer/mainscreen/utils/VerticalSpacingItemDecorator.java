package com.amrita.videoplayer.mainscreen.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpacingItemDecorator extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;
    private final Paint mPaint;

    public VerticalSpacingItemDecorator(int verticalSpaceHeight) {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#9544a2"));
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        outRect.top = verticalSpaceHeight;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        final int offset = (int) (mPaint.getStrokeWidth());
        for (int i = 0; i < parent.getChildCount(); i++) {
            final View view = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();

            final int position = params.getViewAdapterPosition();

            if (position < state.getItemCount()) {
                c.drawLine(view.getLeft(), view.getBottom() + offset, view.getRight(), view.getBottom() + offset, mPaint);
            }
        }
        super.onDraw(c, parent, state);
    }
}
