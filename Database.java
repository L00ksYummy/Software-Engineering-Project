package org.yourcompany.softproj;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    // Database constructor and basic getters and setters for the class
    // We don't actually use any of these but just incase someone would
    // need to in the future, we still added them
    // (obviously this program won't be reused but it's good practice)
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

        // delcaring variables
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // actual printing of the data
        while (rs.next()) {
            System.out.println();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                System.out.println(columnName + ": " + rs.getString(columnName));
            }
            System.out.println();
        }
    }

    public Runnable search(Connection conn, Scanner scan) {

        /*
         * Because we are using hashmaps in the main function
         * We make the value variable a runnable so it can directly call the function
         * below is a lambda expression declaring the function and returning
         * it as a runnable for the hasmap to call
         */
        return () -> {
            try {

                String searchIn;
                String searchFor;

                while (true) {
                    System.out.println(
                            "Would you like to search by First Name(1), Last Name(2), or MRN(3)? (e to cancel process): ");
                    searchIn = scan.next();

                    if (searchIn.equalsIgnoreCase("e")) {
                        System.out.println("Ending Process");
                        System.exit(0);
                    } else {
                        switch (searchIn) {
                            case "1" -> {
                                searchIn = "`First Name`";
                            }
                            case "2" -> {
                                searchIn = "Last";
                            }
                            case "3" -> {
                                searchIn = "MRN";
                            }
                            case "e" -> {
                                System.exit(0);
                            }
                            case "E" -> {
                                System.exit(0);
                            }
                            default -> {
                                System.out.println("Error: invalid input!");
                                continue;
                            }
                        }

                    }

                    System.out.println("Enter search query (e to cancel): ");
                    searchFor = scan.next();

                    if (searchFor.equalsIgnoreCase("e")) {
                        System.exit(0);
                    } else if (searchIn.equals("MRN")) {
                        searchFor = "MRN" + searchFor;
                    }

                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt
                            .executeQuery(String.format("select * from patients where %s = '%s'", searchIn, searchFor));

                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    boolean found = false;

                    while (rs.next()) {
                        for (int i = 1; i < columnCount; i++) {
                            String columnName = metaData.getColumnName(i);
                            System.out.printf("%s: %s%n", columnName, rs.getString(i));
                        }
                        found = true;
                        System.out.println();
                    }

                    if (found == false)
                        System.out.println("Error: no such record exists.\n");

                    System.out.println("\nSearch Complete!\n");

                    // while loop to control repeat logic
                    while (true) {
                        System.out.println("Would you like to search again?(y or n): ");
                        String again = scan.next();

                        if (again.equalsIgnoreCase("n")) {
                            System.exit(0);
                        } else if (!again.equalsIgnoreCase("y")) {
                            System.out.println("Please enter a valid input...");
                        } else
                            break;
                    }

                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        };
    }

    public Runnable add(Connection conn, Scanner scan) {
        return () -> {
            try {

                while (true) {
                    System.out.println("What table do you want to add to? ");
                    System.out.println("Patients(1), Procedures(2), Procedure History(3) (e to cancel process): ");
                    String tableName = scan.next();
                    System.out.println();

                    // basic invalid input check
                    if (tableName.equalsIgnoreCase("e")) {
                        System.exit(0);
                    } else if (!tableName.equals("1")
                            && !tableName.equals("2")
                            && !tableName.equals("3")) {
                        System.out.println("Invalid input...");
                        continue;
                    } else {
                        switch (tableName) {
                            case "1" -> {
                                tableName = "patients";
                            }
                            case "2" -> {
                                tableName = "procedures";
                            }
                            case "3" -> {
                                tableName = "procedures_table";
                            }
                        }
                    }

                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("select * from " + tableName);

                    ResultSetMetaData md = rs.getMetaData();
                    int columnCount = md.getColumnCount();

                    String values = " ";
                    String columnNames = " ";

                    System.out.println("\n*** Enter e at any time to cancel the process ***\n");

                    // concatonation of column names for input
                    // otherwise data would be inserted into all different rows
                    // independant of eachother
                    for (int i = 1; i <= columnCount; i++) {
                        scan.nextLine();
                        System.out.println();
                        System.out.println();

                        String columnName = md.getColumnName(i);

                        // Here we are checking for certain parameters in the input
                        String input = "";
                        OUTER: while (true) {
                            System.out.println("Enter " + columnName + ":");
                            input = scan.nextLine();
                            if (input.equalsIgnoreCase("e")) {
                                System.exit(0);
                            }
                            switch (columnName) {
                                case "MRN" -> {
                                    if (input.contains("\\d+")) {
                                        System.out.println("Input must be numeric!!");
                                    } else if (input.length() > 5) {
                                        System.out.println("Error: MRN's must be 5 digits");
                                    } else {
                                        input = "MRN" + input;
                                        break OUTER;
                                    }
                                }
                                case "Procedure ID" -> {
                                    if (!input.matches("\\d+")) {
                                        System.out.println("Input must be numeric!!");
                                    } else if (input.length() > 4) {
                                        System.out.println("Error: pID's must be 4 digits");
                                    } else {
                                        input = "P" + input;
                                        break OUTER;
                                    }
                                }
                                case "Cost" -> {
                                    if (!input.matches("\\d+")) {
                                        System.out.println("Error: Prices must be numeric");
                                    } else {
                                        input = "$" + input;
                                        break OUTER;
                                    }

                                }
                                default -> {
                                    break OUTER;
                                }
                            }
                        }

                        // puting quotes around value so it inserts into table correctly
                        input = "'" + input + "'";

                        // if the column name is larger than one word, add backticks
                        if (columnName.contains(" ")) {
                            columnName = "`" + columnName + "`";
                        }

                        // adding commas and concatonating values for later use
                        if (i < columnCount) {
                            values += input + ", ";
                            columnNames += columnName + ", ";
                        } else {
                            values += input;
                            columnNames += columnName;
                        }

                        System.out.println();

                    }

                    // final insert into the table
                    stmt.execute("INSERT INTO " + tableName + "(" + columnNames + ") VALUES(" + values + ")");

                    System.out.println("Records Added to " + tableName + "\n");

                    while (true) {
                        System.out.println("Would you like to add another record?(y or n):");
                        String input = scan.next();

                        if (input.equalsIgnoreCase("n")) {
                            System.exit(0);
                        } else if (!input.equalsIgnoreCase("y")) {
                            System.out.println("Error: invalid input\n");
                        } else {
                            System.out.println();
                            break;
                        }

                    }
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            }
        };

    }

    public Runnable update(Connection conn, Scanner scan) {
        return () -> {
            try {

                Statement stmt = conn.createStatement();

                String tableToUpdate = "";

                boolean repeat = true;
                while (repeat) {

                    System.out.println();
                    System.out.println("What table are you updating?");
                    System.out
                            .println("Patients(1), Procedures(2), Procedures History(3) or e to cancel the process: ");
                    tableToUpdate = scan.next();

                    // can use switch statement here because these are the only 3 tables,
                    // using hashmaps elsewhere for dynamic reuse
                    // (incase more columns are added and such)
                    switch (tableToUpdate) {
                        case "1" -> {
                            tableToUpdate = "patients";
                            repeat = false;
                        }
                        case "2" -> {
                            tableToUpdate = "procedures";
                            repeat = false;
                        }
                        case "3" -> {
                            tableToUpdate = "procedures_history";
                            repeat = false;
                        }
                        case "e" -> {
                            System.exit(0);
                        }
                        case "E" -> {
                            System.exit(0);
                        }
                        default -> {
                            System.out.println("please enter a valid option!!");
                        }
                    }
                }

                String fieldToFind = "";
                String valueToUpdate = "";
                int length = 0;

                if (tableToUpdate.equals("patients") || tableToUpdate.equals("procedure_history")) {
                    fieldToFind = "MRN";
                    length = 5;
                } else {
                    length = 4;
                    fieldToFind = "`Procedure Id`";
                }

                while (true) {
                    System.out.println(
                            "Enter the " + Integer.toString(length)
                                    + " digit MRN of the record you want to update (e to cancel process): ");
                    valueToUpdate = scan.next();

                    // making sure MRN is numeric and 5 digits long, otherwise demand reinput
                    if (!valueToUpdate.matches("\\d+")) {
                        System.out.println("Error: invalid input! Value must be numeric");
                        continue;
                    } else if (valueToUpdate.length() != length) {
                        System.out.println("Error! invalid input! Value must be 5 numbers long");
                        continue;
                    } else if (valueToUpdate.equalsIgnoreCase("e")) {
                        System.exit(0);
                    } else {
                        valueToUpdate = "MRN" + valueToUpdate;
                    }

                    String tempQuery = String.format("select * from %s WHERE %s = '%s'", tableToUpdate, fieldToFind,
                            valueToUpdate);

                    ResultSet temp = stmt.executeQuery(tempQuery);
                    if (!temp.next()) {
                        System.out.println("No " + fieldToFind + " found under the value: " + valueToUpdate);
                        System.out.println("Try again!\n");
                    } else {
                        this.printData(conn, tempQuery);
                        System.out.println("\n^^^^^^ Records being updated ^^^^^^\n");
                        break;
                    }
                }

                // initialization of variables
                ResultSet rs = stmt.executeQuery("select * from " + tableToUpdate);
                ResultSetMetaData meta = rs.getMetaData();

                int columnCount = meta.getColumnCount();
                String fieldToUpdate = "";

                HashMap<String, String> map = new HashMap();

                while (true) {
                    System.out.println();
                    System.out.println("What field are you updating in " + tableToUpdate + "?");

                    // getting a string line of all the column names for the statement later
                    String columnNames = " ";
                    for (int i = 1; i <= columnCount; i++) {
                        columnNames += meta.getColumnName(i) + "(" + i + ")";

                        // quick check for columns in the hashmap
                        if (!map.containsValue(meta.getColumnName(i))) {
                            map.put(Integer.toString(i), meta.getColumnName(i));
                        }

                        // adding commans between column names and at the end,
                        // adding the cancel process message
                        if (i < columnCount) {
                            columnNames += ", ";
                        } else
                            columnNames += "(e to cancel process): ";
                    }

                    System.out.println();
                    System.out.println(columnNames);

                    fieldToUpdate = scan.next();

                    // basic check for invalid input
                    if (fieldToUpdate.equalsIgnoreCase("e")) {
                        System.exit(0);
                    } else if (!map.containsKey(fieldToUpdate)) {
                        System.out.println("Error: invalid input!");
                    } else if (fieldToUpdate.equalsIgnoreCase("e")) {
                        System.out.println("Canceling process");
                        System.exit(0);
                    } else {
                        fieldToUpdate = "`" + map.get(fieldToUpdate) + "`";
                        break;
                    }
                }

                // getting the updated value for the tables
                System.out.println();
                System.out.println("Enter the updated value for " + fieldToUpdate);
                String value = scan.next();

                // quotes around value if not numeric
                if (!value.matches("\\d+")) {
                    value = "'" + value + "'";
                }

                String query = "";

                // Update query
                query = String.format(
                        "UPDATE " + tableToUpdate + " SET %s = %s WHERE %s = '%s';",
                        fieldToUpdate, value, fieldToFind, valueToUpdate);

                // Check if field(s) updated
                int rowsAffected = stmt.executeUpdate(query);

                // confirmation of update or error message
                if (rowsAffected > 0) {
                    System.out.println("✅ Patient record updated successfully.");
                } else {
                    System.out.println("⚠️ No patient found with that MRN.");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage() + "\n");
            }
        };
    }

    public Runnable delete(Connection conn, Scanner scan) {
        return () -> {
            try {

                System.out.println("\nEnter the table you want to delete from");

                boolean repeat = true;
                String tableName = "";
                while (repeat) {
                    System.out
                            .println("Patients(1), Procedures(2), Procedures History(3) or e to cancel the process: ");
                    tableName = scan.next();

                    // can use switch statement here because these are the only 3 tables,
                    // using hashmaps elsewhere for dynamic reuse
                    // (incase more columns are added and such)
                    switch (tableName) {
                        case "1" -> {
                            tableName = "patients";
                            repeat = false;
                        }
                        case "2" -> {
                            tableName = "procedures";
                            repeat = false;
                        }
                        case "3" -> {
                            tableName = "procedures_history";
                            repeat = false;
                        }
                        case "e" -> {
                            System.exit(0);
                        }
                        case "E" -> {
                            System.exit(0);
                        }
                        default -> {
                            System.out.println("please enter a valid option!!");
                        }
                    }
                }

                String valueToDelete = "";
                String fieldToDelete = "";
                while (true) {

                    if (tableName.equals("patients") || tableName.equals("procedure_history")) {

                        while (true) {
                            System.out.println(
                                    "Enter the 5 digit MRN of the record you want to delete (e to cancel process): ");
                            valueToDelete = scan.next();

                            // making sure MRN is numeric and 5 digits long, otherwise demand reinput
                            if (!valueToDelete.matches("\\d+")) {
                                System.out.println("Error: invalid input! Value must be numeric");
                            } else if (valueToDelete.length() != 5) {
                                System.out.println("Error! invalid input! Value must be 5 numbers long");
                            } else if (valueToDelete.equalsIgnoreCase("e")) {
                                System.exit(0);
                            } else {
                                fieldToDelete = "MRN";
                                valueToDelete = "'MRN" + valueToDelete + "'";
                                break;
                            }

                        }
                    } else {
                        while (true) {
                            System.out.println("Enter the PID of the record you want to delete: ");
                            valueToDelete = scan.next();

                            if (!valueToDelete.matches("\\d+")) {
                                System.out.println("Error: invalid input! Value must be numeric");
                            } else if (valueToDelete.length() != 4) {
                                System.out.println("Error! invalid input! Value must be 5 numbers long");
                            } else if (valueToDelete.equalsIgnoreCase("e")) {
                                System.exit(0);
                            } else {
                                fieldToDelete = "`Procedure ID`";
                                valueToDelete = "'P" + valueToDelete + "'";
                                break;
                            }
                        }
                    }

                    PreparedStatement stmt = conn
                            .prepareStatement(
                                    "DELETE from " + tableName + " WHERE " + fieldToDelete + " = " + valueToDelete);

                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Patient record deleted successfully.");
                        break;
                    } else {
                        System.out.println("No patient found with that MRN.");
                        while (true) {
                            System.out.println("Try again? (y or n): ");
                            String input = scan.next();
                            if (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                                System.out.println("Error: invalid input");
                            } else if (input.equalsIgnoreCase("n")) {
                                System.exit(0);
                            } else
                                break;
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        };
    }
}
