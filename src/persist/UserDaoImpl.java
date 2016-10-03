package persist;

import model.AuthLevel;
import model.User;

import java.io.*;
import java.util.Map;
import java.util.HashMap;

/**
 * An implementation of a generic data access object, using a file as the backing store.
 */
public class UserDaoImpl implements IDao<User,String> {

    private String _fname;
    private Map<String, User> entries;

    public UserDaoImpl(String fileName) {
        entries = new HashMap<>();
        _fname = fileName;
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
                String[] arr = currLine.split("\\|");
                if (arr.length != 6)
                    continue;
                User entry = new User(arr[0], arr[1], AuthLevel.getFromString(arr[2]));
                entry.setFirstName(arr[3]);
                entry.setLastName(arr[4]);
                entry.setEmail(arr[5]);
                entries.put(arr[0], entry);
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
            for (User u : entries.values()) {
                String serialized = "";
                serialized += u.getUsername() + "|";
                serialized += u.getPassword() + "|";
                serialized += u.getAuthorization().getDisplayText() + "|";
                serialized += u.getFirstName() + "|";
                serialized += u.getLastName() + "|";
                serialized += u.getEmail() + "\n";
                out.write(serialized);
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
        entries.put(newObj.getUsername(), newObj);
        writeFile();
        return true;
    }

    @Override
    public boolean update(String pKey, User toUpdate) {
        readFile();
        entries.put(pKey, toUpdate);
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
            return entries.get(pKey);
        else
            return null;
    }
}
