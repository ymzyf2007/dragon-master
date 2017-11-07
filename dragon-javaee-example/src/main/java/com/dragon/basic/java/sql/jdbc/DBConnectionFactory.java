package com.dragon.basic.java.sql.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionFactory {

	static {
		try {
			Class.forName(SystemConstants.driveName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(SystemConstants.url,
					SystemConstants.username, SystemConstants.password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	/**
	 * 释放连接
	 */
	public static void close(Connection conn, java.sql.Statement stmt,
			java.sql.ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (Exception e) {
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (Exception e) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (Exception e) {
			}
		}
	}

}