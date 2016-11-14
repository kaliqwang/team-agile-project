package model.persist;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserDAO implements GenericDAO<User,String> {

    private String _fname;
    private Map<String, User.Data> entries;
    private Gson json;

    /**
     * This creates a UserDAO with the specified file name
     * @param fileName the fileName of the file used for the DAO
     */
    public UserDAO(String fileName) {
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
        Map<String, User.Data> newEntries = null;
        try {
            newEntries = json.fromJson(rdr,
                    new TypeToken<Map<String, User.Data>>() {}.getType());
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
        try {
            String serialized = json.toJson(entries);
            out.write(serialized);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public boolean persist(User newObj) {
        readFile();
        entries.put(newObj.getUsername(), newObj.getPlainData());
        writeFile();
        return true;
    }

    @Override
    public boolean update(String pKey, User toUpdate) {
        readFile();
        entries.put(pKey, toUpdate.getPlainData());
        writeFile();
        return true;
    }

    @Override
    public boolean remove(String pKey) {
        readFile();
        if (entries.containsKey(pKey)) {
            entries.remove(pKey);
        }
        writeFile();
        return true;
    }

    @Override
    public User get(String pKey) {
        readFile();
        if (entries.containsKey(pKey))
            return new User(entries.get(pKey));
        else
            return null;
    }
}
