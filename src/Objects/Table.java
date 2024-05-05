package Objects;

import Kernel.Sprite;

public class Table extends Sprite {
    public Table(String imageName, int x, int y, int width, int height, boolean collidable){
        super(imageName, width, height, collidable);
        xPosition = x;
        yPosition = y;
    }
}
