package com.example.boguhnindustries.reactiontesttest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Lorenz on 29.03.2017.
 */

public class Player implements GameObject {

    private Rect playerrect;
    private int color;
    private Context context;
    private Point resolution;
    public boolean firsttouch;


    public Rect getPlayerrect() {
        return playerrect;
    }

    public Player(Rect player, int color, Point resolution, Context context){
        this.color=color;
        this.playerrect = player;
        this.context = context;
        this.firsttouch=true;
        this.resolution= resolution;
    }

    public void draw(Canvas canvas){
        Paint paint= new Paint();
        paint.setColor(color);
        canvas.drawRect(playerrect,paint);
    }
    public void update(){
    }

    public boolean collidesWithWalls(Point p){
        if(p.x<130||p.y<145||p.x>(resolution.x-130)||p.y>(resolution.y-145)){
        return true;
        }
        return false;
    }

    //left top rigth bottom
    public void update(Point p){

        playerrect.set(p.x-(playerrect.width()/2), p.y- (playerrect.height() /2),p.x+(playerrect.width()/2), p.y+ playerrect.height()/2);

    }

    /**checks if the playerrect contains a certain point(is touched)
     *(IF THE PLAYER IS TOUCHED OR NOT!!!!!)
     * @param x
     * @param y
     * @return
     */
    public boolean isin(int x,int y){
       if(playerrect.contains(x,y)){
           return true;
       }
       if(playerrect.contains(x+50,y+50)){
           return true;
       }
        if(playerrect.contains(x+50,y)){
            return true;
        }
        if(playerrect.contains(x,y+50)){
            return true;
        }
        if(playerrect.contains(x-50,y-50)){
            return true;
        }
        if(playerrect.contains(x-50,y)){
            return true;
        }
        if(playerrect.contains(x,y-50)){
            return true;
        }

       return false;

    }



}
