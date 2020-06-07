package model.dao.mySQLJDBCImpl;

import model.dao.HaveDAO;
import model.mo.Have;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HaveDAOMySQLJDBCImpl implements HaveDAO {

    Connection conn;

    public HaveDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Have insert(
            Long userId,
            Long travelCode,
            Long quantity
    ) {

        PreparedStatement ps;
        Have have = new Have();
        have.setUserId(userId);
        have.setTravelCode(travelCode);

        try {

            String sql
                    = " SELECT userId "
                    + " FROM cart "
                    + " WHERE "
                    + " userid = ? AND travel_code";

            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setLong(i++, have.getUserId());
            ps.setLong(i++, have.getTravelCode());

            ResultSet resultSet = ps.executeQuery(); //eseguo la query appena creata
            boolean exist;
            exist = resultSet.next();
            resultSet.close();

            if (exist) {
                have.setQuantity(quantity);

                sql
                        = " UPDATE have "
                        + " SET quantity = ?"
                        + " WHERE userid = ? AND travel_code = ?";

                ps = conn.prepareStatement(sql);
                i = 1;
                ps.setDouble(i++, have.getQuantity());
                ps.setDouble(i++, have.getUserId());
                ps.setDouble(i++, have.getTravelCode());

                ps.executeUpdate();

                return have;
            }

            sql
                    = " INSERT INTO have "
                    + "   ( "
                    + "     userid," +
                    "       travel_code"
                    + "     quantity"
                    + "   ) "
                    + " VALUES (?,?,?)";

            ps = conn.prepareStatement(sql);
            i = 1;
            ps.setLong(i++, have.getUserId());
            ps.setDouble(i++, have.getTravelCode());
            ps.setDouble(i++, have.getQuantity());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return have;

    }

    @Override
    public boolean update(Have have, String field, String value) {
        PreparedStatement ps;

        try {
            String sql
                    = "UPDATE have "
                    + "SET " + field + " = ?"
                    + "WHERE userid = ? AND travel_code = ?";


            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, value);
            ps.setLong(i++, have.getUserId());
            ps.setLong(i++, have.getTravelCode());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void delete(Have have) {

        PreparedStatement ps;

        try {
            String sql
                    = " DELETE"
                    + " FROM have"
                    + " WHERE "
                    + " userid = ? AND travel_code = ?";

            ps = conn.prepareStatement(sql);
            if (have.getUserId() != null && have.getTravelCode() != null) {
                ps.setLong(1, have.getUserId());
                ps.setLong(1, have.getTravelCode());
                ps.executeUpdate();
                ps.close();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Have find(Long userId) {

        PreparedStatement ps;
        Have have = null;

        try {

            String sql
                    = " SELECT * "
                    + "   FROM have "
                    + " WHERE "
                    + "   userid = ?";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, userId);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                have = read(resultSet);
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return have;

    }

    @Override
    public Have find(Long userId, Long travelCode) {

        PreparedStatement ps;
        Have have = null;

        try {

            String sql
                    = " SELECT * "
                    + "   FROM have "
                    + " WHERE "
                    + "   userid = ? AND travel_code = ?";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, userId);
            ps.setLong(1, travelCode);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                have = read(resultSet);
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return have;

    }

    Have read(ResultSet rs) {

        Have have = new Have();

        try {
            have.setUserId(rs.getLong("userid"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        try {
            have.setTravelCode(rs.getLong("travel_code"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        try {
            have.setQuantity(rs.getLong("quantity"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return have;
    }

}