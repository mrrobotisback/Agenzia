package model.dao.mySQLJDBCImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

import model.mo.User;
import model.mo.Travel;
import model.dao.TravelDAO;
import model.dao.exception.DuplicatedObjectException;


public class TravelDAOMySQLJDBCImpl implements TravelDAO {

  private final String COUNTER_ID = "contactId";
  Connection conn;

  public TravelDAOMySQLJDBCImpl(Connection conn) {//metodo che memorizza la connessione nella var esempl
    this.conn = conn;
  }

  @Override
  public Travel insert(
          User user,
          String firstname,
          String surname,
          String email,
          String address,
          String city,
          String phone,
          String sex) throws DuplicatedObjectException {

    PreparedStatement ps;
    Travel contact = new Travel();
    contact.setUser(user);
    contact.setFirstname(firstname);
    contact.setSurname(surname);
    contact.setEmail(email);
    contact.setAddress(address);
    contact.setCity(city);
    contact.setPhone(phone);
    contact.setSex(sex);

    try {

      String sql
              = " SELECT contactId "
              + " FROM travel "
              + " WHERE "
              + " deleted ='N' AND "
              + " firstname = ? AND"
              + " surname = ? AND"
              + " email = ? AND"
              + " userId = ? ";

      ps = conn.prepareStatement(sql);
      int i = 1; 
      ps.setString(i++, contact.getFirstname());//setto il nome sulla query
      ps.setString(i++, contact.getSurname());
      ps.setString(i++, contact.getEmail());
      ps.setLong(i++, contact.getUser().getUserId());

      ResultSet resultSet = ps.executeQuery(); //eseguo la query appena creata

      boolean exist;
      exist = resultSet.next();
      resultSet.close();

      if (exist) {
        throw new DuplicatedObjectException("TravelDAOJDBCImpl.create: Tentativo di inserimento di un contatto già esistente.");
      }
      //setta sulla tabella counter che c'è un untente in più
      sql = "update counter set counterValue=counterValue+1 where counterId='" + COUNTER_ID + "'";

      ps = conn.prepareStatement(sql);
      ps.executeUpdate();

      sql = "SELECT counterValue FROM counter where counterId='" + COUNTER_ID + "'";

      ps = conn.prepareStatement(sql);
      resultSet = ps.executeQuery();
      resultSet.next();

      contact.setContactId(resultSet.getLong("counterValue"));

      resultSet.close();

      sql
              = " INSERT INTO contact "
              + "   ( contactId,"
              + "     userId,"
              + "     firstname,"
              + "     surname,"
              + "     email,"
              + "     address,"
              + "     city,"
              + "     phone,"
              + "     sex,"
              + "     deleted "
              + "   ) "
              + " VALUES (?,?,?,?,?,?,?,?,?,'N')";

      ps = conn.prepareStatement(sql);
      i = 1;
      ps.setLong(i++, contact.getContactId());
      ps.setLong(i++, contact.getUser().getUserId());
      ps.setString(i++, contact.getFirstname());
      ps.setString(i++, contact.getSurname());
      ps.setString(i++, contact.getEmail());
      ps.setString(i++, contact.getAddress());
      ps.setString(i++, contact.getCity());
      ps.setString(i++, contact.getPhone());
      ps.setString(i++, contact.getSex());

      ps.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return contact;

  }

  @Override
  public void update(Travel travel) throws DuplicatedObjectException {
    PreparedStatement ps;

    try {

      String sql
              = " SELECT contactId "
              + " FROM contact "
              + " WHERE "
              + " deleted ='N' AND "
              + " firstname = ? AND"
              + " surname = ? AND"
              + " email = ? AND"
              + " userId = ? AND "
              + " contactId <> ?";

      ps = conn.prepareStatement(sql);
      int i = 1;
      ps.setString(i++, travel.getFirstname());
      ps.setString(i++, travel.getSurname());
      ps.setString(i++, travel.getEmail());
      ps.setLong(i++, travel.getUser().getUserId());
      ps.setLong(i++, travel.getContactId());

      ResultSet resultSet = ps.executeQuery();

      boolean exist;
      exist = resultSet.next();
      resultSet.close();

      if (exist) {
        throw new DuplicatedObjectException("TravelDAOJDBCImpl.create: Tentativo di aggiornamento in un contatto già esistente.");
      }

      sql 
              = " UPDATE contact "
              + " SET "
              + "   firstname = ?, "
              + "   surname = ?, "
              + "   email = ?, "              
              + "   address = ?, "
              + "   city = ?, "
              + "   phone = ?, "
              + "   sex= ? "
              + " WHERE "
              + "   contactId = ? ";

      ps = conn.prepareStatement(sql);
      i = 1;
      ps.setString(i++, travel.getFirstname());
      ps.setString(i++, travel.getSurname());
      ps.setString(i++, travel.getEmail());      
      ps.setString(i++, travel.getAddress());
      ps.setString(i++, travel.getCity());
      ps.setString(i++, travel.getPhone());
      ps.setString(i++, travel.getSex());
      ps.setLong(i++, travel.getContactId());
      ps.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public void delete(Travel travel) {

    PreparedStatement ps;

    try {

      String sql 
              = " UPDATE travel "
              + " SET deleted='Y' "
              + " WHERE "
              + " contactId=?";

      ps = conn.prepareStatement(sql);
      ps.setLong(1, travel.getTravelId());
      ps.executeUpdate();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public Travel findByContactId(Long TravelId) {

    PreparedStatement ps;
    Travel travel = null;

    try {

      String sql
              = " SELECT *"
              + " FROM contact "
              + " WHERE "
              + "   contactId = ? AND "
              + "   deleted  = 'N' ";

      ps = conn.prepareStatement(sql);
      ps.setLong(1, TravelId);

      ResultSet resultSet = ps.executeQuery();

      if (resultSet.next()) {
        travel = read(resultSet);
      }
      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return travel;

  }

  @Override
  public List<String> findInitialsByUser(User user) {

    PreparedStatement ps;
    String initial;
    ArrayList<String> initials = new ArrayList<String>();

    try {

      String sql
              = " SELECT DISTINCT UCase(Left(surname,1)) AS initial "
              + " FROM contact "
              + " WHERE "
              + "   userId = ? "
              + "   AND deleted = 'N' "
              + " ORDER BY UCase(Left(surname,1))";

      ps = conn.prepareStatement(sql);
      ps.setLong(1, user.getUserId());

      ResultSet resultSet = ps.executeQuery();

      while (resultSet.next()) {
        initial = resultSet.getString("initial");
        initials.add(initial);
      }

      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return initials;
  }

  @Override
  public List<Travel> findByInitialAndSearchString(User user, String initial, String searchString) {

    PreparedStatement ps;
    Travel travel;
    ArrayList<Travel> travels = new ArrayList<Travel>();

    try {

      String sql
              = " SELECT * FROM travel "
              + " WHERE "
              + "   userId = ? "
              + "   AND deleted='N' ";
      if (initial != null) {
        sql += " AND UCASE(LEFT(surname,1)) = ? ";
      }
      if (searchString != null) {
        sql += " AND ( INSTR(surname,?)>0 ";
        sql += " OR INSTR(firstname,?)>0 ";
        sql += " OR INSTR(address,?)>0 ";
        sql += " OR INSTR(city,?)>0 ";
        sql += " OR INSTR(phone,?)>0 ";
        sql += " OR INSTR(email,?)>0 )";
      }
      sql += "ORDER BY surname, firstname, email";

      ps = conn.prepareStatement(sql);
      int i = 1;
      ps.setLong(i++, user.getUserId());
      if (initial != null) {
        ps.setString(i++, initial);
      }
      if (searchString != null) {
        ps.setString(i++, searchString);
        ps.setString(i++, searchString);
        ps.setString(i++, searchString);
        ps.setString(i++, searchString);
        ps.setString(i++, searchString);
        ps.setString(i++, searchString);
      }

      ResultSet resultSet = ps.executeQuery();

      while (resultSet.next()) {
        travel = read(resultSet);
        travels.add(travel);
      }

      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return travels;
  }

  Travel read(ResultSet rs) {
    Travel travel = new Travel();
    User user = new User();
    travel.setUser(user);
    try {
      travel.setTravelId(rs.getLong("travelId"));
    } catch (SQLException sqle) {
    }
    try {
      travel.getUser().setUserId(rs.getLong("userId"));
    } catch (SQLException sqle) {
    }
    try {
      travel.setFirstname(rs.getString("firstname"));
    } catch (SQLException sqle) {
    }
    try {
      travel.setSurname(rs.getString("surname"));
    } catch (SQLException sqle) {
    }
    try {
      travel.setEmail(rs.getString("email"));
    } catch (SQLException sqle) {
    }
    try {
      travel.setAddress(rs.getString("address"));
    } catch (SQLException sqle) {
    }
    try {
      travel.setCity(rs.getString("city"));
    } catch (SQLException sqle) {
    }
    try {
      travel.setPhone(rs.getString("phone"));
    } catch (SQLException sqle) {
    }
    try {
      travel.setSex(rs.getString("sex"));
    } catch (SQLException sqle) {
    }
    try {
      travel.setDeleted(rs.getString("deleted").equals("Y"));
    } catch (SQLException sqle) {
    }
    return travel;
  }
}
