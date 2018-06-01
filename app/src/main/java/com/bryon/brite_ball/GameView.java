package com.bryon.brite_ball;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private Paint m_paint;
    private Canvas m_canvas;
    private SurfaceHolder m_holder;
    private Context m_context;
    private int ScreenW;
    private int ScreenH;

    private volatile boolean m_playing;
    private Thread m_gameThread = null;

    int gameScore = 0;
    int gameHighScore = 0;

    Point startHoopPosition;
    Point startNeonBallPosiition;

    private Bitmap spriteBall;
    private Bitmap spriteNet;


    private Rect spriteRect;

    private Paint mPaint;
    private Path mPath;
    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private BallObject ball;
    private BallObject badBall;

    public GameView(Context context, int screenW, int screenH) {
        super(context);

        m_holder = getHolder();
        m_paint = new Paint();
        mPaint = new Paint();
        ball = new BallObject(context,screenW,screenH,700,300,false);
        badBall = new BallObject(context,screenW,screenH,300,700,true);
        ScreenH = screenH;
        ScreenW = screenW;


//        int buttonWidth =spriteBall.getWidth();
//        int buttonHeight =spriteBall.getHeight();
//        int buttonpadding = screenW/80;
//        spriteRect = new Rect(buttonpadding,  m_screenH/8,buttonWidth,m_screenH/8+buttonHeight);
       // spriteRect = new Rect(,spriteBall.getWidth(),spriteBall.getHeight());


        spriteNet = BitmapFactory.decodeResource(context.getResources(), R.drawable.neonnet);
        spriteNet = Bitmap.createScaledBitmap(spriteNet, screenW/2, screenH/3, true);

        mPath = new Path();
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();

        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            //touch screen
            case MotionEvent.ACTION_DOWN:
                if(x >= ball.getLeft() && x <= ball.getRight()
                        && y >= ball.getTop() && y <= ball.getBottom())
                {
                    ball.randomLocation(ScreenW,ScreenH);
                    badBall.randomLocation(ScreenW,ScreenH);
                    gameScore = gameScore + 1;

                }else if(x >= badBall.getLeft() && x <= badBall.getRight()
                        && y >= badBall.getTop() && y <= badBall.getBottom()){
                    ball.randomLocation(ScreenW,ScreenH);
                    badBall.randomLocation(ScreenW,ScreenH);
                    gameScore = gameScore + 3;



                } else{
                    ball.randomLocation(ScreenW,ScreenH);
                    badBall.randomLocation(ScreenW,ScreenH);
                    gameScore = 0;
                }
                break;
        }

        return true;
    }

    @Override
    public void run() {
        while(m_playing) {
            update();
            draw();
        }
    }

    private void update(){
        ball.update();
        badBall.update();
    }


    private void draw(){
        if(m_holder.getSurface().isValid()) {
            // lock the memory for the canvas
            m_canvas = m_holder.lockCanvas();

            // draw the player


            m_paint.setTextSize(48);
            m_paint.setTextAlign(Paint.Align.LEFT);
            m_paint.setColor(Color.argb(255, 139,69, 19));

            m_canvas.drawColor(Color.argb(255, 255, 255, 255));

            m_canvas.drawText("Score: " + gameScore, 0, 50, m_paint);

            m_canvas.drawBitmap(ball.getSprite(), ball.getX(), ball.getY(), m_paint);
            m_canvas.drawBitmap(badBall.getSprite(), badBall.getX(), badBall.getY(), m_paint);

            m_holder.unlockCanvasAndPost(m_canvas);
        }
    }






    public void pause() {
        m_playing = false;
        try {
            m_gameThread.join();
        } catch(InterruptedException e) {

        }
    }

    public void resume() {
        m_playing = true;
        m_gameThread = new Thread(this);
        m_gameThread.start();
    }
}
