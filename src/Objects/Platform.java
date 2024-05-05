package Objects;

import Kernel.Sprite;

import java.awt.*;

public class Platform extends Sprite {
    private int PrevX;
    private int PrevY;
    private int movSize = 20;
    private boolean movNap = false;
    public Platform(String imageName, int x, int y, int width, int height, boolean collidable){
        super(imageName, width, height, collidable);
        xPosition = x;
        yPosition = y;
        PrevX = x;
        PrevY = y;
    }
    public Platform(String imageName, int x, int y, int size, boolean collidable){
        super(imageName, size, size, collidable);
        xPosition = x;
        yPosition = y;
        PrevX = x;
        PrevY = y;
    }
    public Platform(String imageName,int x, int y, int width, int height){
        super(imageName, width, height, true);
        xPosition = x;
        yPosition = y;
        PrevX = x;
        PrevY = y;
    }
    public Platform(String imageName,int x, int y, int size){
        super(imageName, size, size, true);
        xPosition = x;
        yPosition = y;
        PrevX = x;
        PrevY = y;
    }
}
