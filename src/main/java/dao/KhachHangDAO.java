package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.KhachHang;
import db.DatabaseConnection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khanhnguyen
 */
/**
 * Data Access Object for customer information.
 */
public class KhachHangDAO {

    private DatabaseConnection dbConnection;

    public KhachHangDAO() {
        dbConnection = new DatabaseConnection();
    }

    /**
     * Retrieves all customers from the database.
     */
    public List<KhachHang> getAllKhachHang() throws SQLException, ClassNotFoundException {
        List<KhachHang> khachHangList = new ArrayList<>();
        String query = "SELECT * FROM KHACHHANG";

        try {
            dbConnection.open();
            ResultSet rs = dbConnection.executeQuery(query);
            while (rs.next()) {
                KhachHang khachHang = new KhachHang(
                        rs.getString("MaKH"),
                        rs.getString("TenKH"),
                        rs.getDate("NgaySinh"),
                        rs.getString("GioiTinh"),
                        rs.getString("DiaChi"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("LoaiKH")
                );
                khachHangList.add(khachHang);
            }
            rs.close();
        } finally {
            dbConnection.close();
        }
        return khachHangList;
    }

    public void addKhachHang(KhachHang khachHang) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO KHACHHANG (MaKH, TenKH, NgaySinh, GioiTinh, DiaChi, DienThoai, Email, LoaiKH) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, khachHang.getMaKh());
            pstmt.setString(2, khachHang.getTenKh());
            pstmt.setDate(3, new java.sql.Date(khachHang.getNgaySinh().getTime()));
            pstmt.setString(4, khachHang.getGioiTinh());
            pstmt.setString(5, khachHang.getDiaChi());
            pstmt.setString(6, khachHang.getDienThoai());
            pstmt.setString(7, khachHang.getEmail());
            pstmt.setString(8, khachHang.getLoaiKh());

            pstmt.executeUpdate();
        } finally {
            dbConnection.close();
        }
    }

    public boolean deleteKhachHang(String maKH) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM KHACHHANG WHERE MaKH = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);

            pstmt.setString(1, maKH);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } finally {
            dbConnection.close();
        }
    }

    public ResultSet search(String column, String searchTerm) throws SQLException {
        String query = "SELECT * FROM KHACHHANG WHERE " + column + " LIKE ?";
        ResultSet rs = null;
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, "%" + searchTerm + "%"); // Tìm kiếm gần đúng
            rs = pstmt.executeQuery();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(KhachHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public ResultSet searchByName(String searchTerm) throws SQLException {
        return search("TenKH", searchTerm);
    }

    public ResultSet searchByYearOfBirth(String searchTerm) throws SQLException {
        return search("NgaySinh", searchTerm);
    }

    public ResultSet searchByAddress(String searchTerm) throws SQLException {
        return search("DiaChi", searchTerm);
    }

    public ResultSet searchByGender(String searchTerm) throws SQLException {
        return search("GioiTinh", searchTerm);
    }

    public ResultSet searchByTypeCustomer(String searchTerm) throws SQLException {
        return search("LoaiKH", searchTerm);
    }

    public boolean update(KhachHang khachHang) throws SQLException, ClassNotFoundException {
        String updateQuery = "UPDATE KHACHHANG SET TenKH = ?, NgaySinh = ?, GioiTinh = ?, DiaChi = ?, DienThoai = ?, Email = ?, LoaiKH = ? WHERE MaKH = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(updateQuery);
            pstmt.setString(1, khachHang.getTenKh());
            pstmt.setDate(2, new java.sql.Date(khachHang.getNgaySinh().getTime()));
            pstmt.setString(3, khachHang.getGioiTinh());
            pstmt.setString(4, khachHang.getDiaChi());
            pstmt.setString(5, khachHang.getDienThoai());
            pstmt.setString(6, khachHang.getEmail());
            pstmt.setString(7, khachHang.getLoaiKh());
            pstmt.setString(8, khachHang.getMaKh());

            return pstmt.executeUpdate() > 0;
        } finally {
            dbConnection.close();
        }
    }

    public boolean isEmailUnique(String email, String currentEmail) throws SQLException, ClassNotFoundException {
        // Kiểm tra xem email mới có trùng với email hiện tại không
        if (email.equals(currentEmail)) {
            return true; // Nếu trùng, email là duy nhất
        }
        String emailQuery = "SELECT COUNT(*) FROM KhachHang WHERE Email = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(emailQuery);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; // Trả về true nếu không có email nào trùng
            }
        } finally {
            dbConnection.close();
        }
        return false; // Trả về false nếu có lỗi xảy ra hoặc không có kết quả
    }

    // Kiểm tra mã khách hàng đã tồn tại hay chưa
    public boolean isMaKHExist(String maKH) throws ClassNotFoundException, SQLException {
        String maKHQuery = "SELECT COUNT(*) FROM KhachHang WHERE MaKH = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(maKHQuery);
            pstmt.setString(1, maKH);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu số lượng > 0, mã khách hàng đã tồn tại
            }
        } finally {
            dbConnection.close();
        }
        return false; // Mã khách hàng không tồn tại
    }

    // Tìm kiếm tất cả
    public List<KhachHang> searchInAllColumns(String searchTerm) throws SQLException, ClassNotFoundException {
        List<KhachHang> khachHangList = new ArrayList<>();
        String query = "SELECT * FROM KHACHHANG WHERE "
                + "MaKH LIKE ? OR TenKH LIKE ? OR GioiTinh LIKE ? OR DiaChi LIKE ? OR DienThoai LIKE ? OR Email LIKE ? OR LoaiKH LIKE ?";

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

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Tạo đối tượng KhachHang từ kết quả truy vấn và thêm vào danh sách
                KhachHang khachHang = new KhachHang(
                        rs.getString("MaKH"),
                        rs.getString("TenKH"),
                        rs.getDate("NgaySinh"),
                        rs.getString("GioiTinh"),
                        rs.getString("DiaChi"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("LoaiKH")
                );
                khachHangList.add(khachHang);
            }
        } catch (SQLException e) {
        } finally {
            dbConnection.close();
        }
        return khachHangList;
    }
    
}
