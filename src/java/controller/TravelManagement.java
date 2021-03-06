package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import model.dao.DAOFactory;
import model.dao.UserDAO;
import model.mo.User;
import model.session.dao.LoggedUserDAO;
import model.session.dao.SessionDAOFactory;
import model.session.mo.LoggedUser;
import services.config.Configuration;
import services.logservice.LogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TravelManagement {

  private TravelManagement() {
  }

  public static void view(HttpServletRequest request, HttpServletResponse response) {
    Configuration conf = new Configuration();
    SessionDAOFactory sessionDAOFactory;
    LoggedUser loggedUser;
    DAOFactory daoFactory = null;

    Logger logger = LogService.getApplicationLogger();

    try {

      sessionDAOFactory = SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
      assert sessionDAOFactory != null;
      sessionDAOFactory.initSession(request, response);

      LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
      loggedUser = loggedUserDAO.find();
      daoFactory = DAOFactory.getDAOFactory(conf.DAO_IMPL);
      assert daoFactory != null;
      daoFactory.beginTransaction();
      UserDAO userDAO = daoFactory.getUserDAO();
      if (loggedUser != null) {
        User user = userDAO.findByUserId(loggedUser.getUserId());
        User userRole = userDAO.checkRole(user.getUsername());
        if (userRole.getRole().equals("admin")){
          request.setAttribute("admin",true);
        } else {
          request.setAttribute("admin",false);
        }
      } else {
        request.setAttribute("admin",false);
      }

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("viewUrl", "travelManagement/view");

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
      throw new RuntimeException(e);
    }

  }

  public static void searchTravel(HttpServletRequest request, HttpServletResponse response) throws IOException {
    services.config.Configuration conf = new services.config.Configuration();
    model.session.dao.SessionDAOFactory sessionDAOFactory;
    model.dao.DAOFactory daoFactory = null;
    model.session.mo.LoggedUser loggedUser;
    String applicationMessage = null;

    Logger logger = services.logservice.LogService.getApplicationLogger();

    try {

      sessionDAOFactory = model.session.dao.SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
      assert sessionDAOFactory != null;
      sessionDAOFactory.initSession(request, response);

      daoFactory = model.dao.DAOFactory.getDAOFactory(conf.DAO_IMPL);
      assert daoFactory != null;
      daoFactory.beginTransaction();

      model.dao.TravelDAO travelDAO = daoFactory.getTravelDAO();

      String field = request.getParameter("field");
      String value = request.getParameter("value");

      List<model.mo.Travel> travels = travelDAO.find(field, value);

      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      JsonObject ajaxResponse = new JsonObject();

      GsonBuilder gsonBuilder = new GsonBuilder();
      Gson gson = gsonBuilder.create();

      String JSONObject = gson.toJson(travels);
      ajaxResponse.addProperty("message", JSONObject);
      out.println(ajaxResponse);

      out.println();

      daoFactory.commitTransaction();

      out.close();

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Helper Error perdincibacco searchTravel", e);

      try {
        if (daoFactory != null) {
          daoFactory.rollbackTransaction();
        }
      } catch (Throwable t) {
        t.printStackTrace();
      }
      throw new RuntimeException(e);

    } finally {
      try {
        if (daoFactory != null) {
          daoFactory.closeTransaction();
        }
      } catch (Throwable t) {
        t.printStackTrace();
      }
    }
  }


}
