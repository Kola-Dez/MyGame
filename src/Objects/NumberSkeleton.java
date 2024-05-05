package Objects;

import Kernel.Sprite;

public class NumberSkeleton extends Sprite {
    public NumberSkeleton(String imageName, int x, int y, int size, boolean collidable){
        super(imageName, size, size, collidable);
        xPosition = x;
        yPosition = y;
    }
}
