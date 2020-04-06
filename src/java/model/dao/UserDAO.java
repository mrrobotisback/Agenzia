package model.dao;
//metodi che ci servono dopo aver fatto la progettazione
import model.mo.User;
import model.dao.exception.DuplicatedObjectException;

public interface UserDAO {

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
          String cf,
          String role
  ) throws DuplicatedObjectException;

  public void update(User user);

  public void delete(User user);

  public User findByUserId(Long userId);
  
  public User findByUsername(String username);
  public User checkRole(String username);

}
