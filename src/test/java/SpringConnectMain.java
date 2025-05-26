import java.sql.*;

public class SpringConnectMain {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/topjava", "user", "password")) {
            System.out.println("Подключение к базе данных успешно!");

            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT version()")) {  // Для PostgreSQL
                if (rs.next()) {
                    System.out.println("Версия БД: " + rs.getString(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка подключения: " + e.getMessage());
        }
    }
}
