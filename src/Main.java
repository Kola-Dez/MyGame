import Kernel.Contrlollers.Controller;
import Kernel.Definitions;
import Kernel.Contrlollers.ModelController;

public class Main {
    public static void main(String[] args) {
        Definitions.addResource();
        new Controller(new ModelController(Definitions.resources));
    }
}
