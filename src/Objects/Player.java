package Objects;

import kernel.Definitions;
import kernel.Sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Sprite {
    private double prevX, prevY;// предыдущий X & Y
    private static final int accel = 3;// Ускорение игрока
    private int velocity;// скорость
    private Definitions.VelocityState velocityState;// Состояние движения игрока {LEFT,RIGHT,STILL} STILL = ВСЕ ЕЩЕ
    private boolean inAir; // Находится ли игрок в воздухе
    private boolean hasDoubleJump; // Имеет ли игрок двойной прыжок
    private boolean facingRight; //Обращен ли игрок вправо
    private boolean dead;
    private transient ArrayList<Image> player_sprites;// Изображения игрока

    public Player(){
        super("player0.png", Definitions.playerDimension, Definitions.playerDimension);
        velocity = 0; // Инициализация начальной скорости
        inAir = true; // Игрок изначально в воздухе
        hasDoubleJump = true; // Игрок изначально может совершить двойной прыжок
        facingRight = true; // Игрок изначально обращен вправо
        dead = false; // Игрок изначально жив
        xPosition = 90; // Установка начальной координаты X
        yPosition = 530; // Установка начальной координаты Y
        player_sprites = new ArrayList<>(); // Инициализация списка изображений игрока
        loadImages(); // Загрузка изображений
    }
    public void loadImages(){
        player_sprites = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            try {
                player_sprites.add(ImageIO.read(new File(String.format(Definitions.imageDirectory + "player%d.png", i))));
            }catch (IOException ioe){
                System.err.println("Невозможно загрузить файл изображения.");
            }
        }
    }
//        public void loadImages(){
//            player_sprites = new ArrayList<>();
//            for (int i = 0; i < 6; i++) {
//                Image bufferedImage = setTextureRect(i*200, 385, 190, 190);
//                player_sprites.add(bufferedImage);
//            }
//        }
    public int getVelocity(){
        return this.velocity;
    }
    public void setVelocity(int vel){
        velocity = vel;
    }
    public boolean isAirborne(){
        return inAir;
    }
    public void setDoubleJump(boolean doubleJump){
        hasDoubleJump = doubleJump;
    }
    public boolean isFacingRight(){
        return facingRight;
    }
    // Установка состояния нахождения в воздухе
    public void setAirborne(boolean inAir){
        this.inAir = inAir;
        if(inAir){
            if (velocityState == Definitions.VelocityState.LEFT){
                image = (player_sprites.get(5));
                facingRight = false;
            }else if (velocityState == Definitions.VelocityState.RIGHT){
                image = player_sprites.get(2);
                facingRight = true;
            }
        }else{
            if (velocityState == Definitions.VelocityState.LEFT){
                image = player_sprites.get(4);
                facingRight = false;
            }else if (velocityState == Definitions.VelocityState.RIGHT){
                image = player_sprites.get(1);
                facingRight = true;
            }
        }
    }
    // Метод для выполнения прыжка
    public void jump(){
        if(hasDoubleJump){
            if(inAir){
                hasDoubleJump = false;
            }
            velocity = -32;
            inAir = true;
            image = facingRight ? player_sprites.get(2) : player_sprites.get(5);
        }
    }
    // Установка направления движения игрока
    public void setDirection(Definitions.VelocityState state){
        if(state == Definitions.VelocityState.LEFT){
            image = inAir ? player_sprites.get(5) : player_sprites.get(4);
            facingRight = false;
        }else if (state == Definitions.VelocityState.RIGHT){
            image = inAir ? player_sprites.get(2) : player_sprites.get(1);
            facingRight = true;
        }else if(state == Definitions.VelocityState.STILL) {
            if (velocityState == Definitions.VelocityState.LEFT){
                image = player_sprites.get(3);
                facingRight = false;
            } else if (velocityState == Definitions.VelocityState.RIGHT) {
                image = player_sprites.get(0);
                facingRight = true;
            }
        }
        velocityState = state;
    }
    // Установка изображения в стоячую позу, обращенную вправо
    public void setStandingRightImage(){
        image = player_sprites.get(0);
    }
    // Установка изображения в стоячую позу, обращенную влево
    public void setStandingLeftImage(){
        image = player_sprites.get(3);
    }
    // Установка состояния "мертв"
    public void kill() {
        dead = true;
    }

    // Проверка, мертв ли игрок
    public boolean isDead() {
        return dead;
    }
    // Получение текущего состояния движения игрока
    public Definitions.VelocityState getVelocityState(){
        return velocityState;
    }
    // Получение предыдущей координаты X
    public double getPrevX(){
        return prevX;
    }
    // Получение предыдущей координаты Y
    public double getPrevY(){
        return prevY;
    }
    // Обновление информации о игроке (позиция, скорость и т.д.)
    public void updateInfo(){
        prevX = xPosition;
        prevY = yPosition;
        // Обновление информации о игроке на основе физики мира
        if(velocityState == Definitions.VelocityState.LEFT){
            xPosition -= 8;
        } else if (velocityState == Definitions.VelocityState.RIGHT) {
            xPosition += 8;
        }
        yPosition += velocity;
        velocity += accel;
    }
    // Проверка пересечения с другим спрайтом
    public boolean overlaps(Sprite sprite){
        if (xPosition + imageWidth - Definitions.playerPadding < sprite.getX()){
            return false;
        }
        if (xPosition + Definitions.playerPadding > sprite.getX() + sprite.getWidth()){
            return false;
        }
        if(yPosition + imageHeight < sprite.getY()){
            return false;
        }
        return !(yPosition + Definitions.playerPaddingTop > sprite.getY() + sprite.getHeight());
    }
}
