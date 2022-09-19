package si.model;

import si.display.GameScreen;

import java.awt.*;

import static si.display.GameScreen.move;
import static si.model.SpaceInvadersGame.SCREEN_HEIGHT;
import static si.model.SpaceInvadersGame.SCREEN_WIDTH;

public class Player implements Hittable {
    private int x;
    private int y;
    private int count = 0;
    private double speedX;
    private double speedY;
    private double templeX;
    private double templeY;
    private double angle;
    private Rectangle hitBox;
    private boolean alive = true;
    private int bonusType = 0;

    public Player() {
        x = SpaceInvadersGame.SCREEN_WIDTH / 2;
        y = SpaceInvadersGame.SCREEN_HEIGHT / 2;
        templeX = SpaceInvadersGame.SCREEN_WIDTH / 2;
        templeY = SpaceInvadersGame.SCREEN_HEIGHT / 2;
        speedX = 0;
        speedY = 0;
        angle = 180;
        hitBox = new Rectangle(x, y, 32, 20);
    }

    public boolean isHit2(Stone b) {
        Rectangle s = b.getHitBox();
        if (s.intersects(hitBox.getBounds())) {
            alive = false;
        }
        return s.intersects(hitBox.getBounds());
    }

    public boolean isHit(Bullet b) {
        boolean hit = hitBox.intersects(b.getHitBox());
        if (hit) {
            alive = false;
        }
        return hit;
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    public boolean isAlive() {
        return alive;
    }

    public void changeAlive(boolean t){this.alive = true;}

    public void resetDestroyed() {
        alive = true;
        x = SpaceInvadersGame.SCREEN_WIDTH / 2;
        y = SpaceInvadersGame.SCREEN_HEIGHT / 2;
        templeX = SpaceInvadersGame.SCREEN_WIDTH / 2;
        templeY = SpaceInvadersGame.SCREEN_HEIGHT / 2;
        speedX = 0;
        speedY = 0;
        angle = 180;
        hitBox = new Rectangle(x, y, 32, 20);
    }

    public int getPoints() {
        return 0;
    }

    public boolean isPlayer() {
        return true;
    }

    public boolean isEnemyShip() {
        return false;
    }

    public boolean isStone() {
        return false;
    }

    public Bullet fire() {
        Bullet b = null;
        if(count ==0){
            double temple_x = (Math.sin(Math.toRadians(angle)))*8 + x;
            double temple_y = (Math.cos(Math.toRadians(angle)))*8 + y;
            b = new Bullet((int)temple_x, (int)temple_y, this.angle, "Player");
            count = 6;
        }else{
            count--;
        }
        return b;
    }

    public Bullet fireDouble(){
        Bullet b = null;
        if(count ==0){
            double temple_x = (Math.sin(Math.toRadians(angle)))*8 + x;
            double temple_y = (Math.cos(Math.toRadians(angle)))*8 + y;
            b = new Bullet((int)temple_x, (int)temple_y, this.angle, "Player");
            count = 2;
        }else{
            count--;
        }
        return b;

    }

    public void move(double speedX, double speedY) {
        templeX += speedX;
        templeY += speedY;
        if (templeX >= SpaceInvadersGame.SCREEN_WIDTH) {
            templeX = templeX - SpaceInvadersGame.SCREEN_WIDTH;
        } else if (templeY >= SpaceInvadersGame.SCREEN_HEIGHT) {
            templeY = templeY - SpaceInvadersGame.SCREEN_HEIGHT;
        } else if(templeX < 0) {
            templeX = templeX + SpaceInvadersGame.SCREEN_WIDTH;
        } else if(templeY < 0) {
            templeY = templeY + SpaceInvadersGame.SCREEN_HEIGHT;
        }
        this.x = (int) templeX;
        this.y = (int) templeY;
        hitBox = new Rectangle(x, y, 32, 20);

    }

    public void acceleration(double d) {
        speedX = speedX + (Math.sin(Math.toRadians(angle)))*d;
        speedY = speedY + (Math.cos(Math.toRadians(angle)))*d;
    }

    public void turn(double turnLeftAngle, double turnRightAngle) {
        angle += turnLeftAngle;
        angle -= turnRightAngle;
        if (angle < 0) {
            angle += 360;
        } else if (angle > 360) {
            angle -= 360;
        }
    }
    public void changeLocation(double tmpX, double tmpY){
        templeX = tmpX;
        templeY = tmpY;
        x = (int)templeX;
        y = (int)templeY;
        hitBox = new Rectangle(x, y, 32, 20);
    }

    public double willMoveX(){
        double willX = x + (Math.sin(Math.toRadians(angle)))*0.5*5;
        if (willX >= SCREEN_WIDTH) {
            willX = willX - SCREEN_WIDTH;
        } else if(willX < 0) {
            willX = willX + SCREEN_WIDTH;
        }
        return willX;
    }

    public double willMoveY(){
        double willY = y + (Math.cos(Math.toRadians(angle)))*0.5*5;
        if (willY >= SCREEN_HEIGHT) {
            willY = willY - SCREEN_HEIGHT;
        } else if(willY < 0) {
            willY = willY + SCREEN_HEIGHT;
        }
        return willY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY(){
        return speedY;
    }

    public double getTempleX() {
        return templeX;
    }

    public double getTempleY(){return templeY;}

    public double getAngle() {
        return angle;
    }

    public void destroy(){this.alive = false;}

    public void changeCount(){this.count = 0;}
    public void changeBonusType(int i){this.bonusType = i;}
    public int getBonusType(){return bonusType;}
}