package controller;

import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import model.*;
import model.persist.*;

import javafx.fxml.FXML;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.XYChart.Data;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

import java.util.stream.IntStream;
import java.util.stream.Collectors;


public class DataGraphController {

    @FXML
    private ComboBox<Location> dataGraphLocation;

    @FXML
    private ComboBox<Integer> dataGraphYear;

    @FXML
    private CheckBox dataGraphContaminantData;

    @FXML
    private CheckBox dataGraphVirusData;

    @FXML
    private LineChart<Number, Number> lineChart;

    private Stage _dialogStage;

    private WaterPurityReportDAO _purityReportData;

    private LocationDAO _locationData;

    private List<WaterPurityReport> _currentDataAll;

    private List<WaterPurityReport> _currentDataLocation;

    private boolean currentDataLocationInitialized;

    private List<WaterPurityReport> _currentDataLocationYear;

    private boolean currentDataLocationYearInitialized;

    private ArrayList<Data> dataPoints1 = new ArrayList<>();

    private ArrayList<Data> dataPoints2 = new ArrayList<>();

    private boolean dataPointsDirty;

    /**
     * This method initializes all the locations onto the combo box
     */
    public void initializeLocations() {
        dataGraphLocation.setItems(FXCollections.observableArrayList(_locationData.getAll()));
    }

    /**
     * This method initializes all the years onto the combo box
     */
    public void initializeYears() {
        int j, k;
        WaterPurityReport earliest = _purityReportData.get(1);
        WaterPurityReport latest = _purityReportData.get(_purityReportData.getCount());
        if (earliest != null) {
//            j = earliest.getDate().toInstant().atZone(ZoneId.of("EST")).toLocalDate().getYear();
//            k = latest.getDate().toInstant().atZone(ZoneId.of("EST")).toLocalDate().getYear();

            Calendar cal = Calendar.getInstance();
            cal.setTime(earliest.getDate());
            j = cal.get(Calendar.YEAR);
            cal.setTime(latest.getDate());
            k = cal.get(Calendar.YEAR);
            dataGraphYear.setItems(FXCollections.observableArrayList(IntStream.range(j, k+1).boxed().collect(Collectors.toList())));
        }
    }

    /**
     * This method initializes all the data onto the combo box
     */
    public void initializeData() {
        _currentDataAll = _purityReportData.getAll();
    }

    /**
     * This method sets the stage passed in as the Dialog Stage
     * @param dialogStage the stage that will be set as the dialog stage
     */
    public void setDialogStage(Stage dialogStage) {
        _dialogStage = dialogStage;
    }


    /**
     * This method takes in a data access object and assigns
     * it to be the purity report dao.
     * @param dao the generic dao to be passed in to be used for the purity reports
     */
    public void setPurityReportDao(GenericDAO<WaterPurityReport, Integer> dao) { _purityReportData = (WaterPurityReportDAO) dao; }

    /**
     * This method takes in a data access object and assigns
     * it to be the location dao.
     * @param dao the location data access object to be passed in.
     */
    public void setLocationDao(LocationDAO dao) { _locationData = dao; }

    @FXML
    private void initialize() {
        // Data Graph
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");
        yAxis.setLabel("Concentration (ppm)");
        lineChart = new LineChart<>(xAxis,yAxis);
    }

    @FXML
    private void handleCancelPressed() {
        _dialogStage.close();
    }

    @FXML
    private void handleDataGraphLocationSelected() {
        Location selectedLocation = dataGraphLocation.getValue();
        if (selectedLocation != null) {
            _currentDataLocation = _currentDataAll.stream().filter(r -> r.getLocation().equals(selectedLocation)).collect(Collectors.toList());
            currentDataLocationInitialized = true;
            initializeYears();
            handleDataGraphYearSelected();
        }
    }

    @FXML
    private void handleDataGraphYearSelected() {
        Integer selectedYear = dataGraphYear.getValue();
        Calendar cal = Calendar.getInstance();
        if (selectedYear != null && currentDataLocationInitialized) {
            List<WaterPurityReport> temp = new ArrayList<>();
            for (WaterPurityReport r : _currentDataLocation) {
                cal.setTime(r.getDate());
                if (cal.get(Calendar.YEAR) == selectedYear) {
                    temp.add(r);
                }
            }
            _currentDataLocationYear = temp;
            currentDataLocationYearInitialized = true;
            dataPointsDirty = true;
            handleDataGraphContaminantDataSelected();
            handleDataGraphVirusDataSelected();
        }

    }

    @FXML
    private void handleDataGraphContaminantDataSelected() {
        if (currentDataLocationYearInitialized) {
            if (dataPointsDirty) { cleanDataPoints(); }
            updateDataGraph();
        }
    }

    @FXML
    private void handleDataGraphVirusDataSelected() {
        if (currentDataLocationYearInitialized) {
            if (dataPointsDirty) { cleanDataPoints(); }
            updateDataGraph();
        }
    }
    @SuppressWarnings("unchecked")
    private void cleanDataPoints() {
        dataPoints1 = new ArrayList();
        dataPoints2 = new ArrayList();

        int[] data1 = new int[13];
        int[] count1 = new int[13];
        int[] data2 = new int[13];
        int[] count2 = new int[13];

        Calendar cal = Calendar.getInstance();

        for (WaterPurityReport r : _currentDataLocationYear) {
            cal.setTime(r.getDate());
            int m = cal.get(Calendar.MONTH);
            data1[m] += r.getContaminantPPM();
            count1[m] += 1;
            data2[m] += r.getVirusPPM();
            count2[m] += 1;
        }
        for (int i = 1; i < 13; i++) {
            dataPoints1.add(new Data(i, data1[i]/count1[i]));
            dataPoints2.add(new Data(i, data2[i]/count2[i]));
        }
        dataPointsDirty = false;
    }
    @SuppressWarnings("unchecked")
    private void updateDataGraph() {
        Series s1 = new Series();
        s1.setName("Contaminants");
        s1.getData().addAll(dataPoints1);

        Series s2 = new Series();
        s2.setName("Viruses");
        s2.getData().addAll(dataPoints2);

        if (dataGraphContaminantData.isSelected()) {
            lineChart.getData().add(s1);
        } else {
            lineChart.getData().remove(s1);
        }

        if (dataGraphVirusData.isSelected()) {
            lineChart.getData().add(s2);
        } else {
            lineChart.getData().remove(s2);
        }
    }
}
