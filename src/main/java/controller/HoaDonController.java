package controller;

import com.opencsv.CSVWriter;
import dao.HoaDonDAO;
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
import model.HoaDon;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import view.HoaDonJPanel;

/**
 *
 * @author khanhnguyen
 */
public class HoaDonController {

    private final HoaDonDAO hoaDonDAO;
    private final HoaDonJPanel hoaDonJPanel;

    public HoaDonController(HoaDonJPanel hoaDonJPanel) {
        this.hoaDonDAO = new HoaDonDAO();
        this.hoaDonJPanel = hoaDonJPanel;
    }

    public List<HoaDon> getAllHoaDon() throws ClassNotFoundException {
        try {
            return hoaDonDAO.getAllHoaDon();
        } catch (SQLException e) {
            return null;
        }
    }

    public void addHoaDon(HoaDon hoaDon) throws SQLException, ClassNotFoundException {
        hoaDonDAO.addHoaDon(hoaDon);
    }

    public void deleteHoaDon(HoaDon hoaDon) throws SQLException, ClassNotFoundException {
        hoaDonDAO.deleteHoaDon(hoaDon.getMaHD());

    }

    // Phương thức xuất CSV với hộp thoại chọn file
    public void exportToCSV() throws ClassNotFoundException {
        JTable table = hoaDonJPanel.getjTableHoaDon();

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

    // Xuất Excel
    public void exportToExcel() {
        JTable table = hoaDonJPanel.getjTableHoaDon();

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
        DefaultTableModel model = (DefaultTableModel) hoaDonJPanel.getjTableHoaDon().getModel();
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

    public void loadHoaDonData(HoaDonJPanel hoaDonJPanel, List<HoaDon> hoaDonList) throws ClassNotFoundException, SQLException {
        // Nếu danh sách sản phẩm là null, lấy tất cả sản phẩm từ cơ sở dữ liệu
        if (hoaDonList == null) {
            hoaDonList = hoaDonDAO.getAllHoaDon();
        }

        DefaultTableModel model = (DefaultTableModel) hoaDonJPanel.getTableModel();
        model.setRowCount(0); 

        // Thêm vào bảng
        for (HoaDon hd : hoaDonList) {
            model.addRow(new Object[]{
                hd.getMaHD(),
                hd.getNgayHD(),
                hd.getMaKH(),
                hd.getMaNV(),
                hd.getTriGia()
            });
        }
    }

    public void searchInAllColumns(String searchTerm) throws ClassNotFoundException {
        try {
            List<HoaDon> hoaDonList = hoaDonDAO.searchInAllColumns(searchTerm); 

            if (hoaDonList.isEmpty()) {
                // Nếu không có kết quả, hiển thị thông báo
                JOptionPane.showMessageDialog(null, "No results found for keyword: " + searchTerm, "Error", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Nếu có kết quả, hiển thị dữ liệu trên bảng
                loadHoaDonData(hoaDonJPanel, hoaDonList);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error searching across all columns", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void searchByMaHD(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = hoaDonDAO.searchByMaHD(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }

    public void searchByMaKH(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = hoaDonDAO.searchByMaKH(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }

    public void searchByMaNV(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = hoaDonDAO.searchByMaNV(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }

    public void searchByTriGia(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = hoaDonDAO.searchByTriGia(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }

    public void updateHoaDon() throws ClassNotFoundException {
        try {
            int selectedRow = hoaDonJPanel.getjTableHoaDon().getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row to update", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String maHD = hoaDonJPanel.getjTextMaHD().getText();
            Date ngayHD = hoaDonJPanel.getjDateNgayHD().getDate();
            String maKH = (String) hoaDonJPanel.getjCbMaKH().getSelectedItem();
            String maNV = (String) hoaDonJPanel.getjCbMaKH().getSelectedItem();

            BigDecimal triGia;
            triGia = new BigDecimal(hoaDonJPanel.getjTextTriGia().getText());

            HoaDon hoaDon = new HoaDon(maHD, ngayHD, maKH, maNV, triGia);

            if (hoaDonDAO.update(hoaDon)) {
                JOptionPane.showMessageDialog(null, "Update successful!");
                hoaDonJPanel.loadHoaDonData(); 
            } else {
                JOptionPane.showMessageDialog(null, "Update failed!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating data: " + e.getMessage());
        }
    }

    // Kiểm tra mã HD
    public boolean isMaHDExist(String maHD, String currentMaHD) throws ClassNotFoundException, SQLException {
        return hoaDonDAO.isMaHDExist(maHD, currentMaHD);
    }

    // Mã HD hiện tại
    public String getCurrentMaHD() {
        int selectedRow = hoaDonJPanel.getjTableHoaDon().getSelectedRow();
        if (selectedRow != -1) {
            // Lấy MAHD từ dòng đã chọn
            return (String) hoaDonJPanel.getjTableHoaDon().getValueAt(selectedRow, 0);
        }
        return null;
    }

    // Lấy ds mã KH
    public void loadMaKHToComboBox() {
        try {
            // Gọi DAO để lấy danh sách Mã NCCreload
            List<String> maKHList = hoaDonDAO.getAllMaKH();

            // Xoá khi reload
            hoaDonJPanel.getjCbMaKH().removeAllItems();

            // Thêm từng Mã NCC vào JComboBox
            for (String maKH : maKHList) {
                hoaDonJPanel.getjCbMaKH().addItem(maKH);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error loading invoice list", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Lấy ds mã NV
    public void loadMaNVToComboBox() {
        try {
            // Gọi DAO để lấy danh sách Mã NCCreload
            List<String> maNVList = hoaDonDAO.getAllMaNV();

            // Xoá khi reload
            hoaDonJPanel.getjCbMaNV().removeAllItems();

            // Thêm từng Mã NCC vào JComboBox
            for (String maNV : maNVList) {
                hoaDonJPanel.getjCbMaNV().addItem(maNV);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error loading employee list", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Filter giá sp
    public void filterByPriceRange(String minPriceInput, String maxPriceInput) throws SQLException {
        try {
            BigDecimal minPrice = new BigDecimal(minPriceInput);
            BigDecimal maxPrice = new BigDecimal(maxPriceInput);
            // Lấy dữ liệu từ DAO
            ResultSet rs = hoaDonDAO.filterByPriceRange(minPrice, maxPrice);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (NumberFormatException e) {
            // Xử lý lỗi khi người dùng nhập không phải số
            JOptionPane.showMessageDialog(null, "Please enter numbers only", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
