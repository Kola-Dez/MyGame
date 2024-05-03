package Objects;

import kernel.Sprite;

public class Number extends Sprite {
    public Number(String imageName, int x, int y, int size, boolean collidable){
        super(imageName, size, size, collidable);
        xPosition = x;
        yPosition = y;
    }
}
