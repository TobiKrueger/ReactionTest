package com.example.boguhnindustries.reactiontesttest;

import android.graphics.Canvas;
import android.provider.Settings;
import android.view.SurfaceHolder;

/**
 * Created by Lorenz on 29.03.2017.
 */

public class MainThread extends Thread {

    public static int MAX_FPS=20000000;
    private SurfaceHolder surfaceholder;
    private GamePanel gamePanel;

    public void setRunning(boolean running) {
        this.running = running;
    }

    private boolean running;
    public static Canvas canvas;


    public MainThread (SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceholder = surfaceHolder;
        this.gamePanel = gamePanel;


    }

    @Override
    public void run(){
        long startTime=System.nanoTime();
        //long timeMillis= 1000/MAX_FPS;
        long waitTime=0;
        //long totalTime=0;
        //long targentTime=1000/MAX_FPS;


        while(running){
            startTime= System.nanoTime();
            canvas= null;
            double fps=1_000_000_000.0/MAX_FPS;

            try{
                canvas = this.surfaceholder.lockCanvas();
                synchronized (surfaceholder){
                    waitTime= System.nanoTime();
                    //System.out.println((waitTime-(startTime+1000/MAX_FPS))/1_000_000);
                    //System.out.println(waitTime);

                        //                 (+1000)
                        if(startTime+10<waitTime){
                            startTime=waitTime;
                            this.gamePanel.update();
                            this.gamePanel.draw(canvas);
                        }


                }

            }catch (Exception e ){
                e.printStackTrace();
            }
            finally {
                if(canvas!=null){
                    try{
                        surfaceholder.unlockCanvasAndPost(canvas);

                    }catch (Exception e){e.printStackTrace();}

                }

                try{

                }catch (Exception e){e.printStackTrace(); }



            }



        }



    }



}
