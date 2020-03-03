package com.codegym.games.racer;


import com.codegym.engine.cell.*;
import com.codegym.games.racer.road.RoadManager;


public class RacerGame extends Game {
    public static final int WIDTH=64, HEIGHT=64;
    public static final int CENTER_X=WIDTH/2;
    public static final int ROADSIDE_WIDTH=14;
    private RoadMarking roadMarking;
    private PlayerCar player;
    private RoadManager roadManager;
    private boolean isGameStopped;
    private FinishLine finishLine;
    private static final int RACE_GOAL_CARS_COUNT=40;
    private ProgressBar progressBar;
    private int score;



     @Override
    public void initialize(){
        setScreenSize(WIDTH, HEIGHT);

         createGame();
         showGrid(false);
    }


     private void createGame()
     {
         isGameStopped=false;
         roadMarking = new RoadMarking() ;
         player = new PlayerCar();
         roadManager = new RoadManager();
finishLine = new FinishLine();
progressBar = new ProgressBar(RACE_GOAL_CARS_COUNT);
         drawScene();
         setTurnTimer(40);
         score=3500;

    }

    private void drawField(){
        for (int x=0; x<WIDTH; x++) {
            for (int y=0; y<HEIGHT; y++){
                if (x==CENTER_X) {
                    setCellColor(x,y,Color.WHITE);
                }
                else if (x>=ROADSIDE_WIDTH && x<WIDTH-ROADSIDE_WIDTH){
                    setCellColor(x,y,Color.DIMGRAY);
                }
                else setCellColor(x,y,Color.GREEN);
            }
        }
    }

    public void setCellColor(int x, int y, Color color) {
        if (x<0||y<00||x>=WIDTH||y>=HEIGHT)
            return;
            super.setCellColor(x,y,color);

    }

    private void moveAll(){
         roadMarking.move(player.speed);
         player.move();
         roadManager.move(player.speed);
         finishLine.move(player.speed);
         progressBar.move(roadManager.getPassedCarsCount());
    }

    @Override
    public void onTurn(int step){
        if (roadManager.checkCrash(player)==true){
            gameOver();
            drawScene();
        }

        else if (finishLine.isCrossed(player)==true) {
            win();
            drawScene();
        }
        else {
            if(RACE_GOAL_CARS_COUNT<=roadManager.getPassedCarsCount()) finishLine.show();

            moveAll();
            roadManager.generateNewRoadObjects(this);
            score-=5;
            setScore(score);
            drawScene();

        }


    }

    private void drawScene(){
        drawField();
        roadMarking.draw(this);
        player.draw(this);
        roadManager.draw(this);
        finishLine.draw(this);
        progressBar.draw(this);
    }

    @Override
    public void onKeyPress(Key key){
         if (key==Key.RIGHT){
             player.setDirection(Direction.RIGHT);
         }
         else if (key==Key.LEFT){
             player.setDirection(Direction.LEFT);
         }
         if (key==Key.SPACE&&isGameStopped==true){
             createGame();
         }
         if (key==Key.UP){
             player.speed=2;
         }

     }
     @Override
    public void onKeyReleased(Key key){
         if (key == Key.RIGHT && player.getDirection() == Direction.RIGHT) {
             player.setDirection(Direction.NONE);
         }
         else  if (key == Key.LEFT && player.getDirection() == Direction.LEFT) {
             player.setDirection(Direction.NONE);
         }
         if (key==Key.UP){
             player.speed=1;
         }
     }
     private void gameOver(){
         isGameStopped=true;
         showMessageDialog(Color.GREEN, "GAME OVER", Color.RED, 10);
         stopTurnTimer();
         player.stop();
     }
     private void win(){
         isGameStopped=true;
         showMessageDialog(Color.BLACK,"YOU WIN",Color.RED,50);
         stopTurnTimer();
     }

}
