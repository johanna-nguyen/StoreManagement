package model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author khanhnguyen
 */
public class PhieuXuat {

    private String maXuat;
    private String maSP;
    private int soLuong;
    private BigDecimal giaXuat;
    private Date ngayXuat;
    private String maKH;

    // Constructors, Getters, and Setters

    public PhieuXuat(String maXuat, String maSP, int soLuong, BigDecimal giaXuat, Date ngayXuat, String maKH) {
        this.maXuat = maXuat;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.giaXuat = giaXuat;
        this.ngayXuat = ngayXuat;
        this.maKH = maKH;
    }

    public String getMaXuat() {
        return maXuat;
    }

    public void setMaXuat(String maXuat) {
        this.maXuat = maXuat;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getGiaXuat() {
        return giaXuat;
    }

    public void setGiaXuat(BigDecimal giaXuat) {
        this.giaXuat = giaXuat;
    }

    public Date getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(Date ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    

}
