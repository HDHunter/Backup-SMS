package com.testSSM.test.DaoImpl;

import com.testSSM.test.dao.UserDao;
import com.testSSM.test.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository("user")
public class UserDaoImpl implements UserDao {
	@Autowired
	private SqliteUtil sUtil;

	private JdbcTemplate jdbcTemplate;

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	@Override
	public List<User> findUserList() {
		List<User> userList = null;
		try {
			conn = sUtil.getConnection();// 链接数据库
			stmt = conn.createStatement();// 创建数据库
			String sql = "select * from User ";// 编写sql语句
			rs = stmt.executeQuery(sql);
			User user;
			userList = new ArrayList<>();// 户政集合

			while (rs.next()) {
				user = new User();
				user.setId(rs.getLong("id"));
				user.setName(rs.getString("name"));
				user.setPhone(rs.getString("phone"));
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sUtil.close(conn, stmt, rs);// 关闭数据库
		}
		return userList;
	}
}