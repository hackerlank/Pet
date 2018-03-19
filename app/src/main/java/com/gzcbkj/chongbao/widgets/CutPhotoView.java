package com.gzcbkj.chongbao.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.gzcbkj.chongbao.R;

import static android.graphics.Paint.Style.FILL_AND_STROKE;

/**
 * Created by gigabud on 16-7-15.
 */
public class CutPhotoView extends View {

    private Paint mPaint;
    private float mCutRatio = 1.0f;

    public CutPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCutRatio = 1.0f;
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CutPhotoView);
            mCutRatio = array.getFloat(R.styleable.CutPhotoView_cut_ratio, 1.0f);
            array.recycle();
        }
        setBackgroundColor(Color.TRANSPARENT);
    }

    public void setCutRatio(float cutRatio) {
        mCutRatio = cutRatio;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(0, 0, 0, 0);

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setStyle(FILL_AND_STROKE);
            mPaint.setAntiAlias(true);
        }
        mPaint.setColor(Color.argb(128, 0, 0, 0));
        canvas.drawRect(0, 0, canvasWidth, canvasHeight, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.CLEAR));
        mPaint.setColor(Color.TRANSPARENT);
        canvas.drawRect(getCutLeft(), getCutTop(), getCutRight(), getCutBottom(), mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    public int getCutLeft() {
        return 0;
    }

    public int getCutRight() {
        return getWidth();
    }

    public int getCutTop() {
        return (int) ((getHeight() - mCutRatio * getWidth()) * 0.5 + 0.5);
    }

    public int getCutBottom() {
        return (int) ((getHeight() + mCutRatio * getWidth()) * 0.5 + 0.5);
    }


}
