package model.dao;
//metodi che ci servono dopo aver fatto la porgettazione 
import model.mo.User;

public interface UserDAO {

  public User insert( 
          String username,
          String password,
          String firstname,
          String surname,
          String languageCode);

  public void update(User user);

  public void delete(User user);

  public User findByUserId(Long userId);
  
  public User findByUsername(String username);

}
