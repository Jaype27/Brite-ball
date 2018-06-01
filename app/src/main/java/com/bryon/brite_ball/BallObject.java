package com.bryon.brite_ball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class BallObject {

    Bitmap sprite;
    private int X,Y;
    private int left,right,top,bottom ;
    int width,height;
    int randX,randY;
    int originX, originY;


    public BallObject(Context context,int screenW,int screenH, int x,int y,boolean isBad){
        X=x;
        Y=y;
        originX = x;
        originY = y;
        height = screenH;
        width = screenW;


        if(isBad){
            sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.neonnet);
            sprite = Bitmap.createScaledBitmap(sprite, screenW/4, screenH/8, true);
        }else{
            sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.neonball);
            sprite = Bitmap.createScaledBitmap(sprite, screenW/4, screenH/8, true);
        }





    }
    public void update(){
        top = Y;
        bottom = Y + sprite.getHeight();
        left = X;
        right = X + sprite.getWidth();
        //randomLocation(height,width);

    }

    public Bitmap getSprite() {
        return sprite;
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void randomLocation(int width,int height){
         randX  = new Random().nextInt(width-sprite.getWidth())+1;
         randY  = new Random().nextInt(height-sprite.getHeight())+1;

        X = randX;
        Y = randY;
    }
}
