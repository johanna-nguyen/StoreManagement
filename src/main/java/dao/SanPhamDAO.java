package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.SanPham;
import db.DatabaseConnection;
import java.math.BigDecimal;
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
public class SanPhamDAO {

    private DatabaseConnection dbConnection;

    public SanPhamDAO() {
        dbConnection = new DatabaseConnection();
    }

    /**
     * Retrieves all customers from the database.
     */
    public List<SanPham> getAllSanPham() throws SQLException, ClassNotFoundException {
        List<SanPham> sanPhamList = new ArrayList<>();
        String query = "SELECT * FROM SANPHAM";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                SanPham sanPham = new SanPham(
                        rs.getString("MaSP"),
                        rs.getString("TenSP"),
                        rs.getString("DonViTinh"),
                        rs.getString("NuocSanXuat"),
                        rs.getBigDecimal("GiaBan"),
                        rs.getInt("SoLuongTon"),
                        rs.getString("MaNCC"),
                        rs.getDate("NgayHetHan"),
                        rs.getString("HinhAnhSP")
                );
                sanPhamList.add(sanPham);
            }
            rs.close();
        } finally {
            dbConnection.close();
        }
        return sanPhamList;
    }

    public void addSanPham(SanPham sanPham) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO SANPHAM (MaSP, TenSP, DonViTinh, NuocSanXuat, GiaBan, SoLuongTon, MaNCC, NgayHetHan, HinhAnhSP) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, sanPham.getMaSP());
            pstmt.setString(2, sanPham.getTenSP());
            pstmt.setString(3, sanPham.getDvt());
            pstmt.setString(4, sanPham.getNuocSX());
            pstmt.setBigDecimal(5, sanPham.getGiaBan());
            pstmt.setInt(6, sanPham.getSoLuongTon());
            pstmt.setString(7, sanPham.getMaNCC());
            pstmt.setDate(8, new java.sql.Date(sanPham.getNgayHetHan().getTime()));
            pstmt.setString(9, sanPham.getHinhAnhSP());

            pstmt.executeUpdate();
        } finally {
            dbConnection.close();
        }
    }

    public boolean deleteSanPham(String maSP) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM SANPHAM WHERE MaSP = ?";
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

    // Tìm kiếm tất cả
    public List<SanPham> searchInAllColumns(String searchTerm) throws SQLException, ClassNotFoundException {
        List<SanPham> sanPhamList = new ArrayList<>();
        String query = "SELECT * FROM SANPHAM WHERE "
                + "TenSP LIKE ? OR NuocSanXuat LIKE ? OR GiaBan LIKE ? ";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            String formattedTerm = "%" + searchTerm + "%";  
            pstmt.setString(1, formattedTerm);
            pstmt.setString(2, formattedTerm);
            pstmt.setString(3, formattedTerm);
        

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Tạo đối tượng SP từ kết quả truy vấn và thêm vào danh sách
                SanPham sanPham = new SanPham(
                        rs.getString("MaSP"),
                        rs.getString("TenSP"),
                        rs.getString("DonViTinh"),
                        rs.getString("NuocSanXuat"),
                        rs.getBigDecimal("GiaBan"),
                        rs.getInt("SoLuongTon"),
                        rs.getString("MaNCC"),
                        rs.getDate("NgayHetHan"),
                        rs.getString("HinhAnhSP")
                );
                sanPhamList.add(sanPham);
            }
        } catch (SQLException e) {
        } finally {
            dbConnection.close();
        }
        return sanPhamList;
    }

    public ResultSet search(String column, String searchTerm) throws SQLException {
        String query = "SELECT * FROM SANPHAM WHERE " + column + " LIKE ?";
        ResultSet rs = null;
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, "%" + searchTerm + "%"); // Tìm kiếm gần đúng
            rs = pstmt.executeQuery();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public ResultSet searchByName(String searchTerm) throws SQLException {
        return search("TenSP", searchTerm);
    }

    public ResultSet searchByNuocSX(String searchTerm) throws SQLException {
        return search("NuocSanXuat", searchTerm);
    }

    public ResultSet searchByGiaBan(String searchTerm) throws SQLException {
        String query = "SELECT * FROM SANPHAM WHERE GiaBan = ?";
        ResultSet rs = null;
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);

            
            pstmt.setDouble(1, Double.parseDouble(searchTerm));

            rs = pstmt.executeQuery();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            // Xử lý ngoại lệ nếu việc chuyển đổi không thành công
            System.err.println("Giá trị tìm kiếm không phải là số hợp lệ.");
        }
        return rs;
    }

    public ResultSet filterByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) throws SQLException {
        String query = "SELECT * FROM SANPHAM WHERE GiaBan BETWEEN ? AND ?";
        ResultSet rs = null;
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setBigDecimal(1, minPrice);
            pstmt.setBigDecimal(2, maxPrice);
            rs = pstmt.executeQuery();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    // sửa sp
    public boolean update(SanPham sanPham) throws SQLException, ClassNotFoundException {
        String updateQuery = "UPDATE SANPHAM SET TenSP = ?, DonViTinh = ?, NuocSanXuat = ?, GiaBan = ?, SoLuongTon = ?, MaNCC = ?, NgayHetHan = ?, HinhAnhSP = ? WHERE MaSP = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(updateQuery);
            pstmt.setString(1, sanPham.getTenSP());
            pstmt.setString(2, sanPham.getDvt());
            pstmt.setString(3, sanPham.getNuocSX());
            pstmt.setBigDecimal(4, sanPham.getGiaBan());
            pstmt.setInt(5, sanPham.getSoLuongTon());
            pstmt.setString(6, sanPham.getMaNCC());
            pstmt.setDate(7, new java.sql.Date(sanPham.getNgayHetHan().getTime()));
            pstmt.setString(8, sanPham.getHinhAnhSP());
            pstmt.setString(9, sanPham.getMaSP());
            return pstmt.executeUpdate() > 0;
        } finally {
            dbConnection.close();
        }
    }

    // Kiểm tra mã SP đã tồn tại hay chưa
    public boolean isMaSPExist(String maSP, String currentMaSP) throws ClassNotFoundException, SQLException {
        // Kiểm tra xem MASP mới có trùng với MASP hiện tại 
        if (maSP.equals(currentMaSP)) {
            return true; 
        }
        String maKHQuery = "SELECT COUNT(*) FROM SANPHAM WHERE MaSP = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(maKHQuery);
            pstmt.setString(1, maSP);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } finally {
            dbConnection.close();
        }
        return false; 
    }

    // lấy hình ảnh sp
    public String getHinhAnhByMaSP(String maSP) throws SQLException, ClassNotFoundException {
        String query = "SELECT HinhAnhSP FROM SANPHAM WHERE MaSP = ?";
        String hinhAnhSP = null;

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, maSP);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                hinhAnhSP = rs.getString("HinhAnhSP"); 
            }

            rs.close();
        } finally {
            dbConnection.close();
        }
        return hinhAnhSP;
    }

    // cập nhật hình ảnh sp theo mã sp
    public void updateHinhAnhSP(String maSP, String filePath) throws SQLException, ClassNotFoundException {
        String updateQuery = "UPDATE SANPHAM SET HinhAnhSP = ? WHERE MaSP = ?";
        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(updateQuery);
            pstmt.setString(1, filePath);
            pstmt.setString(2, maSP);
            pstmt.executeUpdate();
        } finally {
            dbConnection.close();
        }
    }

    // ds nhà cung cấp
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
    
    
}
