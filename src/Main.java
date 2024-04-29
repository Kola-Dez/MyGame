import java.util.HashMap;

public class Main {

    static HashMap<String, Integer> resources;
    public static void main(String[] args) {
        resources = new HashMap<>();
        resources.put("NOW_LEVEL", 1);
        resources.put("MANY_COIN", 0);
        resources.put("COINS", 0);
        resources.put("MANY_KILL_SKELETON", 0);
        Model model = new Model(resources);
        new Controller(model);
    }
}
