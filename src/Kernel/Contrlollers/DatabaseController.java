package Kernel.Contrlollers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class DatabaseController {
    private final Connection connection;
    private PreparedStatement preparedStatement;

    public DatabaseController(Connection connection){
        this.connection = connection;
    }

    public HashMap<String, String> get(int id) {
        HashMap<String, String> data = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM player WHERE id = ?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                data.put("NAME", resultSet.getString("name"));
                data.put("MANY_KILL_SKELETON", String.valueOf(resultSet.getInt("skeletonKill")));
                data.put("TIME_WIN", resultSet.getString("timeWin"));
            }
        } catch (SQLException e){
            System.err.println("Ошибка с запросом: \n" + e);
        }
        return data;
    }
    public ArrayList<HashMap<String, String>> getRecordsWithMinTimeWin() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM player");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                HashMap<String, String> data = new HashMap<>();
                data.put("NAME", resultSet.getString("name"));
                data.put("MANY_KILL_SKELETON", String.valueOf(resultSet.getInt("skeletonKill")));
                data.put("TIME_WIN", resultSet.getString("timeWin"));
                list.add(data);
            }
        } catch (SQLException e){
            System.err.println("Ошибка с запросом: \n" + e);
        }
        return list;
    }

    public void put(HashMap<String, String> data) {
        try {
            String sql = "INSERT INTO player (name, skeletonKill, timeWin) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, data.get("NAME"));
            preparedStatement.setString(2, data.get("MANY_KILL_SKELETON"));
            preparedStatement.setString(3, data.get("TIME_WIN"));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении данных в базу данных: \n" + e);
        }
    }
    public void delete(int id){
        try {
            String sql = "DELETE FROM player WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            System.out.println("Запись с id=" + id + " успешно удалена из базы данных.");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении записи из базы данных: \n" + e);
        }
    }
}
