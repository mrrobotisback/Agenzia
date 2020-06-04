package model.dao.mySQLJDBCImpl;

import model.dao.CartDAO;
import model.mo.Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartDAOMySQLJDBCImpl implements CartDAO {

    Connection conn;

    public CartDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Cart insert(
            Long userId,
            Double total
    ) {

        PreparedStatement ps;
        Cart cart = new Cart();
        cart.setUserId(userId);

        try {

            String sql
                    = " SELECT userId "
                    + " FROM cart "
                    + " WHERE "
                    + " userid = ?";

            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setLong(i++, cart.getUserId());

            ResultSet resultSet = ps.executeQuery(); //eseguo la query appena creata
            boolean exist;
            exist = resultSet.next();
            resultSet.close();

            if (exist) {
                cart.setTotal(total);

                sql
                        = " INSERT INTO cart "
                        + "   ( "
                        + "     total"
                        + "   ) "
                        + " VALUES (?)";

                ps = conn.prepareStatement(sql);
                i = 1;
                ps.setDouble(i++, cart.getTotal());

                ps.executeUpdate();

                return cart;
            }

            sql
                    = " INSERT INTO cart "
                    + "   ( "
                    + "     userid,"
                    + "     total"
                    + "   ) "
                    + " VALUES (?,?)";

            ps = conn.prepareStatement(sql);
            i = 1;
            ps.setLong(i++, cart.getUserId());
            ps.setDouble(i++, cart.getTotal());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cart;

    }

    @Override
    public boolean update(Cart cart, String field, String value) {
        PreparedStatement ps;

        try {
            String sql
                    = "UPDATE cart "
                    + "SET " + field + " = ?"
                    + "WHERE userId = ?";


            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, value);
            ps.setLong(i++, cart.getUserId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void delete(Cart cart) {

        PreparedStatement ps;

        try {
            String sql
                    = " DELETE"
                    + " FROM cart"
                    + " WHERE "
                    + " userid = ?";

            ps = conn.prepareStatement(sql);
            if (cart.getUserId() != null) {
                ps.setLong(1, cart.getUserId());
                ps.executeUpdate();
                ps.close();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Cart findByUserId(Long id) {

        PreparedStatement ps;
        Cart cart = null;

        try {

            String sql
                    = " SELECT * "
                    + "   FROM cart "
                    + " WHERE "
                    + "   userid = ?";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                cart = read(resultSet);
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cart;

    }

    Cart read(ResultSet rs) {

        Cart cart = new Cart();

        try {
            cart.setUserId(rs.getLong("userid"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        try {
            cart.setTotal(rs.getDouble("total"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return cart;
    }

}
