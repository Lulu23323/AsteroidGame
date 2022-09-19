package si.model;

import java.awt.*;
import java.util.Random;

import static si.model.SpaceInvadersGame.SCREEN_HEIGHT;
import static si.model.SpaceInvadersGame.SCREEN_WIDTH;

public class EnemyShip implements Hittable {
    private String name;
    boolean alive;
    private double x, y, angleMove;
    private AlienType type = AlienType.D;
    private Random rand;
    public static final int SHIP_SCALE = 2;
    Rectangle hitBox;
    int count = 0;

    public EnemyShip(double x, double y, double angleMove) {
        this.x = x;
        this.y = y;
        this.angleMove = angleMove;
        this.hitBox = new Rectangle((int)x, (int)y, type.getWidth(), type.getHeight());
        this.alive = true;
    }

    public EnemyShip(){
        int count = (int) (Math.random()*(4-1+1)+1);
        if(count == 1){
            x = Math.random()*(SCREEN_WIDTH-0+1)+0;
            y = 0;
        } else if(count == 2){
            x = Math.random()*(SCREEN_WIDTH-0+1)+0;
            y = SCREEN_HEIGHT;
        } else if(count == 3){
            x = SCREEN_WIDTH;
            y = Math.random()*(SCREEN_HEIGHT-0+1)+0;
        } else if(count == 4){
            x = 0;
            y = Math.random()*(SCREEN_HEIGHT-0+1)+0;
        }
        angleMove = Math.random()*(360-0+1)+0;
        alive = true;
        hitBox = new Rectangle((int)x, (int)y, 32, 20);
    }

    public boolean isHit(Bullet b) {
        boolean hit = getHitBox().intersects(b.getHitBox());
        if (hit) {
            alive = false;
        }
        return hit;
    }

    @Override
    public boolean isHit2(Stone e) {
        boolean hit = getHitBox().intersects(e.getHitBox());
        if (hit) {
            alive = false;
        }
        return hit;
    }

    @Override
    public Rectangle getHitBox() {
        return new Rectangle((int) x, (int) y, SHIP_SCALE * type.getWidth(), SHIP_SCALE * type.getHeight());
    }

    public boolean isAlive() {
        return alive;
    }

    public int getPoints() {
        return type.getScore();
    }

    public boolean isPlayer() {
        return false;
    }
    public boolean isEnemyShip() {
        return true;
    }
    public boolean isStone() {
        return false;
    }


    public void move(double angleMove) {
        if(x>=0 && x<=SCREEN_WIDTH && y >= 0 && y<= SCREEN_HEIGHT){
            x += (Math.sin(Math.toRadians(angleMove)))*1;
            y += (Math.cos(Math.toRadians(angleMove)))*1;
            hitBox = new Rectangle((int)x, (int)y, 32, 20);
        }else{
            alive = false;
        }

    }

    public Bullet fire(double X, double Y) {
        Bullet b = null;
        double angle = 180+Math.toDegrees(Math.atan((x-X)/(y-Y)));
        if(Y>y){
            angle = angle + 180;
        }
        if(count == 100){
            double temple_x = Math.sin(Math.toRadians(angle))*8 + x;
            double temple_y = Math.cos(Math.toRadians(angle))*8 + y;
            b = new Bullet((int)temple_x, (int)temple_y, angle, "EnemyShip");
            count = 0;
        } else{
            count++;
        }
        return b;
    }

    public double willMoveX(){
        double willX = x + (Math.sin(Math.toRadians(angleMove)))*0.5*5;
        if (willX >= SCREEN_WIDTH) {
            willX = willX - SCREEN_WIDTH;
        } else if(willX < 0) {
            willX = willX + SCREEN_WIDTH;
        }
        return willX;
    }

    public double willMoveY(){
        double willY = y + (Math.cos(Math.toRadians(angleMove)))*0.5*5;
        if (willY >= SCREEN_HEIGHT) {
            willY = willY - SCREEN_HEIGHT;
        } else if(willY < 0) {
            willY = willY + SCREEN_HEIGHT;
        }
        return willY;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public AlienType getType() {
        return type;
    }

    public double getAngleMove() {
        return angleMove;
    }

    public void destroy() {alive = false;}

    public void resetDestroyed() {
        int count = (int) (Math.random()*(4-1+1)+1);
        if(count == 1){
            x = Math.random()*(SCREEN_WIDTH-0+1)+0;
            y = 0;
        } else if(count == 2){
            x = Math.random()*(SCREEN_WIDTH-0+1)+0;
            y = SCREEN_HEIGHT;
        } else if(count == 3){
            x = SCREEN_WIDTH;
            y = Math.random()*(SCREEN_HEIGHT-0+1)+0;
        } else if(count == 4){
            x = 0;
            y = Math.random()*(SCREEN_HEIGHT-0+1)+0;
        }
        angleMove = Math.random()*(360-0+1)+0;
        alive = true;
        hitBox = new Rectangle((int)x, (int)y, 32, 20);
    }



}
