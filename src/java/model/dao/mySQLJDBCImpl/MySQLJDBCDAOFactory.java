package model.dao.mySQLJDBCImpl;

import model.dao.DAOFactory;
import model.dao.TravelDAO;
import model.dao.UserDAO;
import model.dao.exception.DuplicatedObjectException;
import model.mo.User;
import services.config.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLJDBCDAOFactory extends DAOFactory {

  private Connection connection;

  @Override
  public void beginTransaction() {
    Configuration conf = new Configuration();
    try {
      Class.forName(conf.DATABASE_DRIVER);
      this.connection = DriverManager.getConnection(conf.DATABASE_URL);
      this.connection.setAutoCommit(false);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public void commitTransaction() {
    try {
      this.connection.commit();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void rollbackTransaction() {

    try {
      this.connection.rollback();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public void closeTransaction() {
    try {
      this.connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public UserDAO getUserDAO() {
    return new model.dao.mySQLJDBCImpl.UserDAOMySQLJDBCImpl(connection) {
      @Override
      public User insert(String firstname, String surname, String username, String password, String birthday, String sex, String via, String numero, String citta, String provincia, String cap, String phone, String email, String work, String cf, String role) throws DuplicatedObjectException {
        return null;
      }
    };
  }

  @Override
  public TravelDAO getTravelDAO() {
    return new model.dao.mySQLJDBCImpl.TravelDAOMySQLJDBCImpl(connection);
  }
}