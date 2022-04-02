package rifqimuhammadaziz.view;

import rifqimuhammadaziz.dao.DepartmentDaoImpl;
import rifqimuhammadaziz.entity.Department;
import rifqimuhammadaziz.entity.Student;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDataForm extends Container {
    private JTable tableDepartment;
    private JButton btnDelete;
    public JPanel rootPanel;

    private DepartmentDaoImpl departmentDao;
    private List<Department> departments;
    private DepartmentTableModel departmentTableModel;
    private Department selectedDepartment;

    public static void main(String[] args) {
        JFrame frame = new JFrame("DepartmentDataForm");
        frame.setContentPane(new DepartmentDataForm().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public DepartmentDataForm() {
        departments = new ArrayList<>();
        departmentDao = new DepartmentDaoImpl();

        // fetch data
        try {
            departments.addAll(departmentDao.getAll());
            System.out.println(departments.toString());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // get department data and add to table
        departmentTableModel = new DepartmentTableModel(departments);
        tableDepartment.setModel(departmentTableModel);
        tableDepartment.setAutoCreateRowSorter(true);

        // JTable
        tableDepartment.getSelectionModel().addListSelectionListener(e -> {
            if (!tableDepartment.getSelectionModel().isSelectionEmpty()) {
                int selectedIndex = tableDepartment.convertRowIndexToModel(tableDepartment.getSelectedRow());
                selectedDepartment = departments.get(selectedIndex);
                if (selectedDepartment != null) {
                    btnDelete.setEnabled(true);
                }
            }
        });

        // Button Delete
        btnDelete.addActionListener(e -> {
            try {
                int validate = JOptionPane.showConfirmDialog(null,"Sure? You want to exit?", "Swing Tester",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (validate == JOptionPane.YES_OPTION) {
                    if (departmentDao.deleteData(selectedDepartment) == 1) {
                        departments.clear();
                        departments.addAll(departmentDao.getAll());
                        departmentTableModel.fireTableDataChanged();
                    }
                } else if (validate == JOptionPane.NO_OPTION) {
                    departmentTableModel.fireTableDataChanged();
                }
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
    }

    private static class DepartmentTableModel extends AbstractTableModel {

        private List<Department> departments;
        private final String[] COLUMNS = {"ID", "DEPARTMENT"};

        public DepartmentTableModel(List<Department> departments) {
            this.departments = departments;
        }

        @Override
        public int getRowCount() {
            return departments.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> departments.get(rowIndex).getId();
                case 1 -> departments.get(rowIndex).getName();
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
