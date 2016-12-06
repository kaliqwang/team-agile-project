package fxapp;

import model.*;
import model.persist.*;
import controller.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main application class.
 */
public class MainFXApplication extends Application {
    /**  the java logger for this class */
    private static final Logger LOGGER = Logger.getLogger("MainFXApplication");

    /** the main container for the application window */
    private Stage mainStage;

    /** the main layout for the main window */
    private BorderPane rootLayout;

    private GenericDAO<User, String> userData;
    private GenericDAO<WaterSourceReport, Integer> waterSourceData;
    private GenericDAO<WaterPurityReport, Integer> waterPurityData;
    private LocationDAO locationData;
    private User currUser;

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        userData = new UserNetDAO("http://localhost:2340/user");
        waterSourceData = new WaterSourceReportDAO("waterSourceReport.json");
        waterPurityData = new WaterPurityReportDAO("waterPurityReport.json");
        locationData = new LocationDAO("location.json");
        initRootLayout(mainStage);
        showWelcomeScreen();
    }

    /**
     * return a reference to the main window stage
     * @return reference to main Stage
     * */
    public Stage getMainStage() { return mainStage;}


    /**
     * Initialize the main Stage for the application.  Most other views will be shown in this Stage.
     *
     * @param mainStage  the main Stage window of the application
     */
    private void initRootLayout(Stage mainStage) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = createLoader("../view/MainStage.fxml");
            rootLayout = loader.load();

            // Give the controller access to the main app.
            MainStageController controller = loader.getController();
            controller.setMainApp(this);

            // Set the Main App title
            mainStage.setTitle("Clean Water Database");

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            mainStage.setScene(scene);
            mainStage.show();


        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for mainStage!!");
            e.printStackTrace();
        }
    }

    /**
     * Setup our default application view that is shown on application startup
     * This is displayed in the startup window
     *
     * pre-condition - the main stage is already initialized and showing (initRootLayout has been called)
     * post-condition - the view is initialized and displayed
     *
     */
    public void showWelcomeScreen() {
        try {
            FXMLLoader loader = createLoader("../view/WelcomeScreen.fxml");
            rootLayout.setCenter(loader.load());

            // Give the controller access to the main app.
            WelcomeScreenController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method shows the Home screen where most operations will be done from.
     */
    public void showHomeScreen() {
        try {
            FXMLLoader loader = createLoader("../view/HomeScreen.fxml");
            rootLayout.setCenter(loader.load());

            // Give the controller access to the main app.
            HomeScreenController controller = loader.getController();
            controller.setMainApp(this);
            controller.setUserDao(userData);
            controller.setSourceReportDao(waterSourceData);
            controller.setPurityReportDao(waterPurityData);
            controller.setCurrUser(currUser);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method checks if a user is logged in.
     * @return true if a user is logged in false other wise.
     */
    public boolean showUserLoginDialog() {
        try {
            FXMLLoader loader = createLoader("../view/UserLoginDialog.fxml");

            // Create the dialog Stage
            Stage dialogStage = createDialogStage("User Login", loader.load(), true);

            // Connect dialog stage to controller.
            UserLoginController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUserDao(userData);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            currUser = controller.getSelectedUser();
            return (currUser != null);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method shows the User Creation dialog
     * @return returns true if a user is created false other wise
     */
    public boolean showUserCreateDialog() {
        try {
            FXMLLoader loader = createLoader("../view/UserCreateDialog.fxml");

            // Create the dialog Stage
            Stage dialogStage = createDialogStage("Create User", loader.load(), true);

            // Connect dialog stage to controller.
            UserCreateController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUserDao(userData);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            currUser = controller.getUser();
            return (currUser != null);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method shows the User Edit dialog
     * @return returns true if a user is edited false other wise
     */
    public boolean showUserEditDialog() {
        try {
            FXMLLoader loader = createLoader("../view/UserEditDialog.fxml");

            // Create the dialog Stage
            Stage dialogStage = createDialogStage("Edit User", loader.load(), true);

            UserEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUserDao(userData);
            controller.setCurrUser(currUser);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method shows the Water Source Report Creation Dialog
     * @return the Water Source report that was created else returns null
     */
    public WaterSourceReport showWaterSourceReportCreateDialog() {
        try {
            FXMLLoader loader = createLoader("../view/WaterSourceReportCreateDialog.fxml");

            // Create the dialog Stage
            Stage dialogStage = createDialogStage("Submit Water Source Report", loader.load(), false);

            // Connect dialog stage to controller.
            WaterSourceReportCreateController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setReportDao(waterSourceData);
            controller.setLocationDao(locationData);
            controller.setCurrUser(currUser);
            controller.initializeLocations();

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.getWaterSourceReport();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method shows the Water Source Report List Dialog when called.
     */
    public void showWaterSourceReportListDialog() {
        try {
            FXMLLoader loader = createLoader("../view/WaterSourceReportListDialog.fxml");

            // Create the dialog Stage
            Stage dialogStage = createDialogStage("View Water Source Reports", loader.load(), false);

            // Connect dialog stage to controller.
            WaterSourceReportListController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUserDao(userData);
            controller.setReportDao(waterSourceData);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method shows the Water Purity Report Creation Dialog
     * @return the Water Purity report that was created else returns null
     */
    public WaterPurityReport showWaterPurityReportCreateDialog() {
        try {
            FXMLLoader loader = createLoader("../view/WaterPurityReportCreateDialog.fxml");

            // Create the dialog Stage
            Stage dialogStage = createDialogStage("Submit Water Purity Report", loader.load(), false);

            // Connect dialog stage to controller.
            WaterPurityReportCreateController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setReportDao(waterPurityData);
            controller.setLocationDao(locationData);
            controller.setCurrUser(currUser);
            controller.initializeLocations();

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.getWaterPurityReport();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method shows the Water Purity Report List Dialog when called.
     */
    public void showWaterPurityReportListDialog() {
        try {
            FXMLLoader loader = createLoader("../view/WaterPurityReportListDialog.fxml");

            // Create the dialog Stage
            Stage dialogStage = createDialogStage("View Water Purity Reports", loader.load(), false);

            // Connect dialog stage to controller.
            WaterPurityReportListController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUserDao(userData);
            controller.setReportDao(waterPurityData);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method shows the Data Graph Dialog when called.
     */
    public void showDataGraphDialog() {
        try {
            FXMLLoader loader = createLoader("../view/DataGraphDialog.fxml");

            // Create the dialog Stage
            Stage dialogStage = createDialogStage("View Data Graph", loader.load(), false);

            // Connect dialog stage to controller.
            DataGraphController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPurityReportDao(waterPurityData);
            controller.setLocationDao(locationData);
            controller.initializeLocations();
            controller.initializeYears();
            controller.initializeData();

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method shows the Add Location Dialog when called.
     */
    public void showAddLocationDialog() {
        try {
            FXMLLoader loader = createLoader("../view/AddLocationDialog.fxml");

            // Create the dialog Stage
            Stage dialogStage = createDialogStage("AddLocation", loader.load(), false);

            // Connect dialog stage to controller.
            AddLocationController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setLocationDao(locationData);
            controller.setCurrUser(currUser);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method takes in arguments and runs the application
     * @param args arguments that are used to run the application
     */
    public static void main(String[] args) {
        launch(args);
    }

    private FXMLLoader createLoader(String fxmlFile) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource(fxmlFile));
        return loader;
    }

    private Stage createDialogStage(String title, Pane p, boolean borderless) {
        Stage dialogStage = new Stage();
        Scene scene = new Scene(p);
        if (borderless) {
            dialogStage.initStyle(StageStyle.UNDECORATED);
        }
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(mainStage);
        dialogStage.setTitle(title);
        dialogStage.setScene(scene);
        return dialogStage;
    }
}
