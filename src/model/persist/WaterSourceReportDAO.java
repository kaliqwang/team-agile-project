package model.persist;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.WaterSourceReport;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaterSourceReportDAO implements GenericDAO<WaterSourceReport, Integer> {

    private String _fname;
    private Map<Integer, WaterSourceReport.Data> entries;
    private Gson json;

    public WaterSourceReportDAO(String fileName) {
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
        Map<Integer, WaterSourceReport.Data> newEntries = null;
        try {
            newEntries = json.fromJson(rdr,
                    new TypeToken<Map<Integer, WaterSourceReport.Data>>() {}
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

    public int nextIndex() {
        readFile();
        return entries.size() + 1;
    }

    public List<WaterSourceReport> getAll() {
        readFile();
        List<WaterSourceReport> ret = new ArrayList<>();
        for (WaterSourceReport.Data entry : entries.values()) {
            ret.add(new WaterSourceReport(entry));
        }
        return ret;
    }
}