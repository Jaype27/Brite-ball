package com.bryon.brite_ball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private Paint m_paint;
    private Canvas m_canvas;
    private SurfaceHolder m_holder;
    private Context m_context;

    private volatile boolean m_playing;
    private Thread m_gameThread = null;

    int gameLevel = 1;
    int gameScore = 0;
    int gameHighScore = 0;

    Point startHoopPosition;
    Point startNeonBallPosiition;

    private Bitmap spriteBall;
    private Bitmap spriteNet;

    private Paint assetPaint;

    public GameView(Context context, int screenW, int screenH) {
        super(context);

        m_holder = getHolder();
        m_paint = new Paint();
        assetPaint = new Paint();

        spriteBall = BitmapFactory.decodeResource(context.getResources(), R.drawable.neonball);
        spriteBall = Bitmap.createScaledBitmap(spriteBall, screenW/4, screenH/8, false);
        spriteNet = BitmapFactory.decodeResource(context.getResources(), R.drawable.neonnet);
        spriteNet = Bitmap.createScaledBitmap(spriteNet, screenW/2, screenH/3, false);
    }

    @Override
    public void run() {
        if(m_playing) {
            update();
            draw();
        }
    }

    private void update(){

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

            m_canvas.drawText("Level: " + gameLevel, 30, 50, m_paint);
            m_canvas.drawText("Score: " + gameScore, 30, 100, m_paint);

            m_canvas.drawBitmap(spriteBall, 300, 300, m_paint);
            m_canvas.drawBitmap(spriteNet, 100, 100, m_paint);

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
