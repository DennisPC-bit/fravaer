package BE;

import java.util.ArrayList;

public class User {
    private int id;
    private int role;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    int totalAttendance;
    private ArrayList<Class> classes;


    public User(int role, String username, String password, String firstName, String lastName) {
        setRole(role);
        setUsername(username);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
    }
    public User(int role, String username, String password, String firstName, String lastName, int totalAttendance) {
        setRole(role);
        setUsername(username);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setTotalAttendance(totalAttendance);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<Class> getClasses() {
        return classes;
    }

    public void addClass(Class c) {
        classes.add(c);
    }

    public void addClasses(ArrayList<Class> c) {
        classes.addAll(c);
    }

    public void removeClass(Class c) {
        classes.remove(c);
    }

    public void removeClasses(ArrayList<Class> c) {
        classes.removeAll(c);
    }

    public void setTotalAttendance(int totalAttendance) {
        this.totalAttendance = totalAttendance;
    }

    public int getTotalAttendance() {
        return totalAttendance;
    }
}
