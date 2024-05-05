package Objects;

import Kernel.Definitions;
import Kernel.Sprite;

public class Button extends Sprite {
    private boolean isPress = false;
    private int movePress;
    public Button(String imageName, int x, int y, int width, int height, boolean collidable, int movePress) {
        super(imageName, x, y, width, height, collidable);
        this.movePress = movePress;
    }
    public void press(){
        this.setY(this.getY() + movePress);
        isPress = false;
    }
    public void letGo(){
        this.setY(this.getY() - movePress);
        isPress = true;
    }
    public boolean getIsPress(){
        return this.isPress;
    }
}
