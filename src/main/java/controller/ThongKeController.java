package controller;

import com.toedter.calendar.JCalendar;
import dao.ThongKeDAO;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author khanhnguyen
 */
public class ThongKeController {

    private ThongKeDAO thongkeDAO;

    public ThongKeController() {
        this.thongkeDAO = new ThongKeDAO();
    }

    public int tinhTongSoSPBanRaHomNay() throws SQLException, ClassNotFoundException {
        return thongkeDAO.tinhTongSoSPBanRaHomNay();
    }

    public BigDecimal tinhTongDoanhThuHomNay() throws SQLException, ClassNotFoundException {
        return thongkeDAO.tinhTongDoanhThuHomNay();
    }

    public int getTongSoKhachHangHomNay() throws SQLException, ClassNotFoundException {
        return thongkeDAO.getTongSoKhachHangHomNay();
    }
    
}
