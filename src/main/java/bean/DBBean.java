package bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 完成与数据库的连接和数据的访问
 *
 */
public class DBBean {
	private String driverStr = "com.mysql.cj.jdbc.Driver";
	private String connStr = "jdbc:mysql://127.0.0.1:3306/crawler?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
	private String dbusername = "root";
	private String dbpassword = "123456";
	private Connection conn = null;
	private Statement stmt = null;

	public DBBean() {
		try {
			Class.forName(driverStr);
			conn = DriverManager.getConnection(connStr, dbusername, dbpassword);
			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 执行更新操作
	 * @param s
	 * SQL语句
	 * @return
	 * 更新操作的结果
	 */
	public int executeUpdate(String s) {
		int result = 0;
		try {
			result = stmt.executeUpdate(s);

			System.out.println(result);

		} catch (Exception ex) {
			System.out.println("failure_update");
		}
		return result;
	}
	
	/**
	 * 执行查询操作
	 * @param s
	 * SQL语句
	 * @return
	 * 查询结果
	 */
	public ResultSet executeQuery(String s) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(s);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
//		System.out.println("DBBean_rs:"+rs);
		return rs;
	}

	/**
	 * 关闭数据库
	 */
	public void close() {
		try {
			stmt.close();
			conn.close();
		} catch (Exception e) {
		}
	}
}
