package p3.preparedstatement.curd;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Arrays;

import org.junit.Test;
import p3.preparedstatement.bean.Customer;
import util.JDBCUtils;

// 针对于Customers表的查询操作
public class CustomerForQuery {

    @Test
    public void testQueryForCustomers() {
        String sql = "select id,name,birth,email from customer where id = ?";
        Customer customer = queryForCustomers(sql, 2);
        System.out.println(customer);

        // sql = "select name,email from customer where name = ?";
        // Customer customer1 = queryForCustomers(sql, "周杰伦");
        // System.out.println(customer1);
    }


    // 针对于customer表的通用的查询操作
    public Customer queryForCustomers(String sql, Object... args) {
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
                Customer customer = new Customer();
                // 处理结果集一行数据中的每一个列
                for (int i = 0; i < columnCount; i++) {
                    // 获取每列的列值
                    Object columValue = rs.getObject(i + 1);

                    // 获取列的列名：getColumnName() -- 不推荐使用
					// String columnName = rsmd.getColumnName(i + 1);
                    // 获取列的别名：getColumnLabel() -- 效果同上
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    // 通过反射：给customer对象指定的columnLabel属性，赋值为columValue
                    Field field = Customer.class.getDeclaredField(columnLabel); // 拿到对应的属性名
                    field.setAccessible(true); // 设置私有属性也可以被访问
                    field.set(customer, columValue); // 将当前属性名的值赋值给customer
                }
                return customer;
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
        ResultSet resultSet = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth from customer where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, 2);

            // 执行，并返回结果集
            resultSet = ps.executeQuery();

            // 处理结果集
            // next(): 判断结果集的下一条是否有数据，如果有数据返回true，并指针下移；如果返回false，指针不会下移。
            if (resultSet.next()) {
                // 获取当前这条数据的各个字段值
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);

                // 方式一
                System.out.println("id=" + id + ", name=" + name + ", email=" + email + ", birth=" + birth);
                // id=2, name=王菲, email=wangf@163.com, birth=1988-12-26

                // 方式二
                Object[] data = new Object[]{id, name, email, birth};
                System.out.println(Arrays.toString(data)); // [2, 王菲, wangf@163.com, 1988-12-26]

                // 方式三：将数据封装为一个对象（推荐）
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer); // Customer{id=2, name='王菲', email='wangf@163.com', birth=1988-12-26}
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, resultSet);
        }
    }
}
