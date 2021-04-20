package GUI.CONTROLLER;

import BE.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class RegistrationsController implements Initializable {
    @FXML
    private TableView tblView;
    @FXML
    private TableColumn<Lecture, LocalDateTime> tblDato;
    @FXML
    private TableColumn<Attendance, Lecture> tblFag;
    @FXML
    private TableColumn<Attendance, Boolean> tblPresent;

    private Student student;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //TODO VIRKER IKKE PT
    public void setUser(User user) {
        this.student = student;

        tblView.getItems().addAll(student.getAttendances());
        tblDato.setCellValueFactory(new PropertyValueFactory<>("lectureDate"));
        tblFag.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblPresent.setCellValueFactory(new PropertyValueFactory<>("attended"));

    }
}
