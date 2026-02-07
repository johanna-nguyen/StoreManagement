
package model;


/**
 *
 * @author khanhnguyen
 */

public class NhaCungCap {
    private String maNCC;
    private String tenNCC;
    private String diaChi;
    private String dienThoai;
    private String email;
    private String website;
    private String ghiChu;

    

    // Constructors, Getters, and Setters

    public NhaCungCap(String maNCC, String tenNCC, String diaChi, String dienThoai, String email, String website, String ghiChu) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.diaChi = diaChi;
        this.dienThoai = dienThoai;
        this.email = email;
        this.website = website;
        this.ghiChu = ghiChu;
    }
    
        // Getters and Setters

    public String getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    
}
