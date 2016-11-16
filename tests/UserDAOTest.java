import model.AuthLevel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import model.User;
import model.persist.UserDAO;


/**
 * Created by William Anderson on 11/15/2016.
 */
public class UserDAOTest {
    private UserDAO userDAO;
    private User TestUser1, TestUser2, TestUser3, TestUser4, TestUser5;

    @Before
    public void setUp() throws Exception {
        userDAO = new UserDAO("user_test.json");
        TestUser1 = new User();
        TestUser1.setUsername("wsmith13");
        TestUser1.setFirstName("Will");
        TestUser1.setLastName("Smith");
        TestUser1.setEmail("wsmith13@gmail.com");
        TestUser1.setAuthorization(AuthLevel.WORKER);
        TestUser1.setPassword("1234");

        TestUser2 = new User();
        TestUser2.setUsername("bnye21");
        TestUser2.setFirstName("Bill");
        TestUser2.setLastName("Nye");
        TestUser2.setEmail("bnye21@gmail.com");
        TestUser2.setAuthorization(AuthLevel.ADMINISTRATOR);
        TestUser2.setPassword("4321");

        TestUser3 = new User();
        TestUser3.setUsername("jdepp8");
        TestUser3.setFirstName("Johnny");
        TestUser3.setLastName("Depp");
        TestUser3.setEmail("jdepp8@gmail.com");
        TestUser3.setAuthorization(AuthLevel.MANAGER);
        TestUser3.setPassword("5678");

        TestUser4 = new User();
        TestUser4.setUsername("hanh14");
        TestUser4.setFirstName("Hanh");
        TestUser4.setLastName("Pham");
        TestUser4.setEmail("hanh14@gmail.com");
        TestUser4.setAuthorization(AuthLevel.USER);
        TestUser4.setPassword("8765");

        TestUser5 = new User();
        TestUser5.setUsername("wanderson43");
        TestUser5.setFirstName("William");
        TestUser5.setLastName("Anderson");
        TestUser5.setEmail("wanderson43@gmail.com");
        TestUser5.setAuthorization(AuthLevel.MANAGER);
        TestUser5.setPassword("2130");



        userDAO.persist(TestUser1);
        userDAO.persist(TestUser2);
        userDAO.persist(TestUser3);
        userDAO.persist(TestUser4);
        userDAO.persist(TestUser5);
    }

    @Test
    public void get() throws Exception {
        User attempt1 = userDAO.get("wsmith13");
        User attempt2 = userDAO.get("bnye21");
        User attempt3 = userDAO.get("jdepp8");
        User attempt4 = userDAO.get("hanh14");
        User attempt5 = userDAO.get("wanderson43");
        User attempt6 = userDAO.get("wandy18");

        assertEquals(attempt1, "wsmith13");
        assertEquals(attempt2, "bnye21");
        assertEquals(attempt3, "jdepp8");
        assertEquals(attempt4, "hanh14");
        assertEquals(attempt5, "wanderson43");
        assertEquals(attempt6, null);
    }
}
