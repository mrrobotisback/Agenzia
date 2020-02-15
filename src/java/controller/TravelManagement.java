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
    DAOFactory daoFactory = null;
    LoggedUser loggedUser;
    String applicationMessage = null;

    Logger logger = LogService.getApplicationLogger();//metodo di del package services

    try {

      sessionDAOFactory = SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
      sessionDAOFactory.initSession(request, response);

      LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
      loggedUser = loggedUserDAO.find();

      daoFactory = DAOFactory.getDAOFactory(conf.DAO_IMPL);
      daoFactory.beginTransaction();

      commonView(daoFactory, sessionDAOFactory, request);

      daoFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("applicationMessage", applicationMessage);
      request.setAttribute("viewUrl", "addressBookManagement/view"); 

    } catch (Exception e) { //qualsiasi eccezzione che lancia la pagina la devo gestire loggano, rollbackando
      logger.log(Level.SEVERE, "Controller Error", e);
      try {
        if (daoFactory != null) {
          daoFactory.rollbackTransaction();
        }
      } catch (Throwable t) { //ignore gli eventuali errori di rollback
      }
      throw new RuntimeException(e); //sparo tutto verso la jsp che gestira con la error page. //runtime tutto quello che non va gestito -> gestito dalla error page.

    } finally {
      try {
        if (daoFactory != null) {
          daoFactory.closeTransaction();//devo chiudere la transazione, non lasciarla mai aperta.
        }
      } catch (Throwable t) {
      }

    }

  } //dopo view il dispatcher va ad estrarre da addressbookmanagaer 

  public static void delete(HttpServletRequest request, HttpServletResponse response) {

    Configuration conf = new Configuration();
    SessionDAOFactory sessionDAOFactory;
    DAOFactory daoFactory = null;
    LoggedUser loggedUser;

    Logger logger = LogService.getApplicationLogger();

    try {

      sessionDAOFactory = SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
      sessionDAOFactory.initSession(request, response);

      LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
      loggedUser = loggedUserDAO.find();

      daoFactory = DAOFactory.getDAOFactory(conf.DAO_IMPL);
      daoFactory.beginTransaction();

      Long travelId = Long.valueOf(request.getParameter("travelId"));

      TravelDAO travelDAO = daoFactory.getTravelDAO();
      Travel travel = travelDAO.findByTravelId(travelId);
      travelDAO.delete(travel);

      commonView(daoFactory, sessionDAOFactory, request);

      daoFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("viewUrl", "TravelManagement/view");

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
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

  public static void insertView(HttpServletRequest request, HttpServletResponse response) {

    Configuration conf = new Configuration();
    SessionDAOFactory sessionDAOFactory;
    LoggedUser loggedUser;

    Logger logger = LogService.getApplicationLogger();

    try {

      sessionDAOFactory = SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
      sessionDAOFactory.initSession(request, response);

      LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
      loggedUser = loggedUserDAO.find();

      String selectedInitial = request.getParameter("selectedInitial");

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("selectedInitial", selectedInitial);
      request.setAttribute("viewUrl", "addressBookManagement/insModView");

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
      throw new RuntimeException(e);
    }

  }

  public static void insert(HttpServletRequest request, HttpServletResponse response) {

    Configuration conf = new Configuration();
    SessionDAOFactory sessionDAOFactory;
    DAOFactory daoFactory = null;
    LoggedUser loggedUser;
    String applicationMessage = null;

    Logger logger = LogService.getApplicationLogger();

    try {

      sessionDAOFactory = SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
      sessionDAOFactory.initSession(request, response);

      LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
      loggedUser = loggedUserDAO.find();

      daoFactory = DAOFactory.getDAOFactory(conf.DAO_IMPL);
      daoFactory.beginTransaction();

      UserDAO userDAO = daoFactory.getUserDAO();
      User user = userDAO.findByUserId(loggedUser.getUserId());
      
      TravelDAO travelDAO = daoFactory.getTravelDAO();

      try {

        travelDAO.insert(
                user,
                request.getParameter("firstname"),
                request.getParameter("surname"),
                request.getParameter("email"),
                request.getParameter("address"),
                request.getParameter("city"),
                request.getParameter("phone"),
                request.getParameter("sex"));

      } catch (DuplicatedObjectException e) {
        applicationMessage = "Viaggio già esistente";
        logger.log(Level.INFO, "Tentativo di inserimento viaggio già esistente");
      }

      commonView(daoFactory, sessionDAOFactory, request);

      daoFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("applicationMessage", applicationMessage);
      request.setAttribute("viewUrl", "addressBookManagement/view");

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
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

  public static void modifyView(HttpServletRequest request, HttpServletResponse response) {

    Configuration conf = new Configuration();
    SessionDAOFactory sessionDAOFactory;
    DAOFactory daoFactory = null;
    LoggedUser loggedUser;
    
    Logger logger = LogService.getApplicationLogger();

    try {

      sessionDAOFactory = SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
      sessionDAOFactory.initSession(request, response);

      LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
      loggedUser = loggedUserDAO.find();

      daoFactory = DAOFactory.getDAOFactory(conf.DAO_IMPL);
      daoFactory.beginTransaction();

      String selectedInitial = request.getParameter("selectedInitial");
      Long travelId = Long.valueOf(request.getParameter("travelId"));

      TravelDAO travelDAO = daoFactory.getTravelDAO();
      Travel travel = travelDAO.findByTravelId(travelId);

      daoFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("travel", travel);
      request.setAttribute("selectedInitial", selectedInitial);
      request.setAttribute("viewUrl", "travelManagement/insModView");

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
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

  public static void modify(HttpServletRequest request, HttpServletResponse response) {

    Configuration conf = new Configuration();
    SessionDAOFactory sessionDAOFactory;
    DAOFactory daoFactory = null;
    LoggedUser loggedUser;
    String applicationMessage = null;

    Logger logger = LogService.getApplicationLogger();

    try {

      sessionDAOFactory = SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
      sessionDAOFactory.initSession(request, response);

      LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
      loggedUser = loggedUserDAO.find();

      daoFactory = DAOFactory.getDAOFactory(conf.DAO_IMPL);
      daoFactory.beginTransaction();

      TravelDAO travelDAO = daoFactory.getTravelDAO();
      Travel travel = travelDAO.findByTravelId(Long.valueOf(request.getParameter("travelId")));

      travel.setFirstname(request.getParameter("firstname"));
      travel.setSurname(request.getParameter("surname"));
      travel.setEmail(request.getParameter("email"));
      travel.setAddress(request.getParameter("address"));
      travel.setCity(request.getParameter("city"));
      travel.setPhone(request.getParameter("phone"));
      travel.setSex(request.getParameter("sex"));

      try {

        travelDAO.update(travel);

      } catch (DuplicatedObjectException e) {
        applicationMessage = "Contatto già esistente";
        logger.log(Level.INFO, "Tentativo di inserimento di contatto già esistente");
      }

      commonView(daoFactory, sessionDAOFactory, request);

      daoFactory.commitTransaction();

      request.setAttribute("loggedOn",loggedUser!=null);
      request.setAttribute("loggedUser", loggedUser);
      request.setAttribute("applicationMessage", applicationMessage);
      request.setAttribute("viewUrl", "travelManagement/view");

    } catch (Exception e) {
      logger.log(Level.SEVERE, "Controller Error", e);
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

  private static void commonView(DAOFactory daoFactory, SessionDAOFactory sessionDAOFactory, HttpServletRequest request) {

    List<String> initials;
    List<Travel> travels;

    LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
    LoggedUser loggedUser = loggedUserDAO.find();

    UserDAO userDAO = daoFactory.getUserDAO();
    User user = userDAO.findByUserId(loggedUser.getUserId());

    TravelDAO travelDAO = daoFactory.getTravelDAO();
    initials = travelDAO.findInitialsByUser(user);

    String selectedInitial = request.getParameter("selectedInitial");

    if (selectedInitial == null || (!selectedInitial.equals("*") && !initials.contains(selectedInitial))) {
      if (initials.size() > 0) {
        selectedInitial = initials.get(0);
      } else {
        selectedInitial = "*";
      }
    }

    travels = travelDAO.findByInitialAndSearchString(user,
            (selectedInitial.equals("*") ? null : selectedInitial), null);

    request.setAttribute("selectedInitial", selectedInitial);
    request.setAttribute("initials", initials);
    request.setAttribute("travels", travels);

  }
}
