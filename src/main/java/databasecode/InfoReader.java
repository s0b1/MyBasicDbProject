package databasecode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InfoReader
{
    public static void main(String[] args) throws Exception
    {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "123456";

        Connection connection = DriverManager.getConnection(url, user, password);

        while (true)
        {
            String selectSQL = "SELECT id, message FROM notice WHERE type = 'INFO' AND processed = false";
            try (PreparedStatement selectStmt = connection.prepareStatement(selectSQL);
                 ResultSet rs = selectStmt.executeQuery())
            {

                while (rs.next())
                {
                    int id = rs.getInt("id");
                    String message = rs.getString("message");
                    System.out.println("INFO Message: " + message);

                    // udalenie zapisi
                    String deleteSQL = "DELETE FROM notice WHERE id = ?";
                    try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL))
                    {
                        deleteStmt.setInt(1, id);
                        deleteStmt.executeUpdate();
                    }
                }
            }
            Thread.sleep(1000); // sekunda pauzi pered chteniem
        }
    }
}
