/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.yourcompany.softproj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class SoftwareEngineeringApp {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/project_scheme?serverTimezone=UTC";
        String user = "root";
        String password = "T3rr@pin$2ii2";
        String query = "Select * from patients";
        Scanner scan = new Scanner(System.in);

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("The connection was a success to the database: " + url);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("Hello! This is the database for our Optometry Practice.\n");
            String userInput = "";

            while (true) {
                System.out.println(
                        "Would you like to Search(S), Update(U), Delete(D), Add(A), or end the process(E) to the database? ");
                userInput = scan.next().toUpperCase();

                if (userInput.equals("S")
                        || userInput.equals("D")
                        || userInput.equals("U")
                        || userInput.equals("A")
                        || userInput.equals("E")) {
                    break;
                } else {
                    System.out.println("Please Enter one of the 4 options (S, U, D, A, or E)!");
                }
            }

            switch (userInput) {
                case "S" -> {
                    System.out.println(SearchForDB());
                }

                case "U" -> {
                    System.out.println("Currently Updating");
                }

                case "D" -> {
                    System.out.println("Currently Deleteing");
                }

                case "A" -> {
                    System.out.println("Currently Adding");
                }

                case "E" -> {
                    System.out.println("Ending process");
                }
            }

            scan.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static String SearchForDB() throws SQLException {

        String url = "jdbc:mysql://localhost:3306/project_scheme?serverTimezone=UTC";
        String user = "root";
        String password = "T3rr@pin$2ii2";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("show tables");
            HashMap<String, String> options = new HashMap<>();

            // Printing all available tables to user to choose from. More versatile than
            // just printing a preset name
            // Also making a hashmap which only really works here because its such a small
            // data set
            // But it makes it so we can make changes to the table amount or names without
            // changing the logic
            int count = 1;
            while (rs.next()) {
                String tableName = rs.getString("Tables_in_project_scheme");
                options.put(String.valueOf(count), tableName);
                count++;
            }

            String result = "";
            try (Scanner scan = new Scanner(System.in)) {

                String tableInput;
                while (true) {

                    for (String key : options.keySet()) {
                        System.out.print("For " + key + " press " + options.get(key) + "\n");
                    }
                    System.out.println("(Enter E to end process)");

                    tableInput = scan.next().toUpperCase();

                    if (tableInput.equals("E")) {
                        return "Ending Process";
                    } else if (!options.containsKey(tableInput)) {
                        System.out.println("Invalid input. Please enter one of the options");
                    } else
                        break;
                }

                result = HandleDBSearch(options.get(tableInput));
            }
            return result;
        }
    }

    public static String HandleDBSearch(String tableName) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/project_scheme?serverTimezone=UTC";
        String user = "root";
        String password = "T3rr@pin$2ii2";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = rs.executeQuery("select * from " + tableName);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

        }

        return tableName;
    }

}
