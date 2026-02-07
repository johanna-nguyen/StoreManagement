
package model;

import java.util.Date;

/**
 *
 * @author khanhnguyen
 */

public class KhachHang {
    private String maKh;
    private String tenKh;
    private Date ngaySinh;
    private String gioiTinh;
    private String diaChi;
    private String dienThoai;
    private String email;
    private String loaiKh;

    // Constructors, Getters, and Setters
    public KhachHang(String maKh, String tenKh, Date ngaySinh, String gioiTinh, String diaChi, String dienThoai, String email, String loaiKh) {
        this.maKh = maKh;
        this.tenKh = tenKh;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.dienThoai = dienThoai;
        this.email = email;
        this.loaiKh = loaiKh;
    }

    // Getters and Setters
    public String getMaKh() { return maKh; }
    public void setMaKh(String maKh) { this.maKh = maKh; }

    public String getTenKh() { return tenKh; }
    public void setTenKh(String tenKh) { this.tenKh = tenKh; }

    public Date getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getLoaiKh() { return loaiKh; }
    public void setLoaiKh(String loaiKh) { this.loaiKh = loaiKh; }
}
