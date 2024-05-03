package kernel.Contrlollers;

import Objects.*;
import Objects.Button;
import Objects.Number;
import kernel.Definitions;
import kernel.Model;
import kernel.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MoveController {
    private final ModelController modelController;
    private final ArrayList<Sprite> sprites;
    private final HashMap<String, Integer> resources;
    private final Player player;
    private final Model model;
    public MoveController(Graphics graphics, ModelController modelController) {
        this.modelController = modelController;
        this.sprites = modelController.getSprites();
        this.player = modelController.getPlayer();
        this.resources = modelController.getResources();
        this.model = modelController.getModel();
        update(graphics);
    }
    private void update(Graphics graphics){
        Iterator<Sprite> iterator = sprites.iterator();
        while (iterator.hasNext()) {
            Sprite s = iterator.next();
            if (s instanceof Player) {
                for (Sprite el : sprites) {
                    if (el instanceof Trap) {
                        if (((Trap) el).collidesSize((Player) s, Definitions.playerPaddingTop)) {
                            modelController.setGameIsOver(true);
                            break;
                        }
                    } else if (el instanceof Finish) {
                        if (((Finish) el).overlaps((Player) s)) {
                            modelController.setGameIsOver(true);
                            modelController.setWon(true);
                            break;
                        }
                    } else if (el instanceof Objects.Button) {
                        ((Objects.Button) el).setMove(Definitions.defaultMovePress);
                        if (((Objects.Button) el).collidesWidthAndHeight((Player) s, Definitions.playerPadding, Definitions.playerPaddingTop)) {
                            el.updateImage("ButtonRestartPress.png");
                            if (((Objects.Button) el).getIsPress()) {
                                ((Objects.Button) el).press();
                                ((Objects.Button) el).setIsPress(false);
                                modelController.setIsRestart(true);
                            }
                        } else {
                            if (!((Objects.Button) el).getIsPress()) {
                                el.setY(el.getY() - ((Objects.Button) el).getMove());
                                ((Objects.Button) el).setIsPress(true);
                            }
                            el.updateImage("ButtonRestart.png");
                        }
                    }
                }
                ((Player) s).updateInfo();
                fixCollisions();
                if (s.getY() >= Definitions.heightWindow || player.isDead()) {
                    modelController.setGameIsOver(true);
                }
            }
            if (s instanceof Coin) {
                if (((Coin) s).collidesSize((Player) player, Definitions.playerPadding)) {
                    this.resources.put("COINS", this.resources.get("COINS") + 1);
                    this.resources.put("MANY_COIN", this.resources.get("MANY_COIN") + (this.resources.get("MANY_COIN") >= 10 ? 0 : 1));
                    modelController.setTakeCoin(true);
                    iterator.remove();
                }
            } else if (s instanceof Objects.Number) {
                if (modelController.getIsTakeCoin()) {
                    int tmp = sprites.indexOf(s);
                    String text = Definitions.imageNumbers + this.resources.get("MANY_COIN") + ".png";
                    Objects.Number newNumber = new Number(text, 50, 0, 50, false);
                    sprites.set(tmp, newNumber);
                    modelController.setIsTakeCoin(false);
                }
            } else if (s instanceof FairBol) {
                if (((FairBol) s).isExpired()) {
                    iterator.remove();
                    break;
                }
            } else if (s instanceof Skeleton) {
                if (((Skeleton) s).isDead()) {
                    if (((Skeleton) s).getFramesSinceDeath() == Definitions.skeletonDeathLength) {
                        //sprites.remove(s);
                    } else {
                        ((Skeleton) s).incrementFramesSinceDeath();
                    }
                } else {
                    for (Sprite fairBol : sprites) {
                        if (fairBol instanceof FairBol && s.overlaps(fairBol)) {
                            ((Skeleton) s).kill();
                            ((Skeleton) s).incrementFramesSinceDeath();
                            ((FairBol) fairBol).removeFairBol();
                            break;
                        }
                    }
                }
            }
            if (s instanceof NumberSkeleton) {
                if (modelController.getIsKillSkeleton()) {
                    int tmp = sprites.indexOf(s);
                    String text = Definitions.imageNumbers + this.resources.get("MANY_KILL_SKELETON") + ".png";
                    NumberSkeleton newNumberSkeleton = new NumberSkeleton(text, 200, 0, 50, false);
                    sprites.set(tmp, newNumberSkeleton);
                    modelController.setIsKillSkeleton(false);
                }
            }
            s.update(graphics);
        }
    }
    private void fixCollisions() {
        // Перебираем все спрайты на игровом поле
        for (Sprite s : sprites) {
            // Проверяем, не является ли текущий спрайт игроком
            // Если это не игрок и спрайт является столкновимым и перекрывается с игроком, выполняем действия по исправлению коллизий
            if (!(s instanceof Player) && s.isCollidable() && player.overlaps(s)) {

                // Игрок справа от спрайта, корректируем его положение влево
                if (player.getPrevX() + player.getWidth() - Definitions.playerPadding < s.getX()) {
                    player.setX(s.getX() - player.getWidth() + Definitions.playerPadding - 1);
                }
                // Игрок слева от спрайта, корректируем его положение вправо
                else if (player.getPrevX() + Definitions.playerPadding > s.getX() + s.getWidth()) {
                    player.setX(s.getX() + s.getWidth() - Definitions.playerPadding + 1);
                }
                // Игрок сверху над спрайтом
                else if (player.getPrevY() + player.getHeight() < s.getY()) {

                    // Если игрок смотрит вправо и находится в воздухе, устанавливаем изображение стоящего игрока вправо
                    // Если игрок смотрит влево и находится на земле, устанавливаем изображение стоящего игрока влево
                    if (player.isFacingRight() && player.isAirborne()) {
                        player.setStandingRightImage();
                    }
                    else if (!player.isFacingRight() && !player.isAirborne()) {
                        player.setStandingLeftImage();
                    }

                    // Устанавливаем игрока над спрайтом, устанавливаем флаг, что он на земле и разрешаем двойной прыжок
                    player.setY(s.getY() - player.getHeight() - 1);
                    player.setAirborne(false);
                    player.setDoubleJump(true);

                    // Если игрок движется вниз, обнуляем его вертикальную скорость
                    if (player.getVelocity() > 0) {
                        player.setVelocity(0);
                    }
                }
                // Игрок под спрайтом
                else if (player.getPrevY() + Definitions.playerPaddingTop > s.getY() + s.getHeight()) {
                    // Устанавливаем игрока под спрайтом и устанавливаем отрицательную вертикальную скорость для имитации отскока от поверхности
                    player.setY(s.getY() + s.getHeight() - Definitions.playerPaddingTop + 1);
                    player.setVelocity((int) (-0.6 * player.getVelocity()));
                }
            }
        }
    }
}
