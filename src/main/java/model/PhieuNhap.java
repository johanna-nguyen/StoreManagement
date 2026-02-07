package model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author khanhnguyen
 */
public class PhieuNhap {

    private String maNhap;
    private String maSP;
    private int soLuong;
    private BigDecimal giaNhap;
    private Date ngayNhap;
    private String maNCC;

    // Constructors, Getters, and Setters

    public PhieuNhap(String maNhap, String maSP, int soLuong, BigDecimal giaNhap, Date ngayNhap, String maNCC) {
        this.maNhap = maNhap;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.ngayNhap = ngayNhap;
        this.maNCC = maNCC;
    }

    public String getMaNhap() {
        return maNhap;
    }

    public void setMaNhap(String maNhap) {
        this.maNhap = maNhap;
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

    public BigDecimal getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(BigDecimal giaNhap) {
        this.giaNhap = giaNhap;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

}
