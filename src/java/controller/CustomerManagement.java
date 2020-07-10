package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
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

    public static void info(HttpServletRequest request, HttpServletResponse response) {

        services.config.Configuration conf = new services.config.Configuration();
        model.session.dao.SessionDAOFactory sessionDAOFactory;
        model.session.mo.LoggedUser loggedUser;
        model.dao.DAOFactory daoFactory = null;
        model.mo.User user = null;
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
                user = userDAO.findByUserId(loggedUser.getUserId());
                model.mo.User userRole = userDAO.checkRole(user.getUsername());
                if (userRole.getRole().equals("admin")){
                    request.setAttribute("admin",true);
                } else {
                    request.setAttribute("admin",false);
                }
            } else {
                request.setAttribute("admin",false);
            }

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            assert user != null;
            user.setPassword("");
            String JSONObject = gson.toJson(user);

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("data", JSONObject);
            request.setAttribute("viewUrl", "customerManagement/info");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            throw new RuntimeException(e);
        }

    }

    public static void cart(HttpServletRequest request, HttpServletResponse response) {

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
            request.setAttribute("viewUrl", "customerManagement/cart");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            throw new RuntimeException(e);
        }

    }

    public static void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            model.session.dao.LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
            loggedUser = loggedUserDAO.find();

            daoFactory = model.dao.DAOFactory.getDAOFactory(conf.DAO_IMPL);
            assert daoFactory != null;
            daoFactory.beginTransaction();

            model.dao.UserDAO userDAO = daoFactory.getUserDAO();

            model.mo.User user = userDAO.findByUserId(loggedUser.getUserId());
            model.mo.User userRole = userDAO.checkRole(user.getUsername());

            String fields    = request.getParameter("params");
            String userId    = request.getParameter("id");
            Gson gson = new Gson();
            Map map = gson.fromJson(fields, Map.class);

            String result = "";
            for (Object key : map.keySet()){
                result = result + key + " = '" + (map.get(key)) + "' , ";
            }

            boolean updateResponse = userDAO.updateCustomer(Long.parseLong(userId), result.substring(0, result.length() - 2));

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            JsonObject ajaxResponse = new JsonObject();

            ajaxResponse.addProperty("response", updateResponse);
            out.println(ajaxResponse);

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
