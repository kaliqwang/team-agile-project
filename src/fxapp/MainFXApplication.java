package fxapp;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;
import model.WaterSourceReport;
import model.persist.GenericDAO;
import model.persist.UserDAO;
import model.persist.WaterSourceReportDAO;

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

    private GenericDAO<User, String> usersData;
    private GenericDAO<WaterSourceReport, Integer> waterData;
    private User currUser;

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        usersData = new UserDAO("users.json");
        waterData = new WaterSourceReportDAO("water.json");
        initRootLayout(mainStage);
        showWelcomeScreen(mainStage);
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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/MainStage.fxml"));
            rootLayout = loader.load();

            // Give the controller access to the main app.
            MainStageController controller = loader.getController();
            controller.setMainApp(this);

            // Set the Main App title
            mainStage.setTitle("Water App");

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
     * precondition - the main stage is already initialized and showing (initRootLayout has been called)
     * postcondition - the view is initialized and displayed
     *
     * @param mainStage  the main stage to show this view in
     */
    public void showWelcomeScreen(Stage mainStage) {
        try {
            // Load course overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/WelcomeScreen.fxml"));
            AnchorPane welcomeScreen = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(welcomeScreen);

            // Give the controller access to the main app.
            WelcomeScreenController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for WelcomeScreen!!");
            e.printStackTrace();
        }

    }

    public void showHomeScreen(Stage mainStage) {
        try {
            // Load course overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/HomeScreen.fxml"));
            AnchorPane homeScreen = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(homeScreen);

            // Give the controller access to the main app.
            HomeScreenController controller = loader.getController();
            controller.setMainApp(this);
            controller.setUserDao(usersData);
            controller.setReportDao(waterData);

        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for HomeScreen!!");
            e.printStackTrace();
        }

    }

    public boolean showUserLoginDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/UserLoginDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("User Login");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Connect dialog stage to controller.
            UserLoginController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUserDao(usersData);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            currUser = controller.getSelectedUser();

            return (currUser != null);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showUserCreateDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/UserCreateDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New User Registration");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Connect dialog stage to controller.
            UserCreateController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUserDao(usersData);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            currUser = controller.getUser();

            return (currUser != null);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showUserEditDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/UserEditDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("User Profile Editor");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            UserEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUserDao(usersData);
            controller.setCurrUser(currUser);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public WaterSourceReport showWaterSourceReportCreateDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/WaterSourceReportCreateDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Submit Water Source Report");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setMinHeight(600.0);

            // Connect dialog stage to controller.
            WaterSourceReportCreateController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setReportDao(waterData);
            controller.setCurrUser(currUser);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.getWaterSourceReport();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Displays a dialog which enables the user to read water source reports.
     */
    public void showWaterSourceReportListDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/WaterSourceReportListDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("View Water Source Reports");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(mainStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Connect dialog stage to controller.
            WaterSourceReportListController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUserDao(usersData);
            controller.setReportDao(waterData);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

    } catch (IOException e) {
        e.printStackTrace();
    }

    }
    
    public boolean showWaterPurityReportCreateDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/WaterPurityReportCreateDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Submit Water Purity Report");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Connect dialog stage to controller.
            WaterPurityReportCreateController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setReportDao(waterData);
            controller.setCurrUser(currUser);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.getWaterPurityReport();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
