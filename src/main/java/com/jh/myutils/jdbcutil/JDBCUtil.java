package com.jh.myutils.jdbcutil;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {

	private static String USERNAME;
	private static String PASSWORD;
	private static String DRIVER;
	private static String URL;
	
	private static Connection connection;
	private static PreparedStatement pstmt;
	
	static {
		loadConfig();
	}
	
	public static void loadConfig() {
		try {
			InputStream is = JDBCUtil.class.getClassLoader().getResourceAsStream("config.properties");
			Properties prop = new Properties();
			prop.load(is);
			USERNAME = prop.getProperty("jdbc.username");
			PASSWORD = prop.getProperty("jdbc.password");
			DRIVER = prop.getProperty("jdbc.driver");
			URL = prop.getProperty("jdbc.url");
		} catch(Exception e) {
			throw new RuntimeException("读取数据库文件异常", e);
		}
	}
	
	public static Connection getConnection() {
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch(Exception e) {
			throw new RuntimeException("get connection error!", e);
		}
		return connection;
	}
	
	public static boolean executeUpdate(String sql, Object... params) throws SQLException {
		if(connection == null) {
			connection = getConnection();
		}
		pstmt = connection.prepareStatement(sql);
		boolean flag = false;
		int result = -1; // 表示执行插入删除更新影响数据库行数
		if(params != null) {
			for(int i = 0; i < params.length; i++) {
				pstmt.setObject(i+1, params[i]);
			}
		}
		result = pstmt.executeUpdate();
		flag = result > 0 ? true : false;
		return flag;
	}
	
	public static ResultSet executeQuery(String sql, Object... params) throws SQLException {
		if(connection == null) {
			connection = getConnection();
		}
		pstmt = connection.prepareStatement(sql);
		if(params != null) {
			for(int i = 0; i < params.length; i++) {
				pstmt.setObject(i+1, params[i]);
			}
		}
		ResultSet resultSet = pstmt.executeQuery();
		return resultSet;
	}
	
	public static void closeConnection(){
    	if(connection != null) {
    		try {
    			connection.close();
    			connection = null;
			} catch (SQLException e) {
				throw new RuntimeException("关闭数据库连接异常", e);
			}
    	}
    }
	
	public static void close(ResultSet rs) {
    	if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new RuntimeException("关闭结果集异常", e);
			}
		}
    	if(pstmt != null) {
			try {
				pstmt.close();
				pstmt = null;
			} catch (SQLException e) {
				throw new RuntimeException("关闭PreparedStatement异常", e);
			}
		}
    }
}
