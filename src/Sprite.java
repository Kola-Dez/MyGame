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
        return (this.xPosition < sprite.xPosition + imageWidth && this.xPosition + imageWidth > sprite.xPosition
                && this.yPosition < sprite.yPosition + imageHeight - 70
                && this.yPosition + imageHeight - 20 > sprite.yPosition);
    }

    // Метод для проверки столкновения с игроком
    public boolean collidesWith(Player player, int tmp) {
        return player.getX() < xPosition + imageWidth -
                tmp && player.getX() +
                player.getWidth() - tmp > xPosition
                && player.getY() < yPosition + imageHeight -
                tmp && player.getY() +
                player.getHeight() - tmp > yPosition;
    }
}
