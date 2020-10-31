package p2.dao;

import p2.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class CustomerDAOImpl extends BaseDAO implements CustomerDAO {
    @Override
    public void insert(Connection conn, Customer customer) {
        String sql = "insert into customer(name,email,birth) values(?,?,?)";
        super.action(conn, sql, customer.getName(), customer.getEmail(), customer.getBirth());
    }

    @Override
    public void deleteById(Connection conn, int id) {
        String sql = "delete from customer where id = ?";
        super.action(conn, sql, id);
    }

    @Override
    public void update(Connection conn, Customer customer) {
        String sql = "update customer set name = ?,email = ?,birth = ? where id = ?";
        super.action(conn, sql, customer.getName(), customer.getEmail(), customer.getBirth(), customer.getId());
    }

    @Override
    public Customer getCustomerById(Connection conn, int id) {
        String sql = "select id,name,email,birth from customer where id = ?";
        return super.getInstance(conn, Customer.class, sql, id);
    }

    @Override
    public List<Customer> getAll(Connection conn) {
        String sql = "select id,name,email,birth from customer";
        return super.getForList(conn, Customer.class, sql);
    }

    @Override
    public Long getCount(Connection conn) {
        String sql = "select count(*) from customer";
        return super.getValue(conn, sql);
    }

    @Override
    public Date getMaxBirth(Connection conn) {
        String sql = "select max(birth) from customer";
        return super.getValue(conn, sql);
    }
}
