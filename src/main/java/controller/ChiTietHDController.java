package controller;

import com.opencsv.CSVWriter;
import dao.ChiTietHDDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.ChiTietHD;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import view.ChiTietHDJPanel;

/**
 *
 * @author johanna nguyen
 */
public class ChiTietHDController {

    private final ChiTietHDDAO chiTietHDDAO;
    private final ChiTietHDJPanel chiTietHDJPanel;

    public ChiTietHDController(ChiTietHDJPanel chiTietHDJPanel) {
        this.chiTietHDDAO = new ChiTietHDDAO();
        this.chiTietHDJPanel = chiTietHDJPanel;
    }

    public List<ChiTietHD> getAllChiTietHD() throws ClassNotFoundException, SQLException {
        return chiTietHDDAO.getAllChiTietHD();
    }

    public void addChiTietHD(ChiTietHD chiTietHD) throws SQLException, ClassNotFoundException {
        chiTietHDDAO.addChiTietHD(chiTietHD);
    }

    public void deleteChiTietHD(ChiTietHD chiTietHD) throws SQLException, ClassNotFoundException {
        chiTietHDDAO.deleteChiTietHD(chiTietHD.getMaHD(), chiTietHD.getMaSP());
    }

    // Phương thức xuất CSV 
    public void exportToCSV() throws ClassNotFoundException {
        JTable table = chiTietHDJPanel.getjTableChiTietHD();

        // Chọn đường dẫn và tên file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select CSV file save location");
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return; 
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

    public void exportToExcel() {
        JTable table = chiTietHDJPanel.getjTableChiTietHD();

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
            fileChooser.setDialogTitle("Select Excel file save location");
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

    public void updateTableFromResultSet(ResultSet rs) throws SQLException {
        // Xóa dữ liệu hiện tại trong JTable
        DefaultTableModel model = (DefaultTableModel) chiTietHDJPanel.getTableModel();
        model.setRowCount(0); 

        // Kiểm tra nếu ResultSet không có dữ liệu
        boolean result = false;
        // Cập nhật JTable với dữ liệu từ ResultSet
        while (rs.next()) {
            result = true;
            Object[] row = new Object[model.getColumnCount()];
            for (int i = 0; i < model.getColumnCount(); i++) {
                row[i] = rs.getObject(i + 1); 
            }
            model.addRow(row);
        }
        // Nếu không có kết quả, hiển thị thông báo
        if (!result) {
            JOptionPane.showMessageDialog(null, "No results found", "Notification", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void loadChiTietHDData(ChiTietHDJPanel chiTietHDJPanel, List<ChiTietHD> chiTietHDList) throws ClassNotFoundException, SQLException {
        // Nếu danh sách CTHD là null, lấy tất cả sản phẩm từ cơ sở dữ liệu
        if (chiTietHDList == null) {
            chiTietHDList = chiTietHDDAO.getAllChiTietHD();
        }

        DefaultTableModel model = (DefaultTableModel) chiTietHDJPanel.getTableModel();
        model.setRowCount(0); 

        // Thêm các CTHD từ danh sách vào bảng
        for (ChiTietHD cthd : chiTietHDList) {
            model.addRow(new Object[]{
                cthd.getMaHD(),
                cthd.getMaSP(),
                cthd.getSoLuong(),
                cthd.getDonGia()
            });
        }
    }

    public void searchInAllColumns(String searchTerm) throws ClassNotFoundException {
        try {
            List<ChiTietHD> chiTietHDList = chiTietHDDAO.searchInAllColumns(searchTerm); 

            if (chiTietHDList.isEmpty()) {
                // Nếu không có kết quả, hiển thị thông báo
                JOptionPane.showMessageDialog(null, "No results found for keyword: " + searchTerm, "Notification", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Nếu có kết quả, hiển thị dữ liệu trên bảng
                loadChiTietHDData(chiTietHDJPanel, chiTietHDList);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error searching across all columns", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void searchByMaHD(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = chiTietHDDAO.searchByMaHD(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }

    public void searchByMaSP(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = chiTietHDDAO.searchByMaSP(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }

    public void searchByDonGia(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = chiTietHDDAO.searchByDonGia(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }

    }

    public void updateChiTietHD() throws ClassNotFoundException {
        try {
            int selectedRow = chiTietHDJPanel.getjTableChiTietHD().getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row to update", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String maHD = (String) chiTietHDJPanel.getjCbMaHD().getSelectedItem();
            String maSP = (String) chiTietHDJPanel.getjCbMaSP().getSelectedItem();

            int soLuong;
            soLuong = (Integer) chiTietHDJPanel.getjSpinnerSoLuong().getValue();

            BigDecimal donGia;
            donGia = new BigDecimal(chiTietHDJPanel.getjTextDonGia().getText());

            ChiTietHD chiTietHD = new ChiTietHD(maHD, maSP, soLuong, donGia);

            if (chiTietHDDAO.update(chiTietHD)) {
                JOptionPane.showMessageDialog(null, "Update successful!");
                chiTietHDJPanel.loadChiTietHDData();
            } else {
                JOptionPane.showMessageDialog(null, "Update failed!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating data: " + e.getMessage());
        }
    }

    // Filter giá sp
    public void filterByPriceRange(String minPriceInput, String maxPriceInput) throws SQLException {
        try {
            BigDecimal minPrice = new BigDecimal(minPriceInput);
            BigDecimal maxPrice = new BigDecimal(maxPriceInput);
            // Lấy dữ liệu từ DAO
            ResultSet rs = chiTietHDDAO.filterByPriceRange(minPrice, maxPrice);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (NumberFormatException e) {
            // Xử lý lỗi khi người dùng nhập không phải số
            JOptionPane.showMessageDialog(null, "Please enter numbers only", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Lấy ds mã HD
    public void loadMaHDToComboBox() {
        try {
            // Gọi DAO để lấy danh sách Mã HD
            List<String> maHDList = chiTietHDDAO.getAllMaHD();

            // Xoá khi reload
            chiTietHDJPanel.getjCbMaHD().removeAllItems();

            // Thêm từng Mã HD vào JComboBox
            for (String maHD : maHDList) {
                chiTietHDJPanel.getjCbMaHD().addItem(maHD);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error loading invoice list", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Lấy ds mã SP
    public void loadMaSPToComboBox() {
        try {
            // Gọi DAO để lấy danh sách Mã NCC
            List<String> maSPList = chiTietHDDAO.getAllMaSP();

            // Xoá khi reload
            chiTietHDJPanel.getjCbMaSP().removeAllItems();

            // Thêm từng Mã SP vào JComboBox
            for (String maSP : maSPList) {
                chiTietHDJPanel.getjCbMaSP().addItem(maSP);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error loading product code list", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean exists(String maHD, String maSP) throws SQLException, ClassNotFoundException {
        return chiTietHDDAO.exists(maHD, maSP);
    }
}
