package si.model;

import si.display.PlayerListener;
import ucd.comp2011j.engine.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SpaceInvadersGame implements Game {
    public static final int SCREEN_WIDTH = 768;
    public static final int SCREEN_HEIGHT = 512;
    public static final int BUNKER_TOP = 350;
    private static final Rectangle SCREEN_BOUNDS = new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

    private int playerLives;
    private int playerScore;
    private boolean pause = true;
    private ArrayList<Bullet> playerBullets;
    private ArrayList<Stone> stones;
    private ArrayList<Bonus> bonuses;
    private ArrayList<Bullet> enemyBullets;
    private ArrayList<Hittable> targets;
    private PlayerListener listener;
    private Player player;
    private EnemyShip enemyShip;
    private ArrayList<Level> level;
    private int currentLevel = 0;
    private int count = 1;
    private int shield;
    private int blaster;
    public Bonus bonus;


    public SpaceInvadersGame(PlayerListener listener) {
        this.listener = listener;
        startNewGame();
    }

    @Override
    public int getPlayerScore() {
        return playerScore;
    }

    @Override
    public int getLives() {
        return playerLives;
    }

    public void checkForPause() {
        if (listener.hasPressedPause()) {
            pause = !pause;
            listener.resetPause();
        }
    }

    public void moveEnemy(){
        stones.addAll(level.get(currentLevel).move());
    }

    @Override
    public void updateGame() {
        if (!isPaused()) {
            int i = playerScore/10000;
            if(i==count){
                playerLives++;
                count++;
            }
            if(!bonus.isAlive() && bonus.getAliveTime()<=0){
                player.changeBonusType(0);
            }
            targets.clear();
            targets.addAll(level.get(currentLevel).getHittable());
            targets.add(player);
            hitBonus();
            addEnemyShip();
            playerBullets();
            enemyBullets();
            stonesBomb();
            stones.addAll(level.get(currentLevel).move());
            controlPlayer();
            movePlayer();
            moveEnemyShip();
        }
    }

    private void controlPlayer() {
        if(bonus.isAlive() && bonus.getType()==3 && bonus.getAliveTime()>0){
            if (listener.hasPressedFire()) {
                Bullet b = player.fireDouble();
                if (b != null) {
                    playerBullets.add(b);
                    if(blaster == 0){
                        listener.resetFire();
                        blaster = 2;
                    }
                    blaster--;
                }
            }
            bonus.use();
        }else{
            if (listener.hasPressedFire()) {
                Bullet b = player.fire();
                if (b != null) {
                    playerBullets.add(b);
                    listener.resetFire();
                }
            }
        }
        if (listener.isPressingLeft()) {
            player.turn(3, 0);
        } else if (listener.isPressingRight()) {
            player.turn(0, 3);
        }

    }

    public void dropBonus(int x, int y){
        int rd = (int) (Math.random() * (3 - 0 + 1) + 0);
        if(rd == 1){
            double type = Math.random() * (3 - 1 + 1) + 1;
            Bonus b = new Bonus((int)type, x, y);
            bonuses.add(b);
        }
    }

    public void hitBonus() {
        List<Bonus> remove = new ArrayList<Bonus>();
        for (int i = 0; i < bonuses.size(); i++) {
            if (bonuses.get(i).isAlive() && bonuses.get(i).getHitBox().intersects(player.getHitBox())) {
                bonus = bonuses.get(i);
                bonuses.get(i).destroy();
                bonus.activate();
                player.changeBonusType(bonus.getType());
                bonuses.remove(i);
                if(bonus.getType() == 1){
                    shield = 1;
                }
            }
        }
        bonuses.removeAll(remove);
        if (bonus.isAlive() && bonus.getType() == 1 && bonus.getAliveTime() > 0) {
            targets.remove(player);
            bonus.use();
        } else if (!bonus.isAlive() && bonus.getType() == 1 && shield == 1) {
            targets.add(player);
            shield = 0;
        }
    }

    private void movePlayer() {
        if(bonus.isAlive() && bonus.getType()==2 && bonus.getAliveTime()>0){
            bonus.use();
        }
        if (listener.hasPressedBackwards()) {
            double tmpX = Math.random()*(SCREEN_WIDTH-0+1)+0;
            double tmpY = Math.random()*(SCREEN_HEIGHT-0+1)+0;
            player.changeLocation(tmpX, tmpY);
            while(!player.isAlive()){
                tmpX = Math.random()*(SCREEN_WIDTH-0+1)+0;
                tmpY = Math.random()*(SCREEN_HEIGHT-0+1)+0;
                player.changeLocation(tmpX, tmpY);
            }
            listener.resetBackwards();
        }
        if (listener.hasPressedForwards()) {
            if(bonus.isAlive() && bonus.getType()==2 && bonus.getAliveTime()>0){
                player.acceleration(1.4);
            } else{
                player.acceleration(0.5);
            }
            listener.resetUp();
        }
        player.move(player.getSpeedX(),player.getSpeedY());
    }

    private void addEnemyShip(){
        if(enemyShip != null){
            if(enemyShip.isAlive()){
                targets.add(enemyShip);
            }else{
                enemyBullets.clear();
                int rd = (int) (Math.random()*(300-0+1)+0);
                if(rd == 1){
                    enemyShip = new EnemyShip();
                }

            }
        }else {
            int rd = (int) (Math.random() * (100 - 0 + 1) + 0);
            if (rd == 1) {
                enemyShip = new EnemyShip();
                targets.add(enemyShip);
            }
        }
    }

    private void moveEnemyShip(){
        if(enemyShip!=null){
            if(enemyShip.isAlive()){
                for (Stone st : stones) {
                    Stone s = new Stone(st.willMoveX(), st.willMoveY(), st.getAngleMove(), st.getType());
                    EnemyShip e = new EnemyShip(enemyShip.willMoveX(), enemyShip.willMoveY(), enemyShip.getAngleMove());
                    if(s.getHitBox().intersects(e.getHitBox().getBounds())){
                        enemyShip = new EnemyShip(enemyShip.getX(), enemyShip.getY(), enemyShip.getAngleMove()+90);
                    }
                }
                enemyShip.move(enemyShip.getAngleMove());
                Bullet b = enemyShip.fire(player.willMoveX(), player.willMoveY());
                if (b != null) {
                    enemyBullets.add(b);
                }
            }
        }
    }

    private void playerBullets() {
        List<Bullet> remove = new ArrayList<Bullet>();
        for (int i = 0; i < playerBullets.size(); i++) {
            if (playerBullets.get(i).isAlive() && playerBullets.get(i).getHitBox().intersects(SCREEN_BOUNDS)) {
                playerBullets.get(i).move();
                for (Hittable t : targets) {
                    if (t != playerBullets.get(i) && !t.isPlayer()) {
                        if (t.isHit(playerBullets.get(i))) {
                            if(t.isStone()){
                                dropBonus(t.getX(),t.getY());
                            }
                            playerScore += t.getPoints();
                            playerBullets.get(i).destroy();
                        }
                    }
                }
            } else {
                remove.add(playerBullets.get(i));
            }
        }
        playerBullets.removeAll(remove);
    }

    private void enemyBullets() {
        if(enemyShip != null){
            if(enemyShip.isAlive()) {
                ArrayList<Bullet> remove = new ArrayList<Bullet>();
                for (int i = 0; i < enemyBullets.size(); i++) {
                    Bullet e = enemyBullets.get(i);
                    if (e.isAlive()) {
                        e.move();
                        for (Hittable t : targets) {
                            if (t != e && !t.isEnemyShip()) {
                                if (t.isHit(e)) {
                                    if (t.isPlayer()) {
                                        playerLives--;
                                        pause = true;
                                    }
                                    e.destroy();
                                }
                            }
                        }
                    } else {
                        remove.add(e);
                    }
                }
                enemyBullets.removeAll(remove);
            }
        }
    }

    private void stonesBomb() {
        ArrayList<Stone> remove = new ArrayList<Stone>();
            for (int i = 0; i < stones.size(); i++) {
                Stone e = stones.get(i);
                if (e.isAlive() /*&& e.getHitBox().intersects(SCREEN_BOUNDS)*/) {
                    e.move(0,0);
                    for (Hittable t : targets) {
                        if (t != e) {
                            if (t.isHit2(e)) {
                                if (t.isPlayer()) {
                                    playerLives--;
                                    pause = true;
                                }
                                e.destroy();
                            }
                        }
                    }
                } else {
                    remove.add(e);
                }
            }
        stones.removeAll(remove);
    }


    @Override
    public boolean isPaused() {
        return pause;
    }

    @Override
    public void startNewGame() {
        currentLevel = 0;
        targets = new ArrayList<Hittable>();
        playerLives = 3;
        playerScore = 0;
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
        stones = new ArrayList<Stone>();
        player = new Player();
        bonus = new Bonus();
        bonuses = new ArrayList<Bonus>();
        level = new ArrayList<Level>();
        level.add(currentLevel, new Level(currentLevel, this));
    }

    @Override
    public boolean isLevelFinished() {
        int noShips = level.get(currentLevel).getStonesRemaining();
        return noShips == 0 && !enemyShip.isAlive();
    }

    @Override
    public int getTargetFPS() {
        return 0;
    }

    @Override
    public boolean isPlayerAlive() {
        return player.isAlive();
    }

    @Override
    public void resetDestroyedPlayer() {
        player.resetDestroyed();
        playerBullets = new ArrayList<Bullet>();
        stones = new ArrayList<Stone>();
    }

    @Override
    public void moveToNextLevel() {
        pause = true;
        currentLevel++;
        level.add(currentLevel, new Level(currentLevel, this));
        player.resetDestroyed();
        player.changeBonusType(0);
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
        stones = new ArrayList<Stone>();
        bonus = new Bonus();
        bonuses = new ArrayList<Bonus>();
    }

    @Override
    public boolean isGameOver() {
        return !(playerLives > 0);
    }

    @Override
    public int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    @Override
    public int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public Player getShip() {
        return player;
    }

    public EnemyShip getEnemyShip(){
        return enemyShip;
    }

    public List<Bullet> getBullets() {
        ArrayList<Bullet> bullets = new ArrayList<Bullet>();
        bullets.addAll(playerBullets);
        bullets.addAll(enemyBullets);
        return bullets;
    }

    public List<Stone> getStones() {
        return level.get(currentLevel).getStones();
    }

    public ArrayList<Bonus> getBonuses(){
        return bonuses;
    }


}
