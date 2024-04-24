import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.reflect.Method;

public class Model {
    private ArrayList<Sprite> sprites;
    private Player player;
    private final ArrayList<Sprite> number;
    private final ArrayList<Sprite> traps;
    private final ArrayList<Sprite> coins;
    private Sprite goblet;
    private boolean gameIsOver;
    private boolean won;
    private int ManyCoin;
    private int ManyKillSkeleton;
    private boolean isTakeCoin;
    private boolean isKillSkeleton;

    Model(int nowLevel, int ManyCoin, int ManyKillSkeleton){
        gameIsOver = false;
        won = false;
        this.ManyCoin = ManyCoin;
        isTakeCoin = false;
        this.ManyKillSkeleton = ManyKillSkeleton;
        isKillSkeleton = false;

        traps = new ArrayList<>();
        coins = new ArrayList<>();
        number = new ArrayList<>();

        levelLoad(nowLevel);
    }
    public void levelLoad(int nowLevel){
        sprites = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            if (nowLevel == i) {
                String methodName = String.format("levelsPrint%d", i);
                try {
                    Levels levels = new Levels(sprites, this, traps, coins, number);
                    Method method = levels.getClass().getMethod(methodName);
                    @SuppressWarnings("unchecked")
                    ArrayList<Sprite> newSprites = (ArrayList<Sprite>) method.invoke(levels);
                    sprites = newSprites;
                    sprites.add(new Number(Definitions.imageNumbers + this.ManyCoin + ".png", 50, 0, 50 ,false));
                    sprites.add(new Decor("CoinDecor.gif",0 , 0,50, false));
                    sprites.add(new NumberSkeleton(Definitions.imageNumbers + this.ManyKillSkeleton + ".png", 200, 0, 50 ,false));
                    sprites.add(new Decor("skeletonFaceDecor.gif",150 , 0,50, false));
                    this.goblet = new Sprite("doorDecor.png", 100, 100).setX(1380).setY(35);
                    sprites.add(goblet);
                    this.player = new Player();
                    sprites.add(player);

                    Skeleton.setPlayer(player);
                } catch (Exception ex) {
                    System.err.println("An error occurred: " + ex.getMessage());
                }
                break;
            }else {
                sprites.add(new Decor("GameOverBG.gif", 0, 0, 1500, 800, false));
                sprites.add(new Number(Definitions.imageNumbers + this.ManyCoin + ".png", 50, 0, 50 ,false));
                sprites.add(new Decor("CoinDecor.gif",0 , 0,50, false));
                sprites.add(new NumberSkeleton(Definitions.imageNumbers + this.ManyKillSkeleton + ".png", 400, 0, 50 ,false));
                sprites.add(new Decor("skeletonFaceDecor.gif",150 , 0,200, false));
            }
        }
    }
    // Обновление состояния модели
    public void update(Graphics graphics) {
        Iterator<Sprite> iterator = sprites.iterator();

        while (iterator.hasNext()) {
            Sprite s = iterator.next();
            if (s instanceof Player) {
                for (Sprite el : sprites) {
                    if (el instanceof Trap) {
                        if (el.collidesWith((Player) s, s.getWidth() - Definitions.playerPadding)) {
                            gameIsOver = true;
                            break;
                        }
                    }
                }
                if (s.overlaps(goblet)) {
                    gameIsOver = true;
                    won = true;
                } else {
                    ((Player) s).updateInfo();
                    fixCollisions();
                    if (s.getY() >= Definitions.heightWindow || player.isDead()) {
                        gameIsOver = true;
                    }
                }
            } else if (s instanceof Coin) {
                if (s.collidesWith(player, 0)) {
                    ManyCoin += ManyCoin >= 10 ? 0 : 1;
                    isTakeCoin = true;
                    iterator.remove();
                }
            } else if (s instanceof Number) {
                if (isTakeCoin) {
                    int tmp = sprites.indexOf(s);
                    String text = Definitions.imageNumbers + ManyCoin + ".png";
                    Number newNumber = new Number(text, 50, 0, 50, false);
                    sprites.set(tmp, newNumber);
                    isTakeCoin = false;
                }
            } else if (s instanceof FairBol) {
                if (((FairBol) s).isExpired()) {
                    iterator.remove();
                }
            } else if (s instanceof Skeleton) {
                if (((Skeleton) s).isDead()) {
                    if (((Skeleton) s).getFramesSinceDeath() == Definitions.skeletonDeathLength) {
                        iterator.remove();
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
            }if (s instanceof NumberSkeleton) {
                if (isKillSkeleton) {
                    int tmp = sprites.indexOf(s);
                    String text = Definitions.imageNumbers + ManyKillSkeleton + ".png";
                    NumberSkeleton newNumberSkeleton = new NumberSkeleton(text, 200, 0, 50 ,false);
                    sprites.set(tmp, newNumberSkeleton);
                    isKillSkeleton = false;
                }
            }
            s.update(graphics);
        }
    }
    public int getManyKillSkeleton(){
        return this.ManyKillSkeleton;
    }
    public void setKillSkeleton(){
        this.ManyKillSkeleton += ManyKillSkeleton >= 10 ? 0 : 1;
        this.isKillSkeleton = true;
    }
    public int getManyCoin(){
        return this.ManyCoin;
    }
    public ArrayList<Sprite> getSprite(){
        return this.sprites;
    }

    // Проверка, выиграл ли игрок
    public boolean playerWon() {
        return won;
    }

    // Обработка прыжка игрока
    public void jumpPlayer() {
        player.jump();
    }
    // Установка направления движения игрока
    public void setPlayerDirection(Definitions.VelocityState state){
        player.setDirection(state);
    }
    // Установка направления движения игрока с учетом отпускания клавиши
    public void setPlayerDirection(Definitions.VelocityState state, char keyReleased) {
        if (state == Definitions.VelocityState.STILL && player.getVelocityState() == Definitions.VelocityState.LEFT && keyReleased == 'd') {
            return;
        }
        if (state == Definitions.VelocityState.STILL && player.getVelocityState() == Definitions.VelocityState.RIGHT && keyReleased == 'a') {
            return;
        }
        setPlayerDirection(state);
    }
    // Решение коллизий между спрайтами
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
    // Проверка, завершена ли игра
    public boolean gameIsOver() {
        return gameIsOver;
    }
    // Бросок fairbol
    public void throwFairBol() {
        if (FairBol.getCount() < 3) {
            sprites.add(new FairBol(player.getX(), player.getY(), player.isFacingRight()));
        }
    }
}
