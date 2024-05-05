package Kernel.Contrlollers;

import Objects.*;
import Objects.Button;
import Objects.Number;
import Kernel.Definitions;
import Kernel.Model;
import Kernel.Sprite;

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

    // Конструктор класса, инициализирует контроллер модели и другие необходимые переменные
    public MoveController(Graphics graphics, ModelController modelController) {
        this.modelController = modelController;
        this.sprites = modelController.getSprites();
        this.player = modelController.getPlayer();
        this.resources = modelController.getResources();
        this.model = modelController.getModel();
        update(graphics); // Вызываем метод обновления
    }

    // Метод обновления игрового состояния
    private void update(Graphics graphics) {
        Iterator<Sprite> iterator = sprites.iterator();
        while (iterator.hasNext()) {
            Sprite s = iterator.next();
            if (s instanceof Player) {
                // Обработка коллизий игрока с различными объектами
                for (Sprite el : sprites) {
                    if (el instanceof Trap) {
                        if (((Trap) el).circleCollides((Player) s)) {
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
                        if (((Objects.Button) el).collidesWidthAndHeight((Player) s, Definitions.playerPadding, Definitions.Zero)) {
                            el.updateImage("ButtonRestartPress.png");
                            if (((Objects.Button) el).getIsPress()) {
                                ((Objects.Button) el).press();
                                modelController.setIsRestart(true);
                            }
                        } else {
                            if (!((Objects.Button) el).getIsPress()) {
                                ((Button) el).letGo();
                                el.updateImage("ButtonRestart.png");
                            }
                        }
                    } else if (el instanceof IterBox){
                        // Обработка столкновения с объектом IterBox
                        ((IterBox) el).updateInfo(sprites); // Обновляем информацию о перемещении
                        if (((IterBox) el).collidesSize((Player) s, 30)){ // Проверяем столкновение
                            HashMap<String , Boolean> result = ((IterBox) el).IterCollidable((Player) s); // Получаем результат столкновения
                            if (result.get("playerRight")){
                                ((IterBox) el).moveLeft(); // Перемещаем IterBox влево
                            }else if (result.get("playerLeft")){
                                ((IterBox) el).moveRight(); // Перемещаем IterBox вправо
                            }
                        }
                    } else if(el instanceof MovingPlatform){
                        ((MovingPlatform) el).updatePosition();
                    }
                }
                ((Player) s).updateInfo(); // Обновляем информацию о игроке
                fixCollisions(); // Исправляем коллизии
                if (s.getY() >= Definitions.heightWindow || player.isDead()) {
                    modelController.setGameIsOver(true);
                }
            }
            // Обработка коллизий и других событий с объектами Coin, Number, FairBol, Skeleton и NumberSkeleton
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
                s.updateInfo(sprites);
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
            s.update(graphics); // Обновляем графику спрайта
        }
    }

    // Метод для исправления коллизий между игроком и другими объектами
    private void fixCollisions() {
        for (Sprite s : sprites) {
            if (!(s instanceof Player) && s.isCollidable() && player.overlaps(s)) {
                player.handleCollisionWithSprite(s); // Обрабатываем коллизию
            }
        }
    }
}
