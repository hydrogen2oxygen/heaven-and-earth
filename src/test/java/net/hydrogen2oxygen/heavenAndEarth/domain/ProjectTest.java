package net.hydrogen2oxygen.heavenAndEarth.domain;

import net.hydrogen2oxygen.heavenAndEarth.database.Database;
import net.hydrogen2oxygen.heavenAndEarth.exceptions.DatabaseInitializationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
import java.util.Calendar;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProjectTest {

    private Database database = new Database();
    private User testUser = new User();

    @BeforeAll
    public void setup() {
        database.init("testSettings");
        testUser.setUserName("test");
        testUser.setPassword("test");
    }

    @AfterAll
    public void shutDown() {
        database.shutDown();
    }

    @Test
    public void testProjectPersistence() throws DatabaseInitializationException {
        Project project = new Project();

        Post post = new Post();
        post.setCreated(Calendar.getInstance());
        post.setTitle("Hello World!");
        post.setDraft(false);
        post.getCategories().add(new Category("On my mind"));
        post.getTags().add(new Tag("test"));
        project.getPosts().add(post);

        Page mainPage = new Page();
        mainPage.setTitle("How everything began ...");
        mainPage.setCreated(Calendar.getInstance());
        project.getPages().add(mainPage);

        database.saveProject(testUser, project);

        Project persistedProject = database.readProject(testUser, project.getName());
        System.out.println(persistedProject.getUuid());

        for (Post p : persistedProject.getPosts()) {
            System.out.println(p.getTitle());
        }
    }
}
