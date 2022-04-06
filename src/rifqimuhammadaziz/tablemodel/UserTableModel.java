package rifqimuhammadaziz.tablemodel;

import rifqimuhammadaziz.entity.User;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UserTableModel extends AbstractTableModel {

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
