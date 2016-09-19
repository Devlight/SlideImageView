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


package devlight.io.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by GIGAMOLE on 30.04.2015.
 */
public class SlideImageView extends View {

    private int width;
    private int height;

    private float rate;
    private float originalRate;

    private Bitmap bitmap;

    private float bitmapX;
    private float bitmapY;
    private int slideSize;

    private Axis axis;

    public enum Axis {
        HORIZONTAL,
        VERTICAL
    }

    private HorizontalDirection horizontalDirection = HorizontalDirection.LEFT_TO_RIGHT;

    private enum HorizontalDirection {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }

    private VerticalDirection verticalDirection = VerticalDirection.TOP_TO_BOTTOM;

    private enum VerticalDirection {
        TOP_TO_BOTTOM,
        BOTTOM_TO_TOP
    }

    public SlideImageView(Context context) {
        this(context, null);
    }

    public SlideImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidedImageView);

        try {
            final int sourceId = typedArray.getResourceId(R.styleable.SlidedImageView_siv_source, 0);
            if (sourceId != 0) {
                setSource(sourceId);
            }

            final float rate = typedArray.getFloat(R.styleable.SlidedImageView_siv_rate, 0.3f);
            setRate(rate);

            final int axis = typedArray.getInteger(R.styleable.SlidedImageView_siv_axis, 0);
            setAxis(axis);
        } finally {
            typedArray.recycle();
        }

        setWillNotDraw(false);
    }

    public void setRate(float rate) {
        this.rate = rate * (-1);
        this.originalRate = this.rate;
    }

    public void setAxis(Axis axis) {
        this.axis = axis;
        this.originalRate = this.rate;
    }

    private void setAxis(int axis) {
        if (axis == 0) {
            this.axis = Axis.HORIZONTAL;
        } else {
            this.axis = Axis.VERTICAL;
        }
    }

    public void setSource(Drawable source) {
        this.bitmap = ((BitmapDrawable) source).getBitmap();
        requestLayout();
    }

    public void setSource(Bitmap source) {
        this.bitmap = source;
        requestLayout();
    }

    public void setSource(int sourceId) {
        this.bitmap = ((BitmapDrawable) getResources().getDrawable(sourceId)).getBitmap();
        requestLayout();
    }

    private void createSource() {
        setWillNotDraw(true);

        this.bitmapX = 0;
        this.bitmapY = 0;

        this.horizontalDirection = HorizontalDirection.LEFT_TO_RIGHT;
        this.verticalDirection = VerticalDirection.TOP_TO_BOTTOM;

        this.rate = this.originalRate;

        if (this.bitmap != null) {
            if (this.axis == Axis.HORIZONTAL) {
                this.bitmap = Bitmap.createScaledBitmap(
                        this.bitmap,
                        this.height * this.bitmap.getWidth() / this.bitmap.getHeight(),
                        this.height,
                        false);
                this.slideSize = (this.bitmap.getWidth() - this.width) * (-1);
            } else {
                this.bitmap = Bitmap.createScaledBitmap(
                        this.bitmap,
                        this.width,
                        this.width * this.bitmap.getHeight() / this.bitmap.getWidth(),
                        false);
                this.slideSize = (this.bitmap.getHeight() - this.height) * (-1);
            }
        } else {
            this.bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
        }

        postInvalidate();
        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.width = MeasureSpec.getSize(widthMeasureSpec);
        this.height = MeasureSpec.getSize(heightMeasureSpec);

        createSource();

        this.setMeasuredDimension(this.width, this.height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (this.axis == Axis.HORIZONTAL) {
            if (this.horizontalDirection == HorizontalDirection.LEFT_TO_RIGHT) {
                if (Math.round(this.bitmapX) == this.slideSize) {
                    this.rate = -this.rate;
                    this.horizontalDirection = HorizontalDirection.RIGHT_TO_LEFT;
                }
            } else {
                if (Math.round(this.bitmapX) == 0) {
                    this.rate = -this.rate;
                    this.horizontalDirection = HorizontalDirection.LEFT_TO_RIGHT;
                }
            }

            this.bitmapX += this.rate;
            this.bitmapY = 0;
        } else {
            if (this.verticalDirection == VerticalDirection.TOP_TO_BOTTOM) {
                if (Math.round(this.bitmapY) == this.slideSize) {
                    this.rate = -this.rate;
                    this.verticalDirection = VerticalDirection.BOTTOM_TO_TOP;
                }
            } else {
                if (Math.round(this.bitmapY) == 0) {
                    this.rate = -this.rate;
                    this.verticalDirection = VerticalDirection.TOP_TO_BOTTOM;
                }
            }

            this.bitmapX = 0;
            this.bitmapY += this.rate;
        }

        canvas.drawBitmap(this.bitmap, this.bitmapX, this.bitmapY, null);
        postInvalidate();
    }
}
