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

public class AdminManagement {

    private AdminManagement() {
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
            request.setAttribute("admin",true);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "adminManagement/view");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            throw new RuntimeException(e);
        }

    }

    public static void catalog (HttpServletRequest request, HttpServletResponse response) {
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
            request.setAttribute("admin",true);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "adminManagement/catalog");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            throw new RuntimeException(e);
        }

    }

    public static void user (HttpServletRequest request, HttpServletResponse response) {
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
            request.setAttribute("admin",true);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "adminManagement/user");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            throw new RuntimeException(e);
        }

    }

    public static void order (HttpServletRequest request, HttpServletResponse response) {
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
            request.setAttribute("admin",true);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "adminManagement/order");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            throw new RuntimeException(e);
        }

    }

    public static void report (HttpServletRequest request, HttpServletResponse response) {
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
            request.setAttribute("admin",true);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "adminManagement/report");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            throw new RuntimeException(e);
        }

    }

}
