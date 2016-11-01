package model.persist;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.Location;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kaliq on 11/1/2016.
 */
public class LocationDAO implements GenericDAO<Location, Integer>{
    private String _fname;
    private Map<Integer, Location.Data> entries;
    private Gson json;

    public LocationDAO(String fileName) {
        entries = new HashMap<>();
        _fname = fileName;
        json = new Gson();
        File source = new File(_fname);
        try {
            if (!source.exists()) {
                source.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile() {
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(_fname));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        JsonReader rdr = new JsonReader(in);
        Map<Integer, Location.Data> newEntries = null;
        try {
            newEntries = json.fromJson(rdr,
                    new TypeToken<Map<Integer, Location.Data>>() {}
                            .getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (newEntries != null) {
            entries.putAll(newEntries);
        }
    }

    private void writeFile() {
        BufferedWriter out;
        try {
            out = new BufferedWriter(new FileWriter(_fname));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        String serialized = json.toJson(entries);
        try {
            out.write(serialized);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean persist(Location newObj) {
        readFile();
        entries.put(newObj.getPK(), newObj.getPlainData());
        writeFile();
        return true;
    }

    @Override
    public boolean update(Integer pk, Location toUpdate) {
        readFile();
        entries.put(pk, toUpdate.getPlainData());
        writeFile();
        return true;
    }

    @Override
    public boolean remove(Integer pk) {
        readFile();
        if (entries.containsKey(pk)) {
            entries.remove(pk);
        }
        writeFile();
        return true;
    }

    @Override
    public Location get(Integer pk) {
        readFile();
        if (entries.containsKey(pk))
            return new Location(entries.get(pk));
        else
            return null;
    }

    public int nextIndex() {
        readFile();
        return entries.size() + 1;
    }

    public List<Location> getAll() {
        readFile();
        List<Location> ret = new ArrayList<>();
        for (Location.Data entry : entries.values()) {
            ret.add(new Location(entry));
        }
        return ret;
    }

}
