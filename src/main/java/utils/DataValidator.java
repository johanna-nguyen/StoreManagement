/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.math.BigDecimal;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author khanhnguyen
 */
public class DataValidator {

    public static boolean isValidDataKH(String maKH, String tenKH, Date ngaySinh, String diaChi, String dienThoai, String email) {
        // Kiểm tra xem các trường có null hoặc rỗng hay không
        if (maKH == null || maKH.isEmpty()
                || tenKH == null || tenKH.isEmpty()
                || ngaySinh == null
                || diaChi == null || diaChi.isEmpty()
                || dienThoai == null || dienThoai.isEmpty()
                || email == null || email.isEmpty()) {

            JOptionPane.showMessageDialog(null, "Please fill in all required fields(*)", "Error", JOptionPane.ERROR_MESSAGE);
            return false; 
        }
        return true; 
    }

    // Kiểm tra tên khách hàng
    public static boolean isValidTenKH(String tenKH) {
        // Kiểm tra chỉ cho phép chữ cái và khoảng trắng
        if (!tenKH.matches("^[\\p{L}à-ýÀ-Ý\\s]+$")) {
            JOptionPane.showMessageDialog(null, "Invalid customer name. Only letters are allowed.", "Error", JOptionPane.ERROR_MESSAGE);
            return false; 
        }
        return true;
    }

    // Kiểm tra định dạng email 
    public static boolean checkFormatEmail(String email) {
        if (!email.matches(
                "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,4}$")) {
            JOptionPane.showMessageDialog(null, "Invalid email format", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // Kiểm tra số điện thoại
    public static boolean isValidSoDienThoai(String soDienThoai) {
        // Kiểm tra chỉ cho phép số
        if (!soDienThoai.matches("^[0-9]+$")) {
            JOptionPane.showMessageDialog(null, "Invalid phone number. Only numbers are allowed.", "Error", JOptionPane.ERROR_MESSAGE);
            return false; 
        }
        return true;
    }

    public static boolean isValidDataSP(String maSP, String tenSP, String dvt, String nuocSX, BigDecimal giaBan, String maNCC, Date ngayCapNhat) {
        // Kiểm tra xem các trường có null hoặc rỗng hay không
        if (maSP == null || maSP.isEmpty()
                || tenSP == null || tenSP.isEmpty()
                || dvt == null || dvt.isEmpty()
                || nuocSX == null || nuocSX.isEmpty()
                || giaBan == null
                || maNCC == null || maNCC.isEmpty()
                || ngayCapNhat == null) {
            JOptionPane.showMessageDialog(null, "Please fill in all required fields(*)", "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Trả về false nếu có trường không hợp lệ
        }
        return true; // Trả về true nếu tất cả các trường đều hợp lệ
    }

    public static boolean isValidDataNCC(String maNCC, String tenNCC, String diaChi, String dienThoai, String email) {
        // Kiểm tra xem các trường có null hoặc rỗng hay không
        if (maNCC == null || maNCC.isEmpty()
                || tenNCC == null || tenNCC.isEmpty()
                || diaChi == null || diaChi.isEmpty()
                || dienThoai == null || dienThoai.isEmpty()
                || email == null || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all required fields(*)", "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Trả về false nếu có trường không hợp lệ
        }
        return true; // Trả về true nếu tất cả các trường đều hợp lệ
    }

    public static boolean isValidPassword(String password) {
        // Kiểm tra độ dài mật khẩu
        if (password.length() < 8) {
            JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra chữ cái viết hoa
        if (!password.matches(".*[A-Z].*")) {
            JOptionPane.showMessageDialog(null, "Password must contain at least one uppercase letter", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra chữ cái viết thường
        if (!password.matches(".*[a-z].*")) {
            JOptionPane.showMessageDialog(null, "Password must contain at least one lowercase letter", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra chữ số
        if (!password.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(null, "Password must contain at least one digit", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra ký tự đặc biệt
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            JOptionPane.showMessageDialog(null, "\"Password must contain at least one special character", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Mật khẩu hợp lệ
        return true;
    }

    public static boolean isValidDataDangNhap(String email, String password) {
        // Kiểm tra xem các trường có null hoặc rỗng hay không
        if (email == null || email.isEmpty()
                || password == null || password.isEmpty()) {

            JOptionPane.showMessageDialog(null, "Please fill in all required fields(*)", "Error", JOptionPane.ERROR_MESSAGE);
            return false; 
        }
        return true; 
    }

    public static boolean isValidDataResetPassword(String password, String confirmPassword) {
        // Kiểm tra xem các trường có null hoặc rỗng hay không
        if (password == null || password.isEmpty()
                || confirmPassword == null || confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(null, "Please fill in all required fields(*)", "Error", JOptionPane.ERROR_MESSAGE);
            return false; 
        }
        return true; 
    }

    public static boolean isValidDataHD(String maHD, Date ngayHD, String maKH, String maNV, BigDecimal triGia) {
        // Kiểm tra xem các trường có null hoặc rỗng
        if (maHD == null || maHD.isEmpty()
                || ngayHD == null
                || maKH == null || maKH.isEmpty()
                || maNV == null || maNV.isEmpty()
                || triGia == null) {
            JOptionPane.showMessageDialog(null, "Please fill in all required fields(*)", "Error", JOptionPane.ERROR_MESSAGE);
            return false; 
        }
        return true; 
    }

    public static boolean isValidDataChiTietHD(String maHD, String maSP, Integer soLuong, BigDecimal donGia) {
        // Kiểm tra xem các trường có null hoặc rỗng 
        if (maHD == null || maHD.isEmpty()
                || maSP == null || maSP.isEmpty()
                || soLuong == null
                || donGia == null) {
            JOptionPane.showMessageDialog(null, "Please fill in all required fields(*)", "Error", JOptionPane.ERROR_MESSAGE);
            return false; 
        }
        return true; 
    }

    public static boolean isValidDataNV(String maNV, String tenNV, Date ngaySinh, String diaChi, String dienThoai, String email, BigDecimal doanhSo) {
        // Kiểm tra xem các trường có null hoặc rỗng 
        if (maNV == null || maNV.isEmpty()
                || tenNV == null || tenNV.isEmpty()
                || ngaySinh == null
                || diaChi == null || diaChi.isEmpty()
                || dienThoai == null || dienThoai.isEmpty()
                || email == null || email.isEmpty()
                || doanhSo == null) {

            JOptionPane.showMessageDialog(null, "Please fill in all required fields(*)", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true; 
    }

    public static boolean isValidDataPhieuNhap(String maNhap, String maSP, Integer soLuong, BigDecimal giaNhap, Date ngayNhap, String maNCC) {
        // Kiểm tra xem các trường có null hoặc rỗng
        if (maNhap == null || maNhap.isEmpty()
                || maSP == null || maSP.isEmpty()
                || soLuong == null
                || giaNhap == null
                || ngayNhap == null
                || maNCC == null || maNCC.isEmpty()) {

            JOptionPane.showMessageDialog(null, "Please fill in all required fields(*)", "Error", JOptionPane.ERROR_MESSAGE);
            return false; 
        }
        return true; 
    }
    
    public static boolean isValidDataPhieuXuat(String maXuat, String maSP, Integer soLuong, BigDecimal giaXuat, Date ngayXuat, String maKH) {
        // Kiểm tra xem các trường có null hoặc rỗng hay không
        if (maXuat == null || maXuat.isEmpty()
                || maSP == null || maSP.isEmpty()
                || soLuong == null
                || giaXuat == null
                || ngayXuat == null
                || maKH == null || maKH.isEmpty()) {

            JOptionPane.showMessageDialog(null, "Please fill in all required fields(*)", "Error", JOptionPane.ERROR_MESSAGE);
            return false; 
        }
        return true; 
    }
}
