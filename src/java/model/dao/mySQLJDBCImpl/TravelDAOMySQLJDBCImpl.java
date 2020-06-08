package model.dao.mySQLJDBCImpl;

import model.dao.TravelDAO;
import model.dao.exception.DuplicatedObjectException;
import model.mo.Travel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class TravelDAOMySQLJDBCImpl implements TravelDAO {

  private final String COUNTER_ID = "id";
  Connection conn;

  public TravelDAOMySQLJDBCImpl(Connection conn) {//metodo che memorizza la connessione nella var esempl
    this.conn = conn;
  }

  @Override
  public Travel insert(
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
  ) throws DuplicatedObjectException {

    PreparedStatement ps;
    Travel travel = new Travel();
    travel.setCategoryId(categoryId);
    travel.setPrice(price);
    travel.setName(name);
    travel.setDiscount(discount);
    travel.setStartDate(startDate);
    travel.setMeans(means);
    travel.setDescription(description);
    travel.setStartPlace(startPlace);
    travel.setStartHour(startHour);
    travel.setDuration(duration);
    travel.setSeatsAvailable(seatsAvailable);
    travel.setSeatsTotal(seatsTotal);
    travel.setDestination(destination);
    travel.setVisible(hide);

    try {

      String sql
              = " SELECT * "
              + " FROM travel "
              + " WHERE "
              + " deleted = 0 AND "
              + " name = ?";

      ps = conn.prepareStatement(sql);
      int i = 1; 
      ps.setString(i++, travel.getName());

      ResultSet resultSet = ps.executeQuery(); //eseguo la query appena creata

      boolean exist;
      exist = resultSet.next();
      resultSet.close();

      if (exist) {
        throw new DuplicatedObjectException("TravelDAOJDBCImpl.create: Tentativo di inserimento di un Viaggio già esistente.");
      }

      sql
              = " INSERT INTO travel "
              + "   ( category_id,"
              + "     price,"
              + "     `name`,"
              + "     discount,"
              + "     start_date,"
              + "     means,"
              + "     description,"
              + "     start_place,"
              + "     start_hour,"
              + "     duration, "
              + "     seats_available, "
              + "     seats_total, "
              + "     destination, "
              + "     hide "
              + "   ) "
              + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

      ps = conn.prepareStatement(sql);
      i = 1;
      ps.setLong(i++, travel.getCategoryId());
      ps.setDouble(i++, travel.getPrice());
      ps.setString(i++, travel.getName());
      ps.setDouble(i++, travel.getDiscount());
      ps.setDate(i++, travel.getStartDate());
      ps.setLong(i++, travel.getMeans());
      ps.setString(i++, travel.getDescription());
      ps.setString(i++, travel.getStartPlace());
      ps.setString(i++, travel.getStartHour());
      ps.setString(i++, travel.getDuration());
      ps.setInt(i++, travel.getSeatsAvailable());
      ps.setInt(i++, travel.getSeatsTotal());
      ps.setString(i++, travel.getDestination());
      ps.setBoolean(i++, travel.getVisible());

      ps.executeUpdate();

    } catch (SQLException | ParseException e) {
      throw new RuntimeException(e);
    }

    return travel;

  }

  @Override
  public boolean update(Travel travel, String field, String value) {
    PreparedStatement ps;

    try {
      String sql
              = "UPDATE travel "
              + "SET " + field + " = ?"
              + "WHERE id = ?";


      ps = conn.prepareStatement(sql);
      int i = 1;
      ps.setString(i++, value);
      ps.setLong(i++, travel.getTravelId());

      ps.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return true;
  }

  @Override
  public void delete(Travel travel) {

    PreparedStatement ps;

    try {
      String sql
              = " DELETE"
              + " FROM travel"
              + " WHERE "
              + " id=?";

      ps = conn.prepareStatement(sql);
      if (travel.getTravelId() != null) {
        ps.setLong(1, travel.getTravelId());
        ps.executeUpdate();
        ps.close();
      }


    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public Travel findByTravelId(Long id) {

    PreparedStatement ps;
    Travel travel = null;

    try {

      String sql
              = " SELECT *"
              + " FROM travel "
              + " WHERE "
              + "   id = ? ";

      ps = conn.prepareStatement(sql);
      ps.setLong(1, id);

      ResultSet resultSet = ps.executeQuery();

      if (resultSet.next()) {
        travel = read(resultSet);
      }
      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return travel;

  }

  @Override
  public List<Travel> find (String field, String value) {
    PreparedStatement ps;
    Travel travel;
    ArrayList<Travel> travels = new ArrayList<Travel>();

    try {

      String sql
              = " SELECT *"
              + "   FROM travel "
              + " WHERE "
              + " " + field + " like " + "'%" + value + "%'";

      ps = conn.prepareStatement(sql);

      ResultSet resultSet = ps.executeQuery();//esegue query

      while (resultSet.next()) {
        travel = read(resultSet);
        travels.add(travel);
      }

      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return travels;
  }

  @Override
  public List<Travel> allTravel() {
    PreparedStatement ps;
    Travel travel;
    ArrayList<Travel> travels = new ArrayList<Travel>();

    try {

      String sql
              = " SELECT * "
              + "   FROM travel ";

      ps = conn.prepareStatement(sql);

      ResultSet resultSet = ps.executeQuery();

      while (resultSet.next()) {
        travel = read(resultSet);
        travels.add(travel);
      }

      resultSet.close();
      ps.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return travels;
  }

  Travel read(ResultSet rs) {
    Travel travel = new Travel();

    try {
      travel.setTravelId(rs.getLong("id"));
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    try {
      travel.setCategoryId(rs.getLong("category_id"));
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    try {
      travel.setPrice(rs.getDouble("price"));
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    try {
      travel.setName(rs.getString("name"));
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    try {
      travel.setDiscount(rs.getDouble("discount"));
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    try {
      travel.setStartDate(rs.getString("start_date"));
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    try {
      travel.setMeans(rs.getLong("means"));
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    try {
      travel.setDescription(rs.getString("description"));
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    try {
      travel.setStartPlace(rs.getString("start_place"));
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    try {
      travel.setStartHour(rs.getString("start_hour"));
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    try {
      travel.setDuration(rs.getString("duration"));
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    try {
      travel.setSeatsAvailable(rs.getInt("seats_available"));
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    try {
      travel.setSeatsTotal(rs.getInt("seats_total"));
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    try {
      travel.setDestination(rs.getString("destination"));
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    try {
      travel.setDeleted(rs.getBoolean("deleted"));
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    try {
      travel.setVisible(rs.getBoolean("hide"));
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }

    return travel;
  }
}
