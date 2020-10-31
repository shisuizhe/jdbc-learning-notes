package p4.practice;

import util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Test1 {
    public static void main(String[] args) {
        Test1 test1 = new Test1();
        test1.testInsert();
    }

    public void testInsert() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String name = scanner.next();
        System.out.println("请输入邮箱：");
        String email = scanner.next();
        System.out.println("请输入生日：");
        String brith = scanner.next(); // 1992-09-08

        String sql = "INSERT INTO customer(name, email, birth) values(?, ?, ?)";
        int insertCount = update(sql, name, email, brith);
        if (insertCount > 0) {
            System.out.println("添加成功");
        } else {
            System.out.println("添加失败");
        }

    }

    public int update(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            // ps.execute():
            // 如果执行的是查询操作，有返回结果，则此方法返回true;
            // 如果执行的是增、删、改操作，没有返回结果，则此方法返回false；
            // 方式一:
            // return ps.execute();

            // 方式二：
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
        return 0;
    }
}
