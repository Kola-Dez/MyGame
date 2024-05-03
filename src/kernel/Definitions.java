package kernel;

import javax.swing.*;
import java.util.HashMap;

public abstract class Definitions {


    public enum VelocityState {LEFT,RIGHT,STILL;}
    public static String gameName = "Objects.Skeleton";
    public static String winningText = "You WIN";
    public static String restartText = "Go Restart?";
    public static String losingText = "Продолжить игру?, - 5 Монетки" ;
    public static String notHaveCoin = "Недостаточно монет";
    public static String instructionText = "A - move left\nD - move right\nSpace - Jump (double tap in air to double jump)\nEnter - Throw a fairBoll";
    public static int playerPaddingHorizontal = 10;
    public static int playerPadding = 40;
    public static int playerPaddingTop = 20;
    public static int playerDimension = 105;
    public static int fairBolExpireDist = 200;
    public static int skeletonAttackDist = 35;
    public static int skeletonDeathLength = 45;
    public static int skeletonAttackLength = 54;
    public static int coinDefaultMinus = 5;
    public static int widthWindow = 1500;
    public static int heightWindow = 800;
    public static int defaultMovePress = 5;
    public static int tableMaxSelect = 10;
    public static final String imageDirectory = "image/";
    public static final String imageDecorDirectory = "image/decor/";
    public static final String imageSkeletonDirectory = "image/Skeleton/";
    public static final String imageBgDirectory = "image/Background/";
    public static final String imageAmoDirectory = "image/Amo/";
    public static final String imageNumbers = "Numbers/";
    public static final String imageButtonDirectory = "image/Button/";
    public static HashMap<String, Integer> resources = new HashMap<>();
    public static void addResource(){
        resources.put("NOW_LEVEL", 1);
        resources.put("MANY_COIN", 0);
        resources.put("COINS", 0);
        resources.put("MANY_KILL_SKELETON", 0);
        resources.put("TIME_WIN", 0);
    }
    public static final ImageIcon fairBolIcon = new ImageIcon(imageDirectory + "fairBol-icon.png");

}
