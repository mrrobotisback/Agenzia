package controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.config.Configuration;
import services.logservice.LogService;

import model.mo.User;
import model.mo.Travel;
import model.dao.DAOFactory;
import model.dao.UserDAO;
import model.dao.TravelDAO;
import model.dao.exception.DuplicatedObjectException;

import model.session.mo.LoggedUser;
import model.session.dao.SessionDAOFactory;
import model.session.dao.LoggedUserDAO;

public class TravelManagement {

  private TravelManagement() {
  }

  public static void view(HttpServletRequest request, HttpServletResponse response) {
    Configuration conf = new Configuration();
    SessionDAOFactory sessionDAOFactory;
    LoggedUser loggedUser;

    Logger logger = LogService.getApplicationLogger();

    try {

      sessionDAOFactory = SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
      assert sessionDAOFactory != null;
      sessionDAOFactory.initSession(request, response);

      LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
      loggedUser = loggedUserDAO.find();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("admin",false);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("viewUrl", "travelManagement/view");

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
      throw new RuntimeException(e);
    }

  }
}
