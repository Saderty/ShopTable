import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class ShopTable extends JFrame {
    DefaultTableModel model;
    JTable table;

    ShopTable() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        model = new DefaultTableModel();
        table = new JTable(model);
        table.setBounds(0, 0, 500, 500);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 500, 500);

        JButton rowButton = new JButton();
        rowButton.setBounds(0, 500, 500, 50);
        rowButton.setText("Добавить строку");
        panel.add(rowButton);
        JButton calcButton = new JButton();
        calcButton.setBounds(0, 550, 500, 50);
        calcButton.setText("Рассчитать таблицу");
        panel.add(calcButton);
        JButton printButton = new JButton();
        printButton.setBounds(0, 600, 500, 50);
        printButton.setText("Создать таблицу");
        panel.add(printButton);


        //panel.setBounds(0, 0, 500, 1000);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //panel.setBounds(0,0,500,500);
        scrollPane.setBounds(0, 0, (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 100, 500);
        //scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
//panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(scrollPane);
        add(panel);

        model.addColumn("Название");//0
        model.addColumn("Кол-во");//1
        model.addColumn("Цена");//2
        model.addColumn("%");//3
        model.addColumn("Цена + %");//4
        model.addColumn("Итого");//5
        model.addColumn("Итого + %");//6

        model.addRow(new String[]{"Результат"});

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
                model.insertRow(table.getRowCount() - 1, new String[]{""});
            }
        });

        calcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                double a;
                double b;
                double c = 0;
                double p = 0;
                for (int i = 0; i < table.getRowCount() - 1; i++) {
                    a = Double.parseDouble((String) table.getValueAt(i, 1));
                    b = Double.parseDouble((String) table.getValueAt(i, 2));
                    p = Double.parseDouble((String) table.getValueAt(i, 3)) / 100 + 1;
                    table.setValueAt(b * p, i, 4);
                    table.setValueAt(a * b, i, 5);
                    c += a * b;
                    table.setValueAt(a * b * p, i, 6);
                }
                for (int i = 1; i < table.getColumnCount(); i++) {
                    table.setValueAt("", table.getRowCount() - 1, i);
                }
                table.setValueAt(c, table.getRowCount() - 1, 5);
                table.setValueAt(c * p, table.getRowCount() - 1, 6);
            }
        });

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                toExcel();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ShopTable();
    }

    void toExcel() {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Page 1");
            XSSFRow row = sheet.createRow(0);
            for (int i = 0; i < table.getColumnCount(); i++) {
                row.createCell(i).setCellValue(table.getColumnName(i));
            }
            for (int i = 0; i < table.getRowCount(); i++) {
                row = sheet.createRow(i + 1);
                for (int j = 0; j < table.getColumnCount(); j++) {
                    String s = String.valueOf(table.getValueAt(i, j));
                    if (s == null) s = "";
                    row.createCell(j).setCellValue(s);
                }
            }
            File file = new File("C:\\Магазин\\Таблица " + new Date().toString() + ".xlsx");
            file.getParentFile().mkdirs();
            //workbook.write(new FileOutputStream("/home/saderty/Table " + new Date().toString() + ".xlsx"));
            workbook.write(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
