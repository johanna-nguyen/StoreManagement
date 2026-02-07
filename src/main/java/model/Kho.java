package model;

import java.util.Date;

/**
 *
 * @author khanhnguyen
 */
public class Kho {

    private String maSP;
    private int soLuongTon;
    private Date ngayCapNhatCuoi;
   

    // Constructors, Getters, and Setters

    public Kho(String maSP, int soLuongTon, Date ngayCapNhatCuoi) {
        this.maSP = maSP;
        this.soLuongTon = soLuongTon;
        this.ngayCapNhatCuoi = ngayCapNhatCuoi;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public Date getNgayCapNhatCuoi() {
        return ngayCapNhatCuoi;
    }

    public void setNgayCapNhatCuoi(Date ngayCapNhatCuoi) {
        this.ngayCapNhatCuoi = ngayCapNhatCuoi;
    }

    

}
