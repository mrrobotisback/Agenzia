package java.model.dao.mySQLJDBCImpl;

import model.dao.OrderDAO;
import model.dao.PaymentDAO;
import model.mo.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOMySQLJDBCImpl implements OrderDAO, PaymentDAO {
    Connection conn;

    public OrderDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }

    public Order insert(
            Double total,
            String date,
            Long userId
    ) {

        PreparedStatement ps;
        Order order = new Order();
        order.setTotal(total);
        order.setDate(date);
        order.setUserId(userId);

        try {

            String sql
                    = " INSERT INTO category "
                    + "   ( "
                    + "     name,"
                    + "     description"
                    + "   ) "
                    + " VALUES (?,?)";


            ps = conn.prepareStatement(sql);
            i = 1;
            ps.setString(i++, category.getName());
            ps.setString(i++, category.getDescription());


            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return category;

    }

    @Override
    public boolean update(model.mo.Category category, String field, String value) {
        PreparedStatement ps;

        try {
            String sql
                    = "UPDATE category "
                    + "SET " + field + " = ?"
                    + "WHERE id = ?";


            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, value);
            ps.setLong(i++, category.getCategoryId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void delete(model.mo.Category category) {

        PreparedStatement ps;

        try {
            String sql
                    = " DELETE"
                    + " FROM category"
                    + " WHERE "
                    + " id=?";

            ps = conn.prepareStatement(sql);
            if (category.getCategoryId() != null) {
                ps.setLong(1, category.getCategoryId());
                ps.executeUpdate();
                ps.close();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public model.mo.Category findByCategoryId(Long id) {

        PreparedStatement ps;
        model.mo.Category category = null;

        try {

            String sql
                    = " SELECT * "
                    + "   FROM category "
                    + " WHERE "
                    + "   id = ?";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                category = read(resultSet);
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return category;

    }

    @Override
    public List<model.mo.Category> find (String field, String value) {
        PreparedStatement ps;
        model.mo.Category category;
        ArrayList<model.mo.Category> categories = new ArrayList<model.mo.Category>();

        try {

            String sql
                    = " SELECT *"
                    + "   FROM category "
                    + " WHERE "
                    + " " + field + " like " + "'%" + value + "%'";

            ps = conn.prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                category = read(resultSet);
                categories.add(category);
            }

            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return categories;
    }

    public List<model.mo.Category> allCategory() {
        PreparedStatement ps;
        model.mo.Category category;
        ArrayList<model.mo.Category> categories= new ArrayList<model.mo.Category>();

        try {

            String sql
                    = " SELECT * "
                    + "   FROM category ";

            ps = conn.prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                category = read(resultSet);
                categories.add(category);
            }

            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }

    model.mo.Category read(ResultSet rs) {

        model.mo.Category category = new model.mo.Category();
        try {
            category.setCategoryId(rs.getLong("id"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        try {
            category.setName(rs.getString("name"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        try {
            category.setDescription(rs.getString("description"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return category;
    }
}
