package controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import services.config.Configuration;

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

    public static void insert(HttpServletRequest request, HttpServletResponse response) {

        Configuration conf = new Configuration();
        model.session.dao.SessionDAOFactory sessionDAOFactory;
        model.dao.DAOFactory daoFactory = null;
        model.session.mo.LoggedUser loggedUser;
        String applicationMessage = null;

        Logger logger = services.logservice.LogService.getApplicationLogger();

        try {

            sessionDAOFactory = model.session.dao.SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
            sessionDAOFactory.initSession(request, response);

            model.session.dao.LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
            loggedUser = loggedUserDAO.find();

            daoFactory = model.dao.DAOFactory.getDAOFactory(conf.DAO_IMPL);
            daoFactory.beginTransaction();

            model.dao.UserDAO userDAO = daoFactory.getUserDAO();
            model.mo.User user = userDAO.findByUserId(loggedUser.getUserId());

            model.dao.TravelDAO travelDAO = daoFactory.getTravelDAO();

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

            } catch (model.dao.exception.DuplicatedObjectException e) {
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

    private static void commonView(model.dao.DAOFactory daoFactory, model.session.dao.SessionDAOFactory sessionDAOFactory, HttpServletRequest request) {

        List<String> initials;
        List<model.mo.Travel> travels;

        model.session.dao.LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
        model.session.mo.LoggedUser loggedUser = loggedUserDAO.find();

        model.dao.UserDAO userDAO = daoFactory.getUserDAO();
        model.mo.User user = userDAO.findByUserId(loggedUser.getUserId());

        model.dao.TravelDAO travelDAO = daoFactory.getTravelDAO();
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
