import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class alterProcedureHistory {
    public static void update(String MRN, String pid, String value, String fieldToUpdate){
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
                "UPDATE proc_his SET %s = %s WHERE MRN = '%s' and p_id = %s;",
                fieldToUpdate, value, MRN, pid
            );

            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected > 0) {
                System.out.println("✅ Procedure history updated successfully.");
            } else {
                System.out.println("⚠️ No procedure history found with that MRN/procedure ID.");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    
}
