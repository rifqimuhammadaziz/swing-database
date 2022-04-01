package rifqimuhammadaziz.dao;

import rifqimuhammadaziz.entity.Department;
import rifqimuhammadaziz.entity.Student;
import rifqimuhammadaziz.util.DaoService;
import rifqimuhammadaziz.util.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements DaoService<Student> {

    @Override
    public List<Student> getAll() throws SQLException, ClassNotFoundException {
        List<Student> students = new ArrayList<>();
        String QUERY = "SELECT s.id, first_name, last_name, address, department_id, name FROM student s JOIN department d ON s.department_id = d.id";
        try (Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(QUERY)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {

                        Department department = new Department();
                        department.setId(rs.getInt("department_id"));
                        department.setName(rs.getString("name"));

                        Student student = new Student();
                        student.setId(rs.getString("id"));
                        student.setFirstName(rs.getString("first_name"));
                        student.setLastName(rs.getString("last_name"));
                        student.setAddress(rs.getString("address"));
                        student.setDepartment(department);
                        students.add(student);
                    }
                }
            }
        }
        return students;
    }

    @Override
    public int addData(Student student) throws SQLException, ClassNotFoundException {
        int result = 0;
        String QUERY = "INSERT INTO student(id, first_name, last_name, address, department_id) VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(QUERY)) {
                ps.setString(1, student.getId());
                ps.setString(2, student.getFirstName());
                ps.setString(3, student.getLastName());
                ps.setString(4, student.getAddress());
                ps.setInt(5, student.getDepartment().getId());
                if (ps.executeUpdate() != 0) {
                    connection.commit();
                    result = 1;
                } else {
                    connection.rollback();
                }
            }
        }
        return result;
    }

    @Override
    public int updateData(Student student) throws SQLException, ClassNotFoundException {
        int result = 0;
        String QUERY = "UPDATE student SET first_name = ?, last_name = ?, address = ?, department_id = ? WHERE id = ?";
        try (Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(QUERY)) {
                ps.setString(1, student.getFirstName());
                ps.setString(2, student.getLastName());
                ps.setString(3, student.getAddress());
                ps.setInt(4, student.getDepartment().getId());
                ps.setString(5, student.getId());
                if (ps.executeUpdate() != 0) {
                    connection.commit();
                    result = 1;
                } else {
                    connection.rollback();
                }
            }
        }
        return result;
    }
}
