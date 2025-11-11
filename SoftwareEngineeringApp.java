/*Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
*/

/*
 * list patient id and name then let them pick it
 */

package org.yourcompany.softproj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

public class SoftwareEngineeringApp {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/project_scheme?serverTimezone=UTC";
        String user = "root";
        String password = "T3rr@pin$2ii2";
        Scanner scan = new Scanner(System.in);

        Database db = new Database(url, user, password);
        Connection conn = DriverManager.getConnection(url, user, password);
        HashMap<String, Runnable> operations = new HashMap<>();
        operations.put("S", db.search(conn, scan));

        System.out.println("Hello! This is the database for our Optometry Practice.\n");

        db.printData(conn, "Select Last, 'First Name', MRN from patients");

        String userInput = "";

        while (true) {
            System.out.println(
                    "Would you like to Search(S), Update(U), Delete(D), Add(A) database? (e to cancel process)");
            userInput = scan.next();

            if (userInput.equalsIgnoreCase("E")) {
                break;
            } else if (!operations.containsKey(userInput)) {
                System.out.println("Please Enter one of the 4 options (S, U, D, A, or E)!");
            } else {
                operations.get(userInput).run();
            }

        }

        scan.close();
    }
}
