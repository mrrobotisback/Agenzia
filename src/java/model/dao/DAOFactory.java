package model.dao;
//classe astratta.
import model.dao.mySQLJDBCImpl.MySQLJDBCDAOFactory;

public abstract class DAOFactory {

  // List of DAO types supported by the factory
  public static final String MYSQLJDBCIMPL = "MySQLJDBCImpl";

  public abstract void beginTransaction();
  public abstract void commitTransaction();
  public abstract void rollbackTransaction();
  public abstract void closeTransaction();
  
  public abstract UserDAO getUserDAO(); //accedo al dao degli utenti.

  public abstract TravelDAO getTravelDAO();

  public static DAOFactory getDAOFactory(String whichFactory) {

    if (whichFactory.equals(MYSQLJDBCIMPL)) {
      return new MySQLJDBCDAOFactory();
    } else {
      return null;
    }
  }
}

