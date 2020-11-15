package net.hydrogen2oxygen.heavenAndEarth.database;

import net.hydrogen2oxygen.heavenAndEarth.domain.User;
import net.hydrogen2oxygen.heavenAndEarth.exceptions.DatabaseInitializationException;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.exceptions.NitriteIOException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

/**
 * Database Service Class
 */
@Service
public class Database {

    public static final String SETTINGS = "settings";
    private String dbFilePath;
    private Map<String,Nitrite> databases = new HashMap<>();

    @PostConstruct
    public void init() {

        // One database is for the global settings
        databases.put(SETTINGS,Nitrite.builder()
                .compressed()
                .filePath(dbFilePath)
                .openOrCreate(SETTINGS, SETTINGS));
    }

    @PreDestroy
    public void shutDown() {
        for (Nitrite db : databases.values()) {
            db.close();
        }
    }

    public Nitrite getDatabaseForUser(User user) throws DatabaseInitializationException {

        Nitrite db = databases.get(user.getUserName());

        if (db == null) {
            try {
                db = Nitrite.builder()
                        .compressed()
                        .filePath(dbFilePath)
                        .openOrCreate(user.getUserName(), user.getPassword());

                databases.put(user.getUserName(), db);
            } catch (SecurityException e) {
                throw new DatabaseInitializationException("Could not log into pre-existing database. Wrong user / password combination!");
            } catch (NitriteIOException e) {
                throw new DatabaseInitializationException("Technical error while trying to access database", e);
            }
        }

        return db;
    }
}
