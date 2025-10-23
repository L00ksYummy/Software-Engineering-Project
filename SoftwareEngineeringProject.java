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

public class SoftwareEngineeringProject {

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
                System.out.println("Would you like to Search(S), Update(U), Delete(D), or Add(A) to the database? ");
                userInput = scan.next().toUpperCase();

                if (userInput.equals("S") || userInput.equals("D") || userInput.equals("U") || userInput.equals("A")) {
                    break;
                } else {
                    System.out.println("Please Enter one of the 4 options (S, U, D, or A)!");
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

        try (Connection conn = DriverManager.getConnection(url, user, password);) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("show tables");

            // Printing all available tables to user to choose from. More versatile than
            // just printing names
            while (rs.next()) {

            }

            Scanner scan = new Scanner(System.in);
            HashMap<String, String> options = new HashMap<>();

            // Making a hashmap to store options so we don't have a bunch of nested
            // if/switch statements.
            // Nobody likes unneccesary time complexity

            String tableInput;

            // Using while statement because people don't know how to hit 1, 2, or 3
            while (true) {

                for (String key : options.keySet()) {
                    System.out.print("For " + key + " press " + options.get(key) + "\n");
                }

                tableInput = scan.next();

                if (!options.containsValue(tableInput)) {
                    System.out.println("Invalid input. Please enter one of the 3 options (1,2,3)");
                } else
                    break;
            }

            String result = "";
            // Using switch statement to quickly detect input and run function to handle the
            // table search
            switch (tableInput) {
                case "1" -> {
                    result = HandleDBSearch("patients");
                }
                case "2" -> {
                    result = HandleDBSearch("procedures");
                }
                case "3" -> {
                    result = HandleDBSearch("procedure_history");
                }
            }
            return result;
        }
    }

    public static String HandleDBSearch(String tableName) {

        HashMap<String, String> tableInfo = new HashMap<>();

        System.out.println("What are you looking for in the database?");
        System.out.println("""
                A patient by a name(1)
                A patient by an MRN(2)
                A patient by an address(3)
                Patients under a certain doctor(4)
                Patients under a certain insurance(5)""");

        return null;
    }

}
