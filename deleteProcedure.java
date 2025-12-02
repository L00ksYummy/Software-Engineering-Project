package org.yourcompany.softproj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class deleteProcedure {

    public static void delete(String procedureID) {
        String url = "jdbc:mysql://127.0.0.1:3306/Patient_Info";
        String user = "root";
        String password = "Flounder#12";

        String query = "DELETE FROM procedures WHERE `Procedure ID` = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, procedureID);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("🗑️ Procedure deleted successfully.");
            } else {
                System.out.println("⚠️ No procedure found with that Procedure ID.");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
