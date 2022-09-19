package si.model;

import javafx.geometry.Rectangle2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static si.model.SpaceInvadersGame.SCREEN_HEIGHT;
import static si.model.SpaceInvadersGame.SCREEN_WIDTH;

public class Swarm implements Movable {
    private ArrayList<Stone> currentStones;
    private double angle;
    private int currentLevel;
    private SpaceInvadersGame game;

    public Swarm(int currentLevel, SpaceInvadersGame g) {
        game = g;
        this.currentLevel = currentLevel;
        currentStones = new ArrayList<Stone>();

        for (int i = 0; i < currentLevel+4; i++) {
            int x1 = g.getShip().getX() - 128;
            int y1 = g.getShip().getY() - 80;
            int x2 = g.getShip().getX() + 128;
            int y2 = g.getShip().getY() + 80;
            int x = g.getShip().getX();
            int y = g.getShip().getY();
            while(x>=x1 && x<=x2 && y>=y1 && y<=y2){
                x = (int) (Math.random()*(SCREEN_WIDTH-0+1)+0);
                y = (int) (Math.random()*(SCREEN_HEIGHT-0+1)+0);
            }
            double moveAngle = Math.random()*(360-0+1)+0;
            Stone s = new Stone(x,y, moveAngle, AlienType.A);
            currentStones.add(s);

        }
    }

    public void move() {
        ArrayList<Stone> remove = new ArrayList<Stone>();
        ArrayList<Stone> addStones = new ArrayList<Stone>();
        for (Stone s : currentStones) {
            if(!s.isAlive()){
                if (s.getType() == AlienType.A){
                    addStones.add(new Stone(s.getX(), s.getY(), s.getAngleMove()+Math.random()*(30-10+1)+10, AlienType.B));
                    addStones.add(new Stone(s.getX(), s.getY(), s.getAngleMove()-Math.random()*(30-10+1)+10, AlienType.B));
                } else if (s.getType() == AlienType.B){
                    addStones.add(new Stone(s.getX(), s.getY(), s.getAngleMove()+Math.random()*(40-20+1)+20, AlienType.C));
                    addStones.add(new Stone(s.getX(), s.getY(), s.getAngleMove()-Math.random()*(40-20+1)+20, AlienType.C));
                }
                remove.add(s);
            } else if(s.getType() == AlienType.A && s.isAlive()){
                s.move(s.getAngleMove(), 0.5);
            } else if(s.getType() == AlienType.B && s.isAlive()){
                s.move(s.getAngleMove(), 0.7);
            } else if(s.getType() == AlienType.C && s.isAlive()){
                s.move(s.getAngleMove(), 0.9);
            }
        }
        currentStones.removeAll(remove);
        currentStones.addAll(addStones);
    }

    public List<Hittable> getHittable() {
        return new ArrayList<Hittable>(currentStones);
    }

    public ArrayList<Stone> getStones() {
        return new ArrayList<Stone>(currentStones);
    }

    public int getStonesRemaining() {
        return currentStones.size();
    }
}
