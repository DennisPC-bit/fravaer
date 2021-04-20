package GUI.CONTROLLER;

import BE.Student;
import BE.Subject;
import BE.User;
import BE.UserRole;
import BLL.DataGenerator;
import GUI.MODEL.StudentModel;
import com.jfoenix.controls.JFXComboBox;
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
import java.util.ResourceBundle;

public class TeacherDashboardController implements Initializable {
    @FXML
    private BarChart chartOverallAttendance;
    @FXML
    private BarChart chartIndividualDay;
    @FXML
    private BarChart chartIndividualSubject;
    @FXML
    private NumberAxis overallNumAxis;
    @FXML
    private NumberAxis individualDayNum;
    @FXML
    private NumberAxis individualSubNum;
    @FXML
    private TableView<Student> tblStudents;
    @FXML
    private TableColumn<Student, String> tblFirstname;
    @FXML
    private TableColumn<Student, String> tblLastname;
    @FXML
    private TableColumn<Student, Number> tblAbsence;
    @FXML
    private JFXComboBox subjectFilterCombobox;

    private User user;
    private StudentModel studentModel = StudentModel.getInstance();

    private ObservableList<Student> students = FXCollections.observableArrayList();
    private ObservableList<Subject> subjects = FXCollections.observableArrayList();

    private Subject selectedSubject;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCombobox();
        initializeTable();
    }

    /**
     * Initialize the subject filter combo box.
     */
    private void initializeCombobox() {
        subjects.add(new Subject(5, "Alle"));
        subjects.addAll(studentModel.getSubjects());
        subjectFilterCombobox.setItems(subjects);
    }

    /**
     * Initialize the table view of the class. Should only be called once in the initialize method.
     */
    private void initializeTable() {

        // Assign the table columns to automatically update their cell.
        tblFirstname.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProperty());
        tblLastname.setCellValueFactory(cellData -> cellData.getValue().getLastNameProperty());
        tblAbsence.setCellValueFactory(cellData -> cellData.getValue().getTotalAbsencePercentageProperty());

        // Then assign the table to get the observable student list.
        tblStudents.setItems(students);

        // Now add all the students.
        students.setAll(studentModel.getStudents());

        // Initialize the filter by subject functionality (combo box).
        filterStudentsBySubject();

        initializeCharts();
    }

    private void initializeCharts() {

        //Hide initial student abscence charts.
        chartIndividualDay.setVisible(false);
        chartIndividualSubject.setVisible(false);
        chartOverallAttendance.setVisible(false);

        //
        //INDIVIDUAL DAY ABSENCE
        //
        individualDayNum.setLabel("Procent fravær");
        individualDayNum.setLayoutY(100);

        //provided data
        individualDayNum.setAutoRanging(false);

        //
        //INDIVIDUAL SUBJECT ABSENCE
        //

        //individualSubCat.setLabel("FAG");
        individualSubNum.setLabel("Procent Fravær");

        XYChart.Series data2 = new XYChart.Series();
        data2.setName("Individual Fravær");
    }

    /**
     * Filter the students in a class by the selected subject. Should only be called once in the initialize method.
     */
    private void filterStudentsBySubject() {
        subjectFilterCombobox.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            selectedSubject = (Subject) newValue;
            if (selectedSubject != null) {
                switch (selectedSubject.getName()) {
                    case "Alle":
                        students.setAll(studentModel.getStudents());
                        break;

                    default:
                        students.setAll(studentModel.getStudentsBySubject(selectedSubject));
                }
                System.out.println(String.format("Selected subject: %s", selectedSubject.getName()));
            }
        }));
    }

    /**
     * Set the current logged in user.
     *
     * @param user The user to assign as logged in.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Selects a student, and shows the individual charts for that specific student.
     */
    public void selectStudent() {
        Student selectedStudent = tblStudents.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {

            chartIndividualDay.getData().clear();
            chartIndividualSubject.getData().clear();

            XYChart.Series days = DataGenerator.getMostAbsentDays(selectedStudent);
            days.setName("Individual day absence");

            XYChart.Series classes = DataGenerator.getAbsencePercentageInEachSubject(selectedStudent);

            classes.setName("Individual class absence");

            chartIndividualSubject.getData().addAll(classes);
            chartIndividualDay.getData().addAll(days);

            chartIndividualSubject.setVisible(true);
            chartIndividualDay.setVisible(true);
        }
    }
}
