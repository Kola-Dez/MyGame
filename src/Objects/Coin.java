package Objects;

import Kernel.Sprite;

public class Coin extends Sprite {
    public Coin(String imageName, int x, int y, int size, boolean collidable){
        super(imageName, size, size, collidable);
        xPosition = x;
        yPosition = y;
    }
}
