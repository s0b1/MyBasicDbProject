package databasecode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.Random;

public class Writer
{
    public static void main(String[] args) throws Exception
    {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "123456";

        Connection connection = DriverManager.getConnection(url, user, password);
        Random random = new Random();

        while (true) {
            String message;
            String type;

            if (random.nextBoolean())
            {
                message = "New message: " + LocalDateTime.now();
                type = "INFO";
            }
            else
            {
                message = "Error in " + LocalDateTime.now();
                type = "WARN";
            }

            String sql = "INSERT INTO notice (message, type, processed) VALUES (?, ?, false)";
            try (PreparedStatement stmt = connection.prepareStatement(sql))
            {
                stmt.setString(1, message);
                stmt.setString(2, type);
                stmt.executeUpdate();
            }

            System.out.println("Inserted: " + message + " [" + type + "]");
            Thread.sleep(1000); // 1 sec pause
        }
    }
}
