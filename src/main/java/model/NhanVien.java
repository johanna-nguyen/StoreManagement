package model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author khanhnguyen
 */
public class NhanVien {

    private String maNV;
    private String tenNV;
    private Date ngaySinh;
    private String gioiTinh;
    private String diaChi;
    private String dienThoai;
    private String email;
    private BigDecimal doanhSo;
    private String hinhAnhNV;

    // Constructors, Getters, and Setters

    public NhanVien(String maNV, String tenNV, Date ngaySinh, String gioiTinh, String diaChi, String dienThoai, String email, BigDecimal doanhSo, String hinhAnhNV) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.dienThoai = dienThoai;
        this.email = email;
        this.doanhSo = doanhSo;
        this.hinhAnhNV = hinhAnhNV;
    }

    
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getDoanhSo() {
        return doanhSo;
    }

    public void setDoanhSo(BigDecimal doanhSo) {
        this.doanhSo = doanhSo;
    }

    public String getHinhAnhNV() {
        return hinhAnhNV;
    }

    public void setHinhAnhNV(String hinhAnhNV) {
        this.hinhAnhNV = hinhAnhNV;
    }
    
    
    
}
