package rifqimuhammadaziz.dao;

import rifqimuhammadaziz.entity.Department;
import rifqimuhammadaziz.entity.Student;
import rifqimuhammadaziz.entity.User;
import rifqimuhammadaziz.util.DaoService;
import rifqimuhammadaziz.util.MySQLConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements DaoService<User> {


    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int addData(User user) throws SQLException, ClassNotFoundException {
        int result = 0;
        String QUERY = "INSERT INTO user(username, fullname, gender, address, phonenumber) VALUES(?, ?, ?, ?, ?)";
        try (Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(QUERY)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getFullName());
                ps.setString(3, user.getGender());
                ps.setString(4, user.getAddress());
                ps.setString(5, user.getPhoneNumber());
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
    public int updateData(User user) throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public int deleteData(User user) throws SQLException, ClassNotFoundException {
        return 0;
    }

    public int getUser(User user) throws SQLException, ClassNotFoundException {
        int result = 0;
        String QUERY = "SELECT username FROM user WHERE username = ?";
        try (Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(QUERY)) {
                ps.setString(1, user.getUsername());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        System.out.println("Data exists!");
                    }
                }
            }
        }
        return result;
    }
}
