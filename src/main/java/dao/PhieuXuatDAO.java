package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import db.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.PhieuXuat;

/**
 *
 * @author khanhnguyen
 */
/**
 * Data Access Object for customer information.
 */
public class PhieuXuatDAO {

    private DatabaseConnection dbConnection;

    public PhieuXuatDAO() {
        dbConnection = new DatabaseConnection();
    }

    /**
     * Retrieves all customers from the database.
     */
    public List<PhieuXuat> getAllPhieuXuat() throws SQLException, ClassNotFoundException {
        List<PhieuXuat> phieuXuatList = new ArrayList<>();
        String query = "SELECT * FROM XUATHANG";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PhieuXuat phieuXuat = new PhieuXuat(
                        rs.getString("MaXuat"),
                        rs.getString("MaSP"),
                        rs.getInt("SoLuong"),
                        rs.getBigDecimal("GiaXuat"),
                        rs.getDate("NgayXuat"),
                        rs.getString("MaKH")
                );
                phieuXuatList.add(phieuXuat);
            }
            rs.close();
        } finally {
            dbConnection.close();
        }
        return phieuXuatList;
    }

    public void addPhieuXuat(PhieuXuat phieuXuat) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO XUATHANG (MaXuat, MaSP, SoLuong, GiaXuat, NgayXuat, MaKH) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, phieuXuat.getMaXuat());
            pstmt.setString(2, phieuXuat.getMaSP());
            pstmt.setInt(3, phieuXuat.getSoLuong());
            pstmt.setBigDecimal(4, phieuXuat.getGiaXuat());
            pstmt.setDate(5, (Date) phieuXuat.getNgayXuat());
            pstmt.setString(6, phieuXuat.getMaKH());

            pstmt.executeUpdate();
        } finally {
            dbConnection.close();
        }
        // Cập nhật số lượng tồn kho
        updateKho(phieuXuat.getMaSP(), phieuXuat.getSoLuong(), false);
    }

    // Xoá phiếu xuất hàng
    public boolean deletePhieuXuat(String maXuat) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM XUATHANG WHERE MaXuat = ?";
        String maSP = null;
        int soLuong = 0;

        // Lấy thông tin sản phẩm và số lượng trước khi xóa
        String selectQuery = "SELECT MaSP, SoLuong FROM XUATHANG WHERE MaXuat = ?";
        try {
            dbConnection.open();

            // Lấy thông tin phiếu xuất trước khi xóa
            PreparedStatement selectPstmt = dbConnection.getConnection().prepareStatement(selectQuery);
            selectPstmt.setString(1, maXuat);
            ResultSet rs = selectPstmt.executeQuery();

            if (rs.next()) {
                maSP = rs.getString("MaSP");
                soLuong = rs.getInt("SoLuong");
            }

            // Xóa phiếu xuất
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, maXuat);
            int rowsAffected = pstmt.executeUpdate();

            // Cập nhật số lượng tồn kho
            if (rowsAffected > 0) {
                updateKho(maSP, soLuong, true); // Tăng số lượng khi xóa phiếu xuất
            }
            return rowsAffected > 0;
        } finally {
            dbConnection.close();
        }
    }

    // Tìm kiếm
    public ResultSet search(String column, String searchTerm) throws SQLException {
        String query = "SELECT * FROM XUATHANG WHERE " + column + " LIKE ?";
        ResultSet rs = null;
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, "%" + searchTerm + "%"); // Tìm kiếm gần đúng
            rs = pstmt.executeQuery();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    // Tìm theo MaXuat
    public ResultSet searchByMaXuat(String searchTerm) throws SQLException {
        return search("MaXuat", searchTerm);
    }

    // Tìm kiếm tất cả
    public List<PhieuXuat> searchInAllColumns(String searchTerm) throws SQLException, ClassNotFoundException {
        List<PhieuXuat> phieuXuatList = new ArrayList<>();
        String query = "SELECT * FROM XUATHANG WHERE "
                + "MaXuat LIKE ? OR MaSP LIKE ? OR GiaXuat LIKE ? MaKH LIKE ?";

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
                PhieuXuat phieuXuat = new PhieuXuat(
                        rs.getString("MaXuat"),
                        rs.getString("MaSP"),
                        rs.getInt("SoLuong"),
                        rs.getBigDecimal("GiaXuat"),
                        rs.getDate("NgayXuat"),
                        rs.getString("MaKH")
                );
                phieuXuatList.add(phieuXuat);
            }
        } catch (SQLException e) {
        } finally {
            dbConnection.close();
        }
        return phieuXuatList;
    }

    // Lọc giá
    public ResultSet filterByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) throws SQLException {
        String query = "SELECT * FROM XUATHANG WHERE GiaXuat BETWEEN ? AND ?";
        ResultSet rs = null;
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setBigDecimal(1, minPrice);
            pstmt.setBigDecimal(2, maxPrice);
            rs = pstmt.executeQuery();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PhieuXuatDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    // Sửa phiếu xuất
    public boolean update(PhieuXuat phieuXuat) throws SQLException, ClassNotFoundException {
        String selectQuery = "SELECT SoLuong FROM XUATHANG WHERE MaXuat = ?";
        String updateQuery = "UPDATE XUATHANG SET MaSP = ?, SoLuong = ?, GiaXuat = ?, NgayXuat = ?, MaKH = ? WHERE MaXuat = ?";

        int oldQuantity = 0; // Khai báo biến để lưu số lượng cũ

        try {
            dbConnection.open();

            // Lấy số lượng cũ
            PreparedStatement selectPstmt = dbConnection.getConnection().prepareStatement(selectQuery);
            selectPstmt.setString(1, phieuXuat.getMaXuat());
            ResultSet rs = selectPstmt.executeQuery();

            if (rs.next()) {
                oldQuantity = rs.getInt("SoLuong");
            }

            // Cập nhật phiếu xuất
            PreparedStatement updatePstmt = dbConnection.getConnection().prepareStatement(updateQuery);
            updatePstmt.setString(1, phieuXuat.getMaSP());
            updatePstmt.setInt(2, phieuXuat.getSoLuong());
            updatePstmt.setBigDecimal(3, phieuXuat.getGiaXuat());
            updatePstmt.setDate(4, new java.sql.Date(phieuXuat.getNgayXuat().getTime()));
            updatePstmt.setString(5, phieuXuat.getMaKH());
            updatePstmt.setString(6, phieuXuat.getMaXuat());

            boolean isUpdated = updatePstmt.executeUpdate() > 0;

            // Cập nhật số lượng tồn kho
            if (isUpdated) {
                int quantityChange = oldQuantity - phieuXuat.getSoLuong(); // Tính toán sự thay đổi số lượng
                updateKho(phieuXuat.getMaSP(), quantityChange, quantityChange < 0); // Gọi hàm với isAdd dựa trên sự thay đổi
            }

            return isUpdated;
        } finally {
            dbConnection.close();
        }
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
    public boolean isMaXuatExist(String maXuat, String currentMaXuat) throws ClassNotFoundException, SQLException {
        // Kiểm tra xem MANHAP mới có trùng với MANHAP hiện tại không
        if (maXuat.equals(currentMaXuat)) {
            return true; // Nếu trùng, MANHAP là duy nhất
        }
        String query = "SELECT COUNT(*) FROM XUATHANG WHERE MaXuat = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, maXuat);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu số lượng > 0, mã khách hàng đã tồn tại
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
