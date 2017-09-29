package com.example.boguhnindustries.reactiontesttest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Lorenz on 30.03.2017.
 */

public class ObstacleManager {

    private ArrayList<Obstacle> obstacleList;


    private long gametime;
    private double speed;
    private int color;
    private int obstaclecount;
    Random r;
    private Context context;
    private Point boarders;
    private Player player;
    private double xmax;
    private double ymax;
    private Point playerpoint;

    public boolean isContainsplayer() {
        return containsplayer;
    }

    public void setContainsplayer(boolean containsplayer) {
        this.containsplayer = containsplayer;
    }

    private boolean containsplayer;




    /**creates an arraylist with the obstacles and controls them.
     *
     * @param gameTime
     * @param obstaclecount
     * @param speed
     * @param color
     * @param context
     * @param boarders
     * @param player
     */
    public ObstacleManager(long gameTime,int obstaclecount,double speed,int color,Context context,Point boarders,Player player,Point playerpoint){
        this.speed=speed;
        this.color=color;
        this.gametime=gameTime;
        this.obstaclecount=obstaclecount;
        this.context=context;
        this.boarders =boarders;
        this.player=player;
        obstacleList = new ArrayList<>();
        r  = new Random();
        this.playerpoint=playerpoint;

        this.xmax= 1.1*this.speed;
        this.ymax= 1.1*this.speed;





        populateArraylist();
        //populateArraylist(1,1,1,1,1);

    }

    public void populateArraylist() {
        for(int i=1;i<=obstaclecount;i++){
            int n=r.nextInt(4);
            int x=r.nextInt(700);
            int y= r.nextInt(900);

            // calculating a random double in the range +,- speed/2
            double xspeed= r.nextDouble()*(xmax);
            double yspeed= r.nextDouble()*(ymax);


            // Typauswahl in obstacle schieben....
            switch (n){
                case 0:{

                    obstacleList.add(new Obstacle(100+x,200+y,200,200, color,context,xspeed,yspeed,boarders));
                    break;
                }
                case 1:{

                    obstacleList.add(new Obstacle(100+x,200+y,100,300, color,context,xspeed,yspeed,boarders));
                    break;
                }
                case 2:{

                    obstacleList.add(new Obstacle(100+x,200+y,100,400, color,context,xspeed,yspeed,boarders));
                    break;
                }
                case 3:{

                    obstacleList.add(new Obstacle(100+x,200+y,400,100, color,context,xspeed,yspeed,boarders));
                    break;
                }
                default:{
                    obstacleList.add(new Obstacle(100+x,200+y,400,100, color,context,-1,-1,boarders));
                    break;
                }



            }



        }

        resetObstaclesRandom(playerpoint);

    }

    public void setGametime(long gametime) {
        this.gametime = gametime;
    }

    /**
     * should reset the obstacles random but away from the player rect
     * @param p
     */
    public void resetObstaclesRandom(Point p){

        for(Obstacle ob: obstacleList){


            boolean xisout=true;

            int x;
            int y;

            while(xisout){
                x=r.nextInt(boarders.x-100);
                y=r.nextInt(boarders.y-100);

                if((x>p.x+500||x<p.x-500)||(y>p.y+500||y<p.y-500)){
                    xisout=false;
                    ob.moveAndReset(x,y);
                }

            }
        }
    }

    /**
     * should reset the obstacles random but away from the player rect
     * @param p
     */
    public void regen(Point p){

        for(Obstacle ob: obstacleList){


            boolean xisout=true;

            int x;
            int y;

            while(xisout){
                x=r.nextInt(boarders.x-100);
                y=r.nextInt(boarders.y-100);

                if((x>p.x+500||x<p.x-600)||(y>p.y+600||y<p.y-600)){
                    xisout=false;
                    ob.moveAndReset(x,y);
                }

            }
        }
    }



    public void populateArraylist(int typ1,int typ2,int typ3,int typ4,int typ5){

        for(int i=1;i<=typ1;i++){
            double xspeed=(xmax*-1)+ r.nextDouble()*(xmax*2);
            double yspeed=(ymax*-1)+ r.nextDouble()*(ymax*2);
            int x=r.nextInt(700);
            int y= r.nextInt(900);
            obstacleList.add(new Obstacle(100+x,200+y,100,400, color,context,xspeed,yspeed,boarders));
        }

        for(int i=1;i<=typ2;i++){
            double xspeed=(xmax*-1)+ r.nextDouble()*(xmax*2);
            double yspeed=(ymax*-1)+ r.nextDouble()*(ymax*2);
            int x=r.nextInt(700);
            int y= r.nextInt(900);
            obstacleList.add(new Obstacle(100+x,200+y,100,200, color,context,xspeed,yspeed,boarders));
        }
        for(int i=1;i<=typ3;i++){
            double xspeed=(xmax*-1)+ r.nextDouble()*(xmax*2);
            double yspeed=(ymax*-1)+ r.nextDouble()*(ymax*2);
            int x=r.nextInt(700);
            int y= r.nextInt(900);
            obstacleList.add(new Obstacle(100+x,200+y,200,400, color,context,xspeed,yspeed,boarders));
        }
        for(int i=1;i<=typ4;i++){
            double xspeed=(xmax*-1)+ r.nextDouble()*(xmax*2);
            double yspeed=(ymax*-1)+ r.nextDouble()*(ymax*2);
            int x=r.nextInt(700);
            int y= r.nextInt(900);
            obstacleList.add(new Obstacle(100+x,200+y,200,200, color,context,xspeed,yspeed,boarders));
        }
        for(int i=1;i<=typ5;i++){
            double xspeed=(xmax*-1)+ r.nextDouble()*(xmax*2);
            double yspeed=(ymax*-1)+ r.nextDouble()*(ymax*2);
            int x=r.nextInt(700);
            int y= r.nextInt(900);
            obstacleList.add(new Obstacle(100+x,200+y,400,100, color,context,xspeed,yspeed,boarders));
        }

    }

    public void removeObtacles(){
        obstacleList.clear();

    }


    public void removeObtacles(int objecttoremove){
        obstacleList.remove(objecttoremove-1);

    }


    public void changeColor(int color){
        for(Obstacle ob:obstacleList){
            ob.setColor(color);
        }
    }



    /**
     * Updates the Obstacles and checks if they contains the player
     */
    public void update(){

        for(Obstacle ob: obstacleList){

                long x = (System.nanoTime()-gametime)/1_000_000_000;

                double multiplyer = 2-(1/(x+0.5));

                //System.out.println(multiplyer);
                ob.update(multiplyer);


            if(ob.containsPlayer(player)){
                containsplayer=true;
            }


        }
    }

    public void draw(Canvas canvas){
        for(Obstacle ob:obstacleList){
            ob.draw(canvas);
        }
    }


}
