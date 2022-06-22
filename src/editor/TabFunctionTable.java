package editor;

import functions.*;
import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class TabFunctionTable extends DefaultTableModel {

    private TabulatedFunction function;
    private Component parent;

    public TabFunctionTable(TabulatedFunction function, Component parent) {
        this.function = function;
        this.parent = parent;
    }

    @Override
    public int getRowCount() {
      if (function != null) {
            return function.getPointsCount();
        } else {
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "x";
        }
        if (column == 1) {
            return "y";
        } else {
            return "Undefined";
        }

    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return Double.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return row <= this.getRowCount() && column <= this.getColumnCount();

    }

    @Override
    public Object getValueAt(int row, int column) {
        if (column == 0) {
            return this.function.getPointX(row);
        }
        if (column == 1) {
            return this.function.getPointY(row);
        } else {
            return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        try {
            if (column == 0) {
                this.function.setPointX(row, (double) aValue);
            }
            if (column == 1) {
                this.function.setPointY(row, (double) aValue);

            }
        } catch (InappropriateFunctionPointException e) {
            JOptionPane.showMessageDialog(parent, e.getMessage());
        }
    }

}
