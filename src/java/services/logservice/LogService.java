package services.logservice;

import services.config.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.logging.*;

public class LogService {
    
    private static Logger applicationLogger;

    private LogService(){
    }

    public static Logger getApplicationLogger()
    {
        Configuration conf = new Configuration();
        SimpleFormatter formatterTxt;
        FileHandler fileHandler;
        
        try
        {
            if( applicationLogger == null)
            {
               LogManager lm = LogManager.getLogManager();
               File logDir = new File(conf.LOG_FOLDER);

               		if( !(logDir.exists()) )
               			logDir.mkdir();
                String absoluteTestPath = new File(conf.LOG_FOLDER).getAbsolutePath();
               	Path path = Paths.get(absoluteTestPath);
               	Path setPath = Paths.get(conf.LIB_FOLDER);

               	FileOwnerAttributeView view = Files.getFileAttributeView(path,FileOwnerAttributeView.class);
                UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
                UserPrincipal userPrincipal = lookupService.lookupPrincipalByName(conf.GLOBAL_LOGGER_OWNER);
                GroupPrincipal group = Files.readAttributes(setPath, PosixFileAttributes.class, LinkOption.NOFOLLOW_LINKS).group();

                Files.getFileAttributeView(path, PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS).setGroup(group);
                Files.setOwner(path, userPrincipal);

                applicationLogger = Logger.getLogger(conf.GLOBAL_LOGGER_NAME);
                lm.addLogger(applicationLogger);
                fileHandler = new FileHandler(conf.GLOBAL_LOGGER_FILE, true);

                Path logFilePath = Paths.get(conf.GLOBAL_LOGGER_FILE);
                Files.getFileAttributeView(logFilePath, PosixFileAttributeView.class, LinkOption.NOFOLLOW_LINKS).setGroup(group);
                Files.setOwner(logFilePath, userPrincipal);

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


