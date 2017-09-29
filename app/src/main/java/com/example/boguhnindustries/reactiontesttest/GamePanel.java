package com.example.boguhnindustries.reactiontesttest;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.WindowManager;


/**
 * Created by Lorenz on 29.03.2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    //stuff
    private Context context;
    private MainThread thread;
    private static Canvas  canvas;

    //mystuff
    private Player player;
    private Point playerpoint;
    private boolean moving= false;
    private Borders bordersMeMore;
    private Point borders;
    private ObstacleManager ob;

    private int highscore;
    private int currentscorescore;

    private long gametime;
    private boolean gameover;
    private Paint score;
    private Paint gameoverpaint;

    public GamePanel(Context context) {

        //super constuctor
        super(context);
        //gametime for score
        gametime = System.nanoTime();


        this.context = context;
        getHolder().addCallback(this);
        gameover=false;


        //for the resolution
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        borders = size;



        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        //Constructs the player and Boarders
        player = new Player(new Rect(borders.x/2-100,borders.y/2-100, borders.x/2+100,borders.y/2+100), Color.rgb(255, 0, 0),borders,context);
        playerpoint = new Point(borders.x/2, borders.y/2);
        bordersMeMore = new Borders(borders, Color.GREEN);


        // constructs the obstacle manager with obstacles
        ob = new ObstacleManager(100,4,2,Color.BLUE,context,borders,player,playerpoint );


        //paints for score and gameover
        score =new Paint();
        score.setColor(Color.BLACK);
        score.setTextSize(55);

        gameoverpaint= new Paint();
        gameoverpaint.setColor(Color.BLACK);


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (true) {
            try {
                thread.setRunning(false);
                thread.join();

            } catch (Exception e) {
                e.printStackTrace();
            }

            retry = false;
        }

    }


    /**For the drag and drop action
     *
     * @param event
     * @return true if an action occurs
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(gameover){
                reset();
            }
            if (player.isin((int) event.getX(), (int) event.getY())) {
                moving =true;
                player.firsttouch =false;
            }
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {


            if(moving){
                playerpoint.set((int) event.getX(), (int) event.getY());
            }


        }

        if(event.getAction() == MotionEvent.ACTION_UP){
            moving=false;
        }
        return true;

    }

    /**Updates the playerpoint (point in the middle of the player) and the view
     *
     */
    public void update() {
        if(!gameover){
            player.update(playerpoint);

            // Obstacle update
            ob.update();
            ob.setGametime(this.gametime);
        }




        if(player.collidesWithWalls(playerpoint)||ob.isContainsplayer()){
            gameOver();


        }
    }

    /**
     * Draws the things on the canvas
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        this.canvas = canvas;
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);
        ob.draw(canvas);
        //player
        player.draw(canvas);
        //gameborders
        bordersMeMore.draw(canvas);
        // Obstacles

        //score
        if(!gameover){

            int scorenummber =(int)((System.nanoTime()-gametime)/1_000_000_000);
            currentscorescore=scorenummber;
            String scoretext=""+scorenummber;
            //String scoretext=""+((System.nanoTime()-gametime)/1_000_000_000);
            canvas.drawText(scoretext,(float)(((canvas.getWidth()) /2.0)),50,score);

            //colorchange by score
            changecolor(scorenummber);
        }



        // Gameover text etc
        else if(gameover){
            //in-effektive

            int xgameoverscreen = canvas.getWidth()/4;
            int ygameoverscreen = canvas.getHeight()/4 +canvas.getHeight()/8;

            gameoverpaint.setTextSize(100);
            canvas.drawText("GAME OVER",xgameoverscreen+canvas.getWidth()/18,ygameoverscreen,gameoverpaint);
            gameoverpaint.setTextSize(60);
            canvas.drawText("Tap on screen to play again",xgameoverscreen,ygameoverscreen+canvas.getHeight()/8,gameoverpaint);
            canvas.drawText("Highscore: "+highscore,xgameoverscreen+canvas.getWidth()/6,ygameoverscreen+canvas.getHeight()/20,gameoverpaint);
        }

    }

    private void changecolor(int score) {
        //score/=10;
        switch(score % 4){
            case 3: ob.changeColor(Color.CYAN);
                break;
            case 1: ob.changeColor(Color.MAGENTA);
                break;
            case 2: ob.changeColor(Color.DKGRAY);
                break;
            case 0: ob.changeColor(Color.rgb(20,20,170));
                break;
        }

    /*    if(scorenummber==10){
            ob.changeColor(Color.CYAN);
        }
        if(scorenummber==5){
            ob.changeColor(Color.MAGENTA);
        }
        if(scorenummber==15){
            ob.changeColor(Color.DKGRAY);
        }
        if(scorenummber==20){
            ob.changeColor(Color.YELLOW);
        }*/
    }

    public void reset(){
        gameover = false;
        //sets player back
        playerpoint.set(borders.x/2,borders.y/2);
        player.firsttouch =true;


        //sets obstacleback
        ob.setContainsplayer(false);
        ob.resetObstaclesRandom(playerpoint);
        gametime=System.nanoTime();
        ob.changeColor(Color.BLUE);
        ob.setGametime(gametime);


    }
    public void gameOver(){
        highscore=currentscorescore;


        gameover = true;


    }




}
