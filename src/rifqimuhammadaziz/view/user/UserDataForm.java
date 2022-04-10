package rifqimuhammadaziz.view.user;

import rifqimuhammadaziz.dao.UserDaoImpl;
import rifqimuhammadaziz.entity.User;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDataForm extends JFrame{
    public JTable tableUser;
    private JButton btnDelete;
    private JButton btnRefresh;
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

         // Double click row to edit
        tableUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (e.getClickCount() == 2) {
                    if (!tableUser.getSelectionModel().isSelectionEmpty()) {
                        int selectedIndex = tableUser.convertRowIndexToModel(tableUser.getSelectedRow());
                        selectedUser = users.get(selectedIndex);
                        if (selectedUser != null) {
                            EditForm frame = new EditForm(selectedUser);
                            frame.setTitle("Edit User : " + selectedUser.getUsername());
                            frame.pack();
                            frame.setLocationRelativeTo(null);
                            frame.setVisible(true);
                        }
                    }
                }
            }
        });

        // Select row to delete
        tableUser.getSelectionModel().addListSelectionListener(e -> {
            if (!tableUser.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = tableUser.convertRowIndexToModel(tableUser.getSelectedRow());
                selectedUser = users.get(selectedIndex);
                if (selectedUser != null) {
                    btnDelete.setEnabled(true);
                }
            }
        });

        // Button Delete
        btnDelete.addActionListener(e -> {
            try {
                int validate = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure to Delete Department : \n" + selectedUser.getId() + " - " + selectedUser.getFullName(),
                        "Delete Department",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (validate == JOptionPane.YES_OPTION) {
                    if (userDao.deleteData(selectedUser) == 1) {
                        users.clear();
                        users.addAll(userDao.getAll());
                    }
                } else if (validate == JOptionPane.NO_OPTION) {
                    userTableModel.fireTableDataChanged();
                }
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        btnRefresh.addActionListener(e -> {
//            userTableModel.fireTableDataChanged();
            tableUser.repaint();
            JOptionPane.showMessageDialog(null, "Table Refreshed", "Load Data", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public static void main(String[] args) {
//        try {
//             UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); // WINDOWS LOOK AND FEEL
//             UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); // METAL
//             UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel"); // MOTIF LOOK AND FEEL
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
//            e.printStackTrace();
//        }
        JFrame frame = new JFrame("User Data Form");
        frame.setContentPane(new UserDataForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private static class UserTableModel extends AbstractTableModel {
        private List<User> users;
        private final String[] COLUMNS = {"ID", "USERNAME", "FULLNAME", "GENDER", "ADDRESS", "PHONE NUMBER", "STATUS"};

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
                case 6 -> users.get(rowIndex).getStatus();
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
