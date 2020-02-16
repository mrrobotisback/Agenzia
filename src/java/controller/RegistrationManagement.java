package controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import services.config.Configuration;
import model.dao.UserDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrationManagement {
    private RegistrationManagement(){
    }

    public static void view(HttpServletRequest request, HttpServletResponse response) {

        Configuration conf = new Configuration();
        model.session.dao.SessionDAOFactory sessionDAOFactory;
        model.session.mo.LoggedUser loggedUser;

        Logger logger = services.logservice.LogService.getApplicationLogger();

        try {

            sessionDAOFactory = model.session.dao.SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
            sessionDAOFactory.initSession(request, response);

            model.session.dao.LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
            loggedUser = loggedUserDAO.find();

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "registrationManagement/view");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            throw new RuntimeException(e);
        }

    }

    public static void checkUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Configuration conf = new Configuration();
        model.session.dao.SessionDAOFactory sessionDAOFactory;
        model.dao.DAOFactory daoFactory = null;
        model.session.mo.LoggedUser loggedUser;
        String applicationMessage = null;

        Logger logger = services.logservice.LogService.getApplicationLogger();

        try {

            sessionDAOFactory = model.session.dao.SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
            assert sessionDAOFactory != null;
            sessionDAOFactory.initSession(request, response);

            model.session.dao.LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
            loggedUser = loggedUserDAO.find();

            daoFactory = model.dao.DAOFactory.getDAOFactory(conf.DAO_IMPL);
            assert daoFactory != null;
            daoFactory.beginTransaction();

            String username = request.getParameter("username");

            UserDAO userDAO = daoFactory.getUserDAO();
            model.mo.User user = userDAO.findByUsername(username);

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();

            if (user == null) {
                out.println("<font color=green><b>"+username+"</b> is avaliable");
                loggedUserDAO.destroy();
                applicationMessage = "Username e password errati!";
                loggedUser=null;
            } else {
                out.println("<font color=red><b>"+username+"</b> is already in use</font>");
                loggedUser = loggedUserDAO.create(user.getUserId(), user.getFirstname(), user.getSurname());
            }

            out.println();

            daoFactory.commitTransaction();

            out.close();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error perdincibacco", e);

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

    public static void insert(HttpServletRequest request, HttpServletResponse response) {

        Configuration conf = new Configuration();
        model.session.dao.SessionDAOFactory sessionDAOFactory;
        model.dao.DAOFactory daoFactory = null;
//        model.session.mo.LoggedUser loggedUser;
        String applicationMessage = null;

        Logger logger = services.logservice.LogService.getApplicationLogger();

        try {

            sessionDAOFactory = model.session.dao.SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
            assert sessionDAOFactory != null;
            sessionDAOFactory.initSession(request, response);

//            model.session.dao.LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
//            loggedUser = loggedUserDAO.find();

            daoFactory = model.dao.DAOFactory.getDAOFactory(conf.DAO_IMPL);
            assert daoFactory != null;
            daoFactory.beginTransaction();

            UserDAO userDAO = daoFactory.getUserDAO();


            try {
                userDAO.insert(
                        request.getParameter("firstname"),
                        request.getParameter("surname"),
                        request.getParameter("username"),
                        request.getParameter("password"),
                        request.getParameter("birthday"),
                        request.getParameter("sex"),
                        request.getParameter("via"),
                        request.getParameter("numero"),
                        request.getParameter("citta"),
                        request.getParameter("provincia"),
                        request.getParameter("cap"),
                        request.getParameter("phone"),
                        request.getParameter("email"),
                        request.getParameter("work"),
                        request.getParameter("cf")
                );

            } catch (model.dao.exception.DuplicatedObjectException e) {
                applicationMessage = "User già esistente";
                logger.log(Level.INFO, "Tentativo di inserimento user già esistente");
            }

//            commonView(daoFactory, sessionDAOFactory, request);

            daoFactory.commitTransaction();

            request.setAttribute("loggedOn",false);
//            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("applicationMessage", applicationMessage);
            request.setAttribute("viewUrl", "homeManagement/view");
//            request.setAttribute("controllerAction", "HomeManagement.view");

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

//    private static void commonView(model.dao.DAOFactory daoFactory, model.session.dao.SessionDAOFactory sessionDAOFactory, HttpServletRequest request) {
//
//        List<String> initials;
//        List<model.mo.Travel> travels;
//
//        model.session.dao.LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
//        model.session.mo.LoggedUser loggedUser = loggedUserDAO.find();
//
//        model.dao.UserDAO userDAO = daoFactory.getUserDAO();
//        model.mo.User user = userDAO.findByUserId(loggedUser.getUserId());
//
//        model.dao.TravelDAO travelDAO = daoFactory.getTravelDAO();
//        initials = travelDAO.findInitialsByUser(user);
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
//        travels = travelDAO.findByInitialAndSearchString(user,
//                (selectedInitial.equals("*") ? null : selectedInitial), null);
//
//        request.setAttribute("selectedInitial", selectedInitial);
//        request.setAttribute("initials", initials);
//        request.setAttribute("travels", travels);
//
//    }
}
