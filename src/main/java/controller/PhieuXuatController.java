package controller;

import com.opencsv.CSVWriter;
import dao.PhieuXuatDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.PhieuXuat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import view.PhieuXuatJPanel;

/**
 *
 * @author khanhnguyen
 */
public class PhieuXuatController {

    private final PhieuXuatDAO phieuXuatDAO;
    private final PhieuXuatJPanel phieuXuatJPanel;

    public PhieuXuatController(PhieuXuatJPanel phieuXuatJPanel) {
        this.phieuXuatDAO = new PhieuXuatDAO();
        this.phieuXuatJPanel = phieuXuatJPanel;
    }

    public List<PhieuXuat> getAllPhieuXuat() throws ClassNotFoundException {
        try {
            return phieuXuatDAO.getAllPhieuXuat();
        } catch (SQLException e) {
            return null;
        }
    }

    public void addPhieuXuat(PhieuXuat phieuXuat) throws SQLException, ClassNotFoundException {
        phieuXuatDAO.addPhieuXuat(phieuXuat);
    }

    public void deletePhieuXuat(PhieuXuat phieuXuat) throws SQLException, ClassNotFoundException {
        phieuXuatDAO.deletePhieuXuat(phieuXuat.getMaXuat());
    }

    // Phương thức xuất CSV với hộp thoại chọn file
    public void exportToCSV() throws ClassNotFoundException {
        JTable table = phieuXuatJPanel.getjTablePhieuXuat();

        // Mở hộp thoại để chọn đường dẫn và tên file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose CSV file save location");
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
        JTable table = phieuXuatJPanel.getjTablePhieuXuat();

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

    public void updateTableFromResultSet(ResultSet rs) throws SQLException {
        // Xóa dữ liệu hiện tại trong JTable
        DefaultTableModel model = (DefaultTableModel) phieuXuatJPanel.getTableModel();
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

    public void loadPhieuXuatData(PhieuXuatJPanel phieuXuatJPanel, List<PhieuXuat> phieuXuatList) throws ClassNotFoundException, SQLException {
        // Nếu danh sách phiếu xuất là null, lấy tất cả phiếu nhập từ cơ sở dữ liệu
        if (phieuXuatList == null) {
            phieuXuatList = phieuXuatDAO.getAllPhieuXuat();
        }

        DefaultTableModel model = (DefaultTableModel) phieuXuatJPanel.getTableModel();
        model.setRowCount(0); 

        // Thêm các phiếu xuất từ danh sách vào bảng
        for (PhieuXuat phieuXuat : phieuXuatList) {
            model.addRow(new Object[]{
                phieuXuat.getMaXuat(),
                phieuXuat.getMaSP(),
                phieuXuat.getSoLuong(),
                phieuXuat.getGiaXuat(),
                phieuXuat.getNgayXuat(),
                phieuXuat.getMaKH()
            });
        }
    }

    public void searchInAllColumns(String searchTerm) throws ClassNotFoundException {
        try {
            List<PhieuXuat> phieuXuatList = phieuXuatDAO.searchInAllColumns(searchTerm);

            if (phieuXuatList.isEmpty()) {
                // Nếu không có kết quả, hiển thị thông báo
                JOptionPane.showMessageDialog(null, "No results found for keyword: " + searchTerm, "Notification", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Nếu có kết quả, hiển thị dữ liệu trên bảng
                loadPhieuXuatData(phieuXuatJPanel, phieuXuatList);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error searching across all columns", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void searchByMaXuat(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = phieuXuatDAO.searchByMaXuat(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }

    public void updatePhieuXuat() throws ClassNotFoundException {
        try {
            int selectedRow = phieuXuatJPanel.getjTablePhieuXuat().getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row to updatet", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String maXuat = phieuXuatJPanel.getjTextMaXuat().getText();
            String maSP = (String) phieuXuatJPanel.getjCbMaSPXuat().getSelectedItem();
            
            int soLuong;
            soLuong = Integer.parseInt(phieuXuatJPanel.getjTextSoLuongXuat().getText());

            BigDecimal giaXuat;
            giaXuat = new BigDecimal(phieuXuatJPanel.getjTextGiaXuat().getText());

            Date ngayXuat = phieuXuatJPanel.getjDateNgayXuat().getDate();

            String maKH = (String) phieuXuatJPanel.getjCbMaKH().getSelectedItem();

            PhieuXuat phieuXuat = new PhieuXuat(maXuat,maSP,soLuong,giaXuat,ngayXuat,maKH);

            if (phieuXuatDAO.update(phieuXuat)) {
                JOptionPane.showMessageDialog(null, "Update successful!");
                phieuXuatJPanel.loadPhieuXuatData(); 
            } else {
                JOptionPane.showMessageDialog(null, "Update failed!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating data: " + e.getMessage());
        }
    }

    // Kiểm tra mã xuất
    public boolean isMaXuatExist(String maXuat, String currentMaXuat) throws ClassNotFoundException, SQLException {
        return phieuXuatDAO.isMaXuatExist(maXuat, currentMaXuat);
    }

    // Filter giá sp
    public void filterByPriceRange(String minPriceInput, String maxPriceInput) throws SQLException {
        try {
            BigDecimal minPrice = new BigDecimal(minPriceInput);
            BigDecimal maxPrice = new BigDecimal(maxPriceInput);
            // Lấy dữ liệu từ DAO
            ResultSet rs = phieuXuatDAO.filterByPriceRange(minPrice, maxPrice);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (NumberFormatException e) {
            // Xử lý lỗi khi người dùng nhập không phải số
            JOptionPane.showMessageDialog(null, "Please select a row to update", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Mã xuất hiện tại
    public String getCurrentMaXuat() {
        int selectedRow = phieuXuatJPanel.getjTablePhieuXuat().getSelectedRow();
        if (selectedRow != -1) {
            // Lấy MaNhap từ dòng đã chọn
            return (String) phieuXuatJPanel.getjTablePhieuXuat().getValueAt(selectedRow, 0);
        }
        return null;
    }

    // Lấy ds mã SP
    public void loadMaSPToComboBox() {
        try {
            // Gọi DAO để lấy danh sách Mã SP
            List<String> maSPList = phieuXuatDAO.getAllMaSP();

            // Xoá khi reload
            phieuXuatJPanel.getjCbMaSPXuat().removeAllItems();

            // Thêm từng Mã SP vào JComboBox
            for (String maSP : maSPList) {
                phieuXuatJPanel.getjCbMaSPXuat().addItem(maSP);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error loading product list", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Lấy ds mã KH
    public void loadMaKHToComboBox() {
        try {
            // Gọi DAO để lấy danh sách Mã NCC
            List<String> maKHList = phieuXuatDAO.getAllMaKH();

            // Xoá khi reload
            phieuXuatJPanel.getjCbMaKH().removeAllItems();

            // Thêm từng Mã KH vào JComboBox
            for (String maKH : maKHList) {
                phieuXuatJPanel.getjCbMaKH().addItem(maKH);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error loading customer list", "Rrror", JOptionPane.ERROR_MESSAGE);
        }
    }

}
