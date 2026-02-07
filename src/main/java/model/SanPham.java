package model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author khanhnguyen
 */
public class SanPham {

    private String maSP;
    private String tenSP;
    private String dvt;
    private String nuocSX;
    private BigDecimal giaBan;
    private int soLuongTon;
    private String maNCC;
    private Date ngayHetHan;
    private String hinhAnhSP;

    // Constructors, Getters, and Setters
    public SanPham(String maSP, String tenSP, String dvt, String nuocSX, BigDecimal giaBan, int soLuongTon, String maNCC, Date ngayHetHan, String hinhAnhSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.dvt = dvt;
        this.nuocSX = nuocSX;
        this.giaBan = giaBan;
        this.soLuongTon = soLuongTon;
        this.maNCC = maNCC;
        this.ngayHetHan = ngayHetHan;
        this.hinhAnhSP = hinhAnhSP;
    }

    // Getters and Setters
    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTen(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    public String getNuocSX() {
        return nuocSX;
    }

    public void setNuocSX(String nuocSX) {
        this.nuocSX = nuocSX;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public Date getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(Date ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public String getHinhAnhSP() {
        return hinhAnhSP;
    }

    public void setHinhAnhSP(String hinhAnhSP) {
        this.hinhAnhSP = hinhAnhSP;
    }

    
}
