package controller;

import com.opencsv.CSVWriter;
import dao.KhoDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.Kho;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import view.KhoJPanel;
import view.KhoView;

/**
 *
 * @author khanhnguyen
 */
public class KhoController {

    private final KhoDAO khoDAO;
    private KhoJPanel khoJPanel;
    private KhoView view;  
   


    public KhoController(KhoJPanel khoJPanel) {
        this.khoDAO = new KhoDAO();
        this.khoJPanel = khoJPanel;
        this.view = khoJPanel;
    }
    
    public KhoController(KhoView view) throws ClassNotFoundException {
        this.view = view;
        khoDAO = new KhoDAO();
    }

  
    public void deleteKho(Kho kho) throws SQLException, ClassNotFoundException {
        khoDAO.deleteKho(kho.getMaSP());
    }

    public void searchInAllColumns(String searchTerm) throws ClassNotFoundException {
        try {
            List<Kho> khoList = khoDAO.searchInAllColumns(searchTerm);
            if (khoList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No results found" + searchTerm, "Notification", JOptionPane.INFORMATION_MESSAGE);
            } else {
                khoJPanel.capNhatKho(khoList);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: ", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void searchByMaSP(String searchTerm) {
        try {
            List<Kho> khoList = (List<Kho>) khoDAO.searchByMaSP(searchTerm);
            if (khoList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No results found for product ID: " + searchTerm, "Notification", JOptionPane.INFORMATION_MESSAGE);
            } else {
                khoJPanel.capNhatKho(khoList); 
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Phương thức xuất CSV với hộp thoại chọn file
    public void exportToCSV() throws ClassNotFoundException {
        JTable table = khoJPanel.getjTableKho();

        // Mở hộp thoại để chọn đường dẫn và tên file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose CSV file save location");
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return; // 
        }

        File fileToSave = fileChooser.getSelectedFile();
        String filePath = fileToSave.getAbsolutePath();

        // Tự động thêm đuôi .csv 
        if (!filePath.toLowerCase().endsWith(".csv")) {
            filePath += ".csv";
        }

        // Tạo file CSV
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // Xuất tiêu đề (header)
            String[] header = new String[table.getColumnCount()];
            for (int i = 0; i < table.getColumnCount(); i++) {
                header[i] = table.getColumnName(i);
            }
            writer.writeNext(header);

            // Xuất dữ liệu từ jTable
            for (int i = 0; i < table.getRowCount(); i++) {
                String[] row = new String[table.getColumnCount()];
                for (int j = 0; j < table.getColumnCount(); j++) {
                    row[j] = table.getValueAt(i, j).toString();
                }
                writer.writeNext(row);
            }

            JOptionPane.showMessageDialog(null, "CSV file exported successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error exporting CSV file: " + e.getMessage());
        }
    }

    // Xuất file excel
    public void exportToExcel() {
        JTable table = khoJPanel.getjTableKho();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");

            // Xuất tiêu đề (header)
            TableModel model = table.getModel();
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < model.getColumnCount(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(model.getColumnName(i));
            }

            // Xuất dữ liệu từ jTable
            for (int i = 0; i < model.getRowCount(); i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(model.getValueAt(i, j).toString());
                }
            }

            // Ghi dữ liệu vào file Excel
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose Excel file save location");
            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection != JFileChooser.APPROVE_OPTION) {
                return; 
            }

            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            // Tự động thêm đuôi .xlsx 
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            JOptionPane.showMessageDialog(null, "Excel file exported successfully!");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error exporting Excel file: " + e.getMessage());
        }

    }

    // Tải dữ liệu từ cơ sở dữ liệu và hiển thị lên bảng
    public void taiDuLieuKho() throws SQLException, ClassNotFoundException {
        List<Kho> khoList = khoDAO.getAllKho();
        DefaultTableModel model = (DefaultTableModel) khoJPanel.getTableModel();

        model.setRowCount(0);  

        for (Kho kho : khoList) {
            model.addRow(new Object[]{
                kho.getMaSP(),
                kho.getSoLuongTon(),
                kho.getNgayCapNhatCuoi()
            });
        }
    }
    
    public int getSoLuongTon(String maSP) throws SQLException, ClassNotFoundException {
        return khoDAO.getSoLuongTon(maSP);
    }

     
}
