package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import db.DatabaseConnection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Kho;

/**
 *
 * @author khanhnguyen
 */
/**
 * Data Access Object for customer information.
 */
public class KhoDAO {

    private DatabaseConnection dbConnection;

    public KhoDAO() {
        dbConnection = new DatabaseConnection();
    }

    /**
     * Retrieves all customers from the database.
     */
    public List<Kho> getAllKho() throws SQLException, ClassNotFoundException {
        List<Kho> khoList = new ArrayList<>();
        String query = "SELECT * FROM KHO";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Kho kho = new Kho(
                        rs.getString("MaSP"),
                        rs.getInt("SoLuongTon"),
                        rs.getDate("NgayCapNhatCuoi")
                );
                khoList.add(kho);
            }
            rs.close();
        } finally {
            dbConnection.close();
        }
        return khoList;
    }

    // Xoá phiếu kho
    public boolean deleteKho(String maSP) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM KHO WHERE MaSP = ? ";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, maSP);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } finally {
            dbConnection.close();
        }
    }

    // Tìm kiếm
    public ResultSet search(String column, String searchTerm) throws SQLException {
        String query = "SELECT * FROM KHO WHERE " + column + " LIKE ?";
        ResultSet rs = null;
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, "%" + searchTerm + "%"); // Tìm kiếm gần đúng
            rs = pstmt.executeQuery();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    // Tìm theo MaSP
    public ResultSet searchByMaSP(String searchTerm) throws SQLException {
        return search("MaSP", searchTerm);
    }

    // Tìm kiếm tất cả
    public List<Kho> searchInAllColumns(String searchTerm) throws SQLException, ClassNotFoundException {
        List<Kho> khoList = new ArrayList<>();
        String query = "SELECT * FROM KHO WHERE "
                + "MaSP LIKE ?";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            String formattedTerm = "%" + searchTerm + "%";  // Định dạng từ khóa cho tìm kiếm
            pstmt.setString(1, formattedTerm);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Tạo đối tượng Phiếu Nhập từ kết quả truy vấn và thêm vào danh sách
                Kho kho = new Kho(
                        rs.getString("MaSP"),
                        rs.getInt("SoLuongTon"),
                        rs.getDate("NgayCapNhatCuoi")
                );
                khoList.add(kho);
            }
        } catch (SQLException e) {
        } finally {
            dbConnection.close();
        }
        return khoList;
    }

    public void updateKho(String maSP, int soLuong)
        throws ClassNotFoundException, SQLException {

    String sql = """
        INSERT INTO KHO (MaSP, SoLuongTon, NgayCapNhatCuoi)
        VALUES (?, ?, NOW())
        ON DUPLICATE KEY UPDATE
            SoLuongTon = GREATEST(0, SoLuongTon " +
                             (isAdd ? "+" : "-") + " VALUES(SoLuongTon)),
            NgayCapNhatCuoi = NOW()
    """;

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
    
    // Số lượng tồn sản phẩm trong kho
    public int getSoLuongTon(String maSP)
        throws SQLException, ClassNotFoundException {

    int soLuongTon = 0;
    String sql = "SELECT SoLuongTon FROM KHO WHERE MaSP = ?";

    try {
        dbConnection.open();
        PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(sql);
        pstmt.setString(1, maSP);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            soLuongTon = rs.getInt("SoLuongTon");
        }
    } finally {
        dbConnection.close();
    }

    return soLuongTon;
}


}
