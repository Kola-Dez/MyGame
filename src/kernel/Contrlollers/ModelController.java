package kernel.Contrlollers;

import Objects.*;
import kernel.Definitions;
import kernel.Model;
import kernel.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ModelController {
    private final HashMap<String, Integer> resources;
    private final ArrayList<Sprite> sprites;
    private boolean isKillSkeleton;
    private boolean gameIsOver;
    private boolean isTakeCoin;
    private final Player player;
    private boolean isWon;
    private boolean isRestart;
    private final Model model;
    public ModelController(HashMap<String, Integer> resources){
        isKillSkeleton = false;
        isTakeCoin = false;
        gameIsOver = false;
        isRestart = false;
        isWon = false;
        this.resources = resources;
        model = new Model(resources, this);

        this.player = model.getPlayer();
        this.sprites = model.getSprite();
    }
    public void update(Graphics graphics) {
            new MoveController(graphics, this);
    }

    public void setTakeCoin(boolean isTakeCoin){
        this.isTakeCoin = isTakeCoin;
    }
    public void setIsTakeCoin(boolean isTakeCoin){
        this.isTakeCoin = isTakeCoin;
    }
    public boolean getIsTakeCoin(){
        return this.isTakeCoin;
    }
    public void setIsKillSkeleton(boolean isKillSkeleton){
        this.isKillSkeleton = isKillSkeleton;
    }
    public boolean getIsKillSkeleton(){
        return this.isKillSkeleton;
    }

    public void setGameIsOver(boolean gameIsOver){
        this.gameIsOver = gameIsOver;
    }
    public void setWon(boolean isWon){
        this.isWon = isWon;
    }

    public boolean playerWon() {
        return isWon;
    }

    public Player checksPlayer(){
        return player;
    }
    public HashMap<String, Integer> getResources(){
        return this.resources;
    }

    // Обработка прыжка игрока
    public void jumpPlayer() {
        player.jump();
    }
    // Установка направления движения игрока
    public void setPlayerDirection(Definitions.VelocityState state){
        player.setDirection(state);
    }
    public Model getModel(){
        return this.model;
    }

    // Проверка, завершена ли игра
    public boolean gameIsOver() {
        return gameIsOver;
    }
    public Player getPlayer(){
        return this.player;
    }
    public ArrayList<Sprite> getSprites(){
        return this.sprites;
    }
    public void throwFairBol() {
        if (FairBol.getCount() < 3) {
            sprites.add(new FairBol(player.getX(), player.getY(), player.isFacingRight()));
        }
    }
    public int getManyKillSkeleton(){
        return this.resources.get("MANY_KILL_SKELETON");
    }
    public void setKillSkeleton(){
        this.resources.put("MANY_KILL_SKELETON", this.resources.get("MANY_KILL_SKELETON") + (this.resources.get("MANY_KILL_SKELETON") >= 10 ? 0 : 1));
        this.isKillSkeleton = true;
    }
    public int getManyCoin(){
        return this.resources.get("MANY_COIN");
    }
    public int getManyCoinNuw(){
        return this.resources.get("COINS");
    }
    public boolean getIsRestart(){
        return this.isRestart;
    }
    public void setIsRestart(boolean is){
        this.isRestart = is;
    }
}
