package model.persist;

import model.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import net.RESTClient;

public class UserNetDAO implements GenericDAO<User,String> {

    private final Map<String, User.Data> cache;
    private RESTClient<User.Data> client;

    /**
     * Creates a new instance of a UserNetDAO.
     * @param url the url of the service
     */
    public UserNetDAO(String url) {
        cache = new HashMap<>();
        client = new RESTClient<>(url, User.Data.class);
    }

    @Override
    public boolean persist(User newObj) {
        if (client.post(newObj.getPlainData())) {
            cache.put(newObj.getUsername(), newObj.getPlainData());
            return true;
        }
        return false;
    }

    @Override
    public boolean update(String pKey, User toUpdate) {
        if (client.put(pKey, toUpdate.getPlainData())) {
            cache.put(pKey, toUpdate.getPlainData());
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(String pKey) {
        if (client.delete(pKey)) {
            cache.remove(pKey);
            return true;
        }
        return false;
    }

    @Override
    public User get(String pKey) {
        User.Data fetch = client.get(pKey);
        if (fetch != null) {
            cache.put(pKey, fetch);
        }
        if (cache.containsKey(pKey))
            return new User(cache.get(pKey));
        else
            return null;
    }
}
