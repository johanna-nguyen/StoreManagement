package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import db.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.NhanVien;

/**
 *
 * @author khanhnguyen
 */
/**
 * Data Access Object for customer information.
 */
public class NhanVienDAO {

    private DatabaseConnection dbConnection;

    public NhanVienDAO() {
        dbConnection = new DatabaseConnection();
    }

    /**
     * Retrieves all customers from the database.
     */
    public List<NhanVien> getAllNhanVien() throws SQLException, ClassNotFoundException {
        List<NhanVien> nhanVienList = new ArrayList<>();
        String query = "SELECT * FROM NHANVIEN";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getString("MaNV"),
                        rs.getString("TenNV"),
                        rs.getDate("NgaySinh"),
                        rs.getString("GioiTinh"),
                        rs.getString("DiaChi"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getBigDecimal("DoanhSo"),
                        rs.getString("HinhAnhNV")
                );
                nhanVienList.add(nv);
            }
            rs.close();
        } finally {
            dbConnection.close();
        }
        return nhanVienList;
    }

    public void addNhanVien(NhanVien nhanVien) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO NHANVIEN (MaNV, TenNV, NgaySinh, GioiTinh, DiaChi, DienThoai, Email, DoanhSo, HinhAnhNV) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, nhanVien.getMaNV());
            pstmt.setString(2, nhanVien.getTenNV());
            pstmt.setDate(3, new java.sql.Date(nhanVien.getNgaySinh().getTime()));
            pstmt.setString(4, nhanVien.getGioiTinh());
            pstmt.setString(5, nhanVien.getDiaChi());
            pstmt.setString(6, nhanVien.getDienThoai());
            pstmt.setString(7, nhanVien.getEmail());
            pstmt.setBigDecimal(8, nhanVien.getDoanhSo());
            pstmt.setString(9, nhanVien.getHinhAnhNV());

            pstmt.executeUpdate();
        } finally {
            dbConnection.close();
        }
    }

    public boolean deleteNhanVien(String maNV) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM NHANVIEN WHERE MaNV = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, maNV);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } finally {
            dbConnection.close();
        }
    }

    // Tìm kiếm tất cả
    public List<NhanVien> searchInAllColumns(String searchTerm) throws SQLException, ClassNotFoundException {
        List<NhanVien> nhanVienList = new ArrayList<>();
        String query = "SELECT * FROM NHANVIEN WHERE "
                + "MaNV LIKE ? OR TenNV LIKE ? OR NgaySinh LIKE ? OR GioiTinh LIKE ? OR DiaChi LIKE ? OR DienThoai LIKE ? OR Email LIKE ? OR DoanhSo LIKE ?";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            String formattedTerm = "%" + searchTerm + "%";  // Định dạng từ khóa cho tìm kiếm
            pstmt.setString(1, formattedTerm);
            pstmt.setString(2, formattedTerm);
            pstmt.setString(3, formattedTerm);
            pstmt.setString(4, formattedTerm);
            pstmt.setString(5, formattedTerm);
            pstmt.setString(6, formattedTerm);
            pstmt.setString(7, formattedTerm);
            pstmt.setString(8, formattedTerm);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Tạo đối tượng SP từ kết quả truy vấn và thêm vào danh sách
                NhanVien nhanVien = new NhanVien(
                        rs.getString("MaNV"),
                        rs.getString("TenNV"),
                        rs.getDate("NgaySinh"),
                        rs.getString("GioiTinh"),
                        rs.getString("DiaChi"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getBigDecimal("DoanhSo"),
                        rs.getString("HinhAnhNV")
                );
                nhanVienList.add(nhanVien);
            }
        } catch (SQLException e) {
        } finally {
            dbConnection.close();
        }
        return nhanVienList;
    }

    public ResultSet search(String column, String searchTerm) throws SQLException {
        String query = "SELECT * FROM NHANVIEN WHERE " + column + " LIKE ?";
        ResultSet rs = null;
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, "%" + searchTerm + "%"); // Tìm kiếm gần đúng
            rs = pstmt.executeQuery();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public ResultSet searchByName(String searchTerm) throws SQLException {
        return search("TenNV", searchTerm);
    }

    public ResultSet searchByAdress(String searchTerm) throws SQLException {
        return search("DiaChi", searchTerm);
    }

    public ResultSet searchByPhone(String searchTerm) throws SQLException {
        return search("DienThoai", searchTerm);
    }

    public ResultSet searchByDoanhSo(String searchTerm) throws SQLException {
        String query = "SELECT * FROM NHANVIEN WHERE DoanhSo = ?";
        ResultSet rs = null;
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);

            // Chuyển đổi chuỗi giá trị thành kiểu Double để tương thích với MONEY
            pstmt.setDouble(1, Double.parseDouble(searchTerm));

            rs = pstmt.executeQuery();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            // Xử lý ngoại lệ nếu việc chuyển đổi không thành công
            System.err.println("Giá trị tìm kiếm không phải là số hợp lệ.");
        }
        return rs;
    }

    public ResultSet filterByDoanhSo(BigDecimal minPrice, BigDecimal maxPrice) throws SQLException {
        String query = "SELECT * FROM NHANVIEN WHERE DoanhSo BETWEEN ? AND ?";
        ResultSet rs = null;
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setBigDecimal(1, minPrice);
            pstmt.setBigDecimal(2, maxPrice);
            rs = pstmt.executeQuery();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    // sửa sp
    public boolean update(NhanVien nhanVien) throws SQLException, ClassNotFoundException {
        String updateQuery = "UPDATE NHANVIEN SET TenNV = ?, NgaySinh = ?, GioiTinh = ?, DiaChi = ?, DienThoai = ?, Email = ?, DoanhSo = ?, HinhAnhNV = ? WHERE MaNV = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(updateQuery);
            pstmt.setString(1, nhanVien.getTenNV());
            pstmt.setDate(2, new java.sql.Date(nhanVien.getNgaySinh().getTime()));
            pstmt.setString(3, nhanVien.getGioiTinh());
            pstmt.setString(4, nhanVien.getDiaChi());
            pstmt.setString(5, nhanVien.getDienThoai());
            pstmt.setString(6, nhanVien.getEmail());
            pstmt.setBigDecimal(7, nhanVien.getDoanhSo());
            pstmt.setString(8, nhanVien.getHinhAnhNV());
            pstmt.setString(9, nhanVien.getMaNV());

            return pstmt.executeUpdate() > 0;
        } finally {
            dbConnection.close();
        }
    }

    // Kiểm tra mã NV đã tồn tại hay chưa
    public boolean isMaNVExist(String maNV, String currentMaNV) throws ClassNotFoundException, SQLException {
        // Kiểm tra xem MASP mới có trùng với MASP hiện tại không
        if (maNV.equals(currentMaNV)) {
            return true; // Nếu trùng, MANV là duy nhất
        }
        String query = "SELECT COUNT(*) FROM NHANVIEN WHERE MaNV = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, maNV);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu số lượng > 0, mã khách hàng đã tồn tại
            }
        } finally {
            dbConnection.close();
        }
        return false; // Mã khách hàng không tồn tại
    }

    // lấy hình ảnh sp
    public String getHinhAnhByMaNV(String maNV) throws SQLException, ClassNotFoundException {
        String query = "SELECT HinhAnhNV FROM NHANVIEN WHERE MaNV = ?";
        String hinhAnhNV = null;

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, maNV);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                hinhAnhNV = rs.getString("HinhAnhNV"); // Lấy đường dẫn ảnh từ cột HinhAnhNV
            }

            rs.close();
        } finally {
            dbConnection.close();
        }
        return hinhAnhNV;
    }

    // cập nhật hình ảnh sp theo mã sp
    public void updateHinhAnhNV(String maNV, String filePath) throws SQLException, ClassNotFoundException {
        String updateQuery = "UPDATE NHANVIEN SET HinhAnhNV = ? WHERE MaNV = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(updateQuery);
            pstmt.setString(1, filePath);
            pstmt.setString(2, maNV);
            pstmt.executeUpdate();
        } finally {
            dbConnection.close();
        }
    }

    // Tính tổng trị giá hoá đơn của 1 NV
    public double calculateTotalDoanhSo(String maNV) throws SQLException, ClassNotFoundException {
        String query = "SELECT COALESCE(SUM(TriGia), 0) AS TotalDoanhSo FROM HOADON WHERE MaNV = ?";
        double totalDoanhSo = 0;

        try {
            dbConnection.open(); 
            try (PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query)) {
                pstmt.setString(1, maNV);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    totalDoanhSo = rs.getDouble("TotalDoanhSo");
                }
            }
        } finally {
            dbConnection.close(); // Ensure the connection is closed after the operation
        }
        return totalDoanhSo;
    }

    // Cập nhật doanh số trong bảng nhân 
    public void updateDoanhSo(String maNV, double totalDoanhSo) throws SQLException {
        String updateQuery = "UPDATE NHANVIEN SET DoanhSo = ? WHERE MaNV = ?";

        try (PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(updateQuery)) {
            pstmt.setDouble(1, totalDoanhSo);
            pstmt.setString(2, maNV);
            pstmt.executeUpdate();
        }
    }

    public void updateDoanhSoForNhanVien(String maNV) throws SQLException, ClassNotFoundException {
        double totalDoanhSo = calculateTotalDoanhSo(maNV);
        updateDoanhSo(maNV, totalDoanhSo);
    }

}
