package model.dao.mySQLJDBCImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.mo.User;
import model.dao.UserDAO;


public class UserDAOMySQLJDBCImpl implements UserDAO {

  private final String COUNTER_ID = "userId";  
  Connection conn;

  public UserDAOMySQLJDBCImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
  public User insert(String username, String password, String firstname, String surname, String languageCode) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.  
  }

  @Override
  public void update(User user) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void delete(User user) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  } //tipo .setDelate(true);

  @Override
  public User findByUserId(Long userId) {

    PreparedStatement ps;
    User user = null;

    try {

      String sql
              = " SELECT * "
              + "   FROM user "
              + " WHERE "
              + "   userId = ?";

      ps = conn.prepareStatement(sql); //compila e immagazzina istruzione sql dentro ps;
      ps.setLong(1, userId); //indica il primo parametro come long

      ResultSet resultSet = ps.executeQuery();//esegue query

      if (resultSet.next()) { //preleva riga di db
        user = read(resultSet); //crea oggetto utente completo
      }
      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return user; //ritorna il valore dell'oggetto user appena creato

  }

@Override
  public User findByUsername(String username) {

    PreparedStatement ps;
    User user = null;

    try {

      String sql
              = " SELECT * "
              + "   FROM user "
              + " WHERE "
              + "   username = ?";

      ps = conn.prepareStatement(sql);
      ps.setString(1, username);

      ResultSet resultSet = ps.executeQuery();

      if (resultSet.next()) {
        user = read(resultSet);
      }
      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return user;

  }  
  
  User read(ResultSet rs) {
    
    User user = new User();
    try { //setto userId nella classe user
      user.setUserId(rs.getLong("userId"));//restituisce il valore della colonna userId del DB
    } catch (SQLException sqle) {
    }
    try {//setto username nella classe user
      user.setUsername(rs.getString("username"));//restuisce il calore username della colonna del db
    } catch (SQLException sqle) {
    }
    try {
      user.setPassword(rs.getString("password"));
    } catch (SQLException sqle) {
    }
    try {
      user.setFirstname(rs.getString("firstname"));
    } catch (SQLException sqle) {
    }
    try {
      user.setSurname(rs.getString("surname"));
    } catch (SQLException sqle) {
    }
    try {
      user.setDeleted(rs.getString("deleted").equals("Y"));
    } catch (SQLException sqle) {
    }
    return user;
  }

}
