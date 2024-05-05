package Objects;

import Kernel.Sprite;

public class BG extends Sprite {
    public BG(String imageName, int x, int y, int width, int height, boolean collidable){
        super(imageName, width, height, collidable);
        xPosition = x;
        yPosition = y;
    }
}
