import java.sql.*;

public class JDBC_CRUD_Example {

    static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    static final String USER = "root";
    static final String PASSWORD = "root";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to Database Successfully!");

            insertStudent(conn, 1, "John", 20);
            insertStudent(conn, 2, "Emma", 22);

            readStudents(conn);

            updateStudent(conn, 2, "Emily");

            readStudents(conn);

            deleteStudent(conn, 1);

            readStudents(conn);

            conn.close();

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found!");
        }
    }

    public static void insertStudent(Connection conn, int id, String name, int age) throws SQLException {
        String query = "INSERT INTO students VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.setString(2, name);
        stmt.setInt(3, age);
        int rows = stmt.executeUpdate();
        System.out.println(rows + " row(s) inserted.");
    }

    public static void readStudents(Connection conn) throws SQLException {
        String query = "SELECT * FROM students";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        System.out.println("Student Records:");
        while (rs.next()) {
            System.out.println(rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getInt("age"));
        }
    }
    
    public static void updateStudent(Connection conn, int id, String newName) throws SQLException {
        String query = "UPDATE students SET name=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, newName);
        stmt.setInt(2, id);
        int rows = stmt.executeUpdate();
        System.out.println(rows + " row(s) updated.");
    }

    public static void deleteStudent(Connection conn, int id) throws SQLException {
        String query = "DELETE FROM students WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        int rows = stmt.executeUpdate();
        System.out.println(rows + " row(s) deleted.");
    }
}
