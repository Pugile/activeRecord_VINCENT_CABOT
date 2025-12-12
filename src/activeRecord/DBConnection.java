package activeRecord;

import com.mysql.cj.jdbc.JdbcConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;

public class DBConnection {
    private String tableName = "personne";
    private String userName = "root";
    private String password = "";
    private String serverName = "127.0.0.1";
    private String portNumber = "3306";
    private static String dbname = "testpersonne";
    public static Connection instance;
    private DBConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbname;
        System.out.println(urlDB);
        instance = DriverManager.getConnection(urlDB, connectionProps);
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            new DBConnection();
        }
        return instance;
    }

    public static void setNomDB(String nomDB) throws SQLException, ClassNotFoundException {
        if (!Objects.equals(nomDB, dbname)) {
            dbname = nomDB;
            instance = null;
        }
    }
}