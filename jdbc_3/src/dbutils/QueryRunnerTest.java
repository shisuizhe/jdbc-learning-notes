package dbutils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import org.junit.Test;

import dbutils.bean.Customer;
import util.JDBCUtils;

/*
 * commons-dbutils 是 Apache 组织提供的一个开源 JDBC 工具类库，封装了针对于数据库的增删改查操作
 */

public class QueryRunnerTest {

    // 测试插入
    @Test
    public void testInsert() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection3();
            QueryRunner runner = new QueryRunner();
            String sql = "insert into customer(name,email,birth) values(?,?,?)";
            int insertCount = runner.update(conn, sql, "蔡徐坤", "caixukun@126.com", "1997-09-08");
            System.out.println("添加了" + insertCount + "条记录");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    // 测试查询
    /*
     * BeanHander：是ResultSetHandler接口的实现类，用于封装表中的一条记录。
     */
    @Test
    public void testQuery1() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection3();
            QueryRunner runner = new QueryRunner();
            String sql = "select id,name,email,birth from customer where id = ?";
            BeanHandler<Customer> handler = new BeanHandler<>(Customer.class);
            Customer customer = runner.query(conn, sql, handler, 18);
            System.out.println(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    /*
     * BeanListHandler：是ResultSetHandler接口的实现类，用于封装表中的多条记录构成的集合。
     */
    @Test
    public void testQuery2() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection3();
            QueryRunner runner = new QueryRunner();
            String sql = "select id,name,email,birth from customer where id < ?";
            BeanListHandler<Customer> handler = new BeanListHandler<>(Customer.class);
            List<Customer> list = runner.query(conn, sql, handler, 10);
            list.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    /*
     * MapHander：是ResultSetHandler接口的实现类，对应表中的一条记录。
     * 将字段及相应字段的值作为map中的key和value
     */
    @Test
    public void testQuery3() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection3();
            QueryRunner runner = new QueryRunner();
            String sql = "select id,name,email,birth from customer where id = ?";
            MapHandler handler = new MapHandler();
            Map<String, Object> map = runner.query(conn, sql, handler, 18);
            System.out.println(map);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    /*
     * MapListHander：是ResultSetHandler接口的实现类，对应表中的多条记录。
     * 将字段及相应字段的值作为map中的key和value。将这些map添加到List中
     */
    @Test
    public void testQuery4() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection3();
            QueryRunner runner = new QueryRunner();
            String sql = "select id,name,email,birth from customer where id < ?";
            MapListHandler handler = new MapListHandler();
            List<Map<String, Object>> list = runner.query(conn, sql, handler, 10);
            list.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    /*
     * ScalarHandler：用于查询特殊值
     */
    @Test
    public void testQuery5() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection3();
            QueryRunner runner = new QueryRunner();
            String sql = "select count(*) from customer";
            ScalarHandler<Long> handler = new ScalarHandler<>();
            Long count = runner.query(conn, sql, handler);
            System.out.println(count);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    @Test
    public void testQuery6() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection3();
            QueryRunner runner = new QueryRunner();
            String sql = "select max(birth) from customer";
            ScalarHandler<Date> handler = new ScalarHandler<>();
            Date maxBirth = runner.query(conn, sql, handler);
            System.out.println(maxBirth);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    /*
     * 自定义ResultSetHandler的实现类
     */
    @Test
    public void testQuery7() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection3();
            QueryRunner runner = new QueryRunner();

            String sql = "select id,name,email,birth from customer where id = ?";

            ResultSetHandler<Customer> handler = new ResultSetHandler<Customer>() {
                @Override
                public Customer handle(ResultSet rs) throws SQLException {
                    if (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        String email = rs.getString("email");
                        Date birth = rs.getDate("birth");
                        return new Customer(id, name, email, birth);
                    }
                    return null;
                }
            };

            Customer customer = runner.query(conn, sql, handler, 18);
            System.out.println(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }
}
