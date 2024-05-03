package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static final String URL = "jdbc:mysql://localhost:3306/mygamedb";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "password";
    private static Connection connection = null;
    public Connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Не удалось найти класс драйвера!");
        } catch (SQLException e) {
            System.out.println("Не удалось установить соединение с базой данных!");
        }
    }
    public Connection getConnection(){
        return connection;
    }
    public void closeConnection(){
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при закрытии соединения с базой данных!"); e.printStackTrace();
        }
    }
}
