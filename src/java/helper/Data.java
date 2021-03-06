package helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import model.dao.CategoryDAO;
import model.dao.TravelDAO;
import model.dao.UserDAO;
import model.mo.Category;
import model.mo.Travel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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

    public static void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

            UserDAO userDAO = daoFactory.getUserDAO();

            model.mo.User user = userDAO.findByUserId(loggedUser.getUserId());
            model.mo.User userRole = userDAO.checkRole(user.getUsername());
//            if (userRole.getRole().equals("admin")) {}

            String field    = request.getParameter("field");
            String value    = request.getParameter("value");
            Long id       = Long.valueOf(request.getParameter("id"));
            model.mo.User userToUpdate = userDAO.findByUserId(id);
            boolean updateResponse = userDAO.update(userToUpdate, field, value);
//            model.dao.UserDAO userDAO = daoFactory.getUserDAO();
//            model.mo.User user = userDAO.findByUsername(username);

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

    public static void searchUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

            model.dao.UserDAO userDAO = daoFactory.getUserDAO();

            String field = request.getParameter("field");
            String value = request.getParameter("value");

            List<model.mo.User> users = userDAO.find(field, value);

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            JsonObject ajaxResponse = new JsonObject();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            String JSONObject = gson.toJson(users);
            ajaxResponse.addProperty("message", JSONObject);
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

    public static void searchCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

            model.dao.CategoryDAO categoryDAO = daoFactory.getCategoryDAO();

            String field = request.getParameter("field");
            String value = request.getParameter("value");

            List<model.mo.Category> categories = categoryDAO.find(field, value);

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            JsonObject ajaxResponse = new JsonObject();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            String JSONObject = gson.toJson(categories);
            ajaxResponse.addProperty("message", JSONObject);
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

    public static void allCategory (HttpServletRequest request, HttpServletResponse response) {
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
            CategoryDAO categoryDAO = daoFactory.getCategoryDAO();

            List<Category> categories = categoryDAO.allCategory();

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            JsonObject ajaxResponse = new JsonObject();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            String JSONObject = gson.toJson(categories);
            ajaxResponse.addProperty("message", JSONObject);
            out.println(ajaxResponse);

            out.println();

            daoFactory.commitTransaction();

            out.close();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            throw new RuntimeException(e);
        }

    }

    public static void searchTravel(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

            TravelDAO travelDAO = daoFactory.getTravelDAO();

            String field = request.getParameter("field");
            String value = request.getParameter("value");

            List<Travel> travels = travelDAO.find(field, value);

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            JsonObject ajaxResponse = new JsonObject();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            String JSONObject = gson.toJson(travels);
            ajaxResponse.addProperty("message", JSONObject);
            out.println(ajaxResponse);

            out.println();

            daoFactory.commitTransaction();

            out.close();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Helper Error perdincibacco searchTravel", e);

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

    public static void order(HttpServletRequest request, HttpServletResponse response) {

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
            request.setAttribute("viewUrl", "customerManagement/order");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            throw new RuntimeException(e);
        }

    }

}

// Usage of multiparam sent from api call where we doesn't know number of params and value.
//    Map<String, String[]> data = request.getParameterMap();
//    ArrayList<String> param = new ArrayList<String>();
//
//    Map<String, String> finalParam = new HashMap<String, String>();
//
//            for(Object key : data.keySet()){
//                    String keyStr = (String)key;
//                    String[] value = data.get(keyStr);
//                    param.add(keyStr);
//                    finalParam.put(keyStr, value[0]);
//                    param.add(value[0]);
//                    }
//                    String filed = finalParam.get("field");
//                    String value = finalParam.get("value");
//                    String userId = finalParam.get("id");