package connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

// 测试DBCP的数据库连接池技术
public class DbcpTest {

    // 方式一
    @Test
    public void test1() throws SQLException {
        // 创建了DBCP的数据库连接池
        BasicDataSource source = new BasicDataSource();

        // 设置基本信息
        source.setDriverClassName("com.mysql.cj.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/learn_jdbc");
        source.setUsername("root");
        source.setPassword("123456");

        // 还可以设置其他涉及数据库连接池管理的相关属性：
        source.setInitialSize(10);
        // ...

        Connection conn = source.getConnection();
        System.out.println(conn);
    }

    // 方式二：推荐：使用配置文件
    @Test
    public void test2() throws Exception{
        Properties props = new Properties();

        // 方式1：
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
        // 方式2：
        // FileInputStream is = new FileInputStream("src/dbcp.properties");

        props.load(is);
        DataSource source = BasicDataSourceFactory.createDataSource(props);

        Connection conn = source.getConnection();
        System.out.println(conn);
    }
}
