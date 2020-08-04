package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
        Long idUser = null;

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
                idUser = loggedUser.getUserId();
            }

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("userId", idUser);
            request.setAttribute("viewUrl", "customerManagement/cart");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            throw new RuntimeException(e);
        }

    }

    public static void insertCart(HttpServletRequest request, HttpServletResponse response) {

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

            model.dao.CartDAO cartDAO = daoFactory.getCartDAO();
            model.dao.HaveDAO haveDAO = daoFactory.getHaveDAO();

            String userId = request.getParameter("userId");
            String productId = request.getParameter("productId");
            String price = request.getParameter("price");
            String quantity = "1";
            if (loggedUser != null) {
                cartDAO.insert(Long.parseLong(userId), Double.parseDouble(price));
                haveDAO.insert(Long.parseLong(userId), Long.parseLong(productId), Long.parseLong(quantity));
            }

            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("loggedUser", loggedUser);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            throw new RuntimeException(e);
        }

    }

    public static void getCart(HttpServletRequest request, HttpServletResponse response) {

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

            model.dao.CartDAO cartDAO = daoFactory.getCartDAO();
            model.dao.HaveDAO haveDAO = daoFactory.getHaveDAO();
            model.mo.Cart cart = null;

            String userId = request.getParameter("userId");

            List<model.mo.Have> haves = haveDAO.find(Long.parseLong(userId));

            if (loggedUser != null) {
                cart = cartDAO.findByUserId(Long.parseLong(userId));
                haves = haveDAO.find(Long.parseLong(userId));
            }

            PrintWriter out = response.getWriter();
            JsonObject ajaxResponse = new JsonObject();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            String JSONObjectHaves = gson.toJson(haves);
            String JSONObjectCart = gson.toJson(cart);
            ajaxResponse.addProperty("haves", JSONObjectHaves);
            ajaxResponse.addProperty("cart", JSONObjectCart);
            out.println(ajaxResponse);

            out.println();

            daoFactory.commitTransaction();

            out.close();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            throw new RuntimeException(e);
        }

    }

    public static void updateCart(HttpServletRequest request, HttpServletResponse response) {

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

            model.dao.CartDAO cartDAO = daoFactory.getCartDAO();
            model.dao.HaveDAO haveDAO = daoFactory.getHaveDAO();

            String travelCode = request.getParameter("travelCode");
            String quantity = request.getParameter("quantity");
            String userId = request.getParameter("userId");
            String price = request.getParameter("price");

            if (loggedUser != null) {
                haveDAO.update(Long.parseLong(travelCode), Long.parseLong(userId), Integer.parseInt(quantity));
                cartDAO.update(Long.parseLong(userId), Float.parseFloat(price));
            }

            PrintWriter out = response.getWriter();
            JsonObject ajaxResponse = new JsonObject();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            ajaxResponse.addProperty("message", "true");
            out.println(ajaxResponse);

            out.println();

            daoFactory.commitTransaction();

            out.close();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            throw new RuntimeException(e);
        }

    }

    public static void retriveMinimalTravel(HttpServletRequest request, HttpServletResponse response) { //TODO

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

            model.dao.TravelDAO travelDAO = daoFactory.getTravelDAO();
            model.mo.Travel travel = null;

            String travelCode = request.getParameter("travelCode");

            if (loggedUser != null) {
                travel = travelDAO.findByTravelId(Long.parseLong(travelCode));
            }

            PrintWriter out = response.getWriter();
            JsonObject ajaxResponse = new JsonObject();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            String JSONObjectHaves = gson.toJson(travel);
            ajaxResponse.addProperty("message", JSONObjectHaves);
            out.println(ajaxResponse);

            out.println();

            daoFactory.commitTransaction();

            out.close();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
            throw new RuntimeException(e);
        }

    }

    public static void deleteRowCart(HttpServletRequest request, HttpServletResponse response) { //TODO

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

            model.dao.CartDAO cartDAO = daoFactory.getCartDAO();
            model.dao.HaveDAO haveDAO = daoFactory.getHaveDAO();

            String travelCode = request.getParameter("travelCode");
            String partial = request.getParameter("partial");
            String userId = request.getParameter("userId");

            model.mo.Have have = haveDAO.find(Long.parseLong(userId), Long.parseLong(travelCode));

            if (loggedUser != null && have != null) {
                haveDAO.delete(have);
                cartDAO.update(Long.parseLong(userId), Float.parseFloat(partial));
            }

            PrintWriter out = response.getWriter();
            JsonObject ajaxResponse = new JsonObject();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            ajaxResponse.addProperty("message", "true");
            out.println(ajaxResponse);

            out.println();

            daoFactory.commitTransaction();

            out.close();

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
