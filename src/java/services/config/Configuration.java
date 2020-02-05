package services.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.Date;

import model.dao.DAOFactory;
import model.session.dao.SessionDAOFactory;
import java.util.Properties;

public class Configuration {
    Properties props = new Properties();
    FileInputStream in;

    {
        try {
            in = new FileInputStream("/home/nicolo/universita/agenzia/src/env.properties");
            props.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Date time = new Date(System.currentTimeMillis());

    /* Database Configration */
    public final String DAO_IMPL = DAOFactory.MYSQLJDBCIMPL;
    public final String DATABASE_DRIVER = props.getProperty("DATABASE_DRIVER");
    public final String DATABASE_URL = props.getProperty("DATABASE_BASE_URL") + props.getProperty("db_name") + "?user=" + props.getProperty("user") + "&password=" + props.getProperty("password");

    /* Session Configuration */
    public final String SESSION_IMPL = SessionDAOFactory.COOKIEIMPL;

    /* Logger Configuration */
    public final String GLOBAL_LOGGER_NAME = props.getProperty("GLOBAL_LOGGER_NAME");
    public final String GLOBAL_LOGGER_FILE = props.getProperty("GLOBAL_LOGGER_FILE");
    public final Level GLOBAL_LOGGER_LEVEL = Level.ALL;

}