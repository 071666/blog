package com.yc.blog.dao;

import java.io.BufferedReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JDBC 源辰信息
 * 
 * @author navy
 * @2019年8月1日 步骤： 1、导包 2、加载驱动 3、建立连接 4、创建预编译执行语句块 5、给预编译执行语句块中的占位符赋值
 *            6、执行语句获取结果或结果集 7、处理结果或结果集 8、关闭资源
 */
public class DBHelper {

	// 加载驱动 - 只需要在类第一次加载的时候执行一次
	static {
		try {
			Class.forName(ReadConfig.getInstance().getProperty("driverClassName"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取连接的方法
	 * 
	 * @return 获取到的连接
	 */
	private Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(ReadConfig.getInstance().getProperty("url"), ReadConfig.getInstance());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 给预编译块语句中的占位符?赋值 不定参数
	 * 
	 * @param pstmt
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 */
	private void setParams(PreparedStatement pstmt, Object... params) {
		if (params == null || params.length <= 0) { // 说明没有参数给我， 也就意味着执行的SQL语句中没有占位符?
			return;
		}

		for (int i = 0, len = params.length; i < len; i++) {
			try {
				pstmt.setObject(i + 1, params[i]);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("第 " + (i + 1) + " 个参数注值失败...");
			}
		}
	}

	/**
	 * 给预编译块语句中的占位符?赋值
	 * 
	 * @param pstmt  要赋值的预编译对象
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 */
	private void setParams(PreparedStatement pstmt, List<Object> params) {
		if (params == null || params.isEmpty()) { // 说明没有参数给我， 也就意味着执行的SQL语句中没有占位符?
			return;
		}

		for (int i = 0, len = params.size(); i < len; i++) {
			try {
				pstmt.setObject(i + 1, params.get(i));
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("第 " + (i + 1) + " 个参数注值失败...");
			}
		}
	}

	/**
	 * 关闭资源的方法
	 * 
	 * @param rs    要关闭的结果集
	 * @param pstmt 要关闭的预编译对象
	 * @param con   要关闭的连接
	 */
	private void close(ResultSet rs, PreparedStatement pstmt, Connection con) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 更新操作 可以是insert、delete、update 不定参数
	 * 
	 * @param sql    要执行的更新语句，
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return
	 */
	public int update(String sql, Object... params) { // 采用不定参数形式
		int result = -1;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = this.getConnection();

			pstmt = con.prepareStatement(sql); // 预编译执行语句
			this.setParams(pstmt, params); // 给预编译执行语句中的占位符赋值
			result = pstmt.executeUpdate(); // 执行更新

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(null, pstmt, con);
		}
		return result;
	}

	/**
	 * 更新操作
	 * 
	 * @param sql    要执行的更新语句，可以是insert、delete或update
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return
	 */
	public int update(String sql, List<Object> params) {
		int result = -1;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = this.getConnection();

			pstmt = con.prepareStatement(sql); // 预编译执行语句
			this.setParams(pstmt, params); // 给预编译执行语句中的占位符赋值
			result = pstmt.executeUpdate(); // 执行更新
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(null, pstmt, con);
		}
		return result;
	}

	/**
	 * 事务处理 针对与数据库的多条更新语句操作 执行更新 可以是insert、delete、update 采用不定参数形式
	 */
	public int updates(List<String> sqls, List<List<Object>> params) {
		int result = -1;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = this.getConnection();

			con.setAutoCommit(false); // JDBC默认事务自动提交 所以需要关闭自动事务提交

			for (int i = 0, len = sqls.size(); i < len; i++) {
				pstmt = con.prepareStatement(sqls.get(i)); // 预编译执行语句
				this.setParams(pstmt, params.get(i)); // 给预编译执行语句中的占位符赋值
				result = pstmt.executeUpdate(); // 执行更新
			}

			con.commit(); // 提交事务
		} catch (SQLException e) {
			try {
				con.rollback(); // 回滚事务
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				con.setAutoCommit(true); // 最终还是要开启自动事务提交
			} catch (SQLException e) {
				e.printStackTrace();
			}
			this.close(null, pstmt, con);
		}
		return result;
	}

	/**
	 * 获取结果集中所有列的类名
	 * 
	 * @param rs 结果集对象
	 * @return
	 * @throws SQLException
	 */
	private String[] getColumnNames(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData(); // 获取结果集中的元数据
		int colCount = rsmd.getColumnCount(); // 获取结果集中列的数量
		String[] colNames = new String[colCount];// 数组的长度等于查询语句中所查列的数量
		for (int i = 1; i <= colCount; i++) { // 循环获取结果集中列的名字
			// oracle中默认返回的所有列的列名是大写的，那么我们就其全部转成小写
			colNames[i - 1] = rsmd.getColumnName(i).toLowerCase(); // 将列名改成小写后存到数组中
		}
		return colNames;
	}

	/**
	 * 查询多条记录 不定参数
	 * 
	 * @param sql    要执行的查询语句
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return 满足条件的数据 每一条数据存到一个map中以列名为键，以对应列的值位置，然后将每一条数据即map对象存到list中
	 */
	public List<Map<String, Object>> finds(String sql, Object... params) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			rs = pstmt.executeQuery(); // 执行查询
			Map<String, Object> map = null;

			// 如果获取结果集中列的类名 -> 取到列名后我们存到一个数组中，便于后面的循环取值 -> 如何确定数组的大小?
			String[] colNames = this.getColumnNames(rs);

			Object obj = null; // 列的数据
			String colType = null; // 返回的这个列的数据的类型名称
			Blob blob = null;
			byte[] bt = null;
			while (rs.next()) {// 每循环一次就是一条记录，一条记录的信息就存到一个map中
				map = new HashMap<String, Object>();

				// 循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for (String colName : colNames) {
					// 首先我们不必获取返回的这个列的数据的类型是不是blob，若干是blob那么我们就转成字节数组将这个数据存到本地
					obj = rs.getObject(colName);

					if (obj == null) {
						map.put(colName, obj);
						continue;
					}

					// 获取这个列值对象的类型
					colType = obj.getClass().getSimpleName();
					if ("BLOB".equals(colType)) {
						// 用blob获取，然后转成字节数据
						blob = rs.getBlob(colName);
						bt = blob.getBytes(1, (int) blob.length());
						map.put(colName, bt);
					} else {
						map.put(colName, obj);
					}
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, con);
		}
		return list;
	}

	/**
	 * 查询多条数据的方法
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> finds(List<Object> params, String sql) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			rs = pstmt.executeQuery(); // 执行查询
			Map<String, Object> map = null;

			// 如果获取结果集中列的类名 -> 取到列名后我们存到一个数组中，便于后面的循环取值 -> 如何确定数组的大小?
			String[] colNames = this.getColumnNames(rs);

			Object obj = null; // 列的数据
			String colType = null; // 返回的这个列的数据的类型名称
			Blob blob = null;
			byte[] bt = null;
			while (rs.next()) { // 每次循环得到一行数据
				map = new HashMap<String, Object>();

				// 循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for (String colName : colNames) {
					// 首先我们不必获取返回的这个列的数据的类型是不是blob，若干是blob那么我们就转成字节数组将这个数据存到本地
					obj = rs.getObject(colName);

					if (obj == null) {
						map.put(colName, obj);
						continue;
					}

					// 获取这个列值对象的类型
					colType = obj.getClass().getSimpleName();
					if ("BLOB".equals(colType)) {
						// 用blob获取，然后转成字节数据
						blob = rs.getBlob(colName);
						bt = blob.getBytes(1, (int) blob.length());
						map.put(colName, bt);
					} else {
						map.put(colName, obj);
					}
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, con);
		}
		return list;
	}

	/**
	 * 查询单行 不定参数
	 * 
	 * @param sql    要执行的查询语句
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return 满足条件的数据 每一条数据存到一个map中以列名为键，以对应列的值位置，然后将每一条数据即map对象存到list中
	 */
	public Map<String, Object> find(String sql, Object... params) {
		Map<String, Object> map = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			rs = pstmt.executeQuery(); // 执行查询

			// 如果获取结果集中列的类名 -> 取到列名后我们存到一个数组中，便于后面的循环取值 -> 如何确定数组的大小?
			String[] colNames = this.getColumnNames(rs);

			Object obj = null; // 列的数据
			String colType = null; // 返回的这个列的数据的类型名称
			Blob blob = null;
			byte[] bt = null;

			if (rs.next()) { // 每次循环得到一行数据
				map = new HashMap<String, Object>();

				// 循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for (String colName : colNames) {
					// 首先我们不必获取返回的这个列的数据的类型是不是blob，若干是blob那么我们就转成字节数组将这个数据存到本地
					obj = rs.getObject(colName);

					if (obj == null) {
						map.put(colName, obj);
						continue;
					}

					// 获取这个列值对象的类型
					colType = obj.getClass().getSimpleName();
					if ("BLOB".equals(colType)) {
						// 用blob获取，然后转成字节数据
						blob = rs.getBlob(colName);
						bt = blob.getBytes(1, (int) blob.length());
						map.put(colName, bt);
					} else if ("java.math.BigDecimal".equals(obj.getClass().getName())) {// 类型比较
						// 新增的类型比较如果是大数据直接存字符串
						map.put(colName, rs.getString(colName));
					} else {
						map.put(colName, obj);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, con);
		}
		return map;
	}

	/**
	 * 查询单行 多条件查询
	 * 
	 * @param sql    要执行的查询语句
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return 满足条件的数据 每一条数据存到一个map中以列名为键，以对应列的值位置，然后将每一条数据即map对象存到list中
	 */
	public Map<String, Object> find(String sql, List<Object> params) {
		Map<String, Object> map = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			rs = pstmt.executeQuery(); // 执行查询

			// 如果获取结果集中列的类名 -> 取到列名后我们存到一个数组中，便于后面的循环取值 -> 如何确定数组的大小?
			String[] colNames = this.getColumnNames(rs);

			Object obj = null; // 列的数据
			String colType = null; // 返回的这个列的数据的类型名称
			Blob blob = null;
			byte[] bt = null;

			if (rs.next()) { // 每次循环得到一行数据
				map = new HashMap<String, Object>();

				// 循环获取每一列的值，循环所有的列名，根据列名获取当前这一行这一列的值
				for (String colName : colNames) {
					// 首先我们不必获取返回的这个列的数据的类型是不是blob，若干是blob那么我们就转成字节数组将这个数据存到本地
					obj = rs.getObject(colName);

					if (obj == null) {
						map.put(colName, obj);
						continue;
					}

					// 获取这个列值对象的类型
					colType = obj.getClass().getSimpleName();
					if ("BLOB".equals(colType)) {
						// 用blob获取，然后转成字节数据
						blob = rs.getBlob(colName);
						bt = blob.getBytes(1, (int) blob.length());
						map.put(colName, bt);
					} else {
						map.put(colName, obj);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, con);
		}
		return map;
	}

	/**
	 * 获取总记录数的方法 一行一列 为分页查询准备
	 * 
	 * @param sql    要执行的查询语句
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return 总记录数
	 */
	public int total(String sql, Object... params) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			rs = pstmt.executeQuery(); // 执行查询
			if (rs.next()) { // 每次循环得到一行数据
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, con);
		}
		return result;
	}

	/**
	 * 获取总记录数的方法 一行一列 为分页查询准备 多条件
	 * 
	 * @param sql    要执行的查询语句
	 * @param params 要执行的sql语句中对应占位符?的值，即按照?的顺序给定的值
	 * @return 总记录数
	 */
	public int total(String sql, List<Object> params) {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection(); // 获取连接
			pstmt = con.prepareStatement(sql); // 预编译语句
			this.setParams(pstmt, params); // 给预编译语句中的占位符赋值
			rs = pstmt.executeQuery(); // 执行查询
			if (rs.next()) { // 每次循环得到一行数据
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, con);
		}
		return result;
	}

	/**
	 * DB2.0以对象的方式返回数据 c.newInstance() new XX() 
	 * 获取当前class实例的所有属性和方法 m.invoke(obj,
	 * value) 激活m方法
	 * 
	 * @param <T>
	 * @param c
	 * @param sql
	 * @param params
	 * @return
	 */
	public <T> List<T> findMultiples(Class c, String sql, Object... params) {
		List<T> list = new ArrayList<T>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection();
			pstmt = con.prepareStatement(sql);
			this.setParams(pstmt, params);// 参数注入
			rs = pstmt.executeQuery();
			// 获取所有的列名
			String[] columnNames = this.getColumnNames(rs);

			T t = null;// 声明一个对象
			Object obj = null;// 对应列的值
			String typeName = null;// 对应列的值类型
			// 通过反射获取类中所有的方法methods
			Method[] methods = c.getDeclaredMethods();
			// 处理结果集
			while (rs.next()) {
				// 创建一个对象
				t = (T) c.newInstance();// 调用无参数的构造方法 AdminInfo bean = new AdminInfo();
				// 循环列
				for (String columnName : columnNames) {
					// 通过列名获取对应的列的值
					obj = rs.getObject(columnName);
					// 赋值给对象中的某一个私有化属性 -> 循环所有的方法
					for (Method m : methods) {
						// 是否有对应的setXX 方法名 set + columnName
						String name = "set" + columnName;// set + XX;
						// 判断方法名是否一致
						if (name.equalsIgnoreCase(m.getName())) {
							if (null == obj) {
								continue;
							}
							// 获取对应值的类型
							typeName = obj.getClass().getName();
							System.out.println(typeName);
							// 判断数据类型
							if ("java.math.BigDecimal".equals(typeName)) {
								try {
									m.invoke(t, rs.getInt(columnName));
								} catch (Exception e) {
									m.invoke(t, rs.getDouble(columnName));
								}
							} else if ("java.lang.Integer".equals(typeName)) {
								m.invoke(t, rs.getInt(columnName));
							} else if ("java.lang.Double".equals(typeName)) {
								m.invoke(t, rs.getDouble(columnName));
							} else if ("java.lang.String".equals(typeName)) {
								m.invoke(t, rs.getString(columnName));
							} else if ("java.lang.Date".equals(typeName)) {
								// MySQL 中date类型转换为JavaBean对象中使用String
								m.invoke(t, rs.getString(columnName));
							} else if ("oracle.sql.CLOB".equals(typeName)) {
								Reader in = rs.getCharacterStream(columnName);
								BufferedReader br = new BufferedReader(in);
								StringBuffer sb = new StringBuffer();
								String str = br.readLine();// 每次读一行数据
								while (null != str) {
									sb.append(str);
									str = br.readLine();
								}
								m.invoke(t, sb.toString());
							} else {
								// TODO 后期扩展
							}
						}
					}
				}
				list.add(t);// 设置对象到list集合
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, con);
		}
		return list;
	}

	/**
	 * DB2.0以对象的方式返回数据  多条件判断
	 * c.newInstance() new XX() 
	 * 获取当前class实例的所有属性和方法 
	 * m.invoke(obj,value) 激活m方法
	 * @param <T>
	 * @param c
	 * @param sql
	 * @param params
	 * @return
	 */
	public <T> T findMultiple(Class c, String sql, Object... params) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = this.getConnection();
			pstmt = con.prepareStatement(sql);
			this.setParams(pstmt, params);//参数注入
			rs = pstmt.executeQuery();
			//获取所有的列名
			String[] columnNames = this.getColumnNames(rs);
			
			T t = null;//声明一个对象
			Object obj = null;//对应列的值
			String typeName = null;//对应列的值的类型
			//通过反射获取类中的所有的方法methods
			Method[] methods = c.getDeclaredMethods();
			//处理结果集
			while (rs.next()) {
				// 创建一个对象
				t = (T) c.newInstance();// 调用无参数的构造方法 AdminInfo bean = new AdminInfo();
				// 循环列
				for (String columnName : columnNames) {
					// 通过列名获取对应的列的值
					obj = rs.getObject(columnName);
					// 赋值给对象中的某一个私有化属性 -> 循环所有的方法
					for (Method m : methods) {
						// 是否有对应的setXX 方法名 set + columnName
						String name = "set" + columnName;// set + XX;
						// 判断方法名是否一致
						if (name.equalsIgnoreCase(m.getName())) {
							if (null == obj) {
								continue;
							}
							// 获取对应值的类型
							typeName = obj.getClass().getName();
							System.out.println(typeName);
							// 判断数据类型
							if ("java.math.BigDecimal".equals(typeName)) {
								try {
									m.invoke(t, rs.getInt(columnName));
								} catch (Exception e) {
									m.invoke(t, rs.getDouble(columnName));
								}
							} else if ("java.lang.Integer".equals(typeName)) {
								m.invoke(t, rs.getInt(columnName));
							} else if ("java.lang.Double".equals(typeName)) {
								m.invoke(t, rs.getDouble(columnName));
							} else if ("java.lang.String".equals(typeName)) {
								m.invoke(t, rs.getString(columnName));
							} else if ("java.lang.Date".equals(typeName)) {
								// MySQL 中date类型转换为JavaBean对象中使用String
								m.invoke(t, rs.getString(columnName));
							} else if ("oracle.sql.CLOB".equals(typeName)) {
								Reader in = rs.getCharacterStream(columnName);
								BufferedReader br = new BufferedReader(in);
								StringBuffer sb = new StringBuffer();
								String str = br.readLine();// 每次读一行数据
								while (null != str) {
									sb.append(str);
									str = br.readLine();
								}
								m.invoke(t, sb.toString());
							} else {
								// TODO 后期扩展
							}
						}
					}
				}
				return t;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close(rs, pstmt, con);
		}
		return null;
	}
}
