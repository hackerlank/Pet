package com.gzcbkj.chongbao.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.gzcbkj.chongbao.R;
import com.gzcbkj.chongbao.utils.Utils;

/**
 * 圆形头像Imageview
 *
 * @author gigabud
 */
@SuppressLint("AppCompatCustomView")
public class RoundRectImageView extends ImageView {

    /**
     * 3x3 矩阵，主要用于缩小放大
     */
    private Matrix mMatrix;
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private BitmapShader mBitmapShader;

    /**
     * 绘图的Paint
     */
    private Paint mBitmapPaint;
    /**
     * 圆角的大小
     */
    private float mRoundRadius = 0;

    private RectF mRoundRect;

    private boolean mOnlyTopRound = false;
    private Rect mSrcRect, mDstRect;

    public RoundRectImageView(Context context) {
        this(context, null);
    }

    public RoundRectImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRectImageView(Context context, AttributeSet attrs,
                              int defStyle) {
        super(context, attrs, defStyle);
        setBackgroundColor(Color.TRANSPARENT);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundRectImageView);
            int radius = array.getInteger(R.styleable.RoundRectImageView_round_radius, 0);
            mRoundRadius = Utils.dip2px(getContext(), radius);
            mOnlyTopRound = array.getBoolean(R.styleable.RoundRectImageView_only_top_round, false);
            array.recycle();
        } else {
            mRoundRadius = Utils.dip2px(getContext(), 3);
            mOnlyTopRound = false;
        }
        init();
    }

    private void init() {
        mMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setColor(Color.WHITE);
    }


    /**
     * 设置圆角
     *
     * @param roundRadius round
     *                    dp!!!
     */
    public void setRoundRadius(int roundRadius) {
        this.mRoundRadius = roundRadius * 1.0f
                * getResources().getDisplayMetrics().density;
        invalidate();
    }

    public void setOnlyTopRound(boolean onlyTopRound) {
        mOnlyTopRound = onlyTopRound;
    }


    public void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null || drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            super.onDraw(canvas);
            if (mBitmapPaint == null || mMatrix == null) {
                init();
            }
            mBitmapPaint.setColor(getResources().getColor(R.color.color_187_187_187));
            if (mRoundRect == null) {
                mRoundRect = new RectF(0, 0, getWidth(), getHeight());
            }
            canvas.drawRoundRect(mRoundRect, mRoundRadius, mRoundRadius,
                    mBitmapPaint);

            return;
        }

        Bitmap bmp = drawableToBitamp(drawable);
        if (bmp == null) {
            super.onDraw(canvas);
            return;
        }

        if (mBitmapPaint == null || mMatrix == null) {
            init();
        }

        // 将bmp作为着色器，就是在指定区域内绘制bmp

        mBitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = Math.max(getWidth() * 1.0f / bmp.getWidth(),
                getHeight() * 1.0f / bmp.getHeight());

        mMatrix.setScale(scale, scale);
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(mMatrix);
        // 设置shader
        mBitmapPaint.setShader(mBitmapShader);
        if (mRoundRect == null) {
            mRoundRect = new RectF(0, 0, getWidth(), getHeight());
        }
        canvas.drawRoundRect(mRoundRect, mRoundRadius, mRoundRadius,
                mBitmapPaint);
        if (mOnlyTopRound) {
            if (mSrcRect == null) {
                mSrcRect = new Rect(0, bmp.getHeight() / 2, bmp.getWidth(), bmp.getHeight());
            }else{
                mSrcRect.set(0, bmp.getHeight() / 2, bmp.getWidth(), bmp.getHeight());
            }
            if (mDstRect == null) {
                mDstRect = new Rect(0, getHeight() / 2, getWidth(), getHeight());
            }else{
                mDstRect.set(0, getHeight() / 2, getWidth(), getHeight());
            }
            canvas.drawBitmap(bmp, mSrcRect, mDstRect, null);
        }
    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable || drawable instanceof GlideBitmapDrawable) {
            Bitmap bmp;
            if (drawable instanceof BitmapDrawable) {
                bmp = ((BitmapDrawable) drawable).getBitmap();
            } else {
                bmp = ((GlideBitmapDrawable) drawable).getBitmap();
            }
            if (bmp.getWidth() > 0 && bmp.getHeight() > 0) {
                float ratio1 = bmp.getHeight() * 1.0f / bmp.getWidth();
                float ratio2 = getHeight() * 1.0f / getWidth();
                if (Math.abs(ratio1 - ratio2) > 0.1f) {
                    if (ratio1 < ratio2) {
                        int newWidth = bmp.getHeight() * getWidth() / getHeight();
                        if (newWidth <= 0) {
                            return bmp;
                        }
                        int startX = (int) ((bmp.getWidth() - newWidth) * 0.5);
                        bmp = Bitmap.createBitmap(bmp, startX, 0, newWidth, bmp.getHeight(), null, false);
                    } else {
                        int newHeight = bmp.getWidth() * getHeight() / getWidth();
                        int startY = (int) ((bmp.getHeight() - newHeight) * 0.5);
                        if (newHeight <= 0) {
                            return bmp;
                        }
                        bmp = Bitmap.createBitmap(bmp, 0, startY, bmp.getWidth(), newHeight, null, false);
                    }
                }
            }
            return bmp;
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

}