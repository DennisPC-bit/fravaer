package Dashboard;

import BE.User;
import Main.FxmlLoader;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    private JFXButton btnStatestik;
    @FXML
    private JFXButton btnRegistreringer;

    private User currentUser;
    private boolean isTeacher;


    public void setUser(User u){
        currentUser = u;
    }

    public void exit(){
        System.exit(0);
    }

    public void minimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) dRoot.getScene().getWindow();
        stage.setIconified(true);
    }

    public void maximize(MouseEvent mouseEvent) {

    }

    public void logout() throws IOException {
        Stage root1 = (Stage) dRoot.getScene().getWindow();

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/Main/Login.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

        root1.close();
    }

    public void handleDashboard(){
        FxmlLoader loader = new FxmlLoader();
        Pane view = loader.getPage("studentdb");

        borderPane.setCenter(view);
    }

    public void handleRegistrer(){

    }

    public void handleRegistreringer(){
        FxmlLoader loader = new FxmlLoader();
        Pane view = loader.getPage("tidlRegistreringer");

        borderPane.setCenter(view);
    }

    public void setIsTeacher(){
        btnDashboard.setVisible(false);
        btnDashboard.setDisable(true);

        btnStatestik.setVisible(false);
        btnStatestik.setDisable(true);

        btnRegistreringer.setVisible(false);
        btnRegistreringer.setDisable(true);

        FxmlLoader loader = new FxmlLoader();
        Pane view = loader.getPage("teacherdb");

        borderPane.setCenter(view);
    }

    public void setName(String name){
        lblName.setText("Velkommen til, " + name);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FxmlLoader loader = new FxmlLoader();
        Pane view = loader.getPage("studentdb");

        borderPane.setCenter(view);
    }
}
