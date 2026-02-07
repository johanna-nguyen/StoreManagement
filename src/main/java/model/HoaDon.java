
package model;

import java.math.BigDecimal;
import java.util.Date;


/**
 *
 * @author khanhnguyen
 */

public class HoaDon {
    private String maHD;
    private Date ngayHD;
    private String maKH;
    private String maNV;
    private BigDecimal triGia;

    // Constructors, Getters, and Setters

    public HoaDon(String maHD, Date ngayHD, String maKH, String maNV, BigDecimal triGia) {
        this.maHD = maHD;
        this.ngayHD = ngayHD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.triGia = triGia;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public Date getNgayHD() {
        return ngayHD;
    }

    public void setNgayHD(Date ngayHD) {
        this.ngayHD = ngayHD;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public BigDecimal getTriGia() {
        return triGia;
    }

    public void setTriGia(BigDecimal triGia) {
        this.triGia = triGia;
    }

 
   


    
}
