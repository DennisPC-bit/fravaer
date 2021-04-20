package GUI.CONTROLLER;
import BE.*;
import BLL.DataGenerator;
import GUI.MODEL.StudentModel;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class StudentDashboardController implements Initializable {
    @FXML
    private TableView<Attendance> tblAbsence;
    @FXML
    private TableColumn<Attendance, String> tblDate;
    @FXML
    private TableColumn<Attendance, String> tblSubject;
    @FXML
    private CategoryAxis catAxis;
    @FXML
    private NumberAxis numAxis;
    @FXML
    private BarChart chartAbcence;

    private User user;

    private Student loggedInStudent;
    XYChart.Series data = new XYChart.Series();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chartAbcence.setPrefSize(522, 183);
        numAxis.setAutoRanging(false);

        catAxis.setLabel("Fag");

        numAxis.setLabel("Procent fravær");

        data.setName("Fravær");

        chartAbcence.getData().add(data);
    }

    public void setUser(User user) {
        this.user = user;

        //TODO: MEGET CRUDE, ryd op.
        List<Student> students = StudentModel.getInstance().getStudents();

        for (Student s : students){
            if (this.user.getId() == s.getId()){
                loggedInStudent = s;
            }
        }
        if (loggedInStudent != null) {
            XYChart.Series totalAbsence = DataGenerator.getAbsenceData(loggedInStudent);

            chartAbcence.getData().addAll(totalAbsence);
        }

        List<Attendance> studentAttendances = loggedInStudent.getAttendances();

        ObservableList<Attendance> absences = FXCollections.observableArrayList();

        for (Attendance a : studentAttendances){
            if (!a.isAttended()){
                absences.add(a);
            }
        }

        tblAbsence.setItems(absences);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        tblDate.setCellValueFactory(d -> new ReadOnlyObjectWrapper<>(d.getValue().getLecture().getLectureDate().toLocalDate().format(format)));
        tblSubject.setCellValueFactory(s -> new ReadOnlyStringWrapper(s.getValue().getLecture().getSubject().getName()));
    }
}
