package com.testSSM.test.DaoImpl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * sqlite的工具类
 */
@Repository
public class SqliteUtil {
    /**
     * 数据源
     */
    @Resource
    private DataSource sqliteDataSource;

    /**
     * 获取数据库连接
     *
     * @return conn
     */
    public Connection getConnection() throws SQLException {
        Connection conn = sqliteDataSource.getConnection();
        conn.setAutoCommit(false);
        return conn;
    }

    /**
     * 关闭数据库连接
     */
    public void close(Connection conn, Statement stmt, ResultSet rs) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            rs = null;
        }

        if (null != stmt) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            stmt = null;
        }

        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            conn = null;
        }
    }
}
