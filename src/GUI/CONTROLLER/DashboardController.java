package GUI.CONTROLLER;

import BE.User;
import GUI.FxmlLoader;
import Main.Main;
import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class DashboardController implements Initializable {
    @FXML
    private AnchorPane dRoot;
    @FXML
    private Label lblName;
    @FXML
    private BorderPane borderPane;
    @FXML
    private JFXButton btnDashboard;
    @FXML
    private JFXButton btnRegistrationButton;
    @FXML
    private JFXButton btnRegistrationsButton;

    private final FxmlLoader loader = new FxmlLoader();

    private User currentUser;

    private double xOffset = 0;
    private double yOffset = 0;

    public void setUser(User u) {
        currentUser = u;

        if (u.getRole() == UserRole.Student) {
            FXMLLoader load = new FXMLLoader();
            load.setLocation(getClass().getResource("/GUI/VIEW/StudentDashboardView.fxml"));

            try {
                borderPane.setCenter(load.load());
                StudentDashboardController conn = load.getController();
                conn.setUser(currentUser);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void exit() {
        System.exit(0);
    }

    public void minimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) dRoot.getScene().getWindow();
        stage.setIconified(true);
    }

    public void maximize(MouseEvent mouseEvent) {

    }

    public void logout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/VIEW/LoginView.fxml"));
        Main.getPrimaryStage().setScene(new Scene(fxmlLoader.load()));
    }

    public void handleDashboard() {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/GUI/VIEW/StudentDashboardView.fxml"));
        try {
            Main.setScene(load.load());
            StudentDashboardController studentDashboardController = load.getController();
            studentDashboardController.setUser(currentUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRegister() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/VIEW/PopUpView.fxml"));

        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleRegistrations() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/VIEW/RegistrationsView.fxml"));

        Parent root = fxmlLoader.load();
        RegistrationsController registrationsController = fxmlLoader.getController();

        registrationsController.setUser(currentUser);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setIsTeacher() {
        btnDashboard.setVisible(false);
        btnDashboard.setDisable(true);

        btnRegistrationButton.setVisible(false);
        btnRegistrationButton.setDisable(true);

        btnRegistrationsButton.setVisible(false);
        btnRegistrationsButton.setDisable(true);

        FXMLLoader load = new FXMLLoader(getClass().getResource("/GUI/VIEW/TeacherDashboardView.fxml"));

        try {
            borderPane.setCenter(load.load());
            TeacherDashboardController conn = load.getController();
            conn.setUser(currentUser);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setName(String name) {
        lblName.setText("Velkommen til, " + name);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Pane view = loader.getPage("StudentDashboardView");
        borderPane.setCenter(view);
    }

}
