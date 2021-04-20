package DAL.DB;

import BE.*;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Author: Jonas Buus Nielsen
 * Version: 1.0
 */

public class UserDAL {
    private DbConnectionHandler dbCon = DbConnectionHandler.getInstance();
    private List<Student> students = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public UserDAL() {
    }

    /**
     * Add user to database (id is generated and assigned by the database)
     *
     * @param firstName
     * @param lastName
     * @param userName
     * @param password
     * @param role
     */
    public void addUser(String firstName, String lastName, String userName, String password, int role) {

        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("INSERT INTO [User] VALUES(?,?,?,?)");
            pSql.setString(1, firstName);
            pSql.setString(2, lastName);
            pSql.setString(3, password);
            pSql.setInt(4, role);

            pSql.execute();

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * By default, all lectures are added to a student, with the attendance set to 0 (false).
     * This method sets the attendance value to 1 (true), and uses userId and lectureId as identifiers.
     *
     * @param userId
     * @param lectureId
     */
    public void addAttendanceRegistration(int userId, int lectureId, boolean isAttended) {

        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("UPDATE Attendance SET Attended=? WHERE UserId=? AND LectureId=?");
            pSql.setInt(1, isAttended ? 1 : 0);
            pSql.setInt(2, userId);
            pSql.setInt(3, lectureId);
            pSql.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //TODO make this - DELETE ?
    public List<Student> getStudents() {
        return students;
    }

    /**
     * Retrieves all user information from the database, and creates a list of users.
     * @return A list of all users.
     */
    public List<User> getUsers() {

        List<User> allUsers = new ArrayList<>();

        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("SELECT * FROM [User]");

            pSql.execute();

            ResultSet rs = pSql.getResultSet();
            while (rs.next()) {
                int id = rs.getInt("Id");
                UserRole role = rs.getBoolean("Role") ? UserRole.Student : UserRole.Admin;
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");

                allUsers.add(new User(id, role, username, password, firstName, lastName));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allUsers;
    }

    /**
     * Creates an integer array with all attendance information in the database. This array is used by a method in
     * StudentModel to couple attendances to students, with respect to UserId and LectureId.
     * @return integer array with all attendance inforamtion.
     */
    public int[][] getAttendances() {

        int[][] attendances = new int[0][];

        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("SELECT * FROM Attendance",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            pSql.execute();

            ResultSet rs = pSql.getResultSet();

            attendances = new int[getResultSetSize(rs)][3];


            while (rs.next()) {
                attendances[rs.getRow() - 1][0] = rs.getInt("UserId");
                attendances[rs.getRow() - 1][1] = rs.getInt("LectureId");
                attendances[rs.getRow() - 1][2] = rs.getInt("Attended");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return attendances;
    }

    /**
     * Returns the size of a ResultSet as an int.
     * @param rs
     * @return size of the ResultSet.
     * @throws SQLException
     */
    private int getResultSetSize(ResultSet rs) throws SQLException {
        int size = 0;
        if (rs != null) {
            rs.last();    // moves cursor to the last row
            size = rs.getRow(); // get row id
        }
        rs.first();
        return size;
    }

    /**
     * Retrieve all lectures from the database.
     * @return a list of all lectures.
     */
    public List<Lecture> getLectures() {

        List<Lecture> allLectures = new ArrayList<>();

        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("SELECT\n" +
                    "e.Id , e.SubjectId , d.SubjectName, e.LectureDate, e. LectureTime\n" +
                    "FROM Lecture e\n" +
                    "LEFT OUTER JOIN Subject d\n" +
                    "ON e.SubjectId = d.Id");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();
            while (rs.next()) {
                int lectureId = rs.getInt("Id");
                int subjectId = rs.getInt("SubjectId");
                var subjectName = rs.getString("SubjectName");

                LocalDate lectureDate = rs.getDate("LectureDate").toLocalDate();
                LocalTime lectureTime = rs.getTime("LectureTime").toLocalTime();
                LocalDateTime lecture = LocalDateTime.of(lectureDate, lectureTime);
                allLectures.add(new Lecture(lectureId, new Subject(subjectId, subjectName), lectureDate, lectureTime));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allLectures;
    }

    /**
     * Get a subject with the specified id.
     *
     * @param subjectId The id of the subject to get.
     * @return Returns the fetched subject.
     */
    public Subject getSubject(int subjectId) {
        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("SELECT * FROM Subject WHERE Id = ?");
            pSql.setInt(1, subjectId);
            pSql.execute();

            ResultSet rs = pSql.getResultSet();
            if (rs.getRow() > 0) {
                String subjectName = rs.getString("SubjectName");
                return new Subject(subjectId, subjectName);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieve all subjects from the database.
     *
     * @return a list of all subjects.
     */
    public List<Subject> getSubjects() {

        List<Subject> allSubjects = new ArrayList<>();

        try (Connection con = dbCon.getConnection()) {

            PreparedStatement pSql = con.prepareStatement("SELECT * FROM Subject");
            pSql.execute();

            ResultSet rs = pSql.getResultSet();
            while (rs.next()) {
                int subjectId = rs.getInt("Id");
                String subjectName = rs.getString("SubjectName");
                allSubjects.add(new Subject(subjectId, subjectName));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return allSubjects;
    }


}
