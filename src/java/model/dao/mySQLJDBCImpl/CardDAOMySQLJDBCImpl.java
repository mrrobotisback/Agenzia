package model.dao.mySQLJDBCImpl;

import model.dao.CardDAO;
import model.mo.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class CardDAOMySQLJDBCImpl implements CardDAO {

    Connection conn;

    public CardDAOMySQLJDBCImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Card insert(
            Long number,
            String holder,
            int cvv,
            String expiration,
            Long userId
    ) throws model.dao.exception.DuplicatedObjectException {

        PreparedStatement ps;
        Card card = new Card();
        card.setNumber(number);

        try {

            String sql
                    = " SELECT number "
                    + " FROM card "
                    + " WHERE "
                    + " number = ?";

            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setLong(i++, card.getNumber());

            ResultSet resultSet = ps.executeQuery(); //eseguo la query appena creata
            boolean exist;
            exist = resultSet.next();
            resultSet.close();

            if (exist) {
                throw new model.dao.exception.DuplicatedObjectException("cardDAOJDBCImpl.create: Tentativo di inserimento di una Carta già esistente.");
            }

            sql
                    = " INSERT INTO card "
                    + "   ( "
                    + "     number,"
                    + "     holder,"
                    + "     cvv"
                    + "     expiration"
                    + "     userId"
                    + "   ) "
                    + " VALUES (?,?,?,?,?)";

            ps = conn.prepareStatement(sql);
            i = 1;
            ps.setLong(i++, card.getNumber());
            ps.setString(i++, card.getHolder());
            ps.setInt(i++, card.getCvv());
            ps.setDate(i++, card.getExpiration());
            ps.setLong(i++, card.getUserId());


            ps.executeUpdate();

        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }

        return card;

    }

    @Override
    public boolean update(Card card, String field, String value) {
        PreparedStatement ps;

        try {
            String sql
                    = "UPDATE card "
                    + "SET " + field + " = ?"
                    + "WHERE number = ?";


            ps = conn.prepareStatement(sql);
            int i = 1;
            ps.setString(i++, value);
            ps.setLong(i++, card.getNumber());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void delete(Card card) {

        PreparedStatement ps;

        try {
            String sql
                    = " DELETE"
                    + " FROM card"
                    + " WHERE "
                    + " number=?";

            ps = conn.prepareStatement(sql);
            if (card.getNumber() != null) {
                ps.setLong(1, card.getNumber());
                ps.executeUpdate();
                ps.close();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Card findByUserId(Long id) {

        PreparedStatement ps;
        Card card = null;

        try {

            String sql
                    = " SELECT * "
                    + "   FROM card "
                    + " WHERE "
                    + "   userId = ?";

            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                card = read(resultSet);
            }
            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return card;

    }

    @Override
    public List<Card> find (Long userId, String field, String value) {
        PreparedStatement ps;
        Card card;
        ArrayList<Card> cards = new ArrayList<Card>();

        try {

            String sql
                    = " SELECT *"
                    + "   FROM category "
                    + " WHERE "
                    + " userId = " + userId + "AND " + field + " like " + "'%" + value + "%'";

            ps = conn.prepareStatement(sql);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                card = read(resultSet);
                cards.add(card);
            }

            resultSet.close();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return cards;
    }

    Card read(ResultSet rs) {

        Card card = new Card();

        try {
            card.setNumber(rs.getLong("number"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        try {
            card.setHolder(rs.getString("holder"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        try {
            card.setCvv(rs.getInt("cvv"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        try {
            card.setExpiration(rs.getString("expiration"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        try {
            card.setUserId(rs.getLong("userid"));
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return card;
    }


}
