package controller;

import com.opencsv.CSVWriter;
import dao.PhieuNhapDAO;
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
import model.PhieuNhap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import view.PhieuNhapJPanel;

/**
 *
 * @author khanhnguyen
 */
public class PhieuNhapController {

    private final PhieuNhapDAO phieuNhapDAO;
    private final PhieuNhapJPanel phieuNhapJPanel;

    public PhieuNhapController(PhieuNhapJPanel phieuNhapJPanel) {
        this.phieuNhapDAO = new PhieuNhapDAO();
        this.phieuNhapJPanel = phieuNhapJPanel;
    }

    public List<PhieuNhap> getAllPhieuNhap() throws ClassNotFoundException {
        try {
            return phieuNhapDAO.getAllPhieuNhap();
        } catch (SQLException e) {
            return null;
        }
    }

    public void addPhieuNhap(PhieuNhap phieuNhap) throws SQLException, ClassNotFoundException {
        phieuNhapDAO.addPhieuNhap(phieuNhap);
    }

    public void deletePhieuNhap(PhieuNhap phieuNhap) throws SQLException, ClassNotFoundException {
        phieuNhapDAO.deletePhieuNhap(phieuNhap.getMaNhap());
    }

    // Phương thức xuất CSV với hộp thoại chọn file
    public void exportToCSV() throws ClassNotFoundException {
        JTable table = phieuNhapJPanel.getjTablePhieuNhap();

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
        JTable table = phieuNhapJPanel.getjTablePhieuNhap();

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
        DefaultTableModel model = (DefaultTableModel) phieuNhapJPanel.getTableModel();
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

    public void loadPhieuNhapData(PhieuNhapJPanel phieuNhapJPanel, List<PhieuNhap> phieuNhapList) throws ClassNotFoundException, SQLException {
        // Nếu danh sách phiếu nhập là null, lấy tất cả phiếu nhập từ cơ sở dữ liệu
        if (phieuNhapList == null) {
            phieuNhapList = phieuNhapDAO.getAllPhieuNhap();
        }

        DefaultTableModel model = (DefaultTableModel) phieuNhapJPanel.getTableModel();
        model.setRowCount(0); 

        // Thêm các phiếu nhập từ danh sách vào bảng
        for (PhieuNhap phieuNhap : phieuNhapList) {
            model.addRow(new Object[]{
                phieuNhap.getMaNhap(),
                phieuNhap.getMaSP(),
                phieuNhap.getSoLuong(),
                phieuNhap.getGiaNhap(),
                phieuNhap.getNgayNhap(),
                phieuNhap.getMaNCC()
            });
        }
    }

    public void searchInAllColumns(String searchTerm) throws ClassNotFoundException {
        try {
            List<PhieuNhap> phieuNhapList = phieuNhapDAO.searchInAllColumns(searchTerm);

            if (phieuNhapList.isEmpty()) {
                // Nếu không có kết quả, hiển thị thông báo
                JOptionPane.showMessageDialog(null, "No results found for keyword: " + searchTerm, "Notification", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Nếu có kết quả, hiển thị dữ liệu trên bảng
                loadPhieuNhapData(phieuNhapJPanel, phieuNhapList);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error searching across all columns", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void searchByMaNhap(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = phieuNhapDAO.searchByMaNhap(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }

    public void updatePhieuNhap() throws ClassNotFoundException {
        try {
            int selectedRow = phieuNhapJPanel.getjTablePhieuNhap().getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row to update", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String maNhap = phieuNhapJPanel.getjTextMaNhap().getText();
            String maSP = (String) phieuNhapJPanel.getjCbMaSPNhap().getSelectedItem();
            int soLuong;
            soLuong = Integer.parseInt(phieuNhapJPanel.getjTextSoLuongNhap().getText());

            BigDecimal giaNhap;
            giaNhap = new BigDecimal(phieuNhapJPanel.getjTextGiaNhap().getText());

            Date ngayNhap = phieuNhapJPanel.getjDateNgayNhap().getDate();

            String maNCC = (String) phieuNhapJPanel.getjCbMaNCC().getSelectedItem();

            PhieuNhap phieuNhap = new PhieuNhap(maNhap, maSP, soLuong, giaNhap, ngayNhap,maNCC);

            if (phieuNhapDAO.update(phieuNhap)) {
                JOptionPane.showMessageDialog(null, "Update successful!");
                phieuNhapJPanel.loadPhieuNhapData();
            } else {
                JOptionPane.showMessageDialog(null, "Update failed!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating data: " + e.getMessage());
        }
    }

    // Kiểm tra mã nhập
    public boolean isMaNhapExist(String maNhap, String currentMaNhap) throws ClassNotFoundException, SQLException {
        return phieuNhapDAO.isMaNhapExist(maNhap, currentMaNhap);
    }

    // Filter giá sp
    public void filterByPriceRange(String minPriceInput, String maxPriceInput) throws SQLException {
        try {
            BigDecimal minPrice = new BigDecimal(minPriceInput);
            BigDecimal maxPrice = new BigDecimal(maxPriceInput);
            // Lấy dữ liệu từ DAO
            ResultSet rs = phieuNhapDAO.filterByPriceRange(minPrice, maxPrice);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (NumberFormatException e) {
            // Xử lý lỗi khi người dùng nhập không phải số
            JOptionPane.showMessageDialog(null, "Please enter numbers only", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Mã mã nhập hiện tại
    public String getCurrentMaNhap() {
        int selectedRow = phieuNhapJPanel.getjTablePhieuNhap().getSelectedRow();
        if (selectedRow != -1) {
            // Lấy MaNhap từ dòng đã chọn
            return (String) phieuNhapJPanel.getjTablePhieuNhap().getValueAt(selectedRow, 0);
        }
        return null; 
    }

    // Lấy ds mã SP
    public void loadMaSPToComboBox() {
        try {
            // Gọi DAO để lấy danh sách Mã SP
            List<String> maSPList = phieuNhapDAO.getAllMaSP();

            // Xoá khi reload
            phieuNhapJPanel.getjCbMaSPNhap().removeAllItems();

            // Thêm từng Mã SP vào JComboBox
            for (String maSP : maSPList) {
                phieuNhapJPanel.getjCbMaSPNhap().addItem(maSP);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error loading product ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Lấy ds mã NCC
    public void loadMaNCCToComboBox() {
        try {
            // Gọi DAO để lấy danh sách Mã NCC
            List<String> maNCCList = phieuNhapDAO.getAllMaNCC();

            // Xoá khi reload
            phieuNhapJPanel.getjCbMaNCC().removeAllItems();

            // Thêm từng Mã NCC vào JComboBox
            for (String maNCC : maNCCList) {
                phieuNhapJPanel.getjCbMaNCC().addItem(maNCC);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error loading supplier ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
