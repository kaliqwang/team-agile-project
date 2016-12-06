package model.persist;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.Location;
import model.WaterSourceReport;

import java.io.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WaterSourceReportDAO implements IGenericDAO<WaterSourceReport, Integer> {

    private final String _fname;
    private final Map<Integer, WaterSourceReport.Data> entries;
    private final Gson json;

    /**
     * This creates a WaterSourceReportDAO with the specified file name
     * @param fileName the fileName of the file used for the DAO
     */
    public WaterSourceReportDAO(String fileName) {
        entries = new HashMap<>();
        _fname = fileName;
        json = new Gson();
        File source = new File(_fname);
        try {
            source.createNewFile();
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
        Map<Integer, WaterSourceReport.Data> newEntries = null;
        try {
            newEntries = json.fromJson(rdr, new TypeToken<Map<Integer, WaterSourceReport.Data>>() {}.getType());
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
    public boolean persist(WaterSourceReport newObj) {
        readFile();
        entries.put(newObj.getReportNumber(), newObj.getPlainData());
        writeFile();
        return true;
    }

    @Override
    public boolean update(Integer pKey, WaterSourceReport toUpdate) {
        readFile();
        entries.put(pKey, toUpdate.getPlainData());
        writeFile();
        return true;
    }

    @Override
    public boolean remove(Integer pKey) {
        readFile();
        if (entries.containsKey(pKey)) {
            entries.remove(pKey);
        }
        writeFile();
        return true;
    }

    @Override
    public WaterSourceReport get(Integer pKey) {
        readFile();
        if (entries.containsKey(pKey))
            return new WaterSourceReport(entries.get(pKey));
        else
            return null;
    }

    /**
     * This method get the next index in the file.
     * @return the next index
     */
    public int nextIndex() {
        readFile();
        return entries.size() + 1;
    }

    /**
     * This method gets all the entries.
     * @return array list of all the entries.
     */
    public List<WaterSourceReport> getAll() {
        readFile();
        return entries.values().stream().map(WaterSourceReport::new).collect(Collectors.toList());
    }

    /**
     * This method gets all the entries by location.
     * @param l location input to find entries at that location.
     * @return array list of all the entries by location.
     */
    public List<WaterSourceReport> getAllByLocation(Location l) {
        readFile();
        List<WaterSourceReport> ret = new ArrayList<>();
        for (WaterSourceReport.Data entry : entries.values()) {
            WaterSourceReport temp = new WaterSourceReport(entry);
            if (temp.getLocation().equals(l)) {
                ret.add(new WaterSourceReport(entry));
            }
        }
        return ret;
    }

    /**
     * This method gets all the entries by year.
     * @param y year input to find entries at that year.
     * @return array list of all the entries by year.
     */
    public List<WaterSourceReport> getAllByYear(Integer y) {
        readFile();
        List<WaterSourceReport> ret = new ArrayList<>();
        for (WaterSourceReport.Data entry : entries.values()) {
            WaterSourceReport temp = new WaterSourceReport(entry);
            if (temp.getDate().toInstant().atZone(ZoneId.of("EST")).toLocalDate().getYear() == y) {
                ret.add(new WaterSourceReport(entry));
            }
        }
        return ret;
    }
}
