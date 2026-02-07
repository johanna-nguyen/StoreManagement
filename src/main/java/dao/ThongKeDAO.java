package dao;

import db.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author khanhnguyen
 */
public class ThongKeDAO {

    private DatabaseConnection dbConnection;
    private final PhieuXuatDAO phieuXuatDAO;

    public ThongKeDAO() {
        dbConnection = new DatabaseConnection();
        phieuXuatDAO = new PhieuXuatDAO();

    }

    // Tính tổng SP bán ra
    public int getTongSoSPBanRa(Date startDate, Date endDate) throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(SoLuong) AS TongSoSP FROM XUATHANG WHERE NgayXuat >= ? AND NgayXuat < ?";
        int tongSoSP = 0;

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(sql);
            pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
            pstmt.setDate(2, new java.sql.Date(endDate.getTime()));

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                tongSoSP = rs.getInt("TongSoSP");
            }
        } finally {
            dbConnection.close();
        }

        return tongSoSP;
    }

    // Tính tổng số SP bán ra hôm nay
    public int tinhTongSoSPBanRaHomNay() throws SQLException, ClassNotFoundException {
        // Xác định mốc thời gian bắt đầu và kết thúc của ngày hôm nay
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startDateToday = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 1); // Chuyển đến đầu ngày hôm sau
        Date endDateToday = cal.getTime();

        int tongSPBanRa = 0;

        tongSPBanRa = getTongSoSPBanRa(startDateToday, endDateToday);

        return tongSPBanRa;
    }

    // Tính tổng doanh thu hôm nay
    public BigDecimal tinhTongDoanhThuHomNay() throws SQLException, ClassNotFoundException {
        BigDecimal tongDoanhThu = BigDecimal.ZERO;

        // Xác định thời gian bắt đầu và kết thúc của ngày hôm nay
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startDateToday = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 1); // Chuyển đến đầu ngày hôm sau
        Date endDateToday = cal.getTime();

        String sql = "SELECT SUM(TriGia) AS TongDoanhThu FROM HOADON WHERE NgayHD >= ? AND NgayHD < ?";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(sql);
            pstmt.setDate(1, new java.sql.Date(startDateToday.getTime()));
            pstmt.setDate(2, new java.sql.Date(endDateToday.getTime()));

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                tongDoanhThu = rs.getBigDecimal("TongDoanhThu");
                 System.out.println("Doanh thu: " + tongDoanhThu);
            }
        } finally {
            dbConnection.close();
        }

        return tongDoanhThu;
       
    }

    // Tính tổng khách hàng hôm nay
    public int getTongSoKhachHangHomNay() throws SQLException, ClassNotFoundException {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startDateToday = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 1); // Move to the start of the next day
        Date endDateToday = cal.getTime();

        int tongKhachHang = 0;

        String sql = "SELECT COUNT(DISTINCT MaKH) AS TongKhachHang "
                + "FROM XUATHANG "
                + "WHERE NgayXuat >= ? AND NgayXuat < ?";

        try {
            dbConnection.open();
            PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(sql);

            // Set parameters for the prepared statement
            pstmt.setTimestamp(1, new java.sql.Timestamp(startDateToday.getTime()));
            pstmt.setTimestamp(2, new java.sql.Timestamp(endDateToday.getTime()));

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                tongKhachHang = rs.getInt("TongKhachHang");
            }
        } finally {
            dbConnection.close();
        }

        return tongKhachHang;
    }

    

}
