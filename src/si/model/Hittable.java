package si.model;


import java.awt.*;

public interface Hittable {
	public boolean isAlive();
	public int getPoints();
	public boolean isPlayer();
	public boolean isEnemyShip();
	public boolean isStone();
	public boolean isHit(Bullet b);
	public boolean isHit2(Stone e);
	public Rectangle getHitBox();
	public void destroy();
	public int getX();
	public int getY();
}

