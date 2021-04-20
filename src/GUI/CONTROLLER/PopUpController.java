package GUI.CONTROLLER;

import BE.Student;
import BLL.LoginManager;
import GUI.MODEL.AttendanceModel;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class PopUpController {
    @FXML
    private AnchorPane root;

    /**
     * Calls AttendanceModel's registerAttendance method on the student that is currently logged in,
     * prints that it cant registerAttendance at the given date if registerAttendance is false.
     * also closes the stage after doing the above
     */
    public void handleYes(){
        AttendanceModel attendanceModel = new AttendanceModel();
        Student student = LoginManager.getCurrentStudent();
        Stage root1 = (Stage) root.getScene().getWindow();
        if(attendanceModel.registerAttendance(student,LocalDateTime.now())){
            System.out.println("Registreret tilstedeværelse for " + student.getUsername() + " : " + LocalDateTime.now());
        }else{
            System.out.println("Kunne ikke registrere tilstedeværelse den gevne dag");
        }

        root1.close();
    }

    /**
     * Prints that it didn't register the attendance and closes the stage
     */
    public void handleNo(){
        System.out.println("Ikke registreret tilstedeværelse");
        Stage root1 = (Stage) root.getScene().getWindow();

        root1.close();
    }
}
