package com.example.boguhnindustries.reactiontesttest;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.provider.Settings;

/**
 * Created by Lorenz on 31.03.2017.
 */

public class Borders implements GameObject{

    private Rect upBorder;
    private Rect downBorder;
    private Rect leftBorder;
    private Rect rightBorder;
    private final int SCALE_FAKTOR=40;


    private int color;




    public Borders(Point p,int color){
        this.color= color;
        upBorder = new Rect(0,0,(p.x),(p.y/SCALE_FAKTOR));
        rightBorder = new Rect(p.x-(p.x/SCALE_FAKTOR),0,p.x,p.y);
        downBorder = new Rect(0,(p.y-(p.y)/(SCALE_FAKTOR)),p.x,p.y);
        leftBorder = new Rect(0,0,(p.x/SCALE_FAKTOR),p.y);





        //ltrb

    }



    public void draw(Canvas canvas){
        Paint paint= new Paint();
        paint.setColor(color);
        canvas.drawRect(upBorder,paint);
        canvas.drawRect(downBorder,paint);
        canvas.drawRect(leftBorder,paint);
        canvas.drawRect(rightBorder,paint);




    }
    public void update(){
    }

}
