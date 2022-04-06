package rifqimuhammadaziz.dao;

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

    String imagePath;

    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        List<User> users = new ArrayList<>();
        String QUERY = "SELECT id, username, fullname, gender, address, phonenumber FROM user";
        try (Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(QUERY)) {
                try (ResultSet rs = ps.executeQuery()) { // result from query
                    while (rs.next()) {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setUsername(rs.getString("username"));
                        user.setFullName(rs.getString("fullname"));
                        user.setGender(rs.getString("gender"));
                        user.setAddress(rs.getString("address"));
                        user.setPhoneNumber(rs.getString("phonenumber"));
                        users.add(user);
                    }
                }
            }
        }
        return users;
    }

    @Override
    public int addData(User user) throws SQLException, ClassNotFoundException {
        int result = 0;
        String QUERY = "INSERT INTO user(username, password, fullname, gender, address, phonenumber, image) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(QUERY)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getFullName());
                ps.setString(4, user.getGender());
                ps.setString(5, user.getAddress());
                ps.setString(6, user.getPhoneNumber());
                ps.setString(7, user.getImage());

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

    public int loginUser(User user) throws SQLException, ClassNotFoundException {
        int result = 0;
        String QUERY = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (Connection connection = MySQLConnection.createConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(QUERY)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "Login Success.", "Logged in.", JOptionPane.INFORMATION_MESSAGE);
                        result = 1;
                    } else {
                        JOptionPane.showMessageDialog(null, "Login Failed. Username or Password is incorrect", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        return result;
    }
}
