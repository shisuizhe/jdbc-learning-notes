package p3.preparedstatement.curd;

import org.junit.Test;
import p3.preparedstatement.bean.Customer;
import p3.preparedstatement.bean.Order;
import util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * 使用PreparedStatement来替换Statement，实现对数据表的增删改操作
 *
 * 增删改 查
 *
 */
public class PreparedStatementQueryTest {

    @Test
    public void testGetForList() {
        String sql = "select id,name,email from customer where id < ?";
        List<Customer> list = getForList(Customer.class, sql, 5);
        list.forEach(System.out::println);
        /*
         * Customer{id=2, name='王菲', email='wangf@163.com', birth=null}
         * Customer{id=3, name='林志玲', email='linzl@gmail.com', birth=null}
         * Customer{id=4, name='汤唯', email='tangw@sina.com', birth=null}
         */

        String sql1 = "select order_id orderId,order_name orderName from `order`";
        List<Order> orderList = getForList(Order.class, sql1);
        orderList.forEach(System.out::println);
        /*
         * Order{orderId=1, orderName='AA', orderDate=null}
         * Order{orderId=2, orderName='BB', orderDate=null}
         * Order{orderId=3, orderName='CC', orderDate=null}
         */
    }

    // 针对于不同的表的通用的查询操作，返回表中的多条记录
    public <T> List<T> getForList(Class<T> clazz, String sql, Object... args) {
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

            // 获取结果集的元数据 :ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();
            // 创建集合对象
            ArrayList<T> list = new ArrayList<T>();

            while (rs.next()) {
                T t = clazz.newInstance();
                // 处理结果集一行数据中的每一个列: 给t对象指定的属性赋值
                for (int i = 0; i < columnCount; i++) {
                    // 获取列值
                    Object columValue = rs.getObject(i + 1);
                    // 获取列名
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    // 给t对象指定的columnName属性，赋值为columValue
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columValue);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }


    @Test
    public void testGetInstance() {
        String sql = "select id,name,email from customer where id = ?";
        Customer customer = getInstance(Customer.class, sql, 2);
        System.out.println(customer); // Customer{id=2, name='王菲', email='wangf@163.com', birth=null}

        String sql1 = "select order_id orderId,order_name orderName from `order` where order_id = ?";
        Order order = getInstance(Order.class, sql1, 1);
        System.out.println(order); // Order{orderId=1, orderName='AA', orderDate=null}
    }

    // 针对于不同的表的通用的查询操作，返回表中的一条记录
    public <T> T getInstance(Class<T> clazz, String sql, Object... args) {
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

            // 获取结果集的元数据 :ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();

            if (rs.next()) {
                T t = clazz.newInstance();
                // 处理结果集一行数据中的每一个列
                for (int i = 0; i < columnCount; i++) {
                    // 获取列值
                    Object columValue = rs.getObject(i + 1);
                    // 获取列名
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    // 给t对象指定的columnName属性，赋值为columValue
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }
}
