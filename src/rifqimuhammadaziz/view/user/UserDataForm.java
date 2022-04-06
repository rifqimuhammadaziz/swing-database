package rifqimuhammadaziz.view.user;

import rifqimuhammadaziz.dao.UserDaoImpl;
import rifqimuhammadaziz.entity.User;
import rifqimuhammadaziz.tablemodel.UserTableModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDataForm {
    private JTable tableUser;
    private JButton btnDelete;
    private JButton button2;
    private JPanel rootPanel;

    private UserDaoImpl userDao;
    private List<User> users;
    private UserTableModel userTableModel;
    private User selectedUser;

    public UserDataForm() {
        users = new ArrayList<>();
        userDao = new UserDaoImpl();

        // Fetch Data
        try {
            users.addAll(userDao.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Get user data and add to table
        userTableModel = new UserTableModel(users);
        tableUser.setModel(userTableModel);
        tableUser.setAutoCreateRowSorter(true);

        // Select row to delete
        tableUser.getSelectionModel().addListSelectionListener(e -> {
            if (!tableUser.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = tableUser.convertRowIndexToModel(tableUser.getSelectedRow());
                selectedUser = users.get(selectedIndex);
                if (selectedUser == null) {
                    btnDelete.setEnabled(true);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("UserDataForm");
        frame.setContentPane(new UserDataForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static class UserTableModel extends AbstractTableModel {
        private List<User> users;
        private final String[] COLUMNS = {"ID", "USERNAME", "FULLNAME", "GENDER", "ADDRESS", "PHONE NUMBER"};

        public UserTableModel(List<User> users) {
            this.users = users;
        }

        @Override
        public int getRowCount() {
            return users.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> users.get(rowIndex).getId();
                case 1 -> users.get(rowIndex).getUsername();
                case 2 -> users.get(rowIndex).getFullName();
                case 3 -> users.get(rowIndex).getGender();
                case 4 -> users.get(rowIndex).getAddress();
                case 5 -> users.get(rowIndex).getPhoneNumber();
                default -> "";
            };
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            }
            return Object.class;
        }
    }


}
