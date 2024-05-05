package Kernel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public abstract class Definitions {

    // Перечисление состояний скорости
    public enum VelocityState {LEFT, RIGHT, STILL;}

    // Наименование игры
    public static String gameName = "Skeleton";

    // Текст при победе
    public static String winningText = "You WIN";

    // Текст при перезапуске
    public static String restartText = "Go Restart?";

    // Текст при проигрыше
    public static String losingText = "Продолжить игру?, - 5 Монетки";

    // Сообщение о недостатке монет
    public static String notHaveCoin = "Недостаточно монет";

    // Инструкция по управлению
    public static String instructionText = "A - move left\nD - move right\nSpace - Jump (double tap in air to double jump)\nEnter - Throw a fairBoll";

    // Отступы игрока
    public static int playerPaddingHorizontal = 10;
    public static int playerPadding = 40;
    public static int playerPaddingTop = 20;
    public static int jump = -20;

    // Размеры игрока
    public static int playerDimension = 105;

    // Дистанция истечения снаряда
    public static int fairBolExpireDist = 200;

    // Дистанция атаки скелета
    public static int skeletonAttackDist = 35;

    // Длительность анимации смерти скелета
    public static int skeletonDeathLength = 45;

    // Длительность атаки скелета
    public static int skeletonAttackLength = 54;

    // Количество монет, вычитаемое по умолчанию
    public static int coinDefaultMinus = 5;

    // Размеры окна
    // Получение размера экрана и расчет координат для размещения окна по центру
    private static Toolkit toolkit = Toolkit.getDefaultToolkit(); // Получаем Toolkit для работы с графическими ресурсами
    public static Dimension dimension = toolkit.getScreenSize(); // Получаем размер экрана
    public static int widthWindow = 1500;
    public static int heightWindow = 800;

    // Переменная перемещения по умолчанию
    public static int defaultMovePress = 5;

    // Максимальное количество выбранных элементов в таблице
    public static int tableMaxSelect = 10;

    // Значение нуля
    public static int Zero = 0;

    // Директории для изображений
    public static final String imageDirectory = "image/";
    public static final String imageDecorDirectory = "image/decor/";
    public static final String imageSkeletonDirectory = "image/Skeleton/";
    public static final String imageBgDirectory = "image/Background/";
    public static final String imageAmoDirectory = "image/Amo/";
    public static final String imageNumbers = "Numbers/";
    public static final String imageButtonDirectory = "image/Button/";

    // Ресурсы игры
    public static HashMap<String, Integer> resources = new HashMap<>();

    // Добавление ресурсов по умолчанию
    public static void addResource(){
        resources.put("NOW_LEVEL", 1);
        resources.put("MANY_COIN", 0);
        resources.put("COINS", 0);
        resources.put("MANY_KILL_SKELETON", 0);
        resources.put("TIME_WIN", 0);
    }

    // Иконка снаряда
    public static final ImageIcon fairBolIcon = new ImageIcon(imageDirectory + "fairBol-icon.png");

}
