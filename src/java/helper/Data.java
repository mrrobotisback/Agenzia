package helper;

import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Data {

    public static void checkUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

            String username = request.getParameter("username");

            model.dao.UserDAO userDAO = daoFactory.getUserDAO();
            model.mo.User user = userDAO.findByUsername(username);

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            JsonObject ajaxResponse = new JsonObject();

            if (user == null) {
                ajaxResponse.addProperty("response", "true");
                ajaxResponse.addProperty("message", "Username disponibile!");
                out.println(ajaxResponse);
                loggedUser=null;
            } else {
                ajaxResponse.addProperty("response", "false");
                ajaxResponse.addProperty("message", "Username non disponibile!");
                out.println(ajaxResponse);
            }

            out.println();

            daoFactory.commitTransaction();

            out.close();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Helper Error perdincibacco", e);

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
