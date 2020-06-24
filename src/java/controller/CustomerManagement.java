package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerManagement {

    private CustomerManagement() {
    }

    public static void view(HttpServletRequest request, HttpServletResponse response) {

        services.config.Configuration conf = new services.config.Configuration();
        model.session.dao.SessionDAOFactory sessionDAOFactory;
        model.session.mo.LoggedUser loggedUser;
        model.dao.DAOFactory daoFactory = null;

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

            model.dao.UserDAO userDAO = daoFactory.getUserDAO();
            if (loggedUser != null) {
                model.mo.User user = userDAO.findByUserId(loggedUser.getUserId());
                model.mo.User userRole = userDAO.checkRole(user.getUsername());
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
            request.setAttribute("viewUrl", "customerManagement/view");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            throw new RuntimeException(e);
        }

    }
}
