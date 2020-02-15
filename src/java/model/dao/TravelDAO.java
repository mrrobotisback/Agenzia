package model.dao;

import java.util.List;

import model.mo.User;
import model.mo.Travel;

import model.dao.exception.DuplicatedObjectException;

public interface TravelDAO {

  public Travel insert(
          User user,
          String firstname,
          String surname,
          String email,
          String address,
          String city,
          String phone,
          String sex) throws DuplicatedObjectException;

  public void update(Travel travel) throws DuplicatedObjectException;

  public void delete(Travel travel);

  public Travel findByTravelId(Long travelId);

  public List<String> findInitialsByUser(User user); //prendo iniziali, passo utente e mi ritorna una stringa di iniziali

  public List<Travel> findByInitialAndSearchString(User user, String initial, String searchString); //tiro fuori una lista di contatti che hanno un particolare carattere.
//metodi come interfacce.
}
