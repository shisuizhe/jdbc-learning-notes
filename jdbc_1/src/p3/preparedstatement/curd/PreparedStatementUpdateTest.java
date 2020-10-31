package p3.preparedstatement.curd;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.junit.Test;
import util.JDBCUtils;

/*
 * 使用PreparedStatement来替换Statement，实现对数据表的增删改操作
 *
 * 增删改 查
 *
 */
public class PreparedStatementUpdateTest {

    @Test
    public void testCommonUpdate() {
        String sql1 = "delete from customer where id = ?";
        update(sql1, 1);

        // String sql2 = "update order set order_name = ? where order_id = ?"; // 小心order关键字
        String sql2 = "update `order` set order_name = ? where order_id = ?";
        update(sql2, "CC", "3");
    }


    // 通用的增删改操作
    public void update(String sql, Object... args) { // sql中占位符的个数与可变形参的长度相同！
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // 获取数据库的连接
            conn = JDBCUtils.getConnection();
            // 预编译sql语句，返回PreparedStatement的实例
            ps = conn.prepareStatement(sql);
            // 填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            // 执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 资源的关闭
            JDBCUtils.closeResource(conn, ps);
        }
    }


    // 修改customer表的一条记录
    @Test
    public void testUpdate() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // 获取数据库的连接
            conn = JDBCUtils.getConnection();
            // 预编译sql语句，返回PreparedStatement的实例
            String sql = "update customer set name = ? where id = ?";
            ps = conn.prepareStatement(sql);
            // 填充占位符
            ps.setObject(1, "莫扎特");
            ps.setObject(2, 1);
            // 执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 资源的关闭
            JDBCUtils.closeResource(conn, ps);
        }
    }


    // 向customer表中添加一条记录
    @Test
    public void testInsert() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // 读取配置文件基本信息
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

            Properties pros = new Properties();
            pros.load(is);

            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driverClass = pros.getProperty("driverClass");

            // 加载驱动
            Class.forName(driverClass);

            // 获取连接
            conn = DriverManager.getConnection(url, user, password);

            // 预编译sql语句，返回PreparedStatement的实例
            String sql = "insert into customer(name,email,birth) values(?,?,?)"; // ?:占位符
            ps = conn.prepareStatement(sql);

            // 填充占位符
            ps.setString(1, "哪吒");
            ps.setString(2, "nezha@gmail.com");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse("2000-01-01");
            ps.setDate(3, new Date(date.getTime()));

            // 执行操作
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 资源的关闭
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
    }
}
