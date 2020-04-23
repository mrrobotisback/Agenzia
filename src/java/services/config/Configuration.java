package services.config;

import model.dao.DAOFactory;
import model.session.dao.SessionDAOFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

public class Configuration {
    Properties props = new Properties();
    FileInputStream in;

    {
        try {
            File file = new File("src/env.properties");
            String path = file.getAbsolutePath();
            in = new FileInputStream(path);
            props.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Database Configration */
    public final String DAO_IMPL = DAOFactory.MYSQLJDBCIMPL;
    public final String STRING_FOR_CRYPT = props.getProperty("STRINGFORCRYPT");
    public final String DATABASE_DRIVER = props.getProperty("DATABASE_DRIVER");
    public final String DATABASE_URL =  props.getProperty("DATABASE_BASE_URL") + props.getProperty("db_name")
                                        + "?user=" + props.getProperty("user")
                                        + "&password=" + props.getProperty("password")
                                        + "&serverTimezone=" + props.getProperty("timezone");

    /* Session Configuration */
    public final String SESSION_IMPL = SessionDAOFactory.COOKIEIMPL;

    /* Logger Configuration */
    public final String GLOBAL_LOGGER_OWNER = props.getProperty("GLOBAL_LOGGER_OWNER");
    public final String GLOBAL_LOGGER_NAME = props.getProperty("GLOBAL_LOGGER_NAME");
    public final String GLOBAL_LOGGER_FILE = props.getProperty("GLOBAL_LOGGER_FILE");
    public final String LOG_FOLDER = props.getProperty("LOG_FOLDER");
    public final String LIB_FOLDER = props.getProperty("LIB_FOLDER");
    public final Level GLOBAL_LOGGER_LEVEL = Level.ALL;

}