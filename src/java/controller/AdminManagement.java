package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import model.dao.CategoryDAO;
import model.dao.TravelDAO;
import model.dao.DAOFactory;
import model.dao.UserDAO;
import model.dao.OrderDAO;
import model.mo.User;
import model.mo.Order;
import model.mo.Travel;
import model.session.dao.LoggedUserDAO;
import model.session.dao.SessionDAOFactory;
import model.session.mo.LoggedUser;
import services.config.Configuration;
import services.logservice.LogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

    public static void updateCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        services.config.Configuration conf = new services.config.Configuration();
        model.session.dao.SessionDAOFactory sessionDAOFactory;
        model.dao.DAOFactory daoFactory = null;
        model.session.mo.LoggedUser loggedUser;
        String applicationMessage = null;
        boolean updateResponse = false;

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
            CategoryDAO categoryDAO = daoFactory.getCategoryDAO();

            model.mo.User user = userDAO.findByUserId(loggedUser.getUserId());
            model.mo.User userRole = userDAO.checkRole(user.getUsername());

            String field    = request.getParameter("field");
            String value    = request.getParameter("value");
            Long id       = Long.valueOf(request.getParameter("id"));
            model.mo.Category categoryToUpdate = categoryDAO.findByCategoryId(id);
            if (userRole.getRole().equals("admin")) {
                updateResponse = categoryDAO.update(categoryToUpdate, field, value);
            }

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

    public static void updateTravel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        services.config.Configuration conf = new services.config.Configuration();
        model.session.dao.SessionDAOFactory sessionDAOFactory;
        model.dao.DAOFactory daoFactory = null;
        model.session.mo.LoggedUser loggedUser;
        String applicationMessage = null;
        boolean updateResponse = false;

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
            TravelDAO travelDAO = daoFactory.getTravelDAO();

            model.mo.User user = userDAO.findByUserId(loggedUser.getUserId());
            model.mo.User userRole = userDAO.checkRole(user.getUsername());

            String field    = request.getParameter("field");
            String value    = request.getParameter("value");
            Long id       = Long.valueOf(request.getParameter("id"));
            Travel travelToUpdate = travelDAO.findByTravelId(id);
            if (userRole.getRole().equals("admin")) {
                updateResponse = travelDAO.update(travelToUpdate, field, value);
            }

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

    public static void insertCategory(HttpServletRequest request, HttpServletResponse response) {

        Configuration conf = new Configuration();
        model.session.dao.SessionDAOFactory sessionDAOFactory;
        model.dao.DAOFactory daoFactory = null;
        String applicationMessage = null;
        model.session.mo.LoggedUser loggedUser;
        boolean error = false;

        Logger logger = services.logservice.LogService.getApplicationLogger();

        try {
            PrintWriter out = response.getWriter();
            JsonObject ajaxResponse = new JsonObject();

            sessionDAOFactory = model.session.dao.SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
            assert sessionDAOFactory != null;
            sessionDAOFactory.initSession(request, response);

            model.session.dao.LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
            loggedUser = loggedUserDAO.find();

            daoFactory = model.dao.DAOFactory.getDAOFactory(conf.DAO_IMPL);
            assert daoFactory != null;
            daoFactory.beginTransaction();

            CategoryDAO categoryDAO = daoFactory.getCategoryDAO();

            UserDAO userDAO = daoFactory.getUserDAO();

            model.mo.User user = userDAO.findByUserId(loggedUser.getUserId());

            if (user.getRole().equals("admin")) {
                try {
                    categoryDAO.insert(
                            request.getParameter("name"),
                            request.getParameter("description")
                    );
                } catch (model.dao.exception.DuplicatedObjectException e) {
                    error = true;
                    applicationMessage = "Category già esistente";
                    logger.log(Level.INFO, "Tentativo di inserimento categoria già esistente");
                }
            }

            if (!error) {
                ajaxResponse.addProperty("message", "Categoria: " + request.getParameter("name") +  " inserita!");
                ajaxResponse.addProperty("clear", 1);
            } else {
                ajaxResponse.addProperty("message", "Categoria già esistente!");
                ajaxResponse.addProperty("clear", 0);
            }
            out.println(ajaxResponse);

            out.println();

            daoFactory.commitTransaction();

            out.close();


        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
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

    public static void insertTravel(HttpServletRequest request, HttpServletResponse response) {

        Configuration conf = new Configuration();
        model.session.dao.SessionDAOFactory sessionDAOFactory;
        model.dao.DAOFactory daoFactory = null;
        String applicationMessage = null;
        model.session.mo.LoggedUser loggedUser;
        boolean error = false;

        Logger logger = services.logservice.LogService.getApplicationLogger();

        try {
            PrintWriter out = response.getWriter();
            JsonObject ajaxResponse = new JsonObject();

            sessionDAOFactory = model.session.dao.SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
            assert sessionDAOFactory != null;
            sessionDAOFactory.initSession(request, response);

            model.session.dao.LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
            loggedUser = loggedUserDAO.find();

            daoFactory = model.dao.DAOFactory.getDAOFactory(conf.DAO_IMPL);
            assert daoFactory != null;
            daoFactory.beginTransaction();

            TravelDAO travelDAO = daoFactory.getTravelDAO();

            UserDAO userDAO = daoFactory.getUserDAO();

            model.mo.User user = userDAO.findByUserId(loggedUser.getUserId());

            if (user.getRole().equals("admin")) {
                try {
                    travelDAO.insert(
                            Long.parseLong(request.getParameter("category")),
                            Double.parseDouble(request.getParameter("price")),
                            request.getParameter("name"),
                            Double.parseDouble(request.getParameter("discount")),
                            request.getParameter("start_date"),
                            Long.parseLong(request.getParameter("means")),
                            request.getParameter("description"),
                            request.getParameter("start_place"),
                            request.getParameter("start_hour"),
                            request.getParameter("duration"),
                            Integer.parseInt(request.getParameter("available_seats")),
                            Integer.parseInt(request.getParameter("total_seats")),
                            request.getParameter("destination"),
                            Boolean.parseBoolean(request.getParameter("hide"))
                    );
                } catch (model.dao.exception.DuplicatedObjectException e) {
                    error = true;
                    applicationMessage = "Viaggio già esistente";
                    logger.log(Level.INFO, "Tentativo di inserimento viaggio già esistente");
                }
            }

            if (!error) {
                ajaxResponse.addProperty("message", "Viaggio: " + request.getParameter("name") +  " inserito!");
                ajaxResponse.addProperty("clear", 1);
            } else {
                ajaxResponse.addProperty("message", "Viaggio già esistente!");
                ajaxResponse.addProperty("clear", 0);
            }
            out.println(ajaxResponse);

            out.println();

            daoFactory.commitTransaction();

            out.close();


        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
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
            UserDAO userDAO = daoFactory.getUserDAO();

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

    public static void delete(HttpServletRequest request, HttpServletResponse response) {
        Configuration conf = new Configuration();
        SessionDAOFactory sessionDAOFactory;
        model.dao.DAOFactory daoFactory = null;
        LoggedUser loggedUser;

        Logger logger = LogService.getApplicationLogger();

        try {

            sessionDAOFactory = SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
            assert sessionDAOFactory != null;
            sessionDAOFactory.initSession(request, response);

            LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
            loggedUser = loggedUserDAO.find();

            daoFactory = DAOFactory.getDAOFactory(conf.DAO_IMPL);
            assert daoFactory != null;
            daoFactory.beginTransaction();

            Long userId = Long.valueOf(request.getParameter("userId"));

            UserDAO userDAO = daoFactory.getUserDAO();
            User user = userDAO.findByUserId(userId);

            if (user != null) {
                userDAO.delete(user);
            }

            daoFactory.commitTransaction();

            request.setAttribute("user", userDAO.allUser());
            request.setAttribute("loggedOn",loggedUser!=null);
            request.setAttribute("admin",true);
            request.setAttribute("loggedUser", loggedUser);
            request.setAttribute("viewUrl", "adminManagement/user");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
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

    public static void deleteCategory(HttpServletRequest request, HttpServletResponse response) {
        Configuration conf = new Configuration();
        SessionDAOFactory sessionDAOFactory;
        model.dao.DAOFactory daoFactory = null;
        LoggedUser loggedUser;

        Logger logger = LogService.getApplicationLogger();

        try {

            sessionDAOFactory = SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
            assert sessionDAOFactory != null;
            sessionDAOFactory.initSession(request, response);

            LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
            loggedUser = loggedUserDAO.find();

            daoFactory = DAOFactory.getDAOFactory(conf.DAO_IMPL);
            assert daoFactory != null;
            daoFactory.beginTransaction();

            Long categoryId = Long.valueOf(request.getParameter("categoryId"));

            UserDAO userDAO = daoFactory.getUserDAO();
            model.mo.User user = userDAO.findByUserId(loggedUser.getUserId());
            CategoryDAO categoryDAO = daoFactory.getCategoryDAO();
            model.mo.Category category = categoryDAO.findByCategoryId(categoryId);

            if (user != null && user.getRole().equals("admin")) {
                categoryDAO.delete(category);
            }

            daoFactory.commitTransaction();

            PrintWriter out = response.getWriter();
            JsonObject ajaxResponse = new JsonObject();


            ajaxResponse.addProperty("message", 1);
            ajaxResponse.addProperty("result", "Categoria eliminata correttamente");
            out.println(ajaxResponse);

            out.println();

            out.close();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
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

    public static void deleteTravel(HttpServletRequest request, HttpServletResponse response) {
        Configuration conf = new Configuration();
        SessionDAOFactory sessionDAOFactory;
        model.dao.DAOFactory daoFactory = null;
        LoggedUser loggedUser;

        Logger logger = LogService.getApplicationLogger();

        try {

            sessionDAOFactory = SessionDAOFactory.getSesssionDAOFactory(conf.SESSION_IMPL);
            assert sessionDAOFactory != null;
            sessionDAOFactory.initSession(request, response);

            LoggedUserDAO loggedUserDAO = sessionDAOFactory.getLoggedUserDAO();
            loggedUser = loggedUserDAO.find();

            daoFactory = DAOFactory.getDAOFactory(conf.DAO_IMPL);
            assert daoFactory != null;
            daoFactory.beginTransaction();

            Long travelId = Long.valueOf(request.getParameter("travelId"));

            UserDAO userDAO = daoFactory.getUserDAO();
            model.mo.User user = userDAO.findByUserId(loggedUser.getUserId());
            TravelDAO travelDAO = daoFactory.getTravelDAO();
            Travel travel = travelDAO.findByTravelId(travelId);

            if (user != null && user.getRole().equals("admin")) {
                travelDAO.delete(travel);
            }

            daoFactory.commitTransaction();

            PrintWriter out = response.getWriter();
            JsonObject ajaxResponse = new JsonObject();


            ajaxResponse.addProperty("message", 1);
            ajaxResponse.addProperty("result", "Viaggio eliminato correttamente");
            out.println(ajaxResponse);

            out.println();

            out.close();

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller Error", e);
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

    public static void allOrder(HttpServletRequest request, HttpServletResponse response) {
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
            OrderDAO orderDAO = daoFactory.getOrderDAO();

            List<Order> orders = orderDAO.allOrder();

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            JsonObject ajaxResponse = new JsonObject();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            String JSONObject = gson.toJson(orders);
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
