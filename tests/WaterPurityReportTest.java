
import org.junit.Before;
import org.junit.Test;
import model.persist.WaterPurityReportDAO;

import static org.junit.Assert.assertEquals;


public class WaterPurityReportTest {
    private WaterPurityReportDAO waterPurityReportDAO;

    @Before
    public void setUp() throws Exception {
        waterPurityReportDAO = new WaterPurityReportDAO("waterPurityReport.json");

    }

    @Test
    public void get() throws Exception {
        assertEquals(waterPurityReportDAO.get(1).getAuthor(), "manager");
        assertEquals(waterPurityReportDAO.get(1).getContaminantPPM(), 3312.0, 0.000);
        assertEquals(waterPurityReportDAO.get(1).getLocation().getLatitude(), 33.7489954, 0.000);
        assertEquals(waterPurityReportDAO.get(1).getLocation().getLongitude(), -84.3879824, 0.000);
        assertEquals(waterPurityReportDAO.get(1).getVirusPPM(), 123.0, 0.000);
        assertEquals(waterPurityReportDAO.get(1).getWaterPurityCondition().toString(), "Unsafe");

        assertEquals(waterPurityReportDAO.get(2).getAuthor(), "manager");
        assertEquals(waterPurityReportDAO.get(2).getContaminantPPM(), 123.0, 0.000);
        assertEquals(waterPurityReportDAO.get(2).getLocation().getLatitude(), 45.00473059999999, 0.000);
        assertEquals(waterPurityReportDAO.get(2).getLocation().getLongitude(), -84.14389269999998, 0.000);
        assertEquals(waterPurityReportDAO.get(2).getVirusPPM(), 123.0, 0.000);
        assertEquals(waterPurityReportDAO.get(2).getWaterPurityCondition().toString(), "Safe");

        assertEquals(waterPurityReportDAO.get(3).getAuthor(), "username2");
        assertEquals(waterPurityReportDAO.get(3).getContaminantPPM(), 232.0, 0.000);
        assertEquals(waterPurityReportDAO.get(3).getLocation().getLatitude(), 33.7489954, 0.000);
        assertEquals(waterPurityReportDAO.get(3).getLocation().getLongitude(), -84.3879824, 0.000);
        assertEquals(waterPurityReportDAO.get(3).getVirusPPM(), 232.0, 0.000);
        assertEquals(waterPurityReportDAO.get(3).getWaterPurityCondition().toString(), "Treatable");
    }
}
