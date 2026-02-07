package controller;

import com.opencsv.CSVWriter;
import dao.NhanVienDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.NhanVien;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import view.NhanVienJPanel;

/**
 *
 * @author khanhnguyen
 */
public class NhanVienController {

    private final NhanVienDAO nhanVienDAO;
    private final NhanVienJPanel nhanVienJPanel;

    public NhanVienController(NhanVienJPanel nhanVienJPanel) {
        this.nhanVienDAO = new NhanVienDAO();
        this.nhanVienJPanel = nhanVienJPanel;
    }

    public List<NhanVien> getAllNhanVien() throws ClassNotFoundException, SQLException {
        return nhanVienDAO.getAllNhanVien();
    }

    public void addNhanVien(NhanVien nhanVien) throws SQLException, ClassNotFoundException {
        nhanVienDAO.addNhanVien(nhanVien);
    }

    public void deleteNhanVien(NhanVien nhanVien) throws SQLException, ClassNotFoundException {
        nhanVienDAO.deleteNhanVien(nhanVien.getMaNV());
    }

    // Phương thức xuất CSV với hộp thoại chọn file
    public void exportToCSV() throws ClassNotFoundException {
        JTable table = nhanVienJPanel.getjTableNhanVien();

        // Mở hộp thoại để chọn đường dẫn và tên file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file CSV");
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
        JTable table = nhanVienJPanel.getjTableNhanVien();

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

            JOptionPane.showMessageDialog(null, "Xuất file Excel thành công!");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error exporting Excel file: " + e.getMessage());
        }
    }

    public void updateTableFromResultSet(ResultSet rs) throws SQLException {
        // Xóa dữ liệu hiện tại trong JTable
        DefaultTableModel model = (DefaultTableModel) nhanVienJPanel.getTableModel();
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
            JOptionPane.showMessageDialog(null, "No results found", "Notification", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void loadNhanVienData(NhanVienJPanel nhanVienJPanel, List<NhanVien> nhanVienList) throws ClassNotFoundException, SQLException {
        // Nếu danh sách nv là null, lấy tất cả sản phẩm từ cơ sở dữ liệu
        if (nhanVienList == null) {
            nhanVienList = nhanVienDAO.getAllNhanVien();
        }

        DefaultTableModel model = (DefaultTableModel) nhanVienJPanel.getTableModel();
        model.setRowCount(0); 

        // Thêm các sản phẩm từ danh sách vào bảng
        for (NhanVien nv : nhanVienList) {
            model.addRow(new Object[]{
                nv.getMaNV(),
                nv.getTenNV(),
                nv.getNgaySinh(),
                nv.getGioiTinh(),
                nv.getDiaChi(),
                nv.getDienThoai(),
                nv.getEmail(),
                nv.getDoanhSo(),
                nv.getHinhAnhNV()
            });
        }
    }

    public void searchInAllColumns(String searchTerm) throws ClassNotFoundException {
        try {
            List<NhanVien> nhanVienList = nhanVienDAO.searchInAllColumns(searchTerm); 

            if (nhanVienList.isEmpty()) {
                // Nếu không có kết quả, hiển thị thông báo
                JOptionPane.showMessageDialog(null, "No results found for keyword: " + searchTerm, "Notification", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Nếu có kết quả, hiển thị dữ liệu trên bảng
                loadNhanVienData(nhanVienJPanel, nhanVienList);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error searching across all columns", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void searchByName(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = nhanVienDAO.searchByName(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }

    public void searchByAdress(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = nhanVienDAO.searchByAdress(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }

    public void searchByPhone(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = nhanVienDAO.searchByAdress(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }

    public void searchByDoanhSo(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = nhanVienDAO.searchByDoanhSo(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }

    }

    public void updateNhanVien() throws ClassNotFoundException {
        try {
            int selectedRow = nhanVienJPanel.getjTableNhanVien().getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row to update", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String maNV = nhanVienJPanel.getjTextMaNV().getText();
            String tenNV = nhanVienJPanel.getjTextTenNV().getText();
            Date ngaySinh = nhanVienJPanel.getjDateNgaySinh().getDate();
            String gioiTinh = (String) nhanVienJPanel.getjCbGioiTinh().getSelectedItem();
            String diaChi = nhanVienJPanel.getjTextDiaChi().getText();
            String dienThoai = nhanVienJPanel.getjTextDienThoai().getText();
            String email = nhanVienJPanel.getjTextEmail().getText();

            BigDecimal doanhSo;
            doanhSo = new BigDecimal(nhanVienJPanel.getjTextDoanhSo().getText());

            String hinhAnhNV = null;
            try {
                hinhAnhNV = getHinhAnhByMaNV(maNV);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            NhanVien nhanVien = new NhanVien(maNV, tenNV, ngaySinh, gioiTinh, diaChi, dienThoai, email, doanhSo, hinhAnhNV);

            if (nhanVienDAO.update(nhanVien)) {
                JOptionPane.showMessageDialog(null, "Update successful!");
                nhanVienJPanel.loadNhanVienData(); 
            } else {
                JOptionPane.showMessageDialog(null, "Update failed!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating data: " + e.getMessage());
        }
    }

    // Kiểm tra mã nv
    public boolean isMaNVExist(String maNV, String currentMaNV) throws ClassNotFoundException, SQLException {
        return nhanVienDAO.isMaNVExist(maNV, currentMaNV);
    }

    // Filter giá sp
    public void filterByDoanhSo(String minPriceInput, String maxPriceInput) throws SQLException {
        try {
            BigDecimal minPrice = new BigDecimal(minPriceInput);
            BigDecimal maxPrice = new BigDecimal(maxPriceInput);
            // Lấy dữ liệu từ DAO
            ResultSet rs = nhanVienDAO.filterByDoanhSo(minPrice, maxPrice);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (NumberFormatException e) {
            // Xử lý lỗi khi người dùng nhập không phải số
            JOptionPane.showMessageDialog(null, "Please enter numbers only", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Mã nv hiện tại
    public String getCurrentMaNV() {
        int selectedRow = nhanVienJPanel.getjTableNhanVien().getSelectedRow();
        if (selectedRow != -1) {
            // Lấy MANV từ dòng đã chọn
            return (String) nhanVienJPanel.getjTableNhanVien().getValueAt(selectedRow, 0);
        }
        return null; 
    }

    // Hình ảnh nv
    public String getHinhAnhByMaNV(String maNV) throws SQLException, ClassNotFoundException {
        // Khởi tạo đường dẫn hình ảnh là null
        String hinhAnh = null;

        // Gọi phương thức từ sanPhamDAO để lấy đường dẫn hình ảnh
        try {
            hinhAnh = nhanVienDAO.getHinhAnhByMaNV(maNV);
        } catch (SQLException e) {
            // Xử lý ngoại lệ nếu có lỗi trong việc truy vấn
            throw new SQLException("Error retrieving image: " + e.getMessage());
        }

        return hinhAnh;
    }

    // Cập nhật hình ảnh sp theo mã nv
    public void updateHinhAnhNV(String maNV, String filePath) throws SQLException, ClassNotFoundException {
        nhanVienDAO.updateHinhAnhNV(maNV, filePath);
    }

     public void updateDoanhSoForNhanVien(String maNV) throws SQLException, ClassNotFoundException {
        nhanVienDAO.updateDoanhSoForNhanVien(maNV);
    }
    
    public double getDoanhSoForNhanVien(String maNV) throws SQLException, ClassNotFoundException {
        return nhanVienDAO.calculateTotalDoanhSo(maNV);
    }

}
