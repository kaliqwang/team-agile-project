package model.persist;

import com.google.gson.Gson;
import model.WaterSourceReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

/**
 * Created by Rayner Kristanto on 10/11/16.
 */
public class WaterSourceReportDaoImpl implements IDao<WaterSourceReport, Integer> {

    private String _fname;
    private Map<Integer, WaterSourceReport.Data> entries;
    private Gson json;

    public WaterSourceReportDaoImpl(String fileName) {
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
                WaterSourceReport.Data entry = json.fromJson(currLine, WaterSourceReport.Data.class);
                WaterSourceReport currReport = new WaterSourceReport(entry);
                entries.put(currReport.getReportNumber(), entry);
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
            for (WaterSourceReport.Data wsr : entries.values()) {
                String serialized = json.toJson(wsr);
                out.write(serialized+"\n");
            }
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
