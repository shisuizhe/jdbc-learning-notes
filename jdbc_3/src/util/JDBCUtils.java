package util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

// 操作数据库的工具类
public class JDBCUtils {

    // 使用C3P0的数据库连接池技术
    private static ComboPooledDataSource cpds = new ComboPooledDataSource("myc3p0");

    public static Connection getConnection1() throws SQLException {
        return cpds.getConnection();
    }

    // 使用DBCP数据库连接池技术获取数据库连接
    private static DataSource source;

    static {
        try {
            Properties props = new Properties();
            FileInputStream is = new FileInputStream("src/dbcp.properties");
            props.load(is);
            source = BasicDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection2() throws Exception {
        return source.getConnection();
    }

    // 使用Druid数据库连接池技术
    private static DataSource source1;

    static {
        try {
            Properties props = new Properties();
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            props.load(is);
            source1 = DruidDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection3() throws SQLException {
        return source1.getConnection();
    }

    // 使用 commons-dbutils.jar 中提供的DbUtils工具类，实现资源的关闭
    public static void closeResource(Connection conn, Statement ps, ResultSet rs) {
        // try {
        // 	DbUtils.close(conn);
        // } catch (SQLException e) {
        // 	e.printStackTrace();
        // }
        // try {
        // 	DbUtils.close(ps);
        // } catch (SQLException e) {
        // 	e.printStackTrace();
        // }
        // try {
        // 	DbUtils.close(rs);
        // } catch (SQLException e) {
        // 	e.printStackTrace();
        // }

        DbUtils.closeQuietly(conn);
        DbUtils.closeQuietly(ps);
        DbUtils.closeQuietly(rs);
    }

    // ****************************************************************************************************************

    // 获取数据库的连接
    public static Connection getConnection() throws Exception {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties props = new Properties();
        props.load(is);

        String user = props.getProperty("user");
        String password = props.getProperty("password");
        String url = props.getProperty("url");
        String driverClass = props.getProperty("driverClass");

        Class.forName(driverClass);

        return DriverManager.getConnection(url, user, password);
    }

    // 关闭连接和Statement的操作
    public static void closeResource(Connection conn, Statement ps) {
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 关闭资源操作
    public static void closeResource1(Connection conn, Statement ps, ResultSet rs) {
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

