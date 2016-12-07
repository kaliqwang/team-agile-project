package model.persist;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import model.Location;
import model.WaterPurityReport;
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

public class WaterPurityReportNetDAO implements IQueryableReportDAO<WaterPurityReport, Integer> {

    private final Map<Integer, WaterPurityReport.Data> cache;
    private RESTClient<WaterPurityReport.Data> client;
    private JsonParser parser;

    /**
     * Creates a new instance of a WaterPurityReportNetDAO.
     * @param url the url of the service
     */
    public WaterPurityReportNetDAO(String url) {
        client = new RESTClient<>(url, WaterPurityReport.Data.class);
        cache = new HashMap<>();
        parser = new JsonParser();
    }

    private void tryUpdateAll() {
        List<WaterPurityReport.Data> newData = client.get();
        if (newData != null) {
            for (WaterPurityReport.Data entry : newData) {
                cache.put(entry.getRptId(), entry);
            }
        }
    }

    @Override
    public boolean persist(WaterPurityReport newObj) {
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
    public boolean update(Integer pKey, WaterPurityReport toUpdate) {
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
    public WaterPurityReport get(Integer pKey) {
        WaterPurityReport.Data fetch = client.get(pKey.toString());
        if (fetch != null) {
            cache.put(pKey, fetch);
        }
        if (cache.containsKey(pKey))
            return new WaterPurityReport(cache.get(pKey));
        else
            return null;
    }

    @Override
    public List<WaterPurityReport> getAll() {
        tryUpdateAll();
        return cache.values().stream().map(WaterPurityReport::new).collect(Collectors.toList());
    }

    @Override
    public List<WaterPurityReport> getAllByYear(Integer y) {
        tryUpdateAll();
        List<WaterPurityReport> ret = new ArrayList<>();
        for (WaterPurityReport.Data entry : cache.values()) {
            WaterPurityReport temp = new WaterPurityReport(entry);
            if (temp.getDate().toInstant().atZone(ZoneId.of("EST")).toLocalDate().getYear() == y) {
                ret.add(new WaterPurityReport(entry));
            }
        }
        return ret;
    }

    @Override
    public List<WaterPurityReport> getAllByLocation(Location l) {
        tryUpdateAll();
        List<WaterPurityReport> ret = new ArrayList<>();
        for (WaterPurityReport.Data entry : cache.values()) {
            WaterPurityReport temp = new WaterPurityReport(entry);
            if (temp.getLocation().equals(l)) {
                ret.add(new WaterPurityReport(entry));
            }
        }
        return ret;
    }

}