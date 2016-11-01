package model.persist;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.WaterPurityReport;
import model.Location;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class WaterPurityReportDAO implements GenericDAO<WaterPurityReport, Integer> {

    private String _fname;
    private Map<Integer, WaterPurityReport.Data> entries;
    private Gson json;

    public WaterPurityReportDAO(String fileName) {
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
        Map<Integer, WaterPurityReport.Data> newEntries = null;
        try {
            newEntries = json.fromJson(rdr, new TypeToken<Map<Integer, WaterPurityReport.Data>>() {}.getType());
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
    public boolean persist(WaterPurityReport newObj) {
        readFile();
        entries.put(newObj.getReportNumber(), newObj.getPlainData());
        writeFile();
        return true;
    }

    @Override
    public boolean update(Integer pKey, WaterPurityReport toUpdate) {
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
    public WaterPurityReport get(Integer pKey) {
        readFile();
        if (entries.containsKey(pKey))
            return new WaterPurityReport(entries.get(pKey));
        else
            return null;
    }

    public int nextIndex() {
        readFile();
        return entries.size() + 1;
    }

    public List<WaterPurityReport> getAll() {
        readFile();
        List<WaterPurityReport> ret = new ArrayList<>();
        for (WaterPurityReport.Data entry : entries.values()) {
            ret.add(new WaterPurityReport(entry));
        }
        return ret;
    }

    public List<WaterPurityReport> getAllByLocation(Location l) {
        readFile();
        List<WaterPurityReport> ret = new ArrayList<>();
        for (WaterPurityReport.Data entry : entries.values()) {
            WaterPurityReport temp = new WaterPurityReport(entry);
            if (temp.getLocation().equals(l)) {
                ret.add(new WaterPurityReport(entry));
            }
        }
        return ret;
    }

    public List<WaterPurityReport> getAllByYear(Integer y) {
        readFile();
        List<WaterPurityReport> ret = new ArrayList<>();
        for (WaterPurityReport.Data entry : entries.values()) {
            WaterPurityReport temp = new WaterPurityReport(entry);
            if (temp.getDate().toInstant().atZone(ZoneId.of("EST")).toLocalDate().getYear() == y) {
                ret.add(new WaterPurityReport(entry));
            }
        }
        return ret;
    }

    public int getCount() {
        return entries.size();
    }
}
