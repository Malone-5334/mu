package com.baselibrary.widget.recycleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: SXF
 * E-mail: xue.com.fei@outlook.com
 * CreatedTime: 2017/10/20 9:38
 * <p>
 * RecycleViewDivider
 */

public class RecycleViewDivider extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private int orientation;
    private Paint paint;
    private Drawable divider;
    private int dividerHeight;

    public RecycleViewDivider(Context context, int orientation) {
        if (orientation != VERTICAL && orientation != HORIZONTAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.orientation = orientation;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        divider = a.getDrawable(0);
        dividerHeight = divider != null ? divider.getIntrinsicHeight() : 0;
        a.recycle();
    }

    public RecycleViewDivider(Context context, int orientation, int drawableRes) {
        if (orientation != VERTICAL && orientation != HORIZONTAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.orientation = orientation;
        divider = ContextCompat.getDrawable(context, drawableRes);
        dividerHeight = divider != null ? divider.getIntrinsicHeight() : 0;
    }

    public RecycleViewDivider(Context context, int orientation, int dividerHeight, int dividerColor) {
        if (orientation != VERTICAL && orientation != HORIZONTAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.orientation = orientation;
        this.dividerHeight = dividerHeight;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(dividerColor);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, dividerHeight);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (orientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + dividerHeight;
            if (divider != null) {
                divider.setBounds(left, top, right, bottom);
                divider.draw(canvas);
            }
            if (paint != null) {
                canvas.drawRect(left, top, right, bottom, paint);
            }
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + dividerHeight;
            if (divider != null) {
                divider.setBounds(left, top, right, bottom);
                divider.draw(canvas);
            }
            if (paint != null) {
                canvas.drawRect(left, top, right, bottom, paint);
            }
        }
    }

}
