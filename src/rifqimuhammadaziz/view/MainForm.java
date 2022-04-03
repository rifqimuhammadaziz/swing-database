package rifqimuhammadaziz.view;

import rifqimuhammadaziz.dao.DepartmentDaoImpl;
import rifqimuhammadaziz.dao.StudentDaoImpl;
import rifqimuhammadaziz.entity.Department;
import rifqimuhammadaziz.entity.Student;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainForm {
    private JTextField txtID;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextArea txtAddress;
    private JComboBox<Department> cbDepartment;
    private JButton btnAddDepartment;
    private JButton btnUpdate;
    private JButton btnReset;
    private JButton btnSave;
    private JTable tableStudent;
    private JSplitPane rootPanel;
    private JButton btnDelete;
    private JButton btnDepartmentData;
    private JButton btnAddNew;

    private DepartmentDaoImpl departmentDao;
    private StudentDaoImpl studentDao;
    private List<Department> departments;
    private List<Student> students;
    private DefaultComboBoxModel<Department> departmentComboBoxModel;
    private StudentTableModel studentTableModel;
    private Student selectedStudent;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public MainForm() {
        departmentDao = new DepartmentDaoImpl();
        studentDao = new StudentDaoImpl();
        departments = new ArrayList<>();
        students = new ArrayList<>();

        // fetch data
        try {
            departments.addAll(departmentDao.getAll());
            students.addAll(studentDao.getAll());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // get department data and add to combo box
        departmentComboBoxModel = new DefaultComboBoxModel<>(departments.toArray(new Department[0]));
        cbDepartment.setModel(departmentComboBoxModel);

        // get student data and add to table
        studentTableModel = new StudentTableModel(students);
        tableStudent.setModel(studentTableModel);
        tableStudent.setAutoCreateRowSorter(true);

        // Button Add Department
        btnAddDepartment.addActionListener(e -> {
            String newDepartment = JOptionPane.showInputDialog(null, "New Department Name", "Add Department", JOptionPane.INFORMATION_MESSAGE);
            if (newDepartment != null && !newDepartment.trim().isEmpty()) {
                Department department = new Department();
                department.setName(newDepartment);
                try {
                    if (departmentDao.addData(department) == 1) {
                        departments.clear();
                        departments.addAll(departmentDao.getAll());
                        departmentComboBoxModel.removeAllElements();
                        departmentComboBoxModel.addAll(departments);
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Button Save
        btnSave.addActionListener(e -> {
            if (    txtFirstName.getText().trim().isEmpty() ||
                    txtLastName.getText().trim().isEmpty() ||
                    cbDepartment.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(rootPanel, "Please fill form correctly!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Student student = new Student();
                student.setFirstName(txtFirstName.getText());
                student.setLastName(txtLastName.getText().trim().isEmpty() ? null : txtLastName.getText());
                student.setAddress(txtAddress.getText());
                student.setDepartment((Department) cbDepartment.getSelectedItem());
                try {
                    if (studentDao.addData(student) == 1) {
                        students.clear();
                        students.addAll(studentDao.getAll());
                        studentTableModel.fireTableDataChanged();
                        resetForm();
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Button Reset
        btnReset.addActionListener(e -> {
            int validate = JOptionPane.showConfirmDialog(null,"Are you sure to reset form?", "Reset Form",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (validate == JOptionPane.YES_OPTION) {
                resetForm();
            } else {
                return;
            }

        });

        // Button Update
        btnUpdate.addActionListener(e -> {
            if (    txtID.getText().trim().isEmpty() ||
                    txtFirstName.getText().trim().isEmpty() ||
                    cbDepartment.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(rootPanel, "Please fill form correctly!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                selectedStudent.setFirstName(txtFirstName.getText());
                selectedStudent.setLastName(txtLastName.getText().trim().isEmpty() ? null : txtLastName.getText());
                selectedStudent.setAddress(txtAddress.getText());
                selectedStudent.setDepartment((Department) cbDepartment.getSelectedItem());
                try {
                    int validate = JOptionPane.showConfirmDialog(
                            null,
                            "Are you sure to Update Student : " + txtID.getText(),
                            "Update Student",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );
                    if (validate == JOptionPane.YES_OPTION) {
                        if (studentDao.updateData(selectedStudent) == 1) {
                            students.clear();
                            students.addAll(studentDao.getAll());
                            studentTableModel.fireTableDataChanged();
                            resetForm();
                            initialForm();
                        }
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Table Student
        tableStudent.getSelectionModel().addListSelectionListener(e -> {
            if (!tableStudent.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = tableStudent.convertRowIndexToModel(tableStudent.getSelectedRow());
                selectedStudent = students.get(selectedIndex);
                if (selectedStudent != null) {

                    enableTextfield();
                    btnAddNew.setText("Cancel");

                    txtID.setText(selectedStudent.getId());
                    txtFirstName.setText(selectedStudent.getFirstName());
                    txtLastName.setText(selectedStudent.getLastName() != null ? selectedStudent.getLastName() : "");
                    txtAddress.setText(selectedStudent.getAddress());
                    cbDepartment.setSelectedItem(selectedStudent.getDepartment());

                    txtID.setEnabled(false);
                    btnSave.setEnabled(false);
                    btnUpdate.setEnabled(true);
                    btnDelete.setEnabled(true);
                }
            }

            rootPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    initialForm();
                }
            });
        });

        // Button Delete
        btnDelete.addActionListener(e -> {
            try {
                int validate = JOptionPane.showConfirmDialog(null,"Are you sure to delete?", "Delete Data",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (validate == JOptionPane.YES_OPTION) {
                    if (studentDao.deleteData(selectedStudent) == 1) {
                        students.clear();
                        students.addAll(studentDao.getAll());
                        studentTableModel.fireTableDataChanged();
                        resetForm();
                    }
                } else {
                    return;
                }

            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        // Button View Form Department Data
        btnDepartmentData.addActionListener(e -> {
            JFrame frame = new JFrame("Department Data Table");
            frame.setContentPane(new DepartmentDataForm().rootPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

        // Button Add New
        btnAddNew.addActionListener(e -> {
            if (btnAddNew.getText() != "Cancel") {
                prepareToFillForm();
            } else {
                initialForm();
            }
        });
    }

    private void initialForm() {
        resetForm();

        txtID.setEnabled(false);
        txtFirstName.setEnabled(false);
        txtLastName.setEnabled(false);
        txtAddress.setEnabled(false);
        cbDepartment.setEnabled(false);
        cbDepartment.setSelectedItem(null);

        btnAddNew.setText("Add New");

        btnSave.setEnabled(false);
        btnReset.setEnabled(false);
        btnUpdate.setEnabled(false);
        tableStudent.clearSelection();
        selectedStudent = null;
    }

    private void prepareToFillForm() {
        resetForm();

        txtFirstName.grabFocus();

        txtID.setEnabled(false);
        txtFirstName.setEnabled(true);
        txtLastName.setEnabled(true);
        txtAddress.setEnabled(true);
        cbDepartment.setEnabled(true);

        btnAddNew.setText("Cancel");

        btnSave.setEnabled(true);
        btnReset.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        tableStudent.clearSelection();
        selectedStudent = null;
    }

    private void resetForm() {
        txtID.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtAddress.setText("");

        txtID.setEnabled(false);
        btnSave.setEnabled(true);
        btnUpdate.setEnabled(false);
        tableStudent.clearSelection();
        selectedStudent = null;
    }

    private void enableTextfield() {
        txtFirstName.setEnabled(true);
        txtLastName.setEnabled(true);
        txtAddress.setEnabled(true);
        cbDepartment.setEnabled(true);

        txtFirstName.grabFocus();
    }

    private static class StudentTableModel extends AbstractTableModel {

        private List<Student> students;
        private final String[] COLUMNS = {"ID", "FIRST NAME", "LAST NAME", "DEPARTMENT"};

        public StudentTableModel(List<Student> students) {
            this.students = students;
        }

        @Override
        public int getRowCount() {
            return students.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> students.get(rowIndex).getId();
                case 1 -> students.get(rowIndex).getFirstName();
                case 2 -> students.get(rowIndex).getLastName();
                case 3 -> students.get(rowIndex).getDepartment().getName();
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
