import kernel.Contrlollers.Controller;
import kernel.Definitions;
import kernel.Contrlollers.ModelController;

public class Main {
    public static void main(String[] args) {
        Definitions.addResource();
        new Controller(new ModelController(Definitions.resources));
    }
}
