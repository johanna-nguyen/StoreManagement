
package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author khanhnguyen
 */



public class DatabaseConnection {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    private Connection connection;

    static {
        try {

            URL = System.getenv("DB_URL");
            USER = System.getenv("DB_USER");
            PASSWORD = System.getenv("DB_PASSWORD");


            if (URL == null || USER == null || PASSWORD == null) {
                Properties prop = new Properties();
                InputStream is = DatabaseConnection.class
                        .getClassLoader()
                        .getResourceAsStream("config.properties");

                if (is == null) {
                    throw new RuntimeException("config.properties not found");
                }

                prop.load(is);
                URL = prop.getProperty("db.url");
                USER = prop.getProperty("db.user");
                PASSWORD = prop.getProperty("db.password");
            }

        } catch (Exception e) {
            throw new RuntimeException("Cannot load database configuration", e);
        }
    }
    
    /**
     * Opens a connection to the database.
     * @return 
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public void open() throws ClassNotFoundException, SQLException {
        // Load the JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establish the connection
        this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Closes the connection to the database.
     * @throws java.sql.SQLException
     */
    public void close() throws SQLException {
        if (this.connection != null && !this.connection.isClosed()) {
            this.connection.close();
        }
    }

    /**
     * Executes a query and returns a ResultSet.
     * @param sql
     * @return 
     * @throws java.sql.SQLException
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    /**
     * Executes an update and returns the number of affected rows.
     * @param sql
     * @return 
     * @throws java.sql.SQLException
     */
    public int executeUpdate(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate(sql);
    }
    
    public Connection getConnection() {
        return connection;
    }
}









