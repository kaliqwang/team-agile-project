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
    private User TestUser1, TestUser2, TestUser3;

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

        userDAO.persist(TestUser1);
        userDAO.persist(TestUser2);
        userDAO.persist(TestUser3);
    }

    @Test
    public void get() throws Exception {
        User try0 = userDAO.get("wsmith13");
        User try1 = userDAO.get("bnye21");
        User try2 = userDAO.get("jdepp8");
        User try3 = userDAO.get("wandy18");

        assertEquals(try0, "wsmith13");
        assertEquals(try1, "bnye21");
        assertEquals(try2, "jdepp8");
        assertEquals(try3, null);
        /*
        boolean try4 = userDAO.remove("wsmith13");
        boolean try5 = userDAO.remove("bnye21");
        boolean try6 = userDAO.remove("jdepp8");
        boolean try7 = userDAO.remove("wandy18");

        assertEquals(try4, true);
        assertEquals(try5, true);
        assertEquals(try6, true);
        assertEquals(try7, false);
        */
    }
}
