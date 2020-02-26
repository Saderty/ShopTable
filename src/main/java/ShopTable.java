import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShopTable extends JFrame {
    DefaultTableModel model;

    ShopTable() {
        setSize(1000, 1000);
        model = new DefaultTableModel();
        final JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        //table.setBounds(0, 0, 500, 500);
        scrollPane.setBounds(0, 0, 500, 500);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JButton rowButton = new JButton();
        rowButton.setBounds(0, 500, 500, 50);
        rowButton.setText("Add Row");
        add(rowButton);
        JButton calcButton = new JButton();
        calcButton.setBounds(0, 550, 500, 50);
        calcButton.setText("Calc Table");
        add(calcButton);
        add(scrollPane);

        model.addColumn("Name");
        model.addColumn("Price");
        model.addColumn("Number");
        model.addColumn("Result");

        String[] strings = {"11", "22", "33", "44"};
        model.addRow(strings);
        model.addRow(strings);
        model.addRow(strings);
        model.addRow(new String[]{"Total"});

//        ListSelectionModel selectionModel = table.getSelectionModel();
//        selectionModel.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent listSelectionEvent) {
//                int[] selectedRow = table.getSelectedRows();
//                int[] selectedColumns = table.getSelectedColumns();
//
//                System.out.println(Arrays.toString(selectedRow));
//                System.out.println(Arrays.toString(selectedColumns));
//
//                for (int i = 0; i < selectedRow.length; i++) {
//                    for (int j = 0; j < selectedColumns.length; j++) {
//                        //table.setValueAt(table.getValueAt(selectedRow[i], selectedColumns[j]), i, j);
//                        //selectedData = (String) table.getValueAt(selectedRow[i], selectedColumns[j]);
//                    }
//                }
//            }
//        });

        rowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                model.insertRow(table.getRowCount() - 1, new String[]{"", "", "", ""});
            }
        });

        calcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                double a;
                double b;
                double c = 0;
                for (int i = 0; i < table.getRowCount() - 1; i++) {
                    a = Double.parseDouble((String) table.getValueAt(i, 1));
                    b = Double.parseDouble((String) table.getValueAt(i, 2));
                    table.setValueAt(a * b, i, 3);
                    c += a * b;
                }
                table.setValueAt(c, table.getRowCount()-1, 3);
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ShopTable();
    }
}
