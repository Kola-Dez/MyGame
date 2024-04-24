
public class Main {
    private static final int FIRST_LEVEL = 1;
    private static final int FIRST_COIN_COUNT = 0;
    private static final int KILLS_SKELETON_COUNT = 0;
    public static void main(String[] args) {

        Model model = new Model(FIRST_LEVEL, FIRST_COIN_COUNT, KILLS_SKELETON_COUNT);
        new Controller(model);
    }
}
