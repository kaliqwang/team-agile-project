package persist;

import model.WaterSourceReport;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

/**
 * Created by Rayner Kristanto on 10/11/16.
 */
public class WaterSourceReportDaoImpl implements IDao<WaterSourceReport, String> {

    private String _fname;
    private Map<String, WaterSourceReport> entries;
    public WaterSourceReportDaoImpl(String fileName) {
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
                WaterSourceReport entry = new WaterSourceReport();

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
            for (WaterSourceReport wsr : entries.values()) {
                String serialized = "";
                // serialized += wsr.getDate() + "|";
                serialized += wsr.getReportNumber() + "|";
                serialized += wsr.getAuthor() + "|";
                serialized += wsr.getWaterLocation() + "|";
                serialized += wsr.getWaterType() + "|";
                serialized += wsr.getWaterCondition() + "|";
                out.write(serialized);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Override
    public WaterSourceReport get(String pKey) {
        readFile();
        if (entries.containsKey(pKey))
            return entries.get(pKey);
        else
            return null;
    }
}
