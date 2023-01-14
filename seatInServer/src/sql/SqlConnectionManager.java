package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Ale
 */
public final class SqlConnectionManager {
    
    //fields
    private final Connection connection;
    
    //builder
    public SqlConnectionManager(String dbHost, String dbUsername, String dbPassword) throws SQLException{
        connection = openConnection(dbHost,dbUsername,dbPassword);
        System.out.println("Connected to database: " + connection.getCatalog());
    }
    
    //-----------------------------------------------------------------------------------------------------
    
    private Connection openConnection(String url, String usr, String pwd) throws SQLException{
	Properties props= new Properties();
	props.setProperty("user", usr);
	props.setProperty("password", pwd);
		
	Connection conn = DriverManager.getConnection(url, props);
	return conn;
    }
    
    //-----------------------------------------------------------------------------------------------------
    
    public Connection getConnection(){
        return this.connection;
    }
    
    
}
