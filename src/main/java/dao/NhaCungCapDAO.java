package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import db.DatabaseConnection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.NhaCungCap;
import model.SanPham;

/**
 *
 * @author khanhnguyen
 */
/**
 * Data Access Object for customer information.
 */
public class NhaCungCapDAO {

    private DatabaseConnection dbConnection;

    public NhaCungCapDAO() {
        dbConnection = new DatabaseConnection();
    }

    /**
     * Retrieves all customers from the database.
     */
    public List<NhaCungCap> getAllNhaCungCap() throws SQLException, ClassNotFoundException {
        List<NhaCungCap> nhaCungCapList = new ArrayList<>();
        String query = "SELECT * FROM NHACUNGCAP";

        try {
            dbConnection.open();
            ResultSet rs = dbConnection.executeQuery(query);
            while (rs.next()) {
                NhaCungCap nhaCC = new NhaCungCap(
                        rs.getString("MaNCC"),
                        rs.getString("TenNCC"),
                        rs.getString("DiaChi"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("Website"),
                        rs.getString("GhiChu")
                );
                nhaCungCapList.add(nhaCC);
            }
            rs.close();
        } finally {
            dbConnection.close();
        }
        return nhaCungCapList;
    }

    public void addNhaCungCap(NhaCungCap nhaCungCap) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO NHACUNGCAP (MaNCC, TenNCC, DiaChi, DienThoai, Email, Website, GhiChu) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, nhaCungCap.getMaNCC());
            pstmt.setString(2, nhaCungCap.getTenNCC());
            pstmt.setString(3, nhaCungCap.getDiaChi());
            pstmt.setString(4, nhaCungCap.getDienThoai());
            pstmt.setString(5, nhaCungCap.getEmail());
            pstmt.setString(6, nhaCungCap.getWebsite());
            pstmt.setString(7, nhaCungCap.getGhiChu());

            pstmt.executeUpdate();
        } finally {
            dbConnection.close();
        }
    }

    public boolean deleteNhaCungCap(String maNCC) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM NHACUNGCAP WHERE MaNCC = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);

            pstmt.setString(1, maNCC);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } finally {
            dbConnection.close();
        }
    }

    public ResultSet search(String column, String searchTerm) throws SQLException {
        String query = "SELECT * FROM NHACUNGCAP WHERE " + column + " LIKE ?";
        ResultSet rs = null;
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, "%" + searchTerm + "%"); // Tìm kiếm gần đúng
            rs = pstmt.executeQuery();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NhaCungCapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    // Tìm kiếm tất cả
    public List<NhaCungCap> searchInAllColumns(String searchTerm) throws SQLException, ClassNotFoundException {
        List<NhaCungCap> nhaCungCapList = new ArrayList<>();
        String query = "SELECT * FROM NHACUNGCAP WHERE "
                + "MaNCC LIKE ? OR TenNCC LIKE ? OR DiaChi LIKE ? OR DienThoai LIKE ? OR Email LIKE ? OR Website LIKE ? OR GhiChu LIKE ?";

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
                // Tạo đối tượng SP từ kết quả truy vấn và thêm vào danh sách
                NhaCungCap nhaCC = new NhaCungCap(
                        rs.getString("MaNCC"),
                        rs.getString("TenNCC"),
                        rs.getString("DiaChi"),
                        rs.getString("DienThoai"),
                        rs.getString("Email"),
                        rs.getString("Website"),
                        rs.getString("GhiChu")
                );
                nhaCungCapList.add(nhaCC);
            }
        } catch (SQLException e) {
        } finally {
            dbConnection.close();
        }
        return nhaCungCapList;
    }

    public ResultSet searchByName(String searchTerm) throws SQLException {
        return search("TenNCC", searchTerm);
    }

    public ResultSet searchByAddress(String searchTerm) throws SQLException {
        return search("DiaChi", searchTerm);
    }

    public ResultSet searchByPhone(String searchTerm) throws SQLException {
        return search("DienThoai", searchTerm);
    }

    public boolean update(NhaCungCap nhaCungCap) throws SQLException, ClassNotFoundException {
        String updateQuery = "UPDATE NHACUNGCAP SET TenNCC = ?, DiaChi = ?, DienThoai = ?, Email = ?, Website = ?, GhiChu = ? WHERE MaNCC = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(updateQuery);
            pstmt.setString(1, nhaCungCap.getTenNCC());
            pstmt.setString(2, nhaCungCap.getDiaChi());
            pstmt.setString(3, nhaCungCap.getDienThoai());
            pstmt.setString(4, nhaCungCap.getEmail());
            pstmt.setString(5, nhaCungCap.getWebsite());
            pstmt.setString(6, nhaCungCap.getGhiChu());
            pstmt.setString(7, nhaCungCap.getMaNCC());

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
        String emailQuery = "SELECT COUNT(*) FROM NHACUNGCAP WHERE Email = ?";
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

    // Kiểm tra mã NCC đã tồn tại hay chưa
    public boolean isMaNCCExist(String maNCC) throws ClassNotFoundException, SQLException {
        String maNCCQuery = "SELECT COUNT(*) FROM NHACUNGCAP WHERE MaNCC = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(maNCCQuery);
            pstmt.setString(1, maNCCQuery);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu số lượng > 0, mã NCC đã tồn tại
            }
        } finally {
            dbConnection.close();
        }
        return false; // Mã khách hàng không tồn tại
    }

}
