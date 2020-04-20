package com.baselibrary.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.RequiresApi;

import com.baselibrary.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;


public class GapBottomNavigationView extends BottomNavigationView {
    private int fabId;
    private float centerRadius;
    private float cornerRadius = 12f;
    private float shadowLength = 6f;

    public GapBottomNavigationView(@NotNull Context context) {
        super(context);
    }

    public GapBottomNavigationView(@NotNull Context context, @NotNull AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GapBottomNavigationView(@NotNull Context context, @NotNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(0);
        this.setBackground((Drawable) drawable);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GapBottomNavigationView, defStyleAttr, 0);
        this.fabId = ta.getResourceId(R.styleable.GapBottomNavigationView_anchor_fab, 0);
        this.shadowLength = ta.getFloat(R.styleable.GapBottomNavigationView_shadow_length, 6f);
        this.cornerRadius = ta.getFloat(R.styleable.GapBottomNavigationView_corner_radius, 12f);
        ta.recycle();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewParent parent = this.getParent();
        if (parent != null) {
            View fab = ((ViewGroup) parent).findViewById(this.fabId);
            this.centerRadius = (float) fab.getWidth() / (float) 2 + this.cornerRadius;
            this.invalidate();
        }
    }

    @SuppressLint({"DrawAllocation"})
    @RequiresApi(api = 19)
    protected void onDraw(@NotNull Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        Path path = new Path();
        RectF rectL = new RectF(
                this.shadowLength,
                this.shadowLength,
                (float) this.getHeight() + this.shadowLength,
                (float) this.getHeight() - this.shadowLength);
        path.arcTo(rectL, (float) 90, (float) 180, false);
        path.lineTo((float) (this.getWidth() / 2) - this.centerRadius - this.cornerRadius, this.shadowLength);
        path.quadTo((float) (this.getWidth() / 2) - this.centerRadius, this.shadowLength, (float) (this.getWidth() / 2) - this.centerRadius, this.cornerRadius + this.shadowLength);
        RectF rectCenter = new RectF((float) (this.getWidth() / 2) - this.centerRadius, this.cornerRadius + this.shadowLength - this.centerRadius, (float) (this.getWidth() / 2) + this.centerRadius, this.cornerRadius + this.centerRadius + this.shadowLength);
        path.arcTo(rectCenter, (float) 180, (float) -180, false);
        path.quadTo((float) (this.getWidth() / 2) + this.centerRadius, this.shadowLength, (float) (this.getWidth() / 2) + this.centerRadius + this.cornerRadius, this.shadowLength);
        path.lineTo((float) this.getWidth() - this.shadowLength - (float) (this.getHeight() / 2), this.shadowLength);
        RectF rectR = new RectF((float) this.getWidth() - this.shadowLength - (float) this.getHeight(), this.shadowLength, (float) this.getWidth() - this.shadowLength, (float) this.getHeight() - this.shadowLength);
        path.arcTo(rectR, (float) 270, (float) 180, false);
        path.moveTo((float) this.getWidth() - this.shadowLength - (float) (this.getHeight() / 2), (float) this.getHeight() - this.shadowLength);
        path.lineTo((float) this.getHeight() / (float) 2 + this.shadowLength, (float) this.getHeight() - this.shadowLength);
        path.close();
        setLayerType(1, paint);
        paint.setStyle(Paint.Style.FILL);
        ColorStateList colorStateList = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            colorStateList = getBackgroundTintList();
        }
        paint.setColor(colorStateList != null ? colorStateList.getDefaultColor() : Color.BLACK);
        paint.setMaskFilter(null);
        paint.setAntiAlias(true);
        paint.setShadowLayer(this.shadowLength, (float) 0, (float) 0, Color.LTGRAY);
        canvas.drawPath(path, paint);
    }
}
