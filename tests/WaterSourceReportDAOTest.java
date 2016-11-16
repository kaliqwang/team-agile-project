import model.*;
import model.persist.WaterSourceReportDAO;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Max Yang on 11/15/2016.
 */
public class WaterSourceReportDAOTest {
    WaterSourceReportDAO dao;
    User testUser;
    Location l;
    WaterSourceReport a, b, c;

    @Before
    public void setup() throws Exception {
        dao = new WaterSourceReportDAO("water_test.json");

        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setFirstName("First");
        testUser.setLastName("Last");
        testUser.setAuthorization(AuthLevel.ADMINISTRATOR);

        l = new Location();
        l.setLatitude(0);
        l.setLongitude(0);

        a = new WaterSourceReport();
        a.setReportNumber(1);
        a.setAuthor(testUser.getUsername());
        a.setLocation(l);
        a.setWaterSourceCondition(WaterSourceCondition.POTABLE);
        a.setWaterSourceType(WaterSourceType.BOTTLED);


        b = new WaterSourceReport();
        b.setReportNumber(2);
        b.setAuthor(testUser.getUsername());
        b.setLocation(l);
        b.setWaterSourceCondition(WaterSourceCondition.TREATABLECLEAR);
        b.setWaterSourceType(WaterSourceType.LAKE);


        c = new WaterSourceReport();
        c.setReportNumber(3);
        c.setAuthor(testUser.getUsername());
        c.setLocation(l);
        c.setWaterSourceCondition(WaterSourceCondition.TREATABLEMUDDY);
        c.setWaterSourceType(WaterSourceType.SPRING);

        dao.persist(a);
        dao.persist(b);
        dao.persist(c);
    }

    @Test
    public void GetTest() throws Exception {
        WaterSourceReport rpt1 = dao.get(1);
        WaterSourceReport rpt2 = dao.get(2);
        WaterSourceReport rpt3 = dao.get(3);
        WaterSourceReport rpt4 = dao.get(4);
        //check report 1
        assertEquals((long)rpt1.getReportNumber(), 1);
        assertEquals(rpt1.getAuthor(), a.getAuthor());
        assertEquals(rpt1.getLocation(), a.getLocation());
        assertEquals(rpt1.getWaterSourceCondition(), a.getWaterSourceCondition());
        assertEquals(rpt1.getWaterSourceType(), a.getWaterSourceType());
        //check report 2
        assertEquals((long)rpt2.getReportNumber(), 2);
        assertEquals(rpt2.getAuthor(), b.getAuthor());
        assertEquals(rpt2.getLocation(), b.getLocation());
        assertEquals(rpt2.getWaterSourceCondition(), b.getWaterSourceCondition());
        assertEquals(rpt2.getWaterSourceType(), b.getWaterSourceType());
        //check report 3
        assertEquals((long)rpt3.getReportNumber(), 3);
        assertEquals(rpt3.getAuthor(), c.getAuthor());
        assertEquals(rpt3.getLocation(), c.getLocation());
        assertEquals(rpt3.getWaterSourceCondition(), c.getWaterSourceCondition());
        assertEquals(rpt3.getWaterSourceType(), c.getWaterSourceType());
        //nonexistent report should return null
        assertNull(rpt4);

    }
}
