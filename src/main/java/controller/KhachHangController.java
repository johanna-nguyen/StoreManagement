package controller;

import com.opencsv.CSVWriter;
import dao.KhachHangDAO;
import model.KhachHang;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.DataValidator;
import view.KhachHangJPanel;

/**
 *
 * @author khanhnguyen
 */
public class KhachHangController {

    private final KhachHangDAO khachHangDAO;
    private final KhachHangJPanel khachHangJPanel;

    public KhachHangController(KhachHangJPanel khachHangJPanel) {
        this.khachHangDAO = new KhachHangDAO();
        this.khachHangJPanel = khachHangJPanel;
    }

    public List<KhachHang> getAllKhachHang() throws ClassNotFoundException {
        try {
            return khachHangDAO.getAllKhachHang();
        } catch (SQLException e) {
            return null;
        }
    }
    
    public void loadKhachHangData(KhachHangJPanel khachHangJPanel, List<KhachHang> khachHangList) throws ClassNotFoundException, SQLException {
        // Nếu danh sách sản phẩm là null, lấy tất cả sản phẩm từ cơ sở dữ liệu
        if (khachHangList == null) {
            khachHangList = khachHangDAO.getAllKhachHang();
        }

        DefaultTableModel model = (DefaultTableModel) khachHangJPanel.getTableModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

        // Thêm các sản phẩm từ danh sách vào bảng
        for (KhachHang kh : khachHangList) {
            model.addRow(new Object[]{
                kh.getMaKh(),
                kh.getTenKh(),
                kh.getNgaySinh(),
                kh.getGioiTinh(),
                kh.getDiaChi(),
                kh.getDienThoai(),
                kh.getEmail(),
                kh.getLoaiKh()
            });
        }
    }

    public void addKhachHang(KhachHang khachHang) {
        try {
            khachHangDAO.addKhachHang(khachHang);
        } catch (SQLException | ClassNotFoundException e) {
        }
    }

    public void deleteKhachHang(KhachHang khachHang) {
        try {
            khachHangDAO.deleteKhachHang(khachHang.getMaKh());
        } catch (SQLException | ClassNotFoundException e) {
        }

    }

    // Phương thức xuất CSV với hộp thoại chọn file
    public void exportToCSV() throws ClassNotFoundException {
        JTable table = khachHangJPanel.getjTableKhachHang();

        // Mở hộp thoại để chọn đường dẫn và tên file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file CSV");
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return; 
        }

        File fileToSave = fileChooser.getSelectedFile();
        String filePath = fileToSave.getAbsolutePath();

        // Tự động thêm đuôi .csv nếu người dùng không nhập
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
        JTable table = khachHangJPanel.getjTableKhachHang();

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
            fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection != JFileChooser.APPROVE_OPTION) {
                return; // Nếu người dùng hủy bỏ, không làm gì cả
            }

            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            // Tự động thêm đuôi .xlsx nếu người dùng không nhập
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
        DefaultTableModel model = (DefaultTableModel) khachHangJPanel.getjTableKhachHang().getModel();
        model.setRowCount(0); // Xóa tất cả các hàng

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
            JOptionPane.showMessageDialog(null, "No results found");
        }
    }

    public void searchByName(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = khachHangDAO.searchByName(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }

    public void searchByYearOfBirth(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = khachHangDAO.searchByYearOfBirth(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }

    public void searchByAddress(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = khachHangDAO.searchByAddress(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }

    public void updateKhachHang() throws ClassNotFoundException {
        try {
            int selectedRow = khachHangJPanel.getjTableKhachHang().getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row to update", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String maKhachHang = khachHangJPanel.getjTextMaKH().getText();
            String tenKhachHang = khachHangJPanel.getjTextTenKH().getText();
            Date ngaySinh = khachHangJPanel.getjDateNgaySinh().getDate();
            String gioiTinh = (String) khachHangJPanel.getjCbGioiTinh().getSelectedItem();
            String diaChi = khachHangJPanel.getjTextDiaChi().getText();
            String soDienThoai = khachHangJPanel.getjTextDienThoai().getText();
            String email = khachHangJPanel.getjTextEmail().getText();
            String loaiKhachHang = (String) khachHangJPanel.getjCbLoaiKH().getSelectedItem();

            KhachHang khachHang = new KhachHang(maKhachHang, tenKhachHang, ngaySinh, gioiTinh, diaChi, soDienThoai, email, loaiKhachHang);

            if (khachHangDAO.update(khachHang)) {
                JOptionPane.showMessageDialog(null, "Update successful!");
                khachHangJPanel.loadKhachHangData(); 
            } else {
                JOptionPane.showMessageDialog(null, "Update failed!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating data: " + e.getMessage());
        }
    }

    public boolean isValidEmail(String email, String currentEmail) {
        try {
            // Kiểm tra định dạng email trước
            DataValidator.checkFormatEmail(email);

            // Kiểm tra xem email có duy nhất không
            if (!khachHangDAO.isEmailUnique(email, currentEmail)) {
                JOptionPane.showMessageDialog(null, "Email already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error checking email", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }

    public String getCurrentEmail() {
        int selectedRow = khachHangJPanel.getjTableKhachHang().getSelectedRow();
        if (selectedRow != -1) {
            // Lấy email từ dòng đã chọn
            return (String) khachHangJPanel.getjTableKhachHang().getValueAt(selectedRow, 7); 
        }
        return null; 
    }

    // Kiểm tra mã khách hàng
    public boolean isMaKHExist(String maKH) throws ClassNotFoundException, SQLException {
        return khachHangDAO.isMaKHExist(maKH);
    }
    
    public void searchInAllColumns(String searchTerm) throws ClassNotFoundException {
        try {
            List<KhachHang> khachHangList = khachHangDAO.searchInAllColumns(searchTerm); 

            if (khachHangList.isEmpty()) {
                // Nếu không có kết quả, hiển thị thông báo
                JOptionPane.showMessageDialog(null, "No results found for keyword: " + searchTerm, "Notification", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Nếu có kết quả, hiển thị dữ liệu trên bảng
                loadKhachHangData(khachHangJPanel, khachHangList);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error searching across all columns", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

}
