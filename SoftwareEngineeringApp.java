/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.yourcompany.softproj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class SoftwareEngineeringApp {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/project_scheme?serverTimezone=UTC";
        String user = "root";
        String password = "T3rr@pin$2ii2";
        String query = "Select * from patients";
        Scanner scan = new Scanner(System.in);

        Database db = new Database(url, user, password);
        Connection conn = DriverManager.getConnection(url, user, password);

        db.printData(conn, query);

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
                System.out.println();
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
    }

    public static String get() {

        return null;
    }

}
