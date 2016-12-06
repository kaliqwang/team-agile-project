package model.persist;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import model.Location;
import model.WaterSourceReport;
import net.RESTClient;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;

import java.io.InputStreamReader;
import java.io.Reader;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WaterSourceReportNetDAO implements IQueryableReportDAO<WaterSourceReport, Integer> {

    private final Map<Integer, WaterSourceReport.Data> cache;
    private RESTClient<WaterSourceReport.Data> client;
    private JsonParser parser;

    /**
     * Creates a new instance of a WaterPurityReportNetDAO.
     * @param url the url of the service
     */
    public WaterSourceReportNetDAO(String url) {
        client = new RESTClient<>(url, WaterSourceReport.Data.class);
        cache = new HashMap<>();
        parser = new JsonParser();
    }

    private void tryUpdateAll() {
        List<WaterSourceReport.Data> newData = client.get();
        if (newData != null) {
            for (WaterSourceReport.Data entry : newData) {
                cache.put(entry.getRptId(), entry);
            }
        }
    }

    @Override
    public boolean persist(WaterSourceReport newObj) {
        Integer userId = client.post(newObj.getPlainData(), (httpResponse) -> {
            StatusLine header = httpResponse.getStatusLine();
            if (header.getStatusCode() >= 300) {
                throw new HttpResponseException(header.getStatusCode(), header.getReasonPhrase());
            }
            Reader jsonResponse = new InputStreamReader(httpResponse.getEntity().getContent());
            JsonElement idxAt = parser.parse(jsonResponse);
            return idxAt.getAsJsonObject().get("rptId").getAsInt();
        });
        if (userId != null) {
            newObj.setReportNumber(userId);
            cache.put(userId, newObj.getPlainData());
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Integer pKey, WaterSourceReport toUpdate) {
        if (client.put(pKey.toString(), toUpdate.getPlainData())) {
            cache.put(pKey, toUpdate.getPlainData());
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Integer pKey) {
        if (client.delete(pKey.toString())) {
            cache.remove(pKey);
            return true;
        }
        return false;
    }

    @Override
    public WaterSourceReport get(Integer pKey) {
        WaterSourceReport.Data fetch = client.get(pKey.toString());
        if (fetch != null) {
            cache.put(pKey, fetch);
        }
        if (cache.containsKey(pKey))
            return new WaterSourceReport(cache.get(pKey));
        else
            return null;
    }

    public List<WaterSourceReport> getAll() {
        tryUpdateAll();
        return cache.values().stream().map(WaterSourceReport::new).collect(Collectors.toList());
    }

    public List<WaterSourceReport> getAllByLocation(Location l) {
        tryUpdateAll();
        List<WaterSourceReport> ret = new ArrayList<>();
        for (WaterSourceReport.Data entry : cache.values()) {
            WaterSourceReport temp = new WaterSourceReport(entry);
            if (temp.getLocation().equals(l)) {
                ret.add(new WaterSourceReport(entry));
            }
        }
        return ret;
    }

    public List<WaterSourceReport> getAllByYear(Integer y) {
        tryUpdateAll();
        List<WaterSourceReport> ret = new ArrayList<>();
        for (WaterSourceReport.Data entry : cache.values()) {
            WaterSourceReport temp = new WaterSourceReport(entry);
            if (temp.getDate().toInstant().atZone(ZoneId.of("EST")).toLocalDate().getYear() == y) {
                ret.add(new WaterSourceReport(entry));
            }
        }
        return ret;
    }
}
