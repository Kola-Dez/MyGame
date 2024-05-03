package kernel.Contrlollers;

import Database.Connect;
import kernel.Converter;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestDBController {
    public ArrayList<HashMap<String, String>> result;

    public void setResult(HashMap<String, Integer> resources, String name) {
        Connect connect = new Connect();

        DatabaseController databaseController = new DatabaseController(connect.getConnection());// databaseController
        HashMap<String, String> playerData = new Converter().convertSS(resources);// result
        playerData.put("NAME", name);

        databaseController.put(playerData);

        this.result = databaseController.getRecordsWithMinTimeWin();

        connect.closeConnection();
    }

    public ArrayList<HashMap<String, String>> getResult() {
        return this.result;
    }
}
