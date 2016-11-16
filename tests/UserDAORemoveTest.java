import model.AuthLevel;
import model.User;
import model.persist.UserDAO;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;

/**
 * Created by Rayner Kristanto on 11/15/16.
 */
public class UserDAORemoveTest {


    private UserDAO userDao;

    @Before
    public void setUp() {
        try {
            userDao = new UserDAO("user_test2.json");
            User a = new User("a", "apassword", AuthLevel.USER);
            User b = new User("b", "bpassword", AuthLevel.WORKER);
            User c = new User("c", "cpassword", AuthLevel.MANAGER);
            userDao.persist(a);
            userDao.persist(b);
            userDao.persist(c);

        } catch (Exception e) {
            System.out.println("Something went wrong when creating DAO and users and persisting them");
        }

    }

    @Test
    public void remove() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("user_test2.json"));
            int lines = 0;
            while (reader.readLine() != null) {
                lines = lines + 1;
            }
            reader.close();
            assertEquals(lines, 1);

            boolean aTest = userDao.remove("a");
            boolean bTest = userDao.remove("b");
            boolean cTest = userDao.remove("c");
            boolean dTest = userDao.remove("d");

            assertEquals(aTest, true);
            assertEquals(bTest, true);
            assertEquals(cTest, true);
            assertEquals(dTest, false);

            User a = userDao.get("a");
            User b = userDao.get("c");

            assertEquals(a, null);
            assertEquals(b, null);

            System.out.println("Everything works!");
        } catch (Exception e) {
            System.out.println("Something went wrong with file, remove(), or get()");
        }
    }
}
