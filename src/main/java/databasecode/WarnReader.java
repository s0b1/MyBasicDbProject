package databasecode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WarnReader
{
    public static void main(String[] args) throws Exception
    {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "123456";

        Connection connection = DriverManager.getConnection(url, user, password);

        while (true)
        {
            String selectSQL = "SELECT id, message FROM notice WHERE type = 'WARN' AND processed = false";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectSQL);
                 ResultSet rs = selectStmt.executeQuery())
            {

                while (rs.next())
                {
                    int id = rs.getInt("id");
                    String message = rs.getString("message");
                    System.out.println("WARN Message: " + message);


                    String updateSQL = "UPDATE notice SET processed = true WHERE id = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateSQL))
                    {
                        updateStmt.setInt(1, id);
                        updateStmt.executeUpdate();
                    }
                }
            }
            Thread.sleep(1000);
        }
    }
}
