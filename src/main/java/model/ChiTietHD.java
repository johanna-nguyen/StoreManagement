package model;

import java.math.BigDecimal;

/**
 *
 * @author khanhnguyen
 */
public class ChiTietHD {

    private String maHD;
    private String maSP;
    private int soLuong;
    private BigDecimal donGia;
    private BigDecimal thanhTien;

    // Constructors, Getters, and Setters

    public ChiTietHD(String maHD, String maSP, int soLuong, BigDecimal donGia) {
        this.maHD = maHD;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = calculateThanhTien();

    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
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

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    // Phương thức tính ThanhTien
    public BigDecimal calculateThanhTien() {
        return donGia.multiply(BigDecimal.valueOf(soLuong));
    }

    // Getter cho thanhTien đã tính
    public BigDecimal getThanhTien() {
        return calculateThanhTien();
    }

    
    
}
