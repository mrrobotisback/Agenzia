package model.dao.mySQLJDBCImpl;

import model.dao.OrderDAO;
import model.mo.Order;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOMySQLJDBCImpl implements OrderDAO {
    Connection conn;

    public OrderDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }

    public Order insert(
            Double total,
            String date,
            Long userId,
            String with
    ) {

        PreparedStatement ps;
        Order order = new Order();
        order.setTotal(total);
        order.setDate(date);
        order.setUserId(userId);
        order.setWith(with);

        try {

            String sql
                    = " INSERT INTO `order` "
                    + "   ( "
                    + "     total,"
                    + "     date,"
                    + "     userId"
                    + "   ) "
                    + " VALUES (?,?,?)";


            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            ps.setDouble(i++, order.getTotal());
            ps.setDate(i++, order.getDate());
            ps.setLong(i++, order.getUserId());


            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int generatedKey = 0;

            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

            sql
                    = " INSERT INTO payment "
                    + "   ( "
                    + "     number,"
                    + "     with,"
                    + "     date,"
                    + "     amount"
                    + "   ) "
                    + " VALUES (?,?,?,?)";

            ps = conn.prepareStatement(sql);
            i = 1;
            ps.setLong(i++, generatedKey);
            ps.setString(i++, order.getWith());
            ps.setDate(i++, order.getDate());
            ps.setDouble(i++, order.getTotal());


            ps.executeUpdate();

        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }

        return order;

    }

    @Override
    public boolean update(model.mo.Order order, String field, String value) {
        PreparedStatement ps;

        try {
            String sql
                    = "UPDATE order "
                    + "SET " + field + " = ?"
                    + "WHERE number = ?";


            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, value);
            ps.setLong(i++, order.getOrderNumber());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean update(model.mo.Payment payment, String field, String value) {
        PreparedStatement ps;

        try {
            String sql
                    = "UPDATE payment "
                    + "SET " + field + " = ?"
                    + "WHERE number = ?";


            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, value);
            ps.setLong(i++, payment.getNumber());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void delete(model.mo.Order order) {

        PreparedStatement ps;

        try {
            String sql
                    = " DELETE"
                    + " FROM order o inner join payment p on o.number = p.number"
                    + " WHERE "
                    + " number=?";

            ps = conn.prepareStatement(sql);
            if (order.getOrderNumber() != null) {
                ps.setLong(1, order.getOrderNumber());
                ps.executeUpdate();
                ps.close();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public model.mo.Order findByOrderNumber(Long number) {

        PreparedStatement ps;
        model.mo.Order order = null;

        try {

            String sql
                    = " SELECT * "
                    + "   FROM order o inner join payment p on o.number=p.number  "
                    + " WHERE "
                    + "   number = ?";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, number);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                order = read(resultSet);
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return order;

    }

    @Override
    public List<model.mo.Order> find (String field, String value) {
        PreparedStatement ps;
        model.mo.Order order;
        ArrayList<model.mo.Order> orders = new ArrayList<model.mo.Order>();

        try {

            String sql
                    = " SELECT *"
                    + "   FROM order o inner join payment p on o.number = p.number "
                    + " WHERE "
                    + " " + field + " like " + "'%" + value + "%'";

            ps = conn.prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                order = read(resultSet);
                orders.add(order);
            }

            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return orders;
    }

    public List<model.mo.Order> allOrder() {
        PreparedStatement ps;
        model.mo.Order order;
        ArrayList<model.mo.Order> orders= new ArrayList<model.mo.Order>();

        try {

            String sql
                    = " SELECT * "
                    + "   FROM `order` o inner join payment p on o.number = p.number";

            ps = conn.prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                order = read(resultSet);
                orders.add(order);
            }

            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }

    model.mo.Order read(ResultSet rs) {

        model.mo.Order order = new model.mo.Order();
        try {
            order.setOrderNumber(rs.getLong("number"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        try {
            order.setDate(rs.getString("date"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        try {
            order.setTotal(rs.getDouble("total"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        try {
            order.setWith(rs.getString("with"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return order;
    }
}
