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
import model.PhieuNhap;

/**
 *
 * @author khanhnguyen
 */
/**
 * Data Access Object for customer information.
 */
public class PhieuNhapDAO {

    private DatabaseConnection dbConnection;

    public PhieuNhapDAO() {
        dbConnection = new DatabaseConnection();
    }

    /**
     * Retrieves all customers from the database.
     */
    public List<PhieuNhap> getAllPhieuNhap() throws SQLException, ClassNotFoundException {
        List<PhieuNhap> phieuNhapList = new ArrayList<>();
        String query = "SELECT * FROM NHAPHANG";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PhieuNhap phieuNhap = new PhieuNhap(
                        rs.getString("MaNhap"),
                        rs.getString("MaSP"),
                        rs.getInt("SoLuong"),
                        rs.getBigDecimal("GiaNhap"),
                        rs.getDate("NgayNhap"),
                        rs.getString("MaNCC")
                );
                phieuNhapList.add(phieuNhap);
            }
            rs.close();
        } finally {
            dbConnection.close();
        }
        return phieuNhapList;
    }

    public void addPhieuNhap(PhieuNhap phieuNhap) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO NHAPHANG (MaNhap, MaSP, SoLuong, GiaNhap, NgayNhap, MaNCC) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, phieuNhap.getMaNhap());
            pstmt.setString(2, phieuNhap.getMaSP());
            pstmt.setInt(3, phieuNhap.getSoLuong());
            pstmt.setBigDecimal(4, phieuNhap.getGiaNhap());
            pstmt.setDate(5, (Date) phieuNhap.getNgayNhap());
            pstmt.setString(6, phieuNhap.getMaNCC());

            pstmt.executeUpdate();
        } finally {
            dbConnection.close();
        }
        // Cập nhật số lượng tồn kho
        updateKho(phieuNhap.getMaSP(), phieuNhap.getSoLuong(), true);
    }

    // Xoá phiếu nhập hàng
    public boolean deletePhieuNhap(String maNhap) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM NHAPHANG WHERE MaNhap = ? ";
        String maSP = null;
        int soLuong = 0;

        // Lấy thông tin sản phẩm và số lượng trước khi xóa
        String selectQuery = "SELECT MaSP, SoLuong FROM NHAPHANG WHERE MaNhap = ?";
        try {
            dbConnection.open();

            // Lấy thông tin phiếu nhập trước khi xóa
            PreparedStatement selectPstmt = dbConnection.getConnection().prepareStatement(selectQuery);
            selectPstmt.setString(1, maNhap);
            ResultSet rs = selectPstmt.executeQuery();

            if (rs.next()) {
                maSP = rs.getString("MaSP");
                soLuong = rs.getInt("SoLuong");
            }

            // Xóa phiếu nhập
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, maNhap);
            int rowsAffected = pstmt.executeUpdate();

            // Cập nhật số lượng tồn kho
            if (rowsAffected > 0) {
                updateKho(maSP, soLuong, false); // Giảm số lượng khi xóa phiếu nhập
            }
            return rowsAffected > 0;
        } finally {
            dbConnection.close();
        }
    }

    // Tìm kiếm
    public ResultSet search(String column, String searchTerm) throws SQLException {
        String query = "SELECT * FROM NHAPHANG WHERE " + column + " LIKE ?";
        ResultSet rs = null;
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, "%" + searchTerm + "%"); // Tìm kiếm gần đúng
            rs = pstmt.executeQuery();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    // Tìm theo MaNhap
    public ResultSet searchByMaNhap(String searchTerm) throws SQLException {
        return search("MaNhap", searchTerm);
    }

    // Tìm kiếm tất cả
    public List<PhieuNhap> searchInAllColumns(String searchTerm) throws SQLException, ClassNotFoundException {
        List<PhieuNhap> phieuNhapList = new ArrayList<>();
        String query = "SELECT * FROM NHAPHANG WHERE "
                + "MaNhap LIKE ? OR MaSP LIKE ? OR GiaNhap LIKE ? MaNCC LIKE ?";

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
                // Tạo đối tượng Phiếu Nhập  từ kết quả truy vấn và thêm vào danh sách
                PhieuNhap phieuNhap = new PhieuNhap(
                        rs.getString("MaNhap"),
                        rs.getString("MaSP"),
                        rs.getInt("SoLuong"),
                        rs.getBigDecimal("GiaNhap"),
                        rs.getDate("NgayNhap"),
                        rs.getString("MaNCC")
                );
                phieuNhapList.add(phieuNhap);
            }
        } catch (SQLException e) {
        } finally {
            dbConnection.close();
        }
        return phieuNhapList;
    }

    // Lọc giá
    public ResultSet filterByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) throws SQLException {
        String query = "SELECT * FROM NHAPHANG WHERE GiaNhap BETWEEN ? AND ?";
        ResultSet rs = null;
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setBigDecimal(1, minPrice);
            pstmt.setBigDecimal(2, maxPrice);
            rs = pstmt.executeQuery();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PhieuNhapDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public boolean update(PhieuNhap phieuNhap) throws SQLException, ClassNotFoundException {
        String selectQuery = "SELECT SoLuong FROM NHAPHANG WHERE MaNhap = ?";
        String updateQuery = "UPDATE NHAPHANG SET MaSP = ?, SoLuong = ?, GiaNhap = ?, NgayNhap = ?, MaNCC = ? WHERE MaNhap = ?";

        int oldQuantity = 0; // Khai báo biến để lưu số lượng cũ

        try {
            dbConnection.open();

            // Lấy số lượng cũ
            PreparedStatement selectPstmt = dbConnection.getConnection().prepareStatement(selectQuery);
            selectPstmt.setString(1, phieuNhap.getMaNhap());
            ResultSet rs = selectPstmt.executeQuery();

            if (rs.next()) {
                oldQuantity = rs.getInt("SoLuong");
            }

            // Cập nhật phiếu nhập
            PreparedStatement updatePstmt = dbConnection.getConnection().prepareStatement(updateQuery);
            updatePstmt.setString(1, phieuNhap.getMaSP());
            updatePstmt.setInt(2, phieuNhap.getSoLuong());
            updatePstmt.setBigDecimal(3, phieuNhap.getGiaNhap());
            updatePstmt.setDate(4, new java.sql.Date(phieuNhap.getNgayNhap().getTime()));
            updatePstmt.setString(5, phieuNhap.getMaNCC());
            updatePstmt.setString(6, phieuNhap.getMaNhap());

            boolean isUpdated = updatePstmt.executeUpdate() > 0;

            // Cập nhật số lượng tồn kho
            if (isUpdated) {
                int quantityChange = phieuNhap.getSoLuong() - oldQuantity; // Tính toán sự thay đổi số lượng
                updateKho(phieuNhap.getMaSP(), quantityChange, quantityChange > 0); // Gọi hàm với isAdd dựa trên sự thay đổi
            }

            return isUpdated;
        } finally {
            dbConnection.close();
        }
    }

    // DS NCC
    public List<String> getAllMaNCC() throws SQLException, ClassNotFoundException {
        List<String> maNCCList = new ArrayList<>();
        String query = "SELECT MaNCC FROM NHACUNGCAP";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                maNCCList.add(rs.getString("MaNCC"));
            }

        } finally {
            dbConnection.close();
        }
        return maNCCList;

    }

    // DS SP
    public List<String> getAllMaSP() throws SQLException, ClassNotFoundException {
        List<String> maSPList = new ArrayList<>();
        String query = "SELECT MaSP FROM SANPHAM";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                maSPList.add(rs.getString("MaSP"));
            }

        } finally {
            dbConnection.close();
        }
        return maSPList;

    }

    // Kiểm tra mã nhập đã tồn tại hay chưa
    public boolean isMaNhapExist(String maNhap, String currentMaNhap) throws ClassNotFoundException, SQLException {
        // Kiểm tra xem MANHAP mới có trùng với MANHAP hiện tại không
        if (maNhap.equals(currentMaNhap)) {
            return true; // Nếu trùng, MANHAP là duy nhất
        }
        String query = "SELECT COUNT(*) FROM NHAPHANG WHERE MaNhap = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, maNhap);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } finally {
            dbConnection.close();
        }
        return false;
    }

    // Cập nhật kho
    private void updateKho(String maSP, int soLuong, boolean isAdd) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO KHO (MaSP, SoLuongTon) " +
             "VALUES (?, ?) " +
             "ON DUPLICATE KEY UPDATE " +
             "SoLuongTon = GREATEST(0, SoLuongTon " +
                 (isAdd ? "+" : "-") + " VALUES(SoLuongTon))";



        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(sql);
            pstmt.setString(1, maSP);
            pstmt.setInt(2, soLuong);
            pstmt.executeUpdate();

        } finally {
            dbConnection.close();
        }
    }

}
