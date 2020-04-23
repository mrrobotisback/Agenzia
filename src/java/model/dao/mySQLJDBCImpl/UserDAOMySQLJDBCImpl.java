package model.dao.mySQLJDBCImpl;

import model.dao.UserDAO;
import model.dao.exception.DuplicatedObjectException;
import model.mo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public abstract class UserDAOMySQLJDBCImpl implements UserDAO {

  private final String COUNTER_ID = "userId";
  Connection conn;

  public UserDAOMySQLJDBCImpl(Connection conn) {
    this.conn = conn;
  }

  public User insert(
          String firstname,
          String surname,
          String username,
          String password,
          String birthday,
          String sex,
          String via,
          String numero,
          String citta,
          String provincia,
          String cap,
          String phone,
          String email,
          String work,
          String cf
  ) throws DuplicatedObjectException {

    PreparedStatement ps;
    User user = new User();
    user.setFirstname(firstname);
    user.setSurname(surname);
    user.setUsername(username);
    user.setPassword(password);
    user.setBirthday(birthday);
    user.setSex(sex);
    user.setVia(via);
    user.setNumero(numero);
    user.setCitta(citta);
    user.setProvincia(provincia);
    user.setCap(cap);
    user.setPhone(phone);
    user.setEmail(email);
    user.setWork(work);
    user.setCf(cf);



    try {

      String sql
              = " SELECT userId "
              + " FROM user "
              + " WHERE "
              + " firstname = ? AND"
              + " surname = ? AND"
              + " email = ?";

      ps = conn.prepareStatement(sql);
      int i = 1;
      ps.setString(i++, user.getFirstname());//setto il nome sulla query
      ps.setString(i++, user.getSurname());
      ps.setString(i++, user.getEmail());

      ResultSet resultSet = ps.executeQuery(); //eseguo la query appena creata
      boolean exist;
      exist = resultSet.next();
      resultSet.close();

      if (exist) {
        throw new DuplicatedObjectException("userDAOJDBCImpl.create: Tentativo di inserimento di un utente già esistente.");
      }

      long lastUserId = 0;
      sql = "select userid from user order by userid DESC LIMIT 1";
      ps = conn.prepareStatement(sql);
      ResultSet resultSet1 = ps.executeQuery();
      if (resultSet1.next()) { //preleva riga di db
        lastUserId = read(resultSet1).getUserId();; //crea oggetto utente completo
      }
      resultSet1.close();
      ps.close();
      long nextUser = lastUserId++;

      sql
              = " INSERT INTO user "
              + "   ( "
              + "     userid,"
              + "     firstname,"
              + "     surname,"
              + "     username,"
              + "     password,"
              + "     date_birth,"
              + "     email,"
              + "     street,"
              + "     number,"
              + "     city,"
              + "     province,"
              + "     cap,"
              + "     cellular,"
              + "     sex,"
              + "     profession,"
              + "     role,"
              + "     cf"
              + "   ) "
              + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


      ps = conn.prepareStatement(sql);
      i = 1;
      ps.setInt(i++, (int) lastUserId);
      ps.setString(i++, user.getFirstname());
      ps.setString(i++, user.getSurname());
      ps.setString(i++, user.getUsername());
      ps.setString(i++, user.getPassword());
      ps.setDate(i++,   user.getBirthday());
      ps.setString(i++, user.getEmail());
      ps.setString(i++, user.getVia());
      ps.setString(i++, user.getNumero());
      ps.setString(i++, user.getCitta());
      ps.setString(i++, user.getProvincia());
      ps.setString(i++, user.getCap());
      ps.setString(i++, user.getPhone());
      ps.setString(i++, user.getSex());
      ps.setString(i++, user.getWork());
      ps.setString(i++, "user");
      ps.setString(i++, user.getCf());


      ps.executeUpdate();

    } catch (SQLException | ParseException e) {
      throw new RuntimeException(e);
    }

    return user;

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

  @Override
  public User checkRole(String username) {

    PreparedStatement ps;
    User user = null;

    try {

      String sql
              = " SELECT role "
              + "   FROM user "
              + " WHERE "
              + "   username = ?";

      ps = conn.prepareStatement(sql);
      ps.setString(1, username);

      ResultSet resultSet = ps.executeQuery();

      if (resultSet.next()) {
        user = read(resultSet);
      }
      assert user != null;
      String role = user.getRole();
      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return user;

  }

  public List<User> allUser() {
    PreparedStatement ps;
    User user;
    ArrayList<User> users = new ArrayList<User>();

    try {

      String sql
              = " SELECT * "
              + "   FROM user ";

      ps = conn.prepareStatement(sql);

      ResultSet resultSet = ps.executeQuery();

      while (resultSet.next()) {
        user = read(resultSet);
        users.add(user);
      }

      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return users;
  }

  User read(ResultSet rs) {
    
    User user = new User();
    try {
      user.setUsername(rs.getString("username"));
    } catch (SQLException sqle) {
        sqle.printStackTrace();
      }
      try {
        user.setFirstname(rs.getString("firstname"));
      } catch (SQLException sqle) {
        sqle.printStackTrace();
      }
      try {
        user.setSurname(rs.getString("surname"));
      } catch (SQLException sqle) {
        sqle.printStackTrace();
      }
      try {
        user.setPassword(rs.getString("password"));
      } catch (SQLException sqle) {
        sqle.printStackTrace();
      }
      try {
        user.setEmail(rs.getString("email"));
      } catch (SQLException sqle) {
        sqle.printStackTrace();
      }
      try {
        user.setBirthday(rs.getString("date_birth"));
      } catch (SQLException sqle) {
        sqle.printStackTrace();
      }
      try {
        user.setSex(rs.getString("sex"));
      } catch (SQLException sqle) {
        sqle.printStackTrace();
      }
      try {
        user.setPhone(rs.getString("cellular"));
      } catch (SQLException sqle) {
        sqle.printStackTrace();
      }
      try {
        user.setVia(rs.getString("street"));
      } catch (SQLException sqle) {
        sqle.printStackTrace();
      }
      try {
        user.setNumero(rs.getString("number"));
      } catch (SQLException sqle) {
        sqle.printStackTrace();
      }
      try {
        user.setCitta(rs.getString("city"));
      } catch (SQLException sqle) {
        sqle.printStackTrace();
      }
      try {
        user.setProvincia(rs.getString("province"));
      } catch (SQLException sqle) {
        sqle.printStackTrace();
      }
      try {
        user.setCap(rs.getString("cap"));
      } catch (SQLException sqle) {
        sqle.printStackTrace();
      }
      try {
        user.setWork(rs.getString("profession"));
      } catch (SQLException sqle) {
        sqle.printStackTrace();
      }
      try {
        user.setCf(rs.getString("cf"));
      } catch (SQLException sqle) {
        sqle.printStackTrace();
      }
      try {
        user.setRole(rs.getString("role"));
      } catch (SQLException sqle) {
        sqle.printStackTrace();
      }
      try {
        user.setUserId(rs.getLong("userId"));
      } catch (SQLException sqle) {
        sqle.printStackTrace();
      }

    return user;
  }

}
