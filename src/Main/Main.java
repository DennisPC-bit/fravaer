package Main;

import BLL.DataGenerator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {
    private static Stage primaryStage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("../GUI/VIEW/LoginView.fxml"));
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.initStyle(StageStyle.TRANSPARENT);
        Main.primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return Main.primaryStage;
    }

    public static void setScene(Scene scene){
        Main.primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
