package controller;

import com.opencsv.CSVWriter;
import dao.SanPhamDAO;
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
import model.SanPham;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import view.SanPhamJPanel;

/**
 *
 * @author khanhnguyen
 */
public class SanPhamController {

    private final SanPhamDAO sanPhamDAO;
    private final SanPhamJPanel sanPhamJPanel;

    public SanPhamController(SanPhamJPanel sanPhamJPanel) {
        this.sanPhamDAO = new SanPhamDAO();
        this.sanPhamJPanel = sanPhamJPanel;
    }

    public List<SanPham> getAllSanPham() throws ClassNotFoundException {
        try {
            return sanPhamDAO.getAllSanPham();
        } catch (SQLException e) {
            return null;
        }
    }

    public void addSanPham(SanPham sanPham) throws SQLException, ClassNotFoundException {
        sanPhamDAO.addSanPham(sanPham);
    }

    public void deleteSanPham(SanPham sanPham) throws SQLException, ClassNotFoundException {
        sanPhamDAO.deleteSanPham(sanPham.getMaSP());
    }

    // Phương thức xuất CSV với hộp thoại chọn file
    public void exportToCSV() throws ClassNotFoundException {
        JTable table = sanPhamJPanel.getjTableSanPham();

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
        JTable table = sanPhamJPanel.getjTableSanPham();

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
        DefaultTableModel model = (DefaultTableModel) sanPhamJPanel.getTableModel();
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

    public void loadSanPhamData(SanPhamJPanel sanPhamJPanel, List<SanPham> sanPhamList) throws ClassNotFoundException, SQLException {
        // Nếu danh sách sản phẩm là null, lấy tất cả sản phẩm từ cơ sở dữ liệu
        if (sanPhamList == null) {
            sanPhamList = sanPhamDAO.getAllSanPham();
        }

        DefaultTableModel model = (DefaultTableModel) sanPhamJPanel.getTableModel();
        model.setRowCount(0); 

        // Thêm các sản phẩm từ danh sách vào bảng
        for (SanPham sp : sanPhamList) {
            model.addRow(new Object[]{
                sp.getMaSP(),
                sp.getTenSP(),
                sp.getDvt(),
                sp.getNuocSX(),
                sp.getGiaBan(),
                sp.getSoLuongTon(),
                sp.getMaNCC(),
                sp.getNgayHetHan(),
                sp.getHinhAnhSP()
            });
        }
    }

    public void searchInAllColumns(String searchTerm) throws ClassNotFoundException {
        try {
            List<SanPham> sanPhamList = sanPhamDAO.searchInAllColumns(searchTerm); 

            if (sanPhamList.isEmpty()) {
                // Nếu không có kết quả, hiển thị thông báo
                JOptionPane.showMessageDialog(null, "No results found for keyword: " + searchTerm, "Notification", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Nếu có kết quả, hiển thị dữ liệu trên bảng
                loadSanPhamData(sanPhamJPanel, sanPhamList);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error searching across all columns", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void searchByName(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = sanPhamDAO.searchByName(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }

    public void searchByNuocSX(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = sanPhamDAO.searchByNuocSX(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }

    public void searchByGiaBan(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = sanPhamDAO.searchByGiaBan(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }

    }

    public void updateSanPham() throws ClassNotFoundException {
        try {
            int selectedRow = sanPhamJPanel.getjTableSanPham().getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row to update", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String maSP = sanPhamJPanel.getjTextMaSP().getText();
            String tenSP = sanPhamJPanel.getjTextTenSP().getText();
            String dvt = (String) sanPhamJPanel.getjCbDVT().getSelectedItem();
            String nuocSX = (String) sanPhamJPanel.getjCbNuocSX().getSelectedItem();

            BigDecimal giaBan;
            giaBan = new BigDecimal(sanPhamJPanel.getjTextGiaBan().getText());

            int soLuongTon;
            soLuongTon = Integer.parseInt(sanPhamJPanel.getjTextSoLuongTon().getText());

            String maNCC = (String) sanPhamJPanel.getjCbMaNCC().getSelectedItem();
            Date ngayHetHan = sanPhamJPanel.getjDateNgayCapNhat().getDate();

            String hinhAnhSP = null;
            try {
                hinhAnhSP = getHinhAnhByMaSP(maSP);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SanPhamJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            SanPham sanPham = new SanPham(maSP, tenSP, dvt, nuocSX, giaBan, soLuongTon, maNCC, ngayHetHan, hinhAnhSP);

            if (sanPhamDAO.update(sanPham)) {
                JOptionPane.showMessageDialog(null, "Update successful!");
                sanPhamJPanel.loadSanPhamData(); 
            } else {
                JOptionPane.showMessageDialog(null, "Update failed!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating data: " + e.getMessage());
        }
    }

    // Kiểm tra mã sp
    public boolean isMaSPExist(String maSP, String currentMaSP) throws ClassNotFoundException, SQLException {
        return sanPhamDAO.isMaSPExist(maSP, currentMaSP);
    }

    // Filter giá sp
    public void filterByPriceRange(String minPriceInput, String maxPriceInput) throws SQLException {
        try {
            BigDecimal minPrice = new BigDecimal(minPriceInput);
            BigDecimal maxPrice = new BigDecimal(maxPriceInput);
            // Lấy dữ liệu từ DAO
            ResultSet rs = sanPhamDAO.filterByPriceRange(minPrice, maxPrice);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (NumberFormatException e) {
            // Xử lý lỗi khi người dùng nhập không phải số
            JOptionPane.showMessageDialog(null, "Please enter numbers only", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Mã sp hiện tại
    public String getCurrentMaSP() {
        int selectedRow = sanPhamJPanel.getjTableSanPham().getSelectedRow();
        if (selectedRow != -1) {
            // Lấy MASP từ dòng đã chọn
            return (String) sanPhamJPanel.getjTableSanPham().getValueAt(selectedRow, 0);
        }
        return null; 
    }

    // Hình ảnh sp
    public String getHinhAnhByMaSP(String maSP) throws SQLException, ClassNotFoundException {
        // Khởi tạo đường dẫn hình ảnh là null
        String hinhAnh = null;

        // Gọi phương thức từ sanPhamDAO để lấy đường dẫn hình ảnh
        try {
            hinhAnh = sanPhamDAO.getHinhAnhByMaSP(maSP);
        } catch (SQLException e) {
            // Xử lý ngoại lệ nếu có lỗi trong việc truy vấn
            throw new SQLException("Error retrieving image: " + e.getMessage());
        }

        return hinhAnh;
    }

    // Cập nhật hình ảnh sp theo mã sp
    public void updateHinhAnhSP(String maSP, String filePath) throws SQLException, ClassNotFoundException {
        sanPhamDAO.updateHinhAnhSP(maSP, filePath);
    }

    // Lấy ds mã nhà cung cấp
    public void loadMaNCCToComboBox() {
        try {
            // Gọi DAO để lấy danh sách Mã NCC
            List<String> maNCCList = sanPhamDAO.getAllMaNCC();

            // Xoá khi 
            sanPhamJPanel.getjCbMaNCC().removeAllItems();

            // Thêm từng Mã NCC vào JComboBox
            for (String maNCC : maNCCList) {
                sanPhamJPanel.getjCbMaNCC().addItem(maNCC);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error loading supplier list", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
