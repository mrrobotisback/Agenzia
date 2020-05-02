package model.dao;
//metodi che ci servono dopo aver fatto la progettazione

import model.dao.exception.DuplicatedObjectException;
import model.mo.User;

import java.util.List;

public interface UserDAO {

  User insert(
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

  boolean update(User user, String field, String value);

  void delete(User user);

  User findByUserId(Long userId);
  User find(String field, String value);
  User findByUsername(String username);
  User checkRole(String username);

  List<User> allUser();

}
