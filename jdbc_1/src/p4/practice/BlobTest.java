package p4.practice;

import org.junit.Test;
import p3.preparedstatement.bean.Customer;
import util.JDBCUtils;

import java.io.*;
import java.sql.*;

// 测试使用PreparedStatement操作Blob类型的数据
public class BlobTest {

    // 向数据表customer中插入Blob类型的字段
    @Test
    public void testInsert() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        String sql = "insert into customer(name, email, birth, photo) values(?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setObject(1, "pink");
        ps.setObject(2, "pink@qq.com");
        ps.setObject(3, "2000-08-08");
        FileInputStream fis = new FileInputStream(new File("working-hard.jpg"));
        ps.setBlob(4, fis);

        ps.execute();
        JDBCUtils.closeResource(conn, ps);
    }

    // 查询数据表customer中Blob类型的字段
    @Test
    public void testQuery() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id, name, email, birth, photo from customer where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 16);
            rs = ps.executeQuery();
            if (rs.next()) {
                // 方式一：
                // int id = rs.getInt(1);
                // String name = rs.getString(2);
                // String email = rs.getString(3);
                // Date birth = rs.getDate(4);
                // 方式二：
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Date birth = rs.getDate("birth");

                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);

                // 将Blob类型的字段下载下来，以文件的方式保存在本地
                Blob photo = rs.getBlob("photo");
                is = photo.getBinaryStream();
                fos = new FileOutputStream("blob.jpg");
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            JDBCUtils.closeResource(conn, ps, rs);
        }
    }
}
