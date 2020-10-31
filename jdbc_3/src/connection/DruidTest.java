package connection;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class DruidTest {
    @Test
    public void getConnection() throws Exception{
        Properties props = new Properties();

        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");

        // FileInputStream is = new FileInputStream("src/druid.properties");

        props.load(is);

        DataSource source = DruidDataSourceFactory.createDataSource(props);

        Connection conn = source.getConnection();
        System.out.println(conn);
    }
}
