package com.example.boguhnindustries.reactiontesttest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.Random;


/**
 * Created by Lorenz on 01.04.2017.
 */

public class Obstacle implements GameObject {

    private Rect obstacle;
    private int color;
    private Context context;
    private int height;
    private int width;
    private int x;
    private int y;
    private double xSpeed;
    private double ySpeed;
    private Point boarders;
    private long time;
    private long lastcollusiontime;
    private int lastX;
    private int lastY;
    private boolean inBorder;
    private boolean containsPlayer;
    Random generator3000;


    public double getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public double getySpeed() {
        return ySpeed;
    }

    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    /**
     * Constructor for a obstacle
     *
     * @param x-Para
     * @param y-Para
     * @param width
     * @param height
     * @param color
     * @param context
     */
    public Obstacle(int x, int y, int width, int height, int color, Context context, double xSpeed, double ySpeed, Point boarders) {
        this.x = x;
        this.y = y;
        this.lastX = x;
        this.lastY = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.context = context;
        Paint paint = new Paint();
        paint.setColor(color);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.boarders = boarders;
        this.time = System.nanoTime();
        this.lastcollusiontime = System.nanoTime();
        generator3000 = new Random();


        obstacle = new Rect(x, y, x + width, y + height);
    }

    /**
     * Returns if a Player collides with a obstacle
     *
     * @param player
     * @return
     */
    public boolean containsPlayer(Player player) {

        return obstacle.intersects(player.getPlayerrect(), obstacle);
        /*if (
                        //corners
                        obstacle.contains(player.getPlayerrect().left, player.getPlayerrect().top) ||
                        obstacle.contains(player.getPlayerrect().right, player.getPlayerrect().top) ||
                        obstacle.contains(player.getPlayerrect().left, player.getPlayerrect().bottom) ||
                        obstacle.contains(player.getPlayerrect().right, player.getPlayerrect().bottom) ||

                        //Midpoints
                        //linke seite mitte
                        obstacle.contains(player.getPlayerrect().left, player.getPlayerrect().top + (player.getPlayerrect().height() / 2)) ||
                        //rechte seite die mitte
                        obstacle.contains(player.getPlayerrect().right, player.getPlayerrect().top + (player.getPlayerrect().height() / 2)) ||
                        //unten die mitte
                        obstacle.contains(player.getPlayerrect().left + (player.getPlayerrect().width() / 2), player.getPlayerrect().bottom) ||
                        //oben die mitte
                        obstacle.contains(player.getPlayerrect().left + (player.getPlayerrect().width() / 2), player.getPlayerrect().top)

                ) {

            return true;
        }
        return false;*/
    }


    public void update() {
    }

    /**
     * updates the obstacles
     *
     * @param speed
     */
    public void update(double speed) {

        //checks for collusions
        if (!inBorder && this.collidesWithWalls()) {
            inBorder = true;
            this.lastcollusiontime = System.nanoTime();
            this.lastX = this.x;
            this.lastY = this.y;
        } else {
            inBorder = false;
        }

        // gets the time difference
        long timediff = (System.nanoTime() - this.lastcollusiontime) / 1000000;
        x = (int) (this.lastX + speed * this.xSpeed * timediff / 5.0);
        y = (int) (this.lastY + speed * this.ySpeed * timediff / 5.0);


        this.move(x, y);
    }

    /**
     * moves the obstacle with ist original size
     *
     * @param x
     * @param y
     */
    public void move(int x, int y) {
        obstacle.set(x, y, x + width, y + height);
    }


    /**
     * moves and resets the obstacle
     */
    public void moveAndReset(int x, int y) {
        obstacle.set(x, y, x + width, y + height);
        lastY = y;
        lastX = x;
        lastcollusiontime = System.nanoTime();
    }


    /**
     * returns true if the left boarder is been hit by the obstacle and changes the xspeed
     *
     * @return
     */
    public boolean leftBoarder() {
        if (this.x <= 0 && xSpeed < 0) {
            this.xSpeed *= -1;
            return true;


        }

        return false;
    }

    /**
     * returns true if the right borader is been in by the obstacle and changes the yspeed
     *
     * @return
     */
    public boolean topBoarder() {
        if (this.y <= 0 && ySpeed < 0) {
            this.ySpeed *= -1;
            return true;
        }

        return false;
    }

    public boolean rightBoarder() {
        if ((x + width >= (boarders.x)) && xSpeed > 0) {
            this.xSpeed *= -1;
            return true;
        }

        return false;
    }

    public boolean bottomBoarder() {
        if (y + height >= (boarders.y) && ySpeed > 0) {
            this.ySpeed *= -1;
            return true;
        }

        return false;
    }

    /**
     * returns true if the obstacle collides with a boarder
     *
     * @return
     */
    public boolean collidesWithWalls() {
        if (topBoarder() || leftBoarder() || rightBoarder() || bottomBoarder()) {
            return true;
        }
        return false;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(obstacle, paint);
    }

    public void setColor(int color) {
        this.color = color;
    }
}
