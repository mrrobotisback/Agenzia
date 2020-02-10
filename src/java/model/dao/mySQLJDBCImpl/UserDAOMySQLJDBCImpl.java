package model.dao.mySQLJDBCImpl;

import java.sql.*;
import java.text.ParseException;

import model.mo.User;
import model.dao.UserDAO;
import model.dao.exception.DuplicatedObjectException;

public class UserDAOMySQLJDBCImpl implements UserDAO {

  private final String COUNTER_ID = "userId";
  Connection conn;

  public UserDAOMySQLJDBCImpl(Connection conn) {
    this.conn = conn;
  }

  @Override
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
      ps.setInt(i++, 10);
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
    return user;
  }

}
