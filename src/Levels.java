import java.util.ArrayList;

public class Levels {
    ArrayList<Sprite> sprites;
    Model model;
    ArrayList<Sprite> traps;
    ArrayList<Sprite> all;
    ArrayList<Sprite> coin;
    ArrayList<Sprite> number;
    public Levels(ArrayList<Sprite> sprites, Model model, ArrayList<Sprite> traps, ArrayList<Sprite> coin, ArrayList<Sprite> number){
        this.sprites = sprites;
        this.traps = traps;
        this.model = model;
        this.coin = coin;
        this.number = number;
        this.all = new ArrayList<>();
        all.addAll(sprites);
    }
    public ArrayList<Sprite> levelsPrint1() {

        all.add(new Decor("bgBG1.gif", 0, 0, 1500, 800, false));

        all.add(new Decor("platformDecor.png", 20, 700, 200, 30));
        all.add(new Decor("platformDecor.png", 300, 700, 200, 30));
        all.add(new Decor("platformDecor.png", 600, 700, 400, 30));
        all.add(new Decor("LiansDecor.png", 600, 720, 400, 30, false));
        all.add(new Decor("platformDecor.png", 0, 400, 700, 30));
        all.add(new Decor("LiansDecor.png", 0, 420, 700, 40, false));
        all.add(new Decor("platformDecor.png", 0, 250, 200, 30));
        all.add(new Decor("platformDecor.png", 300, 150, 1000, 30));
        all.add(new Decor("platformDecor.png", 1350, 125, 200, 30));

        coin.add(new Coin("CoinDecor.gif", 300, 310, 50, false));
        coin.add(new Coin("CoinDecor.gif", 800, 60, 50, false));
        coin.add(new Coin("CoinDecor.gif", 700, 610, 50, false));
        coin.add(new Coin("CoinDecor.gif", 800, 610, 50, false));
        coin.add(new Coin("CoinDecor.gif", 0, 500, 50, false));
        coin.add(new Coin("CoinDecor.gif", 0, 88, 50, false));
        all.addAll(coin);

        all.add(new Decor("boxDecor.png", 1100, 620, 200));
        all.add(new Decor("boxDecor.png", 1300, 500, 100));
        all.add(new Decor("boxDecor.png", 1100, 400, 20));
        all.add(new Decor("boxDecor.png", 900, 400, 20));

        all.add(new Decor("homeDecor.gif", 0, 88, 200, 200, false));

        traps.add(new Trap("fairDecor.gif", 600, 90, 80, 80));
        all.addAll(traps);

        all.add(new Skeleton(300, 310, 200, model));
        all.add(new Skeleton(800, 60, 100, model));
        all.add(new Skeleton(700, 610, 100, model));

        return all;
    }
    public ArrayList<Sprite> levelsPrint2() {


        all.add(new Decor("bgBG2.gif", 0, 0, 1500, 800, false));

        all.add(new Decor("platformDecor.png", 20, 700, 1000, 30));
        all.add(new Decor("platformDecor.png", 700, 475, 400, 30));
        all.add(new Decor("platformDecor.png", 200, 425, 400, 30));
        all.add(new Decor("platformDecor.png", 150, 250, 200, 30));
        all.add(new Decor("platformDecor.png", 500, 200, 400, 30));
        all.add(new Decor("platformDecor.png", 1000, 200, 200, 30));
        all.add(new Decor("platformDecor.png", 1350, 125, 200, 30));

        all.add(new Decor("NLODecor.png", 20, 125, 40));
        all.add(new Decor("boxDecor.png", 1100, 620, 200));
        all.add(new Decor("homeDecor.gif", 900, 315, 200, 200, false));
        all.add(new Decor("fikusDecor.png", 800, 440, 40, 40, false));

        traps.add(new Trap("fairDecor.gif", 300, 640, 80, 80));
        traps.add(new Trap("fairDecor.gif", 700, 130, 80, 80));
        all.addAll(traps);

        coin.add(new Coin("CoinDecor.gif", 0, 0, 50, false));
        coin.add(new Coin("CoinDecor.gif", 490, 120, 50, false));
        all.addAll(coin);

        all.add(new Skeleton(340, 605, 120, model));
        all.add(new Skeleton(375, 330, 180, model));
        all.add(new Skeleton(675, 105, 180, model));

        return all;
    }
    public ArrayList<Sprite> levelsPrint3() {

        all.add(new Decor("bgBG3.gif", 0, 0, 1500, 800, false));
        all.add(new Decor("platformDecor.png", 20, 700, 1400, 30));
        all.add(new Decor("platformDecor.png", 200, 425, 400, 30));
        all.add(new Decor("platformDecor.png", 150, 250, 200, 30));

        all.add(new Decor("platformDecor.png", 500, 200, 700, 30));
        all.add(new Decor("LiansDecor.png", 500, 225, 700, 30, false));

        all.add(new Decor("platformDecor.png", 1350, 125, 200, 30));


        all.add(new Decor("boxDecor.png", 1100, 500, 100));
        all.add(new Decor("boxDecor.png", 1050, 550, 50));

        traps.add(new Trap("fairDecor.gif", 500, 640, 80, 80));
        traps.add(new Trap("fairDecor.gif", 700, 130, 80, 80));
        all.addAll(traps);

        coin.add(new Coin("CoinDecor.gif", 800, 100, 100, false));
        coin.add(new Coin("CoinDecor.gif", 500, 500, 50, false));
        all.addAll(coin);


        all.add(new Skeleton(500, 605, 100, model));
        all.add(new Skeleton(700, 605, 200, model));
        all.add(new Skeleton(375, 330, 180, model));
        all.add(new Skeleton(675, 105, 180, model));

        return all;
    }
}
