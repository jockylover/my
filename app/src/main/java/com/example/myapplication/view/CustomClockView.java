package com.example.myapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import com.example.myapplication.R;

import java.util.Calendar;

// CustomClockView.java
public class CustomClockView extends View {
    private Bitmap clockFace;
    private Bitmap hourHand, minuteHand, secondHand;
    private int centerX, centerY;

    public CustomClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Bitmap originalClockFace = BitmapFactory.decodeResource(getResources(), R.drawable.colck_face);
        // 缩放图片
        clockFace = Bitmap.createScaledBitmap(originalClockFace, 768, 768, false);
        hourHand = BitmapFactory.decodeResource(getResources(), R.drawable.hour_hand);
        minuteHand = BitmapFactory.decodeResource(getResources(), R.drawable.minute_hand);
        secondHand = BitmapFactory.decodeResource(getResources(), R.drawable.second_hand);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制表盘
        canvas.drawBitmap(clockFace, centerX - clockFace.getWidth() / 2, centerY - clockFace.getHeight() / 2, null);

        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        // 计算时分秒针的角度
        float hourRotation = 360f * (hours % 12 + minutes / 60f) / 12f;
        float minuteRotation = 360f * (minutes + seconds / 60f) / 60f;
        float secondRotation = 360f * seconds / 60f;

        // 绘制时分秒针
        drawHand(canvas, hourHand, hourRotation);
        drawHand(canvas, minuteHand, minuteRotation);
        drawHand(canvas, secondHand, secondRotation);

        // 刷新视图
        postInvalidateDelayed(1000);
    }

    private void drawHand(Canvas canvas, Bitmap hand, float rotation) {
        Matrix matrix = new Matrix();
        matrix.setTranslate(centerX - hand.getWidth() / 2, centerY - hand.getHeight() / 2);
        matrix.postRotate(rotation, centerX, centerY);
        canvas.drawBitmap(hand, matrix, null);
    }
}

