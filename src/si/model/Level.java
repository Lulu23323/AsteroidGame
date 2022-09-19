package si.model;


import javafx.geometry.Rectangle2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private Swarm swarm;
    private int currentLevel;
    private SpaceInvadersGame game;

    public Level(int l, SpaceInvadersGame g) {
        game = g;
        currentLevel = l;
        reset();
    }

    public int getStonesRemaining() {
        return swarm.getStonesRemaining();
    }

    public List<Hittable> getHittable() {
        List<Hittable> targets = new ArrayList<Hittable>();
        targets.addAll(swarm.getHittable());
        return targets;
    }

    public List<Stone> move() {
        swarm.move();
        ArrayList<Stone> stones = swarm.getStones();
        return stones;
    }

    public List<Stone> getStones() {
        return swarm.getStones();
    }

    public void reset() {
        swarm = new Swarm(this.currentLevel, game);
    }
}
