package com.codegym.games.racer;

import com.codegym.games.racer.GameObject;
import com.codegym.games.racer.RacerGame;
import com.codegym.games.racer.ShapeMatrix;

public class FinishLine extends GameObject {
    public FinishLine(){
        super(RacerGame.ROADSIDE_WIDTH,-1* ShapeMatrix.FINISH_LINE.length,ShapeMatrix.FINISH_LINE);
            }
            private boolean isVisible=false;
    public void show(){
        isVisible=true;
    }
    public void move(int boost){
        if (isVisible==true) y+=boost;
    }
    public boolean isCrossed(PlayerCar playerCar){
        if(playerCar.y<=y) return true;
        else return false;
    }
}
