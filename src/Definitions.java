import javax.swing.*;

public abstract class Definitions {
    public enum VelocityState {LEFT,RIGHT,STILL}
    public static String gameName = "Skeleton";
    public static String winningText = "You WIN!!!";
    public static String losingText = "You DIE:(";
    public static String instructionText = "A - move left\nD - move right\nSpace - Jump (double tap in air to double jump)\nEnter - Throw a fairBoll";
    public static int playerPadding = 40;
    public static int playerPaddingTop = 20;
    public static int playerDimension = 105;
    public static int fairBolExpireDist = 200;
    public static int skeletonAttackDist = 35;
    public static int skeletonDeathLength = 45;
    public static int skeletonAttackLength = 54;
    public static int widthWindow = 1500;
    public static int heightWindow = 800;
    public static final String imageDirectory = "image/";
    public static final String imageDecorDirectory = "image/decor/";
    public static final String imageSkeletonDirectory = "image/Skeleton/";
    public static final String imageBgDirectory = "image/Background/";
    public static final String imageAmoDirectory = "image/Amo/";
    public static final String imageNumbers = "Numbers/";

    public static final ImageIcon fairBolIcon = new ImageIcon(imageDirectory + "fairBol-icon.png");

}
