package Kernel;

import Levels.*;
import Objects.*;
import Objects.Number;
import Kernel.Contrlollers.ModelController;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.reflect.Method;

public class Model {
    private final HashMap<String, Sprite> SpriteObject;
    private final ArrayList<Sprite> number;
    private final ArrayList<Sprite> traps;
    private final ArrayList<Sprite> coins;
    private ArrayList<Sprite> sprites;
    private Player player;
    private Table table;

    public Model(HashMap<String, Integer> resources, ModelController modelController){
        this.SpriteObject = new HashMap<>();
        player = null;

        number = new ArrayList<>();
        traps = new ArrayList<>();
        coins = new ArrayList<>();
        levelLoad(resources, modelController);
    }
    public void levelLoad(HashMap<String, Integer> resources, ModelController modelController){
        sprites = new ArrayList<>();
        Levels levels = new Levels(sprites, modelController, traps, coins, number);
            if (resources.get("NOW_LEVEL") > 0 && resources.get("NOW_LEVEL") < 4) {
                String methodName = String.format("levelsPrint%d", resources.get("NOW_LEVEL"));
                try {
                    Method method = levels.getClass().getMethod(methodName);
                    @SuppressWarnings("unchecked")
                    ArrayList<Sprite> newSprites = (ArrayList<Sprite>) method.invoke(levels);
                    sprites = newSprites;
                    sprites.add(new Number(Definitions.imageNumbers + resources.get("MANY_COIN") + ".png", 50, 0, 50 ,false));
                    sprites.add(new Decor("CoinDecor.gif",0 , 0,50, false));
                    sprites.add(new NumberSkeleton(Definitions.imageNumbers + resources.get("MANY_KILL_SKELETON") + ".png", 200, 0, 50 ,false));
                    sprites.add(new Decor("skeletonFaceDecor.gif",150 , 0,50, false));
                    this.SpriteObject.put("DOOR", new Finish("doorDecor.png", 1400, 30, 100, 100, false));
                    sprites.add(this.SpriteObject.get("DOOR"));
                    this.player = new Player();
                    sprites.add(player);

                    Skeleton.setPlayer(player);
                } catch (Exception ex) {
                    System.err.println("An error occurred: " + ex.getMessage());
                }
            }else {
                sprites = levels.EndGame();
                this.player = new Player();
                this.table = new Table("TablePanel.png", 550, 100, 850, 500,false);
                sprites.add(table);
                sprites.add(player);
            }
    }
    public Table getTable(){
        return this.table;
    }

    public Player getPlayer(){
        return this.player;
    }

    public ArrayList<Sprite> getSprite(){
        return this.sprites;
    }


}
