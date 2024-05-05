package Objects;

import Kernel.Sprite;


public class MovingPlatform extends Sprite {
    private final int movementInterval; // Интервал движения в пикселях
    private final int movementSpeed; // Скорость движения в пикселях
    private int initialX; // Начальная позиция по оси X
    private int destinationX; // Позиция, к которой движется платформа

    // Конструктор для платформы
    public MovingPlatform(String imageName, int x, int y, int width, int height, boolean collidable, int movementInterval, int movementSpeed) {
        super(imageName, width, height, collidable);
        this.xPosition = x;
        this.yPosition = y;
        this.initialX = x;
        this.destinationX = x + movementInterval; // Платформа начнет движение вправо
        this.movementInterval = movementInterval;
        this.movementSpeed = movementSpeed;
    }

    // Метод для обновления позиции платформы
    public void updatePosition() {
        if (xPosition < destinationX) {
            // Платформа движется вправо
            xPosition += movementSpeed;
            // Если достигнута или превышена целевая позиция, меняем направление движения
            if (xPosition >= destinationX) {
                xPosition = destinationX; // Корректируем позицию, чтобы не превысить целевую
                destinationX = initialX; // Платформа начнет движение влево
            }
        } else if (xPosition > destinationX) {
            // Платформа движется влево
            xPosition -= movementSpeed;
            // Если достигнута или превышена целевая позиция, меняем направление движения
            if (xPosition <= destinationX) {
                xPosition = destinationX; // Корректируем позицию, чтобы не превысить целевую
                destinationX = initialX + movementInterval; // Платформа начнет движение вправо
            }
        }
    }
}