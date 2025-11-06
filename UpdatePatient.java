import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class UpdatePatient {

    public static void update(String MRN, String fieldToUpdate, String value) {
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
                "UPDATE patients SET %s = %s WHERE MRN = '%s';",
                fieldToUpdate, value, MRN
            );

            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected > 0) {
                System.out.println("✅ Patient record updated successfully.");
            } else {
                System.out.println("⚠️ No patient found with that MRN.");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
