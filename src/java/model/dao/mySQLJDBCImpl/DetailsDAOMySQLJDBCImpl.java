package model.dao.mySQLJDBCImpl;

import model.dao.DetailsDAO;
import model.mo.Details;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetailsDAOMySQLJDBCImpl implements DetailsDAO {

    Connection conn;

    public DetailsDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Details insert(
            Long orderNumber,
            Long travelCode,
            Long quantity
    ) {

        PreparedStatement ps;
        Details details = new Details();
        details.setOrderNumber(orderNumber);
        details.setTravelCode(travelCode);

        try {

            String sql
                    = " SELECT order_number "
                    + " FROM details "
                    + " WHERE "
                    + " order_number = ? AND travel_code";

            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setLong(i++, details.getOrderNumber());
            ps.setLong(i++, details.getTravelCode());

            ResultSet resultSet = ps.executeQuery(); //eseguo la query appena creata
            boolean exist;
            exist = resultSet.next();
            resultSet.close();

            if (exist) {
                details.setQuantity(quantity);

                sql
                        = " UPDATE deatils "
                        + " SET quantity = ?"
                        + " WHERE order_number = ? AND travel_code = ?";

                ps = conn.prepareStatement(sql);
                i = 1;
                ps.setDouble(i++, details.getQuantity());
                ps.setDouble(i++, details.getOrderNumber());
                ps.setDouble(i++, details.getTravelCode());

                ps.executeUpdate();

                return details;
            }

            sql
                    = " INSERT INTO details "
                    + "   ( "
                    + "     order_number," +
                    "       travel_code"
                    + "     quantity"
                    + "   ) "
                    + " VALUES (?,?,?)";

            ps = conn.prepareStatement(sql);
            i = 1;
            ps.setLong(i++, details.getOrderNumber());
            ps.setDouble(i++, details.getTravelCode());
            ps.setDouble(i++, details.getQuantity());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return details;

    }

    @Override
    public boolean update(Details details, String field, String value) {
        PreparedStatement ps;

        try {
            String sql
                    = "UPDATE details "
                    + "SET " + field + " = ?"
                    + "WHERE order_number = ? AND travel_code = ?";


            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, value);
            ps.setLong(i++, details.getOrderNumber());
            ps.setLong(i++, details.getTravelCode());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void delete(Details details) {

        PreparedStatement ps;

        try {
            String sql
                    = " DELETE"
                    + " FROM details"
                    + " WHERE "
                    + " order_number = ? AND travel_code = ?";

            ps = conn.prepareStatement(sql);
            if (details.getOrderNumber() != null && details.getTravelCode() != null) {
                ps.setLong(1, details.getOrderNumber());
                ps.setLong(1, details.getTravelCode());
                ps.executeUpdate();
                ps.close();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Details find(Long orderNumber) {

        PreparedStatement ps;
        Details details = null;

        try {

            String sql
                    = " SELECT * "
                    + "   FROM details "
                    + " WHERE "
                    + "   order_number = ?";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, orderNumber);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                details = read(resultSet);
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return details;

    }

    @Override
    public Details find(Long orderNumber, Long travelCode) {

        PreparedStatement ps;
        Details details = null;

        try {

            String sql
                    = " SELECT * "
                    + "   FROM details "
                    + " WHERE "
                    + "   order_number = ? AND travel_code = ?";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, orderNumber);
            ps.setLong(1, travelCode);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                details = read(resultSet);
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return details;

    }

    Details read(ResultSet rs) {

        Details details = new Details();

        try {
            details.setOrderNumber(rs.getLong("order_number"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        try {
            details.setTravelCode(rs.getLong("travel_code"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        try {
            details.setQuantity(rs.getLong("quantity"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return details;
    }

}
