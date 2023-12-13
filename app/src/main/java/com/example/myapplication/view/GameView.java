package com.example.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Book {
    public float x, y;  // 书本的坐标
    public boolean isVisible;  // 书本是否可见

    public Book(float x, float y) {
        this.x = x;
        this.y = y;
        this.isVisible = true;
    }
}

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private List<Book> books;
    private int collectedBooks;
    private long startTime;
    private boolean running;
    private Handler handler;

    public GameView(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
    }

    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        paint = new Paint();
        paint.setColor(Color.BLACK);

        books = new ArrayList<>();
        collectedBooks = 0;
        running = false;

        // 初始化书本位置
        if (getWidth() > 0 && getHeight() > 0) {
            initializeBooks();
        }

        // 初始化定时器，每隔1秒生成新的书本
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 1) {
                    generateNewBook();
                }
                return false;
            }
        });
    }

    private void initializeBooks() {
        // 在屏幕上随机生成初始的书本
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            float x = random.nextInt(getWidth());
            float y = random.nextInt(getHeight());
            books.add(new Book(x, y));
        }
    }

    private void generateNewBook() {
        // 生成新的书本
        Random random = new Random();
        float x = random.nextInt(getWidth());
        float y = random.nextInt(getHeight());
        books.add(new Book(x, y));

        // 设置下一次生成新书的定时任务
        handler.sendEmptyMessageDelayed(1, 1000);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 在 Surface 创建时开始游戏
        startTime = System.currentTimeMillis();
        startGame();
    }

    private void startGame() {
        running = true;
        new Thread(this).start();

        // 定时器，每隔1秒发送消息生成新的书本
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 处理 Surface 改变事件
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // 处理 Surface 销毁事件
        running = false;
        handler.removeMessages(1); // 移除定时器消息
    }

    @Override
    public void run() {
        while (running) {
            draw();
            try {
                Thread.sleep(16); // 控制绘制的帧率
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void draw() {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            // 绘制背景
            canvas.drawColor(Color.WHITE);

            // 绘制书本
            for (Book book : books) {
                if (book.isVisible) {
                    canvas.drawRect(book.x, book.y, book.x + 50, book.y + 50, paint);
                }
            }

            // 绘制已收集的书本数量
            paint.setTextSize(40);
            canvas.drawText("Collected Books: " + collectedBooks, 20, 60, paint);

            // 判断游戏是否结束
            if (System.currentTimeMillis() - startTime < 30000) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            } else {
                // 游戏结束，显示总得分
                paint.setTextSize(80);
                canvas.drawText("Game Over", getWidth() / 4, getHeight() / 2, paint);
                paint.setTextSize(60);
                canvas.drawText("Total Books: " + collectedBooks, getWidth() / 4, getHeight() / 2 + 80, paint);
                surfaceHolder.unlockCanvasAndPost(canvas);
                running = false;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float touchX = event.getX();
            float touchY = event.getY();

            // 检查手指触摸的位置是否在书本范围内
            for (Book book : books) {
                if (book.isVisible && touchX >= book.x && touchX <= book.x + 50
                        && touchY >= book.y && touchY <= book.y + 50) {
                    // 碰到书本，书本消失，学到的书本数量加1
                    book.isVisible = false;
                    collectedBooks++;
                }
            }
        }
        return true;
    }
}



