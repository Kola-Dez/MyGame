import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Skeleton extends Sprite {
    public static int defaultImageWidth = 63;
    public static int defaultImageHeight = 95;
    private static Player player;
    private static Model model;
    private boolean dead; // Признак смерти скелета
    private boolean attackingLeft;
    private boolean attackingRight;
    private int framesSinceDeath; // Счетчик кадров с момента смерти скелета
    private int framesSinceAttack; // Счетчик кадров с момента начала атаки
    private boolean walkingRight; // Направление движения скелета
    private final double maxPos; // Максимальная позиция для блуждания скелета
    private final double minPos; // Минимальная позиция для блуждания скелета
    private double xPosBeforeAttack; // Координата X перед началом атаки
    private double yPosBeforeAttack; // Координата Y перед началом атаки
    private final static ArrayList<Image> dying_right_sequence = new ArrayList<>(); // Последовательность изображений для анимации смерти вправо
    private final static ArrayList<Image> dying_left_sequence = new ArrayList<>(); // Последовательность изображений для анимации смерти влево
    private final static ArrayList<Image> attacking_left_sequence = new ArrayList<>(); // Последовательность изображений для анимации атаки влево
    private final static ArrayList<Image> attacking_right_sequence = new ArrayList<>(); // Последовательность изображений для анимации атаки вправо

    // Статический блок инициализации для загрузки последовательностей изображений
    static {
        // Загрузка изображений асинхронно
        loadImagesAsync();
    }

    private static void loadImagesAsync() {
        Runnable task = () -> {
            for (int i = 0; i < 15; i++) {
                dying_right_sequence.add(new ImageIcon(Definitions.imageSkeletonDirectory + "dying_right/%d.png".formatted(i)).getImage());
                dying_left_sequence.add(new ImageIcon(Definitions.imageSkeletonDirectory + "dying_left/%d.png".formatted(i)).getImage());
            }
            for (int i = 0; i < 18; i++) {
                attacking_right_sequence.add(new ImageIcon(Definitions.imageSkeletonDirectory + "attacking_right/%d.png".formatted(i)).getImage());
                attacking_left_sequence.add(new ImageIcon(Definitions.imageSkeletonDirectory + "attacking_left/%d.png".formatted(i)).getImage());
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    // Статический метод для установки ссылки на игрока
    public static void setPlayer(Player p) {
        player = p;
    }

    public Skeleton(int x, int y, int roamDist, Model model) {//roamDist = бродитьРасстояние
        super(Definitions.imageSkeletonDirectory + "Enemy_walking_right.gif", defaultImageWidth, defaultImageHeight);
        xPosition = x;
        yPosition = y;
        maxPos = xPosition + roamDist;
        minPos = xPosition - roamDist;
        dead = false;
        attackingLeft = false;
        attackingRight = false;
        walkingRight = true;
        framesSinceDeath = 0;
        Skeleton.model = model;
    }

    // Метод для проверки, находится ли скелет в состоянии атаки
    public boolean isAttacking(){
        return attackingLeft || attackingRight;
    }
    // Метод для сброса состояния после атаки
    private void resetFromAttack() {
        imageWidth = defaultImageWidth;
        imageHeight = defaultImageHeight;
        xPosition = xPosBeforeAttack;
        yPosition = yPosBeforeAttack;

        attackingRight = false;
        attackingLeft = false;
        if(walkingRight) {
            image = new ImageIcon(Definitions.imageSkeletonDirectory + "Enemy_walking_right.gif").getImage();
        }else{
            image = new ImageIcon(Definitions.imageSkeletonDirectory + "Enemy_walking_left.gif").getImage();
        }
    }

    // Метод для убийства скелета
    public void kill() {
        if (isAttacking()) {
            resetFromAttack();
        }
        image = walkingRight ? new ImageIcon(Definitions.imageSkeletonDirectory + "dying_right/0.png").getImage() : new ImageIcon(Definitions.imageSkeletonDirectory + "dying_left/0.png").getImage();
        dead = true;
        imageWidth += 20;
        if (walkingRight) {
            xPosition -= 20;
        }
        if (model != null) {
            model.setKillSkeleton();
        }
    }
    // Метод для проверки, мертв ли скелет
    public boolean isDead(){
        return dead;
    }
    // Метод для получения количества кадров с момента смерти скелета
    public int getFramesSinceDeath(){
        return framesSinceDeath;
    }
    // Метод для инкрементации счетчика кадров с момента смерти
    public void incrementFramesSinceDeath(){
        image = walkingRight ? dying_right_sequence.get(framesSinceDeath / 3) : dying_left_sequence.get(framesSinceDeath / 3);
        framesSinceDeath++;
    }
    // Метод для проверки, находится ли игрок на том же уровне, что и скелет
    private boolean playerOnSameLevel(){
        // Голова игрока находится выше головы скелета на менее чем 60 пикселей
        return player.getY() + Definitions.playerPaddingTop - yPosition > 0 && player.getY() - yPosition < 60;
    }
    // Метод для проверки, находится ли игрок в дистанции атаки
    private boolean playerInAttackDistance(){
        return player != null &&
                playerOnSameLevel() &&
                (Math.abs(player.getX() + player.getWidth() - Definitions.playerPadding - xPosition)
                        <= Definitions.skeletonAttackDist ||
                Math.abs(player.getX() + Definitions.playerPadding - (xPosition + imageWidth)) <=
                        Definitions.skeletonAttackDist);
    }
    // Метод для поворота влево
    private void turnLeft(){
        walkingRight = false;
        image = new ImageIcon(Definitions.imageSkeletonDirectory + "Enemy_walking_left.gif").getImage();
    }
    // Метод для поворота вправо
    private void turnRight() {
        walkingRight = true;
        image = new ImageIcon(Definitions.imageSkeletonDirectory + "Enemy_walking_right.gif").getImage();
    }
    // Метод для движения вправо
    private void walkRight() {
        xPosition += 2;
    }
    // Метод для движения влево
    private void walkLeft() {
        xPosition -= 2;
    }
    // Метод для атаки влево
    public void attackLeft(){
        xPosBeforeAttack = xPosition;
        yPosBeforeAttack = yPosition;

        // Атака влево
        attackingLeft = true;
        imageWidth = 122;
        imageHeight = 115;

        xPosition -= 50;
        yPosition -= 20;
    }
    // Метод для атаки вправо
    private void attackRight() {
        xPosBeforeAttack = xPosition;
        yPosBeforeAttack = yPosition;

        // Атака вправо
        attackingRight = true;
        imageWidth = 122;
        imageHeight = 115;

        xPosition -= 5;
        yPosition -= 20;
    }
    // Метод для обновления состояния скелета
    public void update(Graphics graphics){
        if(!dead){
            if (isAttacking()){
                if (framesSinceAttack == Definitions.skeletonAttackLength){
                    resetFromAttack(); // Сброс из состояния атаки после завершения
                    update(graphics); // Рекурсивный вызов для обновления состояния после сброса атаки
                    return;
                }else {
                    image = attackingLeft ? attacking_left_sequence.get(framesSinceAttack / 3) : attacking_right_sequence.get(framesSinceAttack / 3);
                    framesSinceAttack++;
                }
                // Проверка столкновения с игроком при завершении атаки
                if (framesSinceAttack == 23 && overlaps(player)) {
                    player.kill();
                }
            }else if (playerInAttackDistance()){
                if (player.getX() <= xPosition){
                    attackLeft();
                }else{
                    attackRight();
                }
                framesSinceAttack = 0;
            } else if (walkingRight && xPosition < maxPos) {
                walkRight();
            } else if (walkingRight && xPosition >= maxPos) {
                turnLeft();
                walkLeft();
            } else if (!walkingRight && xPosition > minPos) {
                walkLeft();
            } else if (!walkingRight && xPosition <= minPos) {
                turnRight();
                walkRight();
            }
        }
        super.update(graphics);
    }
}
