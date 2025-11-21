package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnection {
    private static Connection connect = null;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String userName = "root";
        String password = "";
        String serverName = "127.0.0.1";
        String portNumber = "3306";
        String dbName = "testpersonne";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;
        System.out.println(urlDB);
        return DriverManager.getConnection(urlDB, connectionProps);
    }
}