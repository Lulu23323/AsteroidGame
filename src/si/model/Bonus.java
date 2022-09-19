package si.model;

import java.awt.*;

public class Bonus {
    private int type;
    private double x;
    private double y;
    private int aliveTime;
    private boolean alive;
    private Rectangle hitBox;
    public Bonus(int i, int x, int y){
        this.type = i;
        this.x = x;
        this.y = y;
        this.alive = true;
        this.aliveTime = 0;
    }

    public Bonus(){
        this.type = 0;
        this.alive = true;
        this.aliveTime = 0;
    }

    public void activate(){
        alive = true;
        aliveTime = 300;
    }
    public void use(){
        if(aliveTime > 0){
            aliveTime--;
        }
        if(aliveTime == 0){
            alive = false;
        }
    }
    public int getALiveTime(){return aliveTime;}
    public void destroy(){this.alive = false;}
    public int getType(){return type;}
    public boolean isAlive(){return alive;}
    public Rectangle getHitBox(){
        return new Rectangle((int)x, (int)y, 5, 5);
    }
    public double getX(){return x;}
    public double getY(){return y;}

    public int getAliveTime() {return aliveTime;}
}
