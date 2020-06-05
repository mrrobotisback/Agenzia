package model.dao.mySQLJDBCImpl;

import model.dao.DAOFactory;
import model.dao.TravelDAO;
import model.dao.UserDAO;
import model.dao.CategoryDAO;
import model.dao.OrderDAO;
import model.dao.CardDAO;
import model.dao.CartDAO;
import model.dao.HaveDAO;
import model.dao.DetailsDAO;
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
    } catch (ClassNotFoundException | SQLException e) {
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
  public UserDAO getUserDAO() { return new model.dao.mySQLJDBCImpl.UserDAOMySQLJDBCImpl(connection); }

  @Override
  public TravelDAO getTravelDAO() {
    return new model.dao.mySQLJDBCImpl.TravelDAOMySQLJDBCImpl(connection);
  }

  @Override
  public CategoryDAO getCategoryDAO() {
    return new model.dao.mySQLJDBCImpl.CategoryDAOMySQLJDBCImpl(connection);
  }

  @Override
  public OrderDAO getOrderDAO() {
    return new model.dao.mySQLJDBCImpl.OrderDAOMySQLJDBCImpl(connection);
  }

  @Override
  public CardDAO getCardDAO() {
    return new model.dao.mySQLJDBCImpl.CardDAOMySQLJDBCImpl(connection);
  }

  @Override
  public CartDAO getCartDAO() {
    return new model.dao.mySQLJDBCImpl.CartDAOMySQLJDBCImpl(connection);
  }

  @Override
  public HaveDAO getHaveDAO() {
    return new model.dao.mySQLJDBCImpl.HaveDAOMySQLJDBCImpl(connection);
  }

  public DetailsDAO getDetailsDAO() {
    return new model.dao.mySQLJDBCImpl.DetailsDAOMySQLJDBCImpl(connection);
  }

}