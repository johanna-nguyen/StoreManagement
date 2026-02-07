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
import model.ChiTietHD;

/**
 *
 * @author khanhnguyen
 */
/**
 * Data Access Object for customer information.
 */
public class ChiTietHDDAO {

    private DatabaseConnection dbConnection;

    public ChiTietHDDAO() {
        dbConnection = new DatabaseConnection();
    }

    /**
     * Retrieves all customers from the database.
     */
    public List<ChiTietHD> getAllChiTietHD() throws SQLException, ClassNotFoundException {
        List<ChiTietHD> chiTietHDList = new ArrayList<>();
        String query = "SELECT * FROM CTHD";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ChiTietHD chiTietHD = new ChiTietHD(
                        rs.getString("MaHD"),
                        rs.getString("MaSP"),
                        rs.getInt("SoLuong"),
                        rs.getBigDecimal("DonGia")
                );
                chiTietHDList.add(chiTietHD);
            }
            rs.close();
        } finally {
            dbConnection.close();
        }
        return chiTietHDList;
    }

    public void addChiTietHD(ChiTietHD chiTietHD) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO CTHD (MaHD, MaSP, SoLuong, DonGia) VALUES (?, ?, ?, ?)";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, chiTietHD.getMaHD());
            pstmt.setString(2, chiTietHD.getMaSP());
            pstmt.setInt(3, chiTietHD.getSoLuong());
            pstmt.setBigDecimal(4, chiTietHD.getDonGia());

            pstmt.executeUpdate();
        } finally {
            dbConnection.close();
        }
    }

    // Xoá sp
    public boolean deleteChiTietHD(String maHD, String maSP) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM SANPHAM WHERE MaHD = ? AND MaSP = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, maHD);
            pstmt.setString(2, maSP);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } finally {
            dbConnection.close();
        }
    }

    // Tìm kiếm
    public ResultSet search(String column, String searchTerm) throws SQLException {
        String query = "SELECT * FROM CTHD WHERE " + column + " LIKE ?";
        ResultSet rs = null;
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, "%" + searchTerm + "%"); // Tìm kiếm gần đúng
            rs = pstmt.executeQuery();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChiTietHDDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    // Tìm theo MaHD
    public ResultSet searchByMaHD(String searchTerm) throws SQLException {
        return search("MaHD", searchTerm);
    }

    // Tìm theo MaSP
    public ResultSet searchByMaSP(String searchTerm) throws SQLException {
        return search("MaSP", searchTerm);
    }

    // Tìm theo DonGia
    public ResultSet searchByDonGia(String searchTerm) throws SQLException {
        return search("DonGia", searchTerm);
    }

    // Tìm kiếm tất cả
    public List<ChiTietHD> searchInAllColumns(String searchTerm) throws SQLException, ClassNotFoundException {
        List<ChiTietHD> chiTietHDList = new ArrayList<>();
        String query = "SELECT * FROM CTHD WHERE "
                + "MaHD LIKE ? OR MaSP LIKE ? OR DonGia LIKE ?";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            String formattedTerm = "%" + searchTerm + "%";  // Định dạng từ khóa cho tìm kiếm
            pstmt.setString(1, formattedTerm);
            pstmt.setString(2, formattedTerm);
            pstmt.setString(3, formattedTerm);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Tạo đối tượng CTHD từ kết quả truy vấn và thêm vào danh sách
                ChiTietHD chiTietHD = new ChiTietHD(
                        rs.getString("MaHD"),
                        rs.getString("MaSP"),
                        rs.getInt("SoLuong"),
                        rs.getBigDecimal("DonGia")
                );
                chiTietHDList.add(chiTietHD);
            }
        } catch (SQLException e) {
        } finally {
            dbConnection.close();
        }
        return chiTietHDList;
    }

    // Lọc giá
    public ResultSet filterByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) throws SQLException {
        String query = "SELECT * FROM CTHD WHERE DonGia BETWEEN ? AND ?";
        ResultSet rs = null;
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setBigDecimal(1, minPrice);
            pstmt.setBigDecimal(2, maxPrice);
            rs = pstmt.executeQuery();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ChiTietHDDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    // Sửa CTHD
    public boolean update(ChiTietHD chiTietHD) throws SQLException, ClassNotFoundException {
        String updateQuery = "UPDATE CTHD SET SoLuong = ?, DonGia = ? WHERE MaHD = ? AND MaSP = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(updateQuery);
            pstmt.setInt(1, chiTietHD.getSoLuong());
            pstmt.setBigDecimal(2, chiTietHD.getDonGia());
            pstmt.setString(3, chiTietHD.getMaHD());
            pstmt.setString(4, chiTietHD.getMaSP());

            return pstmt.executeUpdate() > 0;
        } finally {
            dbConnection.close();
        }
    }

    // DS HD
    public List<String> getAllMaHD() throws SQLException, ClassNotFoundException {
        List<String> maMaHDList = new ArrayList<>();
        String query = "SELECT MaHD FROM HOADON";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                maMaHDList.add(rs.getString("MaHD"));
            }

        } finally {
            dbConnection.close();
        }
        return maMaHDList;

    }

    // DS SP
    public List<String> getAllMaSP() throws SQLException, ClassNotFoundException {
        List<String> maMaSPList = new ArrayList<>();
        String query = "SELECT MaSP FROM SANPHAM";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                maMaSPList.add(rs.getString("MaSP"));
            }

        } finally {
            dbConnection.close();
        }
        return maMaSPList;

    }
    
    // Phương thức kiểm tra sự tồn tại của maHD và maSP trong bảng CTHD
    public boolean exists(String maHD, String maSP) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM CTHD WHERE MaHD = ? AND MaSP = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, maHD);
            pstmt.setString(2, maSP);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Nếu số lượng bản ghi lớn hơn 0, thì tồn tại
                return rs.getInt(1) > 0;
            }
        } finally {
            dbConnection.close();
        }
        return false;
    }

}
