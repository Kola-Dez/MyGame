package Objects;

import kernel.Definitions;
import kernel.Sprite;

import java.awt.*;

public class FairBol extends Sprite {
    private static int count; // Счетчик
    private final boolean throwRight; // Направление броска
    private boolean expired; // Просрочен ли
    private final double initialX; // Начальная координата X при броске
    private double rotationAngle; // Угол вращения

    public FairBol (double playerX, double playerY, boolean throwRight){
        super("fairbolAmo.gif", 50, 50);
        xPosition = playerX + ((throwRight) ? Definitions.playerPadding - 5 : Definitions.playerPadding);
        yPosition = playerY + 40;
        this.throwRight = throwRight;
        this.expired = false;
        this.initialX = xPosition;
        count++;
    }
    // Обновление состояния
    public void update(Graphics graphics) {
        xPosition += throwRight ? 20 : -20;
        rotationAngle += 0.5;
        super.update(graphics);
        Graphics2D g2d = (Graphics2D) graphics.create();
        // Проверка просроченности по достижении максимального расстояния полета
        g2d.rotate(rotationAngle, xPosition + (double) getWidth() / 2, yPosition + (double) getHeight() / 2);
        super.update(g2d);
        g2d.dispose();
        if (Math.abs(xPosition - initialX) >= Definitions.fairBolExpireDist) {
            removeFairBol();
        }
    }

    // Получение текущего количества
    public static int getCount(){
        return count;
    }
    // Проверка, просрочен ли
    public boolean isExpired(){
        return expired;
    }
    // Удаление
    public void removeFairBol(){
        count--; // Уменьшение счетчика
        expired = true; // Установка состояния просроченности
    }
}
