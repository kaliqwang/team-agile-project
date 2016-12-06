package model.persist;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import model.Location;
import net.RESTClient;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LocationNetDAO implements IGenericDAO<Location, Integer> {

    private final Map<Integer, Location.Data> cache;
    private RESTClient<Location.Data> client;
    private JsonParser parser;

    public LocationNetDAO(String url) {
        client = new RESTClient<>(url, Location.Data.class);
        cache = new HashMap<>();
        parser = new JsonParser();
    }

    @Override
    public boolean persist(Location newObj) {
        Integer locationId = client.post(newObj.getPlainData(), (httpResponse) -> {
            StatusLine header = httpResponse.getStatusLine();
            if (header.getStatusCode() >= 300) {
                throw new HttpResponseException(header.getStatusCode(), header.getReasonPhrase());
            }
            Reader jsonResponse = new InputStreamReader(httpResponse.getEntity().getContent());
            JsonElement idxAt = parser.parse(jsonResponse);
            return idxAt.getAsJsonObject().get("locationId").getAsInt();
        });
        if (locationId != null) {
            newObj.setPK(locationId);
            cache.put(locationId, newObj.getPlainData());
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Integer pKey, Location toUpdate) {
        //should not be able to update a location, for purposes of consistency
        return false;
    }

    @Override
    public boolean remove(Integer pKey) {
        //should not be able to remove a location, for purposes of consistency
        return false;
    }

    @Override
    public Location get(Integer pKey) {
        Location.Data fetch = client.get(pKey.toString());
        if (fetch != null) {
            cache.put(pKey, fetch);
        }
        if (cache.containsKey(pKey))
            return new Location(cache.get(pKey));
        else
            return null;
    }

    @Override
    public List<Location> getAll() {
        List<Location.Data> newData = client.get();
        if (newData != null) {
            for (Location.Data entry : newData) {
                cache.put(entry.getLocationId(), entry);
            }
        }
        return cache.values().stream().map(Location::new).collect(Collectors.toList());

    }
}
