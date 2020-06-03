package model.dao;

import model.dao.exception.DuplicatedObjectException;
import model.mo.Travel;

import java.util.List;

public interface TravelDAO {

  Travel insert(
          Long categoryId,
          Double price,
          String name,
          Double discount,
          String startDate,
          Long means,
          String description,
          String startPlace,
          String startHour,
          String duration,
          int seatsAvailable,
          int seatsTotal,
          String destination,
          boolean hide
  ) throws DuplicatedObjectException;

  boolean update(Travel travel, String field, String value);

  void delete(Travel user);

  Travel findByTravelId(Long id);
  List<Travel> find(String field, String value);

  List<Travel> allTravel();
}
