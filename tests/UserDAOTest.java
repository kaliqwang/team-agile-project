import model.AuthLevel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import model.User;
import model.persist.UserDAO;

public class UserDAOTest {
    private UserDAO userDAO;

    @Before
    public void setUp() throws Exception {
        userDAO = new UserDAO("user_test.json");
        User testUser1 = new User();
        testUser1.setUsername("wsmith13");
        testUser1.setFirstName("Will");
        testUser1.setLastName("Smith");
        testUser1.setEmail("wsmith13@gmail.com");
        testUser1.setAuthorization(AuthLevel.WORKER);
        testUser1.setPassword("1234");

        User testUser2 = new User();
        testUser2.setUsername("bnye21");
        testUser2.setFirstName("Bill");
        testUser2.setLastName("Nye");
        testUser2.setEmail("bnye21@gmail.com");
        testUser2.setAuthorization(AuthLevel.ADMINISTRATOR);
        testUser2.setPassword("4321");

        User testUser3 = new User();
        testUser3.setUsername("jdepp8");
        testUser3.setFirstName("Johnny");
        testUser3.setLastName("Depp");
        testUser3.setEmail("jdepp8@gmail.com");
        testUser3.setAuthorization(AuthLevel.MANAGER);
        testUser3.setPassword("5678");

        User testUser4 = new User();
        testUser4.setUsername("hanh14");
        testUser4.setFirstName("Hanh");
        testUser4.setLastName("Pham");
        testUser4.setEmail("hanh14@gmail.com");
        testUser4.setAuthorization(AuthLevel.USER);
        testUser4.setPassword("8765");

        User testUser5 = new User();
        testUser5.setUsername("wanderson43");
        testUser5.setFirstName("William");
        testUser5.setLastName("Anderson");
        testUser5.setEmail("wanderson43@gmail.com");
        testUser5.setAuthorization(AuthLevel.MANAGER);
        testUser5.setPassword("2130");



        userDAO.persist(testUser1);
        userDAO.persist(testUser2);
        userDAO.persist(testUser3);
        userDAO.persist(testUser4);
        userDAO.persist(testUser5);
    }

    @Test
    public void get() throws Exception {
        User attempt1 = userDAO.get("wsmith13");
        User attempt2 = userDAO.get("bnye21");
        User attempt3 = userDAO.get("jdepp8");
        User attempt4 = userDAO.get("hanh14");
        User attempt5 = userDAO.get("wanderson43");
        User attempt6 = userDAO.get("wandy18");

        assertEquals(attempt1.getUsername(), "wsmith13");
        assertEquals(attempt2.getUsername(), "bnye21");
        assertEquals(attempt3.getUsername(), "jdepp8");
        assertEquals(attempt4.getUsername(), "hanh14");
        assertEquals(attempt5.getUsername(), "wanderson43");
        assertEquals(attempt6, null);
    }
}
