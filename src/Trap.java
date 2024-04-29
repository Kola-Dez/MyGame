public class Trap extends Sprite{
    public Trap(String imageName, int x, int y, int width, int height){
        super(imageName, width, height);
        xPosition = x;
        yPosition = y;
    }

    public boolean collidesTrap(Player player, int padding) {
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

}
