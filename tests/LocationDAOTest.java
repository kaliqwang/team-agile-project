import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import model.Location;
import model.persist.LocationDAO;

/**
 * Created by Kaliq Wang
 */
public class LocationDAOTest {
    private LocationDAO locationDAO;
    private Location l1, l2, l3;

    @Before
    public void setUp() throws Exception {
        locationDAO = new LocationDAO("location_test.json");
        l1 = new Location();
        l1.setPK(1);
        l1.setName("Test Location 1");
        l1.setLatitude(11.11);
        l1.setLongitude(11.11);
        l2 = new Location();
        l2.setPK(2);
        l2.setName("Test Location 2");
        l2.setLatitude(22.22);
        l2.setLongitude(22.22);
        l3 = new Location();
        l3.setPK(3);
        l3.setName("Test Location 3");
        l3.setLatitude(33.33);
        l3.setLongitude(33.33);
        locationDAO.persist(l1);
        locationDAO.persist(l2);
        locationDAO.persist(l3);
    }

    @Test
    public void get() throws Exception {
        Location try0 = locationDAO.get(0);
        Location try1 = locationDAO.get(1);
        Location try2 = locationDAO.get(2);
        Location try3 = locationDAO.get(3);
        Location try4 = locationDAO.get(4);
        assertEquals(try0, null);
        assertEquals(try1.getPK(), 1);
        assertEquals(try2.getPK(), 2);
        assertEquals(try3.getPK(), 3);
        assertEquals(try4, null);
    }

}