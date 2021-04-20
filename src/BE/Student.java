package BE;

import BLL.AttendanceManager;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Student extends User {
    List<Lecture> lectures = new ArrayList<>();
    List<Attendance> attendances = new ArrayList<>();

    private int attendanceCount;
    private int absenceCount;

    private SimpleDoubleProperty totalAbsencePercentageProperty;
    private static final Duration REGISTRATION_BUFFER = Duration.ofMinutes(15);

    public Student() {
    }

    public Student(int id, UserRole role, String username, String password, String firstName, String lastName) {
        super(id, role, username, password, firstName, lastName);
        attendances = new ArrayList<>();
    }

    public Student(int id, UserRole role, String username, String password, String firstName, String lastName, List<Attendance> attendances) {
        super(id, role, username, password, firstName, lastName);
        this.attendances = attendances;
    }

    public Student(int id, UserRole role, String username, String password, String firstName, String lastName, List<Attendance> attendances, List<Lecture> lectures) {
        super(id, role, username, password, firstName, lastName);
        this.attendances = attendances;
        this.lectures = lectures;
    }

    public Student(User u) {
        super(u.getId(), u.getRole(), u.getUsername(), u.getPassword(), u.getFirstName(), u.getLastName());
    }

    @Override
    protected void initialize() {
        super.initialize();
        totalAbsencePercentageProperty = new SimpleDoubleProperty();
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void addAttendance(Lecture lecture) {
        this.attendances.add(new Attendance(lecture));
    }

    /**
     * Tries to register attendance at the given date, and checks that the registration is within the users lectures
     *
     * @param registration the time you want to register
     * @return If the registration was valid or not
     */
    public boolean registerAttendance(LocalDateTime registration) {

        //Assigns the registrationDate to a variable for convenience
        LocalDate registrationDate = registration.toLocalDate();

        //Assigns the registrationTime to a variable for convenience
        LocalTime registrationTime = registration.toLocalTime();

        //A atomic boolean for returning the result of the registration attempt
        AtomicBoolean isValid = new AtomicBoolean(false);

        //Loops through the lectures
        for (Lecture lecture : this.getLectures()) {
            if (
                    //Check that values are not null
                    lecture != null && lecture.getDate() != null && lecture.getLectureDuration() != null &&
                    //Checks the registration date is the same as the lecture date
                    lecture.getDate().toLocalDate().equals(registrationDate) &&

                    //Checks the registration is within the lecture (minus the REGISTRATION_BUFFER)
                    registrationTime.isAfter(lecture.getDate().toLocalTime().minus(REGISTRATION_BUFFER)) &&

                    //Checks the registration is within the lecture (plus the REGISTRATION_BUFFER)
                    registrationTime.isBefore(LocalTime.from(lecture.getDate().plus(lecture.getLectureDuration()).plus(REGISTRATION_BUFFER)))
            ) {
                isValid.set(true);
                AttendanceManager.updateStudentAttendance(this, lecture, true);
            }
        }
        return isValid.get();
    }

    /**
     * Get the amount of time the student has attended.
     *
     * @return Returns the student's attendance count.
     */
    public int getAttendanceCount() {
        return attendanceCount;
    }

    /**
     * Set the amount of time the student has attended.
     *
     * @param attendanceCount The amount of attendance to set.
     */
    public void setAttendanceCount(int attendanceCount) {
        this.attendanceCount = attendanceCount;
    }

    /**
     * Get the amount of time the student has been absent.
     *
     * @return Returns the student's absence count.
     */
    public int getAbsenceCount() {
        return absenceCount;
    }

    /**
     * Set the amount of time the student has been absent.
     *
     * @param absenceCount The amount of absence to set.
     */
    public void setAbsenceCount(int absenceCount) {
        this.absenceCount = absenceCount;
    }

    /**
     * Get the total absence percentage of this student.
     *
     * @return Returns the total absence percentage.
     */
    public double getTotalAbsencePercentage() {
        return totalAbsencePercentageProperty.get();
    }

    /**
     * Get the total absence percentage property.
     *
     * @return Returns the total absence percentage property.
     */
    public SimpleDoubleProperty getTotalAbsencePercentageProperty() {
        return totalAbsencePercentageProperty;
    }

    /**
     * Set the total absence percentage of this student.
     *
     * @param totalAbsencePercentage The total absence percentage to set.
     */
    public void setTotalAbsencePercentage(double totalAbsencePercentage) {
        //this.totalAbsencePercentageProperty.set(Math.round(totalAbsencePercentage * 100.0) / 100.0);
        this.totalAbsencePercentageProperty.set(totalAbsencePercentage);
    }
}
