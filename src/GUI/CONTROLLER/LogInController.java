package GUI.CONTROLLER;

import BE.User;
import BE.UserRole;
import GUI.MODEL.LoginModel;
import Main.Main;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.List;

public class LogInController implements Initializable {

    public FontAwesomeIconView exitBtn;
    public FontAwesomeIconView iconize;
    public FontAwesomeIconView minMaxBtn;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private Label lblError;
    @FXML
    private Pane topBar;
    private boolean maximized = false;

    private double xOffset = 0;
    private double yOffset = 0;


    public void exit() {
        System.exit(0);
    }

    public void iconize() {
        Main.getPrimaryStage().setIconified(true);
    }

    public void minMax() {
        maximized = !maximized;
        Main.getPrimaryStage().setMaximized(maximized);
    }

    public void login() throws IOException {
        LoginModel loginModel = new LoginModel();
        User user = loginModel.attemptLogin(txtUsername.getText(), txtPassword.getText());
        BorderPane bp = new BorderPane();
        FXMLLoader loader = null;
        if (user != null) {
            if (user.getRole() == UserRole.Admin) {
                loader = new FXMLLoader(getClass().getResource("/GUI/VIEW/TeacherDashboardView.fxml"));
                bp.setTop(topBar);
                bp.setCenter(loader.load());
                TeacherDashboardController teacherDashboardController = loader.getController();
                teacherDashboardController.setUser(user);
            } else {
                loader = new FXMLLoader(getClass().getResource("/GUI/VIEW/DashboardView.fxml"));
                bp.setTop(topBar);
                bp.setCenter(loader.load());
                DashboardController controller = loader.getController();
                controller.setUser(user);
                controller.setName(user.getFirstName());
            }
            Main.setScene(new Scene(bp));
        } else {
            lblError.setText("The username or password is incorrect");
            lblError.setTextFill(Color.RED);
            txtPassword.clear();
            txtUsername.clear();
        }
    }

    /**
     * Login with the enter key.
     *
     * @param keyEvent the keycode we check if its the ENTER key.
     * @throws IOException
     */
    public void LoginByEnter(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            login();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AtomicReference<Double> x = new AtomicReference<>((double) 0);
        AtomicReference<Double> y = new AtomicReference<>((double) 0);
        topBar.setOnMousePressed(mouseEvent -> {
            x.set(mouseEvent.getSceneX());
            y.set(mouseEvent.getSceneY());
        });
        topBar.setOnMouseDragged(mouseEvent -> {
                    if (maximized) {
                        minMax();
                    }
                    Main.getPrimaryStage().setOpacity(0.8f);
                    Main.getPrimaryStage().setX(mouseEvent.getScreenX() - x.get());
                    Main.getPrimaryStage().setY(mouseEvent.getScreenY() - y.get());
                }
        );
        topBar.setOnMouseReleased(v -> Main.getPrimaryStage().setOpacity(1.0f));
        List<FontAwesomeIconView> icons = new ArrayList<>(Arrays.asList(exitBtn, iconize, minMaxBtn));
        topBar.widthProperty().addListener((observableValue, number, t1) -> {
            for (int i = 0; i < icons.size(); i++) {
                icons.get(i).setLayoutX(t1.intValue() - (i + 1) * 20);
            }
        });
    }
}
