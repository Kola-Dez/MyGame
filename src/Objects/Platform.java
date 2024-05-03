package Objects;

import kernel.Sprite;

public class Platform extends Sprite {
    public Platform(String imageName, int x, int y, int width, int height, boolean collidable){
        super(imageName, width, height, collidable);
        xPosition = x;
        yPosition = y;
    }
    public Platform(String imageName, int x, int y, int size, boolean collidable){
        super(imageName, size, size, collidable);
        xPosition = x;
        yPosition = y;
    }
    public Platform(String imageName,int x, int y, int width, int height){
        super(imageName, width, height, true);
        xPosition = x;
        yPosition = y;
    }
    public Platform(String imageName,int x, int y, int size){
        super(imageName, size, size, true);
        xPosition = x;
        yPosition = y;
    }
}
