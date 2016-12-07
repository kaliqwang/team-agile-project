package fxapp;

import controller.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Location;
import model.User;
import model.WaterPurityReport;
import model.WaterSourceReport;
import model.persist.IGenericDAO;
import model.persist.IQueryableReportDAO;

/**
 * Created by angeljon121 on 12/6/16.
 */
public class ControllerFactory {

    private IGenericDAO<User, String> userData;
    private IQueryableReportDAO<WaterSourceReport, Integer> waterSourceData;
    private IQueryableReportDAO<WaterPurityReport, Integer> waterPurityData;
    private IGenericDAO<Location, Integer> locationData;

    public ControllerFactory(
            IGenericDAO<User, String> userData,
            IQueryableReportDAO<WaterSourceReport, Integer> waterSourceData,
            IQueryableReportDAO<WaterPurityReport, Integer> waterPurityData,
            IGenericDAO<Location, Integer> locationData) {
        this.userData = userData;
        this.waterSourceData = waterSourceData;
        this.waterPurityData = waterPurityData;
        this.locationData = locationData;
    }

    public AddLocationController createAddLocationController(FXMLLoader loader, Stage dialogStage, User currUser) {
        AddLocationController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setLocationDao(locationData);
        controller.setCurrUser(currUser);
        return controller;
    }

    public DataGraphController createDataGraphController(FXMLLoader loader, Stage dialogStage) {
        DataGraphController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setPurityReportDao(waterPurityData);
        controller.setLocationDao(locationData);
        controller.initializeLocations();
        controller.initializeYears();
        controller.initializeData();
        return controller;
    }

    public UserLoginController createUserLoginController(FXMLLoader loader, Stage dialogStage) {
        UserLoginController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setUserDao(userData);
        return controller;
    }

    public UserCreateController createUserCreateController(FXMLLoader loader, Stage dialogStage) {
        UserCreateController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setUserDao(userData);
        return controller;
    }

    public UserEditController createUserEditController(FXMLLoader loader, Stage dialogStage, User currUser) {
        UserEditController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setUserDao(userData);
        controller.setCurrUser(currUser);
        return controller;
    }

    public WaterSourceReportCreateController createSourceReportCreateController(FXMLLoader loader, Stage dialogStage, User currUser) {
        WaterSourceReportCreateController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setReportDao(waterSourceData);
        controller.setLocationDao(locationData);
        controller.setCurrUser(currUser);
        controller.initializeLocations();
        return controller;
    }

    public WaterSourceReportListController createSourceReportListController(FXMLLoader loader, Stage dialogStage) {
        WaterSourceReportListController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setUserDao(userData);
        controller.setReportDao(waterSourceData);
        return controller;
    }
    
    public WaterPurityReportCreateController createPurityReportCreateController(FXMLLoader loader, Stage dialogStage, User currUser) {
        WaterPurityReportCreateController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setReportDao(waterPurityData);
        controller.setLocationDao(locationData);
        controller.setCurrUser(currUser);
        controller.initializeLocations();
        return controller;
    }

    public WaterPurityReportListController createPurityReportListController(FXMLLoader loader, Stage dialogStage) {
        WaterPurityReportListController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setUserDao(userData);
        controller.setReportDao(waterPurityData);
        return controller;
    }
}
