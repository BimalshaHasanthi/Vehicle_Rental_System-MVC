package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection dbCon;
    private final Connection con;

    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con= DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/RentalSystem",
                "root",
                "1234"
        );
    }
    public static DBConnection getInstance() throws SQLException, ClassNotFoundException {
        return dbCon == null ? dbCon =new DBConnection() : dbCon;
    }
    public Connection getConnection(){
        return con;
    }

}
