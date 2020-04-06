package controller;

import model.dao.UserDAO;
import services.config.Configuration;
import services.password.Password;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            request.setAttribute("admin",false);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "registrationManagement/view");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            throw new RuntimeException(e);
        }

    }

    public static void insert(HttpServletRequest request, HttpServletResponse response) {

        Configuration conf = new Configuration();
        Password pwd = new Password();
        String crypt = conf.STRING_FOR_CRYPT;
        model.session.dao.SessionDAOFactory sessionDAOFactory;
        model.dao.DAOFactory daoFactory = null;
        String applicationMessage = null;
        model.session.mo.LoggedUser loggedUser;

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

            UserDAO userDAO = daoFactory.getUserDAO();

            if (loggedUser == null) {
                try {
                    userDAO.insert(
                            request.getParameter("firstname"),
                            request.getParameter("surname"),
                            request.getParameter("username"),
                            pwd.hashPassword(request.getParameter("password") + crypt),
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
                            request.getParameter("cf"),
                            "user"
                    );
                    request.setAttribute("admin",false);
                    request.setAttribute("loggedOn",false);
                } catch (model.dao.exception.DuplicatedObjectException e) {
                    applicationMessage = "User già esistente";
                    logger.log(Level.INFO, "Tentativo di inserimento user già esistente");
                }
            } else {
                try {
                    model.mo.User user = userDAO.findByUserId(loggedUser.getUserId());
                    model.mo.User userRole = userDAO.checkRole(user.getUsername());
                    if (userRole.getRole().equals("admin")) {
                        userDAO.insert(
                                request.getParameter("firstname"),
                                request.getParameter("surname"),
                                request.getParameter("username"),
                                pwd.hashPassword(request.getParameter("password") + crypt),
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
                                request.getParameter("cf"),
                                request.getParameter("admin")
                        );
                        request.setAttribute("admin",true);
                        request.setAttribute("loggedOn",true);
                        request.setAttribute("loggedUser", loggedUser);
                    }

                } catch (model.dao.exception.DuplicatedObjectException e) {
                    applicationMessage = "User già esistente";
                    logger.log(Level.INFO, "Tentativo di inserimento user già esistente");
                }
            }
            daoFactory.commitTransaction();


            request.setAttribute("applicationMessage", applicationMessage);
            if (loggedUser == null) {
                request.setAttribute("viewUrl", "homeManagement/view");
            } else {
                request.setAttribute("viewUrl", "adminManagement/user");
            }

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
}
