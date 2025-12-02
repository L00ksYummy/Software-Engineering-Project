package org.yourcompany.softproj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class deleteProcedureHistory {

    public static void delete(String MRN, String procedureID) {
        String url = "jdbc:mysql://127.0.0.1:3306/Patient_Info";
        String user = "root";
        String password = "Flounder#12";

        String query = "DELETE FROM procedure_history WHERE MRN = ? AND `Procedure ID` = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, MRN);
            pstmt.setString(2, procedureID);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Procedure history entry deleted successfully.");
            } else {
                System.out.println("No matching record found in procedure history.");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
