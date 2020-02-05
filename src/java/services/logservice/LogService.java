package services.logservice;

/**
 *
 * @author Mr. Robot
 */
import java.io.IOException;
import java.util.logging.*;

import services.config.Configuration;

public class LogService {
    
    private static Logger applicationLogger;
    
    private LogService(){
    }
    
    public static Logger getApplicationLogger()
    {
        Configuration conf = new Configuration();
        SimpleFormatter formatterTxt;
        Handler fileHandler;
        
        try
        {
            if( applicationLogger == null )
            {
                applicationLogger = Logger.getLogger(conf.GLOBAL_LOGGER_NAME);
                fileHandler = new FileHandler(conf.GLOBAL_LOGGER_FILE, true);
                formatterTxt = new SimpleFormatter();
                fileHandler.setFormatter(formatterTxt);
                applicationLogger.addHandler(fileHandler);
                applicationLogger.setLevel(conf.GLOBAL_LOGGER_LEVEL);
                applicationLogger.setUseParentHandlers(false);
                applicationLogger.log(Level.CONFIG, "Logger: {0} created.", applicationLogger.getName());
            }
        } catch(IOException e){
            applicationLogger.log(Level.SEVERE, "Error occured in Logger creation", e);
        }
        return applicationLogger;
    }
}


