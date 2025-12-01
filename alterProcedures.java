import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class alterProcedures {
    public static void update(String pid, String value, String fieldToUpdate){
         // database connection info
        String url = "jdbc:mysql://localhost:3306/test_db";
        String user = "root";
        String password = "KtPfw05gs67!";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            // Add quotes around value if not numeric
            if (!value.matches("\\d+")) {
                value = "'" + value + "'";
            }

            String query = String.format(
                "UPDATE procedures SET %s = %s WHERE p_id = '%s';",
                fieldToUpdate, value, pid
            );

            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected > 0) {
                System.out.println("✅ Procedure record updated successfully.");
            } else {
                System.out.println("⚠️ No procedure found with that procedure ID.");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    
}
