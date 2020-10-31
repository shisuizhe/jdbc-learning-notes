package connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.junit.Test;

import java.sql.Connection;

public class C3p0Test {
    // 方式一
    @Test
    public void test1() throws Exception {
        // 获取c3p0数据库连接池
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.cj.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/learn_jdbc");
        cpds.setUser("root");
        cpds.setPassword("123456");
        // 通过设置相关的参数，对数据库连接池进行管理：
        // 设置初始时数据库连接池中的连接数
        cpds.setInitialPoolSize(10);

        Connection conn = cpds.getConnection();
        System.out.println(conn);

        // 销毁c3p0数据库连接池
        // DataSources.destroy(cpds);
    }

    // 方式二：使用配置文件
    @Test
    public void  test2() throws Exception {
        ComboPooledDataSource cpds = new ComboPooledDataSource("myc3p0");
        Connection conn = cpds.getConnection();
        System.out.println(conn);
    }
}
