package p3.preparedstatement.curd;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.junit.Test;
import p3.preparedstatement.bean.Order;
import util.JDBCUtils;

// 针对于Order表的通用的查询操作
public class OrderForQuery {
    /*
     * 针对于表的字段名与类的属性名不相同的情况：
     *
     * 1. 必须声明sql时，使用类的属性名来命名字段的别名
     * 2. 使用ResultSetMetaData时，需要使用getColumnLabel()来替换getColumnName()，获取列的别名。
     *
     * 说明：如果sql中没有给字段其别名，getColumnLabel()获取的就是列名
     *
     */
    @Test
    public void testOrderForQuery() {
        // sql语句字段设置别名
        String sql = "select order_id orderId, order_name orderName, order_date orderDate from `order` where order_id = ?";
        Order order = orderForQuery(sql, 2);
        System.out.println(order); // Order{orderId=2, orderName='BB', orderDate=2000-02-01}
    }

    // 通用的针对于order表的查询操作
    public Order orderForQuery(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();

            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();

            // 获取结果集的元数据: ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();

            if (rs.next()) {
                Order order = new Order();
                // 处理结果集一行数据中的每一个列
                for (int i = 0; i < columnCount; i++) {
                    // 获取每列的列值：通过ResultSet
                    Object columValue = rs.getObject(i + 1);

                    // 通过ResultSetMetaData
                    // 获取列的列名：getColumnName() -- 不推荐使用
                    // 获取列的别名：getColumnLabel() -- 效果同上
                    // String columnName = rsmd.getColumnName(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    // 通过反射：给order对象指定的columnLabel属性，赋值为columValue
                    Field field = Order.class.getDeclaredField(columnLabel); // 拿到对应的属性名
                    field.setAccessible(true);      // 设置私有属性也可以被访问
                    field.set(order, columValue);   // 将当前属性名的值赋值给customer
                }
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }


    @Test
    public void testQuery1() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select order_id,order_name,order_date from `order` where order_id = ?"; // sql语句字段没有设置别名
            ps = conn.prepareStatement(sql);
            ps.setObject(1, 1);
            rs = ps.executeQuery();
            if (rs.next()) {
                int id = (int) rs.getObject(1);
                String name = (String) rs.getObject(2);
                Date date = (Date) rs.getObject(3);
                Order order = new Order(id, name, date);
                System.out.println(order); // Order{orderId=1, orderName='AA', orderDate=2010-03-04}
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
    }
}
