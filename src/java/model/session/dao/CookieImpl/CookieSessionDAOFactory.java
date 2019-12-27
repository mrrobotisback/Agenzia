/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.session.dao.CookieImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.session.dao.LoggedUserDAO;
import model.session.dao.SessionDAOFactory;
/**
 *
 * @author Mr. Robot
 */

/**
 * 
 * classe che implementa i cookie 
 * @param request Richiesta dati http
 * @param response risposta al client
 */

public class CookieSessionDAOFactory extends SessionDAOFactory {
    private HttpServletRequest request; //variabili d'esemplare della classe 
    private HttpServletResponse response;

    @Override
    public void initSession(HttpServletRequest request, HttpServletResponse response) {

        try {
          this.request=request;
          this.response=response;
        } catch (Exception e) {
          throw new RuntimeException(e);
          }

    }

    @Override
    public LoggedUserDAO getLoggedUserDAO() {
        return new LoggedUserDAOCookieImpl(request,response);
    }
}
