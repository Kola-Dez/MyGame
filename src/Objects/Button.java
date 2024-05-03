package Objects;

import kernel.Sprite;

public class Button extends Sprite {
    private boolean isPress = false;
    private int move;
    public Button(String imageName, int x, int y, int width, int height, boolean collidable) {
        super(imageName, x, y, width, height, collidable);
    }
    public void press(){
        this.setY(this.getY() + move);
    }
    public void letGo(){
        this.setY(this.getY() - move);
    }
    public boolean getIsPress(){
        return this.isPress;
    }
    public int getMove(){
        return this.move;
    }
    public void setMove(int move){
        this.move = move;
    }
    public void setIsPress(boolean isPress){
        this.isPress = isPress;
    }
}
