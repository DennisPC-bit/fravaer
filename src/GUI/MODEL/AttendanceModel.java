package GUI.MODEL;

import BE.Student;

import java.time.LocalDateTime;

public class AttendanceModel {
    /**
     * Registers attendance for the given student at the given date
     * @param student the student
     * @param date the date you want to register
     * @return if the attempt to register attendance was successful
     */
    public boolean registerAttendance(Student student, LocalDateTime date){
        if(student!=null){
            student.registerAttendance(date);
            return true;
        }
        return false;
    }



}

