package si.model;

import java.awt.*;

public class Bullet implements Movable, Hittable {
    private int x, y;
    private int countDistance;
    private double angle;
    private boolean alive = true;
    private Rectangle hitBox;
    private String name;
    private static int bulletCounter = 0;
    public static final int BULLET_HEIGHT = 8;
    public static final int BULLET_WIDTH = 4;
    public static final int BULLET_SPEED = 5;

    public Bullet(int x, int y, double angle, String name) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.name = name + " " + bulletCounter++;
        hitBox = new Rectangle(x, y, BULLET_WIDTH, BULLET_HEIGHT);
    }

    public void move() {
        double x_d = this.x;
        double y_d = this.y;
        x_d += (Math.sin(Math.toRadians(this.angle)))*BULLET_SPEED;
        y_d += (Math.cos(Math.toRadians(this.angle)))*BULLET_SPEED;
        this.x = (int)x_d;
        this.y = (int)y_d;
        if (this.x >= SpaceInvadersGame.SCREEN_WIDTH) {
            this.x = 0;
            this.y = SpaceInvadersGame.SCREEN_HEIGHT - y;
        } else if (this.x <= 0) {
            this.x = SpaceInvadersGame.SCREEN_WIDTH;
            this.y = SpaceInvadersGame.SCREEN_HEIGHT - y;
        } else if (this.y >= SpaceInvadersGame.SCREEN_HEIGHT) {
            this.y = 0;
            this.x = SpaceInvadersGame.SCREEN_WIDTH - x;
        } else if (this.y <= 0) {
            this.y = SpaceInvadersGame.SCREEN_HEIGHT;
            this.x = SpaceInvadersGame.SCREEN_WIDTH - x;
        }
        hitBox.setLocation(x, y);
        countDistance++;
        if(countDistance>=60){
            alive = false;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHit(Bullet b) {
        if (hitBox.intersects(b.hitBox)) {
            alive = false;
            b.alive = false;
        }
        return hitBox.intersects(b.hitBox);
    }

    public boolean isHit2(Stone b) {
        if (hitBox.intersects(b.hitBox)) {
            alive = false;
            b.alive = false;
        }
        return hitBox.intersects(b.hitBox);
    }

    public Rectangle getHitBox() {
        return new Rectangle(hitBox);
    }

    public boolean isAlive() {
        return alive;
    }

    public int getPoints() {
        return 0;
    }

    public boolean isPlayer() {
        return false;
    }
    public boolean isEnemyShip() {
        return false;
    }
    public boolean isStone() {
        return false;
    }

    public void destroy() {
        alive = false;
    }

}
