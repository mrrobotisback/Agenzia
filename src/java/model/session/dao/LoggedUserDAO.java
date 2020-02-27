package model.session.dao;

import model.session.mo.LoggedUser;

public interface LoggedUserDAO {

  public LoggedUser create(
          Long userId,
          String firstname,
          String surname,
          String role);

  public void update(LoggedUser loggedUser);

  public void destroy();

  public LoggedUser find();
  
}
