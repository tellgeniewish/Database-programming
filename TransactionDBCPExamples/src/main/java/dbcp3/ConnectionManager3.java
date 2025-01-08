package dbcp3;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;

public class ConnectionManager3 {
	private static DataSource ds = null;
    	
    public ConnectionManager3() {
		try {
			Context init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/TestDB");
				// --> refer to resource settings in WebContent/META-INF/Context.xml
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
    	/*  아래는 불필요
    	InputStream input = null;
    	Properties prop = new Properties();

		try {
			input = getClass().getResourceAsStream("/context.properties");
			prop.load(input);			// load the properties file
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} 
		
		try {
    		// DataSource 생성 및 설정
			BasicDataSource bds = new BasicDataSource();
	        bds.setDriverClassName(prop.getProperty("db.driver"));
	        bds.setUrl(prop.getProperty("db.url"));
	        bds.setUsername(prop.getProperty("db.username"));
	        bds.setPassword(prop.getProperty("db.password"));   
	        
	        bds.setMaxTotal(10);
			bds.setInitialSize(10);
			bds.setMinIdle(5);
			bds.setMaxIdle(10);
			bds.setMaxWaitMillis(5000);
			
			ds = bds;
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		*/ 	   
    }

    public Connection getConnection() {
    	Connection conn = null;
    	try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
    }
}
