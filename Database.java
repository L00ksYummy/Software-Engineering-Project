package org.yourcompany.softproj;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

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
                System.out.println(metaData.getColumnName(i) + ": " + rs.getString(i));
            }
            System.out.println();
        }
    }

}
