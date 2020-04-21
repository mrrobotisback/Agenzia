package controller;

import model.session.dao.LoggedUserDAO;
import model.session.dao.SessionDAOFactory;
import model.session.mo.LoggedUser;
import services.config.Configuration;
import services.logservice.LogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        model.dao.DAOFactory daoFactory = null;

        Logger logger = LogService.getApplicationLogger();

        try {

            sessionDAOFactory = SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
            assert sessionDAOFactory != null;
            sessionDAOFactory.initSession(request, response);

            LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
            loggedUser = loggedUserDAO.find();

            List<String> user;
            daoFactory = model.dao.DAOFactory.getDAOFactory(conf.DAO_IMPL);
            assert daoFactory != null;
            daoFactory.beginTransaction();
            model.dao.UserDAO userDAO = daoFactory.getUserDAO();

            request.setAttribute("user", userDAO.allUser());
            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("admin",true);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "adminManagement/user");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            throw new RuntimeException(e);
        }

    }

//    private static void commonView(DAOFactory daoFactory, SessionDAOFactory sessionDAOFactory, HttpServletRequest request) {
//        List<String> initials;
//        List<Contact> contacts;
//
//        LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
//        LoggedUser loggedUser = loggedUserDAO.find();
//
//        UserDAO userDAO = daoFactory.getUserDAO();
//        User user = userDAO.findByUserId(loggedUser.getUserId());
//
//        ContactDAO contactDAO = daoFactory.getContactDAO();
//        initials = contactDAO.findInitialsByUser(user);
//
//        String selectedInitial = request.getParameter("selectedInitial");
//
//        if (selectedInitial == null || (!selectedInitial.equals("*") && !initials.contains(selectedInitial))) {
//            if (initials.size() > 0) {
//                selectedInitial = initials.get(0);
//            } else {
//                selectedInitial = "*";
//            }
//        }
//
//        contacts = contactDAO.findByInitialAndSearchString(user,
//                (selectedInitial.equals("*") ? null : selectedInitial), null);
//
//        request.setAttribute("selectedInitial", selectedInitial);
//        request.setAttribute("initials", initials);
//        request.setAttribute("contacts", contacts);
//
//    }

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
