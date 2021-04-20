package GUI.CONTROLLER;

import BE.User;
import Main.Main;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import BE.UserRole;
import java.io.IOException;

public class DashboardController{
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

    private User currentUser;

    public void setUser(User u) {
        currentUser = u;
        lblName.setText(String.format("Velkommen til, %s %s", u.getFirstName(),u.getLastName()));

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
        else
            setIsTeacher();
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

}
