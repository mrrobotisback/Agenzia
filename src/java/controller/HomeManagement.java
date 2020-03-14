package controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import services.config.Configuration;
import services.logservice.LogService;

import model.mo.User;
import model.dao.DAOFactory;
import model.dao.UserDAO;

import model.session.mo.LoggedUser;
import model.session.dao.SessionDAOFactory;
import model.session.dao.LoggedUserDAO;

import services.password.Password;


public class HomeManagement {

  private HomeManagement() {
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
      request.setAttribute("viewUrl", "homeManagement/view");

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
      throw new RuntimeException(e);
    }

  }

  public static void logon(HttpServletRequest request, HttpServletResponse response) {

    Configuration conf = new Configuration();
    Password pwd = new Password();
    String crypt = conf.STRING_FOR_CRYPT;
    SessionDAOFactory sessionDAOFactory;
    DAOFactory daoFactory = null;
    LoggedUser loggedUser;
    String applicationMessage = null;

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

      String username = request.getParameter("username");
      String password = request.getParameter("password");

      UserDAO userDAO = daoFactory.getUserDAO();
      User user = userDAO.findByUsername(username);
      User userRole = userDAO.checkRole(username);

      boolean cryptedPwd = pwd.checkPassword((password + crypt), user.getPassword());

      if (user == null || !cryptedPwd) {
        loggedUserDAO.destroy();
        applicationMessage = "Username e password errati!";
        loggedUser=null;
      } else {
        loggedUser = loggedUserDAO.create(user.getUserId(), user.getFirstname(), user.getSurname(), user.getRole());
      }

      daoFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      if (userRole.getRole().equals("admin")){
        request.setAttribute("admin",true);
      } else {
        request.setAttribute("admin",false);
      }
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("applicationMessage", applicationMessage);
      request.setAttribute("viewUrl", "homeManagement/view");

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error perdincibacco", e);
      try {
        if (daoFactory != null) {
          daoFactory.rollbackTransaction();
        }
      } catch (Throwable t) {
      }
      throw new RuntimeException(e);

    } finally {
      try {
        if (daoFactory != null) {
          daoFactory.closeTransaction();
        }
      } catch (Throwable t) {
      }
    }

  }

  public static void logout(HttpServletRequest request, HttpServletResponse response) {

    Configuration conf = new Configuration();
    SessionDAOFactory sessionDAOFactory;
    
    Logger logger = LogService.getApplicationLogger();

    try {

      sessionDAOFactory = SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
      assert sessionDAOFactory != null;
      sessionDAOFactory.initSession(request, response);

      LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
      loggedUserDAO.destroy();

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
      throw new RuntimeException(e);

    }
    request.setAttribute("loggedOn",false);
    request.setAttribute("admin",false);
    request.setAttribute("loggedUser", null);
    request.setAttribute("viewUrl", "homeManagement/view");

  }

}
