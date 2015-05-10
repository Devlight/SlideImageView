/*
 * Copyright (C) 2015 Basil Miller
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.gigamole.slideimageview.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;

/**
 * Created by GIGAMOLE on 30.04.2015.
 */
public class SlideImageView extends ImageView {

    private int width;
    private int height;

    private int duration;
    private Bitmap bitmap;
    private boolean isSlowdown;

    private float bitmapX;
    private float slideWidth;

    public SlideImageView(Context context) {
        this(context, null);
    }

    public SlideImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidedImageView);

        try {
            this.duration = typedArray.getInteger(R.styleable.SlidedImageView_duration, 30000);
            this.isSlowdown = typedArray.getBoolean(R.styleable.SlidedImageView_slowdown, true);

            final int sourceId = typedArray.getResourceId(R.styleable.SlidedImageView_source, 0);
            if (sourceId != 0) {
                setSource(sourceId);
            }
        } finally {
            typedArray.recycle();
        }

        setWillNotDraw(false);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setSource(int sourceId) {
        this.bitmap = ((BitmapDrawable) getResources().getDrawable(sourceId)).getBitmap();
    }

    public void setSlowdown(boolean isSlowdown) {
        this.isSlowdown = isSlowdown;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.width = w;
        this.height = h;

        if (this.bitmap != null) {
            this.bitmap = Bitmap.createScaledBitmap(
                    this.bitmap,
                    this.height * this.bitmap.getWidth() / this.bitmap.getHeight(),
                    this.height,
                    false);
            this.slideWidth = this.bitmap.getWidth() - this.width;

            startAnimation(new SlideAnimation());
        } else {
            throw new NullPointerException(getContext().getString(R.string.source_error));
        }

        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(this.bitmap, this.bitmapX, 0, null);
    }

    public class SlideAnimation extends Animation {

        public SlideAnimation() {
            if (!isSlowdown) {
                setInterpolator(new LinearInterpolator());
            }

            setDuration(duration);
            setRepeatCount(INFINITE);
            setRepeatMode(REVERSE);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            bitmapX = -interpolatedTime * slideWidth;

            postInvalidate();
        }
    }
}
