package Objects;

import Kernel.Definitions;
import Kernel.Sprite;

import java.util.ArrayList;
import java.util.HashMap;

public class IterBox extends Sprite {
    private final int moveSpeed;

    public IterBox(String imagePath, int x, int y, int width, int height, boolean collidable, int moveSpeed) {
        super(imagePath, x, y, width, height, collidable);
        this.moveSpeed = moveSpeed;
    }
    public HashMap<String, Boolean> IterCollidable(Player player){
        HashMap<String , Boolean> resource = new HashMap<>();
        resource.put("playerRight",player.getX() > getX());
        resource.put("playerLeft",getX() > player.getX());
        return resource;
    }
    public void moveLeft() {
        this.setX(this.getX() - moveSpeed);
    }

    public void moveRight() {
        this.setX(this.getX() + moveSpeed);
    }
}
