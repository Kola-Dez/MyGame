package Objects;

import kernel.Sprite;

public class Trap extends Sprite {
    public Trap(String imageName, int x, int y, int width, int height){
        super(imageName, width, height);
        xPosition = x;
        yPosition = y;
    }
}
