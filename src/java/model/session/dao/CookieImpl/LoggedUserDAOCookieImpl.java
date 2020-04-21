package model.session.dao.CookieImpl;

import model.session.dao.LoggedUserDAO;
import model.session.mo.LoggedUser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mr. Robot
 */
public class LoggedUserDAOCookieImpl implements LoggedUserDAO{
    
  HttpServletRequest request;
  HttpServletResponse response;
  /**
   * 
   * Costruttore che riceve il request fisico e il response
   *  @param request riceve il parametro da elaborare
   *  @param response parametro che specifica la risposta.
   */
  public LoggedUserDAOCookieImpl(HttpServletRequest request, HttpServletResponse response) {
    this.request = request;
    this.response = response;
  }

  @Override
  public LoggedUser create(
          Long Nome_Utente,
          String Nome,
          String Cognome,
          String role) {

    LoggedUser loggedUser = new LoggedUser();
    loggedUser.setUserId(Nome_Utente);
    loggedUser.setFirstname(Nome);
    loggedUser.setSurname(Cognome);
    loggedUser.setRole(role);

    Cookie cookie;
    cookie = new Cookie("loggedUserAgenzia", encode(loggedUser));//crea cookie
    cookie.setPath("/");
    response.addCookie(cookie);

    return loggedUser;

  }

  @Override
  public void update(LoggedUser loggedUser) {

    Cookie cookie;
    cookie = new Cookie("loggedUserAgenzia", encode(loggedUser));
    cookie.setPath("/");
    response.addCookie(cookie);

  }

  @Override
  public void destroy() {

    Cookie cookie;
    cookie = new Cookie("loggedUserAgenzia", "");
    cookie.setMaxAge(0);
    cookie.setPath("/");
    response.addCookie(cookie);

  }

  @Override
  public LoggedUser find() {

    Cookie[] cookies = request.getCookies();
    LoggedUser loggedUser = null;

    if (cookies != null) {
      for (int i = 0; i < cookies.length && loggedUser == null; i++) {
        if (cookies[i].getName().equals("loggedUserAgenzia")) {
          loggedUser = decode(cookies[i].getValue());
        }
      }
    }

    return loggedUser;

  }

  private String encode(LoggedUser loggedUser) {

    String encodedLoggedUser;
    encodedLoggedUser = loggedUser.getUserId() + "#" + loggedUser.getFirstname() + "#" + loggedUser.getSurname();
    return encodedLoggedUser;

  }

  private LoggedUser decode(String encodedLoggedUser) {

    LoggedUser loggedUser = new LoggedUser();
    
    String[] values = encodedLoggedUser.split("#");

    loggedUser.setUserId(Long.parseLong(values[0]));
    loggedUser.setFirstname(values[1]);
    loggedUser.setSurname(values[2]);

    return loggedUser;
    
  }
}
