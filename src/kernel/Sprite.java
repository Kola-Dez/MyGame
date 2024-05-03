package kernel;
import Objects.Player;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.awt.image.BufferedImage;

public abstract class Sprite implements Serializable {
    protected double xPosition;
    protected double yPosition;
    protected int imageWidth, imageHeight;
    protected boolean collidable; // Столкновимый
    protected transient Image image;
    protected JLabel textLabel;
    protected String currentImagePath;

    public Sprite(String imgName, int width, int height) {
        this.currentImagePath = imgName;
        this.imageWidth = width;
        this.imageHeight = height;
        this.xPosition = 0;
        this.yPosition = 0;
        this.collidable = false;

        nameScan(imgName);
    }
    // Конструктор спрайта с указанием пути к изображению, ширины, высоты и возможности взаимодействия
    public Sprite(String imgName, int width, int height, boolean collidable) {
        this(imgName, width, height); // Вызов предыдущего конструктора
        this.collidable = collidable;
    }

    public Sprite(String imgName, int x, int y, int width, int height, boolean collidable) {
        this(imgName, width, height);
        this.collidable = collidable;
        this.xPosition = x;
        this.yPosition = y;
    }
    public void nameScan(String imgName){
        if (imgName.contains(".gif") && imgName.contains("BG")) {
            setGif(Definitions.imageBgDirectory + imgName);
        } else if (imgName.contains("Enemy") && imgName.contains(".gif")) {
            setGif(Definitions.imageSkeletonDirectory + imgName);
        } else if (imgName.contains(".gif") && imgName.contains("Decor")) {
            setGif(Definitions.imageDecorDirectory + imgName);
        } else if (imgName.contains(".gif") && imgName.contains("Amo")) {
            setGif(Definitions.imageAmoDirectory + imgName);
        } else if (imgName.contains(".png") && imgName.contains("Decor")) {
            setImage(Definitions.imageDecorDirectory + imgName);
        } else if (imgName.contains("TablePanel") && imgName.contains(".png")){
            setImage(Definitions.imageDecorDirectory + imgName);
        } else if (imgName.contains("Button") && imgName.contains(".png")){
            setImage(Definitions.imageButtonDirectory + imgName);
        } else {
            setImage(Definitions.imageDirectory + imgName);
        }
    }
    public Image setTextureRect( int x, int y, int width, int height ) {
        return ((BufferedImage) image).getSubimage(x, y, width, height);
    }
    public double getX() {
        return xPosition;
    }

    public double getY() {
        return yPosition;
    }

    public int getWidth() {
        return imageWidth;
    }

    public int getHeight() {
        return imageHeight;
    }
    public JLabel getTextLabel(){
        return textLabel;
    }

    public Sprite setX(double x) {
        xPosition = x;
        return this;
    }

    public Sprite setY(double y) {
        yPosition = y;
        return this;
    }
    public void setCollidable(boolean collidable) {
        this.collidable = collidable;
    }

    public boolean isCollidable() {
        return collidable;
    }

    // Метод для установки изображения по пути к файлу
    public void setImage(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException ioe) {
            System.err.println("Невозможно загрузить файл изображения. " + imagePath);
        }
    }

    // Метод для установки изображения GIF по пути к файлу
    public void setGif(String imagePath) {
        image = new ImageIcon(imagePath).getImage();
    }

    // Метод для обновления изображения на экране
    public void update(Graphics graphics) {
        // Проверяем, изменились ли координаты или размеры спрайта
        graphics.drawImage(image, (int) xPosition, (int) yPosition, imageWidth, imageHeight, null);
    }
    public void updateImage(String imageName) {
        nameScan(imageName);
    }

    // Метод для проверки перекрытия спрайта с другим спрайтом
    public boolean overlaps(Sprite sprite) {
        // Вычисляем координаты верхнего левого и нижнего правого углов текущего спрайта и спрайта sprite
        int thisLeft = (int) this.xPosition;
        int thisTop = (int) this.yPosition;
        int thisRight = thisLeft + this.imageWidth;
        int thisBottom = thisTop + this.imageHeight;

        int spriteLeft = (int) sprite.xPosition;
        int spriteTop = (int) sprite.yPosition;
        int spriteRight = spriteLeft + sprite.imageWidth;
        int spriteBottom = spriteTop + sprite.imageHeight;

        // Проверяем перекрытие по осям X и Y
        boolean overlapsX = thisRight > spriteLeft && spriteRight > thisLeft;
        boolean overlapsY = thisBottom > spriteTop && spriteBottom > thisTop;

        // Если границы пересекаются по обеим осям, значит происходит перекрытие
        return overlapsX && overlapsY;
    }


    // Метод для проверки столкновения с игроком
    public boolean collidesSize(Player player, int padding) {
        // Получаем координаты верхнего левого угла игрока и элемента
        int playerLeft = (int) player.getX();
        int playerTop = (int) player.getY();
        int elementLeft = (int) xPosition;
        int elementTop = (int) yPosition;

        // Вычисляем координаты нижнего правого угла игрока и элемента
        int playerRight = playerLeft + player.getWidth() - padding;
        int playerBottom = playerTop + player.getHeight() - padding;
        int elementRight = elementLeft + imageWidth - padding;
        int elementBottom = elementTop + imageHeight - padding;

        // Проверяем пересечение по осям X и Y
        boolean intersectsX = playerRight > elementLeft && elementRight > playerLeft;
        boolean intersectsY = playerBottom > elementTop && elementBottom > playerTop;

        // Если границы пересекаются по обеим осям, значит произошло столкновение
        return intersectsX && intersectsY;
    }
    public boolean collidesWidthAndHeight(Player player, int paddingWidth, int paddingHeight) {
        // Получаем координаты верхнего левого угла игрока и элемента
        int playerLeft = (int) player.getX();
        int playerTop = (int) player.getY();
        int elementLeft = (int) xPosition;
        int elementTop = (int) yPosition;

        // Вычисляем координаты нижнего правого угла игрока и элемента
        int playerRight = playerLeft + player.getWidth() - paddingWidth;
        int playerBottom = playerTop + player.getHeight() - paddingHeight;
        int elementRight = elementLeft + imageWidth - paddingWidth;
        int elementBottom = elementTop + imageHeight - paddingHeight;

        // Проверяем пересечение по осям X и Y
        boolean intersectsX = playerRight > elementLeft && elementRight > playerLeft;
        boolean intersectsY = playerBottom > elementTop && elementBottom > playerTop;

        // Если границы пересекаются по обеим осям, значит произошло столкновение
        return intersectsX && intersectsY;
    }
}
