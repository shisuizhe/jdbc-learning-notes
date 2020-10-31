package p1.conn;

import com.mysql.cj.jdbc.Driver;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnTest {
    // 方式一
    @Test
    public void testConn1() throws Exception {
        // 1.提供要连接的数据库
        String url = "jdbc:mysql://localhost:3306/learn_jdbc";

        // 2.将用户名和密码封装在Properties中
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "123456");

        // 3.获取Driver实现类对象
        Driver driver = new Driver();

        // 4.获取连接
        Connection conn = driver.connect(url, props);
        System.out.println(conn);
    }


    // 方式二：对方式一的迭代；在如下的程序中不出现第三方的api，使得程序具有更好的可移植性
    @Test
    public void testConn2() throws Exception {
        // 1.提供要连接的数据库
        String url = "jdbc:mysql://localhost:3306/learn_jdbc";

        // 2.提供连接需要的用户名和密码
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "123456");

        // 3.获取Driver实现类对象：使用反射
        Class<?> clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        // 4.获取连接
        Connection conn = driver.connect(url, props);
        System.out.println(conn);
    }


    // 方式三：使用DriverManager替换Driver
    @Test
    public void testConn3() throws Exception {
        // 1.提供连接的基本信息
        String url = "jdbc:mysql://localhost:3306/learn_jdbc";
        String user = "root";
        String password = "123456";

        // 2.获取Driver实现类对象：使用反射
        Class<?> clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        // 3.注册驱动
        DriverManager.registerDriver(driver);

        // 4.获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }


    // 方式四
    @Test
    public void testConn4() throws Exception {
        // 1.提供连接的基本信息
        String url = "jdbc:mysql://localhost:3306/learn_jdbc";
        String user = "root";
        String password = "123456";

        // 相较于方式三，可以省略如下的操作：
        // Class<?> clazz = Class.forName("com.mysql.cj.jdbc.Driver");
		// Driver driver = (Driver) clazz.newInstance();
        // DriverManager.registerDriver(driver);

        // 为什么可以省略上述操作呢？
        // 在mysql的Driver实现类中，声明了如下的操作：
        // static {
        //     try {
        //         java.sql.DriverManager.registerDriver(new Driver());
        //     } catch (SQLException E) {
        //         throw new RuntimeException("Can't register driver!");
        //     }
        // }

        // 2.获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }

    // 方式五（最终版）：将数据库连接需要的基本信息声明在配置文件中，通过读取配置文件的方式，获取连接
    @Test
    public void testConn5() throws Exception {
        // 1.读取配置文件中的基本信息
        InputStream is = ConnTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

        Properties props = new Properties();
        props.load(is);

        String user = props.getProperty("user");
        String password = props.getProperty("password");
        String url = props.getProperty("url");
        // String driverClass = props.getProperty("driverClass");

        // 2.加载Driver（不需要也可以连接成功）
        // Class.forName(driverClass);

        // 3.获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }
}
