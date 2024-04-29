import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.awt.image.BufferedImage;

public class Sprite implements Serializable {
    protected double xPosition, yPosition;
    protected int imageWidth, imageHeight;
    protected boolean collidable; // Столкновимый
    protected transient Image image;
    protected boolean intersectsX;
    protected boolean intersectsY;

    protected String currentImagePath;

    public Sprite(String imgName, int width, int height) {
        if (imgName.contains(".gif") && imgName.contains("BG")) {
            setGif(Definitions.imageBgDirectory + imgName);
        } else if (imgName.contains("Enemy") && imgName.contains(".gif")) {
            setGif(Definitions.imageSkeletonDirectory + imgName);
        } else if (imgName.contains(".gif") && imgName.contains("Decor")) {
            setGif(Definitions.imageDecorDirectory + imgName);
        }else if (imgName.contains(".gif") && imgName.contains("Amo")) {
            setGif(Definitions.imageAmoDirectory + imgName);
        } else if (imgName.contains(".png") && imgName.contains("Decor")) {
            setImage(Definitions.imageDecorDirectory + imgName);
        } else {
            setImage(Definitions.imageDirectory + imgName);
        }
        imageWidth = width;
        imageHeight = height;
        xPosition = 0;
        yPosition = 0;
        collidable = false;
        currentImagePath = imgName;
    }
    public Image setTextureRect( int x, int y, int width, int height ) {
        return ((BufferedImage) image).getSubimage(x, y, width, height);
    }

    // Конструктор спрайта с указанием пути к изображению, ширины, высоты и возможности взаимодействия
    public Sprite(String imgName, int width, int height, boolean collidable) {
        this(imgName, width, height); // Вызов предыдущего конструктора
        this.collidable = collidable;
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

    public Sprite setX(double x) {
        xPosition = x;
        return this;
    }

    public Sprite setY(double y) {
        yPosition = y;
        return this;
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
    public boolean collidesWith(Player player) {
        // Получаем координаты верхнего левого угла игрока и элемента
        int playerLeft = (int) player.getX();
        int playerTop = (int) player.getY();
        int elementLeft = (int) xPosition;
        int elementTop = (int) yPosition;

        // Вычисляем координаты нижнего правого угла игрока и элемента
        int playerRight = playerLeft + player.getWidth();
        int playerBottom = playerTop + player.getHeight();
        int elementRight = elementLeft + imageWidth;
        int elementBottom = elementTop + imageHeight;

        // Проверяем пересечение по осям X и Y
        boolean intersectsX = playerRight > elementLeft && elementRight > playerLeft;
        boolean intersectsY = playerBottom > elementTop && elementBottom > playerTop;

        // Если границы пересекаются по обеим осям, значит произошло столкновение
        return intersectsX && intersectsY;
    }

}
