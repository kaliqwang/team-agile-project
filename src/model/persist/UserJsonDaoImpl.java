package model.persist;

import model.AuthLevel;
import model.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;


/**
 * Created by Max Yang on 10/17/2016.
 */
public class UserJsonDaoImpl implements IDao<User,String> {

    private String _fname;
    private Map<String, User.Data> entries;
    private Gson json;

    public UserJsonDaoImpl(String fileName) {
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
        try {
            while (in.ready()) {
                String currLine = in.readLine();
                User.Data entry = json.fromJson(currLine, User.Data.class);
                User currUser = new User(entry);
                entries.put(currUser.getUsername(), entry);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
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
            for (User.Data u : entries.values()) {
                String serialized = json.toJson(u);
                out.write(serialized+"\n");
            }
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
