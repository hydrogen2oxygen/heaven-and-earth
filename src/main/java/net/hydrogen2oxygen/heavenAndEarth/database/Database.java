package net.hydrogen2oxygen.heavenAndEarth.database;

import net.hydrogen2oxygen.heavenAndEarth.domain.Project;
import net.hydrogen2oxygen.heavenAndEarth.domain.User;
import net.hydrogen2oxygen.heavenAndEarth.exceptions.DatabaseInitializationException;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteBuilder;
import org.dizitart.no2.exceptions.NitriteIOException;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Database Service Class
 */
@Service
public class Database {

    public static final String SETTINGS = "settings";
    private final Map<String,Nitrite> databases = new HashMap<>();
    private File rootFolder = new File("target");

    @PostConstruct
    public void init() {
        rootFolder = new File(".");
        init(SETTINGS);
    }

    public void init(String settingsName) {

        if (!rootFolder.exists()) {
            rootFolder.mkdirs();
        }

        // One database is for the global settings
        databases.put(SETTINGS,Nitrite.builder()
                .compressed()
                .filePath(rootFolder.getAbsolutePath() + "/" + settingsName + ".db")
                .openOrCreate(settingsName, settingsName));
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

                db = getNitriteBuilder(user).openOrCreate(user.getUserName(), user.getPassword());

                databases.put(user.getUserName(), db);
            } catch (SecurityException e) {
                throw new DatabaseInitializationException("Could not log into pre-existing database. Wrong user / password combination!");
            } catch (NitriteIOException e) {
                throw new DatabaseInitializationException("Technical error while trying to access database", e);
            }
        }

        return db;
    }

    private NitriteBuilder getNitriteBuilder(User user) {
        return Nitrite.builder()
                .compressed()
                .filePath(rootFolder.getPath() + "/" + user.getUserName()
                        .replaceAll(" ","").trim() + ".db");
    }

    public void saveProject(User user, Project project) throws DatabaseInitializationException {

        Nitrite db = getDatabaseForUser(user);

        if (project.getUuid() == null) {
            project.setUuid(UUID.randomUUID());
        }

        if (readProject(user, project.getName()) != null) {
            db.getRepository(Project.class).update(project);
        } else {
            db.getRepository(Project.class).insert(project);
        }
    }

    public Project readProject(User user, String projectName) throws DatabaseInitializationException {
        Nitrite db = getDatabaseForUser(user);
        return db.getRepository(Project.class).find(ObjectFilters.eq("name", projectName)).firstOrDefault();
    }

}

