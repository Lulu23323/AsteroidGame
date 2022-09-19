package si.display;

import si.model.*;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    private static final long serialVersionUID = -8282302849760730222L;
    private SpaceInvadersGame game;

    public GameScreen(SpaceInvadersGame game){
        this.game = game;
    }

    public static int[] move(int[] index, double angle, double d){
        double x_d = index[0] + (Math.sin(Math.toRadians(angle)))*d;
        double y_d = index[1] + (Math.cos(Math.toRadians(angle)))*d;
        int[] index2 = new int[] {(int)x_d, (int)y_d};
        return index2;
    }

    private void drawBonus(Graphics2D gc, Bonus b){
        if(b.isAlive()){
            double x = b.getX();
            double y = b.getY();
            int[] indexA = new int[] {(int) x-5, (int) y+5};
            int[] indexB = new int[] {(int) x-5, (int) y-5};
            int[] indexC = new int[] {(int) x+5, (int) y-5};
            int[] indexD = new int[] {(int) x+5, (int) y+5};
            int[] indexX = new int[] {indexA[0], indexB[0], indexC[0], indexD[0]};
            int[] indexY = new int[] {indexA[1], indexB[1], indexC[1], indexD[1]};
            Polygon pg = new Polygon(indexX, indexY, indexY.length);
            if(b.getType() == 1){
                gc.setColor(Color.RED);
            } else if(b.getType() == 2){
                gc.setColor(Color.YELLOW);
            } else if(b.getType() == 3){
                gc.setColor(Color.BLUE);
            }
            gc.drawPolygon(pg);
            gc.fillPolygon(indexX, indexY, indexY.length);
        }
    }

    private void drawEnemyShip(Graphics2D gc, EnemyShip e) {
        if(e.isAlive()){
            double x = e.getX();
            double y = e.getY();
            int[] indexA = new int[] {(int) x-3, (int) y-3};
            int[] indexB = new int[] {(int) x-8, (int) y};
            int[] indexC = new int[] {(int) x-2, (int) y+3};
            int[] indexD = new int[] {(int) x+2, (int) y+3};
            int[] indexE = new int[] {(int) x+8, (int) y};
            int[] indexF = new int[] {(int) x+3, (int) y-3};
            int[] indexX = new int[] {indexA[0], indexB[0], indexC[0], indexD[0], indexE[0], indexF[0], indexA[0]};
            int[] indexY = new int[] {indexA[1], indexB[1], indexC[1], indexD[1], indexE[1], indexF[1], indexA[1]};
            Polygon pg = new Polygon(indexX, indexY, indexY.length);
            gc.setColor(Color.GREEN);
            gc.drawPolygon(pg);
            gc.fillPolygon(indexX, indexY, indexY.length);
        }
    }

    private void drawPlayerShip(Graphics2D gc, Player p) {
        double angle = p.getAngle();
        int x = p.getX();
        int y = p.getY();
        int[] index = new int[] {x,y};
        int[] indexA = move(index, angle, 8);
        int[] indexB = move(index, angle+150, 8);
        int[] indexC = move(index, angle+210,8);
        int[] indexX = new int[] {indexA[0], indexB[0], indexC[0], indexA[0]};
        int[] indexY = new int[] {indexA[1], indexB[1], indexC[1], indexA[1]};
        Polygon pg = new Polygon(indexX, indexY, 4);
        if(p.getBonusType() == 0){
            gc.setColor(Color.WHITE);
        }else if(p.getBonusType() == 1){
            gc.setColor(Color.RED);
        }else if(p.getBonusType() == 2){
            gc.setColor(Color.YELLOW);
        }else if(p.getBonusType() == 3){
            gc.setColor(Color.BLUE);
        }
        gc.drawPolygon(pg);
        gc.fillPolygon(indexX, indexY, 4);
    }

    private void drawBullet(Graphics2D gc, Bullet b) {
        gc.setColor(Color.WHITE);
        gc.fillRect(b.getX(), b.getY(), b.BULLET_WIDTH, b.BULLET_HEIGHT);
    }

    private void drawStone(Graphics2D gc, Stone s) {
        s.angleUp();
        double d;
        if (s.getType() == AlienType.A) {
            d = 30;
        } else if (s.getType() == AlienType.B) {
            d = 20;
        } else {
            d = 10;
        }
        int x = s.getX();
        int y = s.getY();
        int[] index = new int[] {x,y};
        int[] indexA = move(index, s.getAngle(0),d);
        int[] indexB = move(index, s.getAngle(1),d);
        int[] indexC = move(index, s.getAngle(2),d);
        int[] indexD = move(index, s.getAngle(3),d);
        int[] indexE = move(index, s.getAngle(4),d);
        int[] indexF = move(index, s.getAngle(5),d);
        int[] indexX = new int[] {indexA[0], indexB[0], indexC[0],indexD[0],indexE[0],indexF[0], indexA[0]};
        int[] indexY = new int[] {indexA[1], indexB[1], indexC[1],indexD[1],indexE[1],indexF[1], indexA[1]};
        Polygon pg = new Polygon(indexX, indexY, indexY.length);
        gc.setColor(Color.WHITE);
        gc.drawPolygon(pg);
        gc.fillPolygon(indexX, indexY, indexY.length);
    }

    protected void paintComponent(Graphics g) {
        game.moveEnemy();
        if (game != null) {
            Graphics2D g2 = (Graphics2D) g;
            g.setColor(Color.black);
            g.fillRect(0, 0, SpaceInvadersGame.SCREEN_WIDTH, SpaceInvadersGame.SCREEN_HEIGHT);
            g.setColor(Color.white);
            g.drawString("Lives: " + game.getLives(), 0, 20);
            g.drawString("Score: " + game.getPlayerScore(), SpaceInvadersGame.SCREEN_WIDTH / 2, 20);
            drawPlayerShip(g2,game.getShip() );
            if(game.getEnemyShip() != null){
                drawEnemyShip(g2,game.getEnemyShip());
            }
            for(int i = 0; i<game.getBonuses().size(); i++){
                drawBonus(g2,game.getBonuses().get(i));
            }
            for (Bullet bullet : game.getBullets()) {
                drawBullet(g2, bullet);
            }
            for (Stone e: game.getStones()){
                drawStone(g2,e);
            }
            if (game.isPaused() && !game.isGameOver()) {
                g.setColor(Color.WHITE);
                g.drawString("Press 'p' to continue ", 256, 256);
            } else if (game.isGameOver()) {
                g.setColor(Color.WHITE);
                g.drawString("Game over ", 480, 256);
            }
        }
    }
}
