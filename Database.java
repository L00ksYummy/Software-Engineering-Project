package org.yourcompany.softproj;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class Database {
    String url;
    String user;
    String password;

    public Database(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getURL() {
        return this.url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return this.user;
    }

    public void setPass(String password) {
        this.password = password;
    }

    public String getPass() {
        return this.password;
    }

    public void printData(Connection conn, String query) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            System.out.println();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                System.out.println(columnName + ": " + rs.getString(columnName));
            }
            System.out.println();
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public Runnable search(Connection conn, Scanner scan) {

        return () -> {
            try {
                // map is faster inside if than a bunch of values, less time spent on checking
                HashMap<String, String> executeMap = new HashMap<>();
                executeMap.put("1", "'First Name'");
                executeMap.put("2", "Last");
                executeMap.put("3", "MRN");

                String searchIn = "";
                String searchFor = "";

                boolean repeat = true;
                while (repeat == true) {
                    System.out.println(
                            "Would you like to search by First Name(1), Last Name(2), or MRN(3)? (e to cancel process): ");
                    searchIn = scan.next();

                    if (searchIn.equalsIgnoreCase("e")) {
                        System.out.println("Ending Process");
                        System.exit(0);
                    } else if (!executeMap.containsKey(searchIn)) {
                        System.out.println("Please enter a valid input...");
                    } else
                        searchIn = executeMap.get(searchIn);

                    System.out.println("Enter search query (e to cancel): ");
                    searchFor = scan.next();

                    if (searchFor.equalsIgnoreCase("e")) {
                        System.exit(0);
                    }

                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt
                            .executeQuery(String.format("select * from patients where %s = '%s'", searchIn, searchFor));

                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    while (rs.next()) {
                        for (int i = 1; i < columnCount; i++) {
                            String columnName = metaData.getColumnName(i);
                            System.out.printf("%s: %s%n", columnName, rs.getString(i));
                        }
                        System.out.println();
                    }
                    // while loop to control repeat logic
                    while (true) {
                        System.out.println("Would you like to search again?(y or n): ");
                        String again = scan.next();

                        if (again.equalsIgnoreCase("n")) {
                            repeat = false;
                            break;
                        } else if (!again.equalsIgnoreCase("y")) {
                            System.out.println("Please enter a valid input...");
                        } else
                            break;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
    }
}
