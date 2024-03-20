import java.sql.*;
import java.util.Date;

public class StudentsDB {

    //Parameters to connect to the database
    private static final String dbName = "my_database_name"; //Assignment3_DB
    private static final String dbUser = "my_username"; //postgres
    private static final String dbPassword = "my_password";
    private static final String dbHost = "localhost";
    private static final String dbPort = "my_port"; //5432

    private Connection connection;
    private Statement statement;

    public StudentsDB() {
        try {
            //Establish connection to database
            String connectionString = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
            connection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
            System.out.println("Connected to the database.");
        } catch (SQLException e){
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public void getAllStudents(){
        try {
            //query to retrieve all data from the students table
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
            System.out.println("All students: ");
            while(resultSet.next()) {
                System.out.println(resultSet.getInt("student_id") + ", "
                        + resultSet.getString("first_name") + " "
                        + resultSet.getString("last_name") + ", "
                        + resultSet.getString("email") + ", "
                        + resultSet.getDate("enrollment_date"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving students: " + e.getMessage());
        }
    }

    public void addStudent(String firstName, String lastName, String email, String enrolDate){
        try {


            //query to insert a new student record into the students table
            String query = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, enrolDate);
            preparedStatement.executeUpdate();
            System.out.println("Student added successfully.");
        } catch (SQLException e){
            System.err.println("Error adding new student: " + e.getMessage());
        }
    }

    public void updateStudentEmail(int studentId, String newEmail) {
        try {
            //query to update the email address for a student
            String query = "UPDATE students SET email = ? WHERE student_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newEmail);
            preparedStatement.setInt(2, studentId);
            preparedStatement.executeUpdate();
            System.out.println("Email updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating email: " + e.getMessage());
        }
    }

    public void deleteStudent(int studentID){
        try {
            //query to delete a student record
            String query = "DELETE FROM students WHERE student_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentID);
            preparedStatement.executeUpdate();
            System.out.println("Student deleted successfully.");
        } catch (SQLException e){
            System.err.println("Error deleting student: " + e.getMessage());
        }
    }

    public void closeConnection(){
        try {
            if(connection != null){
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch(SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage()); 
        }
    }

    public static void main(String[] args) {
        //create StudentDB object
        StudentsDB studentDB = new StudentsDB();

        //Testing functions
        studentDB.getAllStudents();
        studentDB.addStudent("Curtis", "Lopes", "myemail@myemail.com", "2024-03-19");
        studentDB.getAllStudents();
        studentDB.updateStudentEmail(1, "newemaild@example.com");
        studentDB.getAllStudents();
        studentDB.deleteStudent(3);
        studentDB.getAllStudents();

        // Close the database connection
        studentDB.closeConnection();
    }
}