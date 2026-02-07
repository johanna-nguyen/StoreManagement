package controller;

import com.opencsv.CSVWriter;
import dao.NhaCungCapDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.NhaCungCap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import view.NhaCungCapJPanel;

/**
 *
 * @author khanhnguyen
 */
public class NhaCungCapController {

    private final NhaCungCapDAO nhaCungCapDAO;
    private final NhaCungCapJPanel nhaCungCapJPanel;

    public NhaCungCapController(NhaCungCapJPanel nhaCungCapJPanel) {
        this.nhaCungCapDAO = new NhaCungCapDAO();
        this.nhaCungCapJPanel = nhaCungCapJPanel;
    }

    public List<NhaCungCap> getAllNhaCungCap() throws ClassNotFoundException {
        try {
            return nhaCungCapDAO.getAllNhaCungCap();
        } catch (SQLException e) {
            return null;
        }
    }

    public void addNhaCungCap(NhaCungCap nhaCungCap) {
        try {
            nhaCungCapDAO.addNhaCungCap(nhaCungCap);
        } catch (SQLException | ClassNotFoundException e) {
        }
    }

    public void deleteNhaCungCap(NhaCungCap nhaCungCap) {
        try {
            nhaCungCapDAO.deleteNhaCungCap(nhaCungCap.getMaNCC());
        } catch (SQLException | ClassNotFoundException e) {
        }

    }

    // Phương thức xuất CSV với hộp thoại chọn file
    public void exportToCSV() throws ClassNotFoundException {
        JTable table = nhaCungCapJPanel.getjTableNhaCungCap();

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
        JTable table = nhaCungCapJPanel.getjTableNhaCungCap();

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
        DefaultTableModel model = (DefaultTableModel) nhaCungCapJPanel.getjTableNhaCungCap().getModel();
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
            JOptionPane.showMessageDialog(null, "No results found");
        }
    }

    public void searchByName(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = nhaCungCapDAO.searchByName(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }

    public void searchByAddress(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = nhaCungCapDAO.searchByAddress(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }
    
    public void searchByPhone(String searchTerm) {
        try {
            // Lấy dữ liệu từ DAO
            ResultSet rs = nhaCungCapDAO.searchByPhone(searchTerm);
            // Cập nhật JTable với dữ liệu từ ResultSet
            updateTableFromResultSet(rs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage());
        }
    }

    public void updateNhaCungCap() throws ClassNotFoundException {
        try {
            int selectedRow = nhaCungCapJPanel.getjTableNhaCungCap().getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row to update", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String maNCC = nhaCungCapJPanel.getjTextMaNCC().getText();
            String tenNCC = nhaCungCapJPanel.getjTextTenNCC().getText();
            String diaChi = nhaCungCapJPanel.getjTextDiaChi().getText();
            String soDienThoai = nhaCungCapJPanel.getjTextDienThoai().getText();
            String email = nhaCungCapJPanel.getjTextEmail().getText();
            String website = nhaCungCapJPanel.getjTextWebsite().getText();
            String ghiChu = nhaCungCapJPanel.getjTextGhiChu().getText();

            NhaCungCap nhaCungCap = new NhaCungCap(maNCC, tenNCC, diaChi, soDienThoai, email, website, ghiChu);

            if (nhaCungCapDAO.update(nhaCungCap)) {
                JOptionPane.showMessageDialog(null, "Update successful!");
                nhaCungCapJPanel.loadNhaCungCapData();
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
            if (!email.matches("^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,4}$")) {
                JOptionPane.showMessageDialog(null, "Invalid email format", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Kiểm tra xem email có duy nhất không
            if (!nhaCungCapDAO.isEmailUnique(email, currentEmail)) {
                JOptionPane.showMessageDialog(null, "Email already exists", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error checking email", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }

    public String getCurrentEmail() {
        int selectedRow = nhaCungCapJPanel.getjTableNhaCungCap().getSelectedRow();
        if (selectedRow != -1) {
            // Lấy email từ dòng đã chọn
            return (String) nhaCungCapJPanel.getjTableNhaCungCap().getValueAt(selectedRow, 5);
        }
        return null; 
    }

    // Kiểm tra mã NCC
    public boolean isMaNCCExist(String maNCC) throws ClassNotFoundException, SQLException {
        return nhaCungCapDAO.isMaNCCExist(maNCC);
    }
    
    
    public void loadNhaCungCapData(NhaCungCapJPanel nhaCCJPanel, List<NhaCungCap> nhaCCList) throws ClassNotFoundException, SQLException {
        // Nếu danh sách sản phẩm là null, lấy tất cả sản phẩm từ cơ sở dữ liệu
        if (nhaCCList == null) {
            nhaCCList = nhaCungCapDAO.getAllNhaCungCap();
        }

        DefaultTableModel model = (DefaultTableModel) nhaCCJPanel.getTableModel();
        model.setRowCount(0);

        // Thêm các sản phẩm từ danh sách vào bảng
        for (NhaCungCap ncc : nhaCCList) {
            model.addRow(new Object[]{
                ncc.getMaNCC(),
                ncc.getTenNCC(),
                ncc.getDiaChi(),
                ncc.getDienThoai(),
                ncc.getEmail(),
                ncc.getWebsite(),
                ncc.getGhiChu()
            });
        }
    }
    
    public void searchInAllColumns(String searchTerm) throws ClassNotFoundException {
        try {
            List<NhaCungCap> nhaCCList = nhaCungCapDAO.searchInAllColumns(searchTerm); 

            if (nhaCCList.isEmpty()) {
                // Nếu không có kết quả, hiển thị thông báo
                JOptionPane.showMessageDialog(null, "No results found for keyword: " + searchTerm, "Notification", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Nếu có kết quả, hiển thị dữ liệu trên bảng
                loadNhaCungCapData(nhaCungCapJPanel, nhaCCList);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error searching across all columns", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
