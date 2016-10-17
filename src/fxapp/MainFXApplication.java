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
import model.persist.IDao;
import model.persist.UserDaoImpl;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main application class.  Some code reused from the code makery tutorial
 *
 * This class handles all the scene switching to reuse the main stage.
 *
 */
public class MainFXApplication extends Application {
    /**  the java logger for this class */
    private static final Logger LOGGER =Logger.getLogger("MainFXApplication");

    /** the main container for the application window */
    private Stage mainScreen;

    /** the main layout for the main window */
    private BorderPane rootLayout;

    private IDao<User, String> usersData;

    @Override
    public void start(Stage primaryStage) {
        mainScreen = primaryStage;
        usersData = new UserDaoImpl("users.db");
        initRootLayout(mainScreen);
        showWelcomeScreen(mainScreen);
    }

    /**
     * return a reference to the main window stage
     * @return reference to main stage
     * */
    public Stage getMainScreen() { return mainScreen;}


    /**
     * Initialize the main screen for the application.  Most other views will be shown in this screen.
     *
     * @param mainScreen  the main Stage window of the application
     */
    private void initRootLayout(Stage mainScreen) {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/MainScreen.fxml"));
            rootLayout = loader.load();

            // Give the controller access to the main app.
            MainScreenController controller = loader.getController();
            controller.setMainApp(this);

            // Set the Main App title
            mainScreen.setTitle("Water App");

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            mainScreen.setScene(scene);
            mainScreen.show();


        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for MainScreen!!");
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
     * @param mainScreen  the main stage to show this view in
     */
    public void showWelcomeScreen(Stage mainScreen) {
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

    public void showHomeScreen(Stage mainScreen) {
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

        } catch (IOException e) {
            //error on load, so log it
            LOGGER.log(Level.SEVERE, "Failed to find the fxml file for HomeScreen!!");
            e.printStackTrace();
        }

    }

    public boolean showLoginDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/LoginDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("User Login");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainScreen);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Connect dialog stage to controller.
            LoginController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUserDao(usersData);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User showRegistrationDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/RegistrationDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New User Registration");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainScreen);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Connect dialog stage to controller.
            RegistrationController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUserDao(usersData);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.getUser();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public WaterSourceReport showWaterSourceReportDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/WaterSourceReportDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Submit Water Source Report");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainScreen);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Connect dialog stage to controller.
            WaterSourceReportController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUserDao(usersData);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.getWaterSourceReport();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void showWaterSourceReportsDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFXApplication.class.getResource("../view/WaterSourceReportsDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("View Water Source Reports");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainScreen);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Connect dialog stage to controller.
            WaterSourceReportsController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setUserDao(usersData);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

    } catch (IOException e) {
        e.printStackTrace();
    }

    }
    public static void main(String[] args) {
        launch(args);
    }
}
