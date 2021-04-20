package GUI.MODEL;

import BE.*;
import BLL.DataGenerator;
import BLL.StudentManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class StudentModel {

    private ObservableList<Subject> subjects;
    private static StudentModel instance;
    private ObservableList<User> teachers;
    private ObservableList<Student> students;
    private ObservableList<Lecture> allLectures;

    private static StudentManager studentManager;

    /**
     * Get the singleton instance of StudentModel.
     */
    public static StudentModel getInstance() {
        if (instance == null) {
            instance = new StudentModel();
        }
        return instance;
    }


    /**
     * The StudentModel constructor initially loads all user/student/teacher/subject information from the database.
     */
    private StudentModel() {
        studentManager = new StudentManager();
        subjects = FXCollections.observableArrayList();
        teachers = FXCollections.observableArrayList();
        students = FXCollections.observableArrayList();
        allLectures = FXCollections.observableArrayList();

        addUsers();
        addLectures();
        addSubjects();
        addAttendances();
    }


    /**
     * Retrieves an integer array from UserDAL. This array contains information about attendance with respect to
     * user id, and lecture id. Each user's list of attendances is then updated with the information fetched from the
     * database.
     */
    private void addAttendances(){

        int[][] attendances = studentManager.getAttendances();

        for (int[] attendance : attendances) {

            for (Student s : students) {
                if (s.getId() == attendance[0]) {
                    for (Attendance a : s.getAttendances()) {
                        if (a.getLecture().getLectureId() == attendance[1]) {
                            a.setAttended(attendance[2] == 1);
                        }
                    }
                }
            }
        }
    }

    /**
     * Retrieves all subjects registered in the database.
     */
    private void addSubjects() {
        var allSubjects = studentManager.getSubjects();
        if (!allSubjects.isEmpty()) {
            subjects.addAll(allSubjects);
        }
    }

    /**
     * Adds a list of all lectures to each student. After this, the list of lectures is used to create a list of
     * attendance objects, which are added to each student (with default attendance set to "false" this state will
     * be updated with data from the database by the addAttendances() method).
     */
    private void addLectures() {
        List<Lecture> allLectures = studentManager.getLectures();
        setAllLectures(allLectures);

        if (!students.isEmpty()) {

            var absenceData = new DataGenerator();
            // Add all the lectures to all the students.
            for (Student student : students) {
                student.setLectures(allLectures);
                for (Lecture l : allLectures) {
                    student.addAttendance(l);
                }

                // Add absence percentage.
                var totalAbsence = absenceData.getTotalAbsencePercentage(student);
                student.setTotalAbsencePercentage(totalAbsence);
            }
        } else System.out.println("No students! Can't add lectures.");
    }

    /**
     * Retrieves a list of all users from the database. Users with "student" role, are added to the students list.
     * Users with "teacher" role, are added to teachers list.
     */
    private void addUsers() {
        List<User> allUsers = studentManager.getUsers();

        for (User u : allUsers) {
            if (u.getRole() == UserRole.Student) {
                students.add(new Student(u));
            } else {
                teachers.add(u);
            }
        }
    }


    /**
     * Get the students in a given subject.
     *
     * @param subject The subject to get students of.
     * @return Returns a list of students attending the subject.
     */
    public List<Student> getStudentsBySubject(Subject subject) {
        var foundStudents = new ArrayList<Student>();

        if (subject != null && !students.isEmpty()) {
            for (Student student : students) {
                var lectures = student.getLectures();
                for (Lecture lecture : lectures) {
                    var assignedSubject = lecture.getSubject();

                    if (assignedSubject != null && assignedSubject.getId() == subject.getId()) {
                        foundStudents.add(student);
                        //System.out.println("Filtered student: " + student.getFirstName());
                        break;
                    }
                    // else System.out.println("No student with subject found: " + assignedSubject.getName());
                }
            }
        }
        return foundStudents;
    }


    public void setAllLectures(List<Lecture> allLectures) {
        this.allLectures.addAll(allLectures);
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<User> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<User> teachers) {
        this.teachers.addAll(teachers);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students.addAll(students);
    }

    public List<Lecture> getAllLectures() {
        return allLectures;
    }

    public static void main(String[] args) {
        StudentModel studentModel = StudentModel.getInstance();

        List<Student> students = studentModel.getStudents();

        for(Student s : students){
            for(Lecture l : s.getLectures()){
                System.out.println(l.getDate());
            }
        }
    }

}

