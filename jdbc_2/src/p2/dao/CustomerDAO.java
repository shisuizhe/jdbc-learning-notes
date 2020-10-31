package p2.dao;

import p2.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

// 此接口用于规范针对于customers表的常用操作
public interface CustomerDAO {

    // 将cust对象添加到数据库中
    void insert(Connection conn, Customer customer);

    // 针对指定的id，删除表中的一条记录
    void deleteById(Connection conn, int id);

    // 针对内存中的cust对象，去修改数据表中指定的记录
    void update(Connection conn, Customer customer);

    // 针对指定的id查询得到对应的Customer对象
    Customer getCustomerById(Connection conn, int id);

    // 查询表中的所有记录构成的集合
    List<Customer> getAll(Connection conn);

    // 返回数据表中的数据的条目数
    Long getCount(Connection conn);

    // 返回数据表中最大的生日
    Date getMaxBirth(Connection conn);
}
