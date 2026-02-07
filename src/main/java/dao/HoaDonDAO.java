package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import db.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.HoaDon;

/**
 *
 * @author khanhnguyen
 */
/**
 * Data Access Object for customer information.
 */
public class HoaDonDAO {

    private DatabaseConnection dbConnection;

    public HoaDonDAO() {
        dbConnection = new DatabaseConnection();
    }

    /**
     * Retrieves all customers from the database.
     */
    public List<HoaDon> getAllHoaDon() throws SQLException, ClassNotFoundException {
        List<HoaDon> hoaDonList = new ArrayList<>();
        String query = "SELECT * FROM HOADON";

        try {
            dbConnection.open();
            ResultSet rs = dbConnection.executeQuery(query);
            while (rs.next()) {
                HoaDon hoaDon = new HoaDon(
                        rs.getString("MaHD"),
                        rs.getDate("NgayHD"),
                        rs.getString("MaKH"),
                        rs.getString("MaNV"),
                        rs.getBigDecimal("TriGia")
                );
                hoaDonList.add(hoaDon);
            }
            rs.close();
        } finally {
            dbConnection.close();
        }
        return hoaDonList;
    }

    // Thêm HD
    public void addHoaDon(HoaDon hoaDon) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO HOADON (MaHD, NgayHD, MaKH, MaNV, TriGia) VALUES (?, ?, ?, ?, ?)";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, hoaDon.getMaHD());
            pstmt.setDate(2, (Date) hoaDon.getNgayHD());
            pstmt.setString(3, hoaDon.getMaKH());
            pstmt.setString(4, hoaDon.getMaNV());
            pstmt.setBigDecimal(5, hoaDon.getTriGia());
            pstmt.executeUpdate();
        } finally {
            dbConnection.close();
        }
    }

    // Xoá HD
    public boolean deleteHoaDon(String maHD) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM HOADON WHERE MaHD = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);

            pstmt.setString(1, maHD);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } finally {
            dbConnection.close();
        }
    }

    // Tìm kiếm
    public ResultSet search(String column, String searchTerm) throws SQLException {
        String query = "SELECT * FROM HOADON WHERE " + column + " LIKE ?";
        ResultSet rs = null;
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, "%" + searchTerm + "%"); // Tìm kiếm gần đúng
            rs = pstmt.executeQuery();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HoaDonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    // Tìm kiếm tất cả
    public List<HoaDon> searchInAllColumns(String searchTerm) throws SQLException, ClassNotFoundException {
        List<HoaDon> hoaDonList = new ArrayList<>();
        String query = "SELECT * FROM HOADON WHERE "
                + "MaHD LIKE ? OR MaKH LIKE ? OR MaNV LIKE ? OR TriGia LIKE ?";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            String formattedTerm = "%" + searchTerm + "%";  // Định dạng từ khóa cho tìm kiếm
            pstmt.setString(1, formattedTerm);
            pstmt.setString(2, formattedTerm);
            pstmt.setString(3, formattedTerm);
            pstmt.setString(4, formattedTerm);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Tạo đối tượng HoaDon từ kết quả truy vấn và thêm vào danh sách
                HoaDon hoaDon = new HoaDon(
                        rs.getString("MaHD"),
                        rs.getDate("NgayHD"),
                        rs.getString("MaKH"),
                        rs.getString("MaNV"),
                        rs.getBigDecimal("TriGia")
                );
                hoaDonList.add(hoaDon);
            }
        } catch (SQLException e) {
        } finally {
            dbConnection.close();
        }
        return hoaDonList;
    }

    // Tìm theo MAHD
    public ResultSet searchByMaHD(String searchTerm) throws SQLException {
        return search("MaHD", searchTerm);
    }

    // Tìm theo MAKH
    public ResultSet searchByMaKH(String searchTerm) throws SQLException {
        return search("MaKH", searchTerm);
    }

    // Tìm theo MANV
    public ResultSet searchByMaNV(String searchTerm) throws SQLException {
        return search("MaNV", searchTerm);
    }

    // Tìm theo Tri Gia
    public ResultSet searchByTriGia(String searchTerm) throws SQLException {
        return search("TriGia", searchTerm);
    }

    // Sửa HĐ
    public boolean update(HoaDon hoaDon) throws SQLException, ClassNotFoundException {
        String updateQuery = "UPDATE HOADON SET NgayHD = ?, MaKH = ?, MaNV = ?, TriGia = ? WHERE MaHD = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(updateQuery);
            pstmt.setDate(1, new java.sql.Date(hoaDon.getNgayHD().getTime()));
            pstmt.setString(2, hoaDon.getMaKH());
            pstmt.setString(3, hoaDon.getMaNV());
            pstmt.setBigDecimal(4, hoaDon.getTriGia());
            pstmt.setString(5, hoaDon.getMaHD());
            return pstmt.executeUpdate() > 0;
        } finally {
            dbConnection.close();
        }
    }

    // Kiểm tra mã HD đã tồn tại hay chưa
    public boolean isMaHDExist(String maHD, String currentMaHD) throws ClassNotFoundException, SQLException {
        // Kiểm tra xem MAHD mới có trùng với MAHD hiện tại không
        if (maHD.equals(currentMaHD)) {
            return true; // Nếu trùng, MAHD là duy nhất
        }
        String querry = "SELECT COUNT(*) FROM HOADON WHERE MaHD = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(querry);
            pstmt.setString(1, querry);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu số lượng > 0, mã NCC đã tồn tại
            }
        } finally {
            dbConnection.close();
        }
        return false;
    }

    // DS KH
    public List<String> getAllMaKH() throws SQLException, ClassNotFoundException {
        List<String> maKHList = new ArrayList<>();
        String query = "SELECT MaKH FROM KHACHHANG";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                maKHList.add(rs.getString("MaKH"));
            }

        } finally {
            dbConnection.close();
        }
        return maKHList;

    }

    // DS NV
    public List<String> getAllMaNV() throws SQLException, ClassNotFoundException {
        List<String> maNVList = new ArrayList<>();
        String query = "SELECT MaNV FROM NHANVIEN";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                maNVList.add(rs.getString("MaNV"));
            }

        } finally {
            dbConnection.close();
        }
        return maNVList;

    }

    // Lọc giá
    public ResultSet filterByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) throws SQLException {
        String query = "SELECT * FROM HOADON WHERE GiaBan BETWEEN ? AND ?";
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
}
