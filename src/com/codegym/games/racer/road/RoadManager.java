package com.codegym.games.racer.road;

import com.codegym.games.racer.PlayerCar;
import com.codegym.games.racer.RacerGame;
import com.codegym.engine.cell.Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RoadManager {
    public static final int LEFT_BORDER=RacerGame.ROADSIDE_WIDTH;
    public static final int RIGHT_BORDER=RacerGame.WIDTH-LEFT_BORDER;
    private static final int  FIRST_LANE_POSITION=16;
    private static final int  FOURTH_LANE_POSITION=44;
    private List<RoadObject>  items = new ArrayList<RoadObject>();
    private static final int PLAYER_CAR_DISTANCE=12;
    private int passedCarsCount=0;





    public int getPassedCarsCount() {
        return passedCarsCount;
    }


    private void addRoadObject(RoadObjectType type, Game game){
        int x = game.getRandomNumber(FIRST_LANE_POSITION,FOURTH_LANE_POSITION);
        int y = -1*RoadObject.getHeight(type);
        RoadObject temp = createRoadObject(type, x, y);
        if (isRoadSpaceFree(temp)==true){
            items.add(temp);
        }

    };

    private RoadObject createRoadObject(RoadObjectType type, int x, int y){
        RoadObject retObject;
        if (type == RoadObjectType.SPIKE)retObject= new Spike(x,y);
        else if (type == RoadObjectType.DRUNK_CAR) retObject=new MovingCar(x, y);
        else retObject=new Car(type,x,y);

        return retObject;
    }
    public void draw(Game game){
        for (RoadObject x : items) x.draw(game);
    }

    public void move(int boost){
        for (RoadObject x : items) x.move(boost+x.speed, items);
        deletePassedItems();
    }
    private boolean spikeExists(){
        boolean istrue=false;
        for (int i = 0; i<items.size(); i++){
            if (items.get(i).type==RoadObjectType.SPIKE) istrue=true;

    }
        return istrue;
    }
    private void generateSpike(Game game){

        if (game.getRandomNumber(100)<10&&spikeExists()==false){
           addRoadObject(RoadObjectType.SPIKE, game);
        }
    }
    public void generateNewRoadObjects(Game game){
        generateSpike(game);
        generateRegularCar(game);
        generateMovingCar(game);

    }
    private void deletePassedItems(){
        Iterator<RoadObject> itr = items.iterator();
        while (itr.hasNext()){
            RoadObject x=itr.next();
            if (x.y>=RacerGame.HEIGHT){
                itr.remove();
                if (x.type!=RoadObjectType.SPIKE) passedCarsCount++;
            }
            }
        }
        public boolean checkCrash(PlayerCar playerCar){
            Iterator<RoadObject> itr = items.iterator();
            boolean temp=false;
            while (itr.hasNext()){
                RoadObject x=itr.next();

                if (x.isCollision(playerCar)==true) temp=true;

            }
            return temp;

        }
        private void generateRegularCar(Game game){
        int carTypeNumber=game.getRandomNumber(4);
        if (game.getRandomNumber(100)<30){
            addRoadObject(RoadObjectType.values()[carTypeNumber],game);
        }
    }
    private boolean isRoadSpaceFree(RoadObject object){
        Iterator<RoadObject> itr = items.iterator();
        boolean temp=true;
        while (itr.hasNext()){
            RoadObject x=itr.next();

            if (x.isCollisionWithDistance(object, PLAYER_CAR_DISTANCE)) temp=false;

        }
        return temp;


    }
    private boolean movingCarExists(){
        boolean istrue=false;
        for (int i = 0; i<items.size(); i++){
            if (items.get(i).type==RoadObjectType.DRUNK_CAR) istrue=true;

        }
        return istrue;

    }
    private void generateMovingCar(Game game){
        if (game.getRandomNumber(100)<10 && movingCarExists()==false){
            addRoadObject(RoadObjectType.DRUNK_CAR,game);
        }
    }
}






