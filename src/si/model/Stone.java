package si.model;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import static si.model.SpaceInvadersGame.SCREEN_HEIGHT;
import static si.model.SpaceInvadersGame.SCREEN_WIDTH;

public class Stone implements Hittable {
    private String name;
    boolean alive;
    private double x, y, angleMove;
    private double[] angleArr = new double[] {-1, -1, -1, -1, -1, -1};
    private AlienType type;
    private Random rand;
    public static final int SHIP_SCALE = 2;
    Rectangle hitBox;

    public Stone(double x, double y, double angleMove, AlienType type) {
        this.x = x;
        this.y = y;
        this.angleMove = angleMove;
        this.type = type;
        this.rand = new Random((long) (x * 100 + y));
        this.hitBox = new Rectangle((int)x, (int)y, type.getWidth(), type.getHeight());
        this.alive = true;
    }

    public double willMoveX(){
        double willX = x + (Math.sin(Math.toRadians(angleMove)))*0.5*15;
        if (willX >= SCREEN_WIDTH) {
            willX = willX - SCREEN_WIDTH;
        } else if(willX < 0) {
            willX = willX + SCREEN_WIDTH;
        }
        return willX;
    }

    public double willMoveY(){
        double willY = y + (Math.cos(Math.toRadians(angleMove)))*0.5*15;
        if (willY >= SCREEN_HEIGHT) {
            willY = willY - SCREEN_HEIGHT;
        } else if(willY < 0) {
            willY = willY + SCREEN_HEIGHT;
        }
        return willY;
    }


    public boolean isHit(Bullet b) {
        boolean hit = getHitBox().intersects(b.getHitBox());
        if (hit) {
            alive = false;
        }
        return hit;
    }

    public boolean isHit2(Stone e) {return false;}

    public void angleUp(){
        for(int i = 0; i<angleArr.length; i++){
            if(angleArr[i] == -1){
                this.angleArr[i] = Math.random()*(60*(i+1)-60*i+1)+60*i;
            }
            angleArr[i] = this.angleArr[i] + 0.1;
        }

    }

    public double getAngle(int i) {return angleArr[i];}

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
        return false;
    }
    public boolean isStone() {
        return true;
    }

    public void move(double angleMove, double ss) {
        double speedX = Math.sin(Math.toRadians(angleMove))*ss;
        double speedY = Math.cos(Math.toRadians(angleMove))*ss;
        x += speedX;
        y += speedY;
        if (x >= SpaceInvadersGame.SCREEN_WIDTH) {
            x = x - SpaceInvadersGame.SCREEN_WIDTH;
        } else if (y >= SpaceInvadersGame.SCREEN_HEIGHT) {
            y = y - SpaceInvadersGame.SCREEN_HEIGHT;
        } else if(x < 0) {
            x = x + SpaceInvadersGame.SCREEN_WIDTH;
        } else if(y < 0) {
            y = y + SpaceInvadersGame.SCREEN_HEIGHT;
        }
        hitBox = new Rectangle((int)x, (int)y, SHIP_SCALE * type.getWidth(), SHIP_SCALE * type.getHeight());
    }

    public Shape getShape() {
        return new Rectangle();
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {return (int) y;}

    public double getAngleMove(){return this.angleMove;}

    public AlienType getType() {
        return type;
    }

    public void destroy() {alive = false;}
}
