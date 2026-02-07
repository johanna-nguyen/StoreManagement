package view;

import com.toedter.calendar.JDateChooser;
import controller.NhanVienController;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.NhanVien;
import utils.DataValidator;

/**
 *
 * @author khanhnguyen
 */
public class NhanVienJPanel extends javax.swing.JPanel {

    private final NhanVienController nhanVienController;

    /**
     * Creates new form TrangChuJPanel
     *
     * @throws java.lang.ClassNotFoundException
     */
    public NhanVienJPanel() throws ClassNotFoundException, SQLException {
        initComponents();
        nhanVienController = new NhanVienController(this);
        loadNhanVienData();

        // Bắt sự kiện trường Search
        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSearchKeyPressed(evt);
            }
        });

    }

    public JComboBox<String> getjCbGioiTinh() {
        return jCbGioiTinh;
    }

    public void setjCbGioiTinh(JComboBox<String> jCbGioiTinh) {
        this.jCbGioiTinh = jCbGioiTinh;
    }

    public JDateChooser getjDateNgaySinh() {
        return jDateNgaySinh;
    }

    public void setjDateNgaySinh(JDateChooser jDateNgaySinh) {
        this.jDateNgaySinh = jDateNgaySinh;
    }

    public JTable getjTableNhanVien() {
        return jTableNhanVien;
    }

    public void setjTableNhanVien(JTable jTableNhanVien) {
        this.jTableNhanVien = jTableNhanVien;
    }

    public JTextField getjTextDiaChi() {
        return jTextDiaChi;
    }

    public void setjTextDiaChi(JTextField jTextDiaChi) {
        this.jTextDiaChi = jTextDiaChi;
    }

    public JTextField getjTextDienThoai() {
        return jTextDienThoai;
    }

    public void setjTextDienThoai(JTextField jTextDienThoai) {
        this.jTextDienThoai = jTextDienThoai;
    }

    public JTextField getjTextDoanhSo() {
        return jTextDoanhSo;
    }

    public void setjTextDoanhSo(JTextField jTextDoanhSo) {
        this.jTextDoanhSo = jTextDoanhSo;
    }

    public JTextField getjTextEmail() {
        return jTextEmail;
    }

    public void setjTextEmail(JTextField jTextEmail) {
        this.jTextEmail = jTextEmail;
    }

    public JTextField getjTextMaNV() {
        return jTextMaNV;
    }

    public void setjTextMaNV(JTextField jTextMaNV) {
        this.jTextMaNV = jTextMaNV;
    }

    public JTextField getjTextTenNV() {
        return jTextTenNV;
    }

    public void setjTextTenNV(JTextField jTextTenNV) {
        this.jTextTenNV = jTextTenNV;
    }

    public DefaultTableModel getTableModel() {
        return (DefaultTableModel) jTableNhanVien.getModel();
    }

    // Load bảng NV
    public void loadNhanVienData() throws ClassNotFoundException, SQLException {
        List<NhanVien> nhanVienList = nhanVienController.getAllNhanVien();
        DefaultTableModel model = (DefaultTableModel) jTableNhanVien.getModel();
        model.setRowCount(0);

        for (NhanVien nv : nhanVienList) {
            model.addRow(new Object[]{
                nv.getMaNV(),
                nv.getTenNV(),
                nv.getNgaySinh(),
                nv.getGioiTinh(),
                nv.getDiaChi(),
                nv.getDienThoai(),
                nv.getEmail(),
                nv.getDoanhSo(),
                nv.getHinhAnhNV()
            });
        }

    }

    // Khởi tạo hình ảnh mặc định
    private void setDefaultImage() {
    // Load ảnh từ thư mục resources/images trong classpath
    ImageIcon defaultImage = null;
    try {
        defaultImage = new ImageIcon(getClass().getResource("/image/default.png"));
        System.out.println("Link picture correct");
    } catch (NullPointerException e) {
        System.out.println("Cannot find default.png");
        return;
    }

    // Lấy hình ảnh từ ImageIcon và thay đổi kích thước
    Image imgDefault = defaultImage.getImage();
    Image scaledImgDefault = imgDefault.getScaledInstance(150, 150, Image.SCALE_SMOOTH);

    // Gán hình ảnh đã thay đổi kích thước cho JLabel
    jLabelPicture.setIcon(new ImageIcon(scaledImgDefault));

    }

    // Xoá các trường
    private void clearFormField() {
        jTextMaNV.setText("");
        jTextTenNV.setText("");
        jDateNgaySinh.setDate(null);
        jCbGioiTinh.setSelectedIndex(0);
        jTextDiaChi.setText("");
        jTextDienThoai.setText("");
        jTextEmail.setText("");
        jTextDoanhSo.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnView = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableNhanVien = new javax.swing.JTable();
        jlbAdd = new javax.swing.JLabel();
        jlbUpdate = new javax.swing.JLabel();
        jlbDelete = new javax.swing.JLabel();
        jlbPrint = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jlbCSV = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jlbExcel = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jCbSearch = new javax.swing.JComboBox<>();
        jTextFieldSearch = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jTextMaNV = new javax.swing.JTextField();
        jTextTenNV = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jCbGioiTinh = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jDateNgaySinh = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jTextDiaChi = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextEmail = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabelPicture = new javax.swing.JLabel();
        jTextPicture = new javax.swing.JTextField();
        jButtonChoosePicture = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jTextDienThoai = new javax.swing.JTextField();
        jTextDoanhSo = new javax.swing.JTextField();
        jlbReset = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextMinPrice = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextMaxPrice = new javax.swing.JTextField();
        jButtonFilterPrice = new javax.swing.JButton();

        jpnView.setBackground(new java.awt.Color(255, 255, 255));

        jTableNhanVien.setFont(new java.awt.Font("Helvetica Neue", 0, 10)); // NOI18N
        jTableNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Employee ID", "Name", "Birthday", "Gender", "Address", "Phone", "Email", "Sales", "Photo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableNhanVien.getTableHeader().setReorderingAllowed(false);
        jTableNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableNhanVien);
        if (jTableNhanVien.getColumnModel().getColumnCount() > 0) {
            jTableNhanVien.getColumnModel().getColumn(8).setMinWidth(0);
            jTableNhanVien.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTableNhanVien.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        jlbAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add.png"))); // NOI18N
        jlbAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlbAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbAddMouseClicked(evt);
            }
        });

        jlbUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/pencil.png"))); // NOI18N
        jlbUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlbUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbUpdateMouseClicked(evt);
            }
        });

        jlbDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete.png"))); // NOI18N
        jlbDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlbDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbDeleteMouseClicked(evt);
            }
        });

        jlbPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/printer.png"))); // NOI18N
        jlbPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlbPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbPrintMouseClicked(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("ADD");

        jLabel6.setForeground(new java.awt.Color(51, 51, 255));
        jLabel6.setText("EDIT");

        jLabel7.setForeground(new java.awt.Color(51, 51, 255));
        jLabel7.setText("DELETE");

        jLabel8.setForeground(new java.awt.Color(51, 51, 255));
        jLabel8.setText("PRINT");

        jlbCSV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/csv.png"))); // NOI18N
        jlbCSV.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlbCSV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbCSVMouseClicked(evt);
            }
        });

        jLabel10.setForeground(new java.awt.Color(51, 51, 255));
        jLabel10.setText("EXCEL");

        jlbExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/excel.png"))); // NOI18N
        jlbExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlbExcel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbExcelMouseClicked(evt);
            }
        });

        jLabel12.setForeground(new java.awt.Color(51, 51, 255));
        jLabel12.setText("CSV");

        jCbSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Search by Name", "Search by Address", "Search by Phone Number", "Search by Employee Sales" }));

        jTextFieldSearch.setForeground(new java.awt.Color(204, 204, 204));
        jTextFieldSearch.setText("Type to search...");
        jTextFieldSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldSearchMouseClicked(evt);
            }
        });
        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSearchKeyPressed(evt);
            }
        });

        jLabel2.setText("Employee ID (*)");

        jLabel3.setText("Name (*)");

        jCbGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        jLabel4.setText("Birthday(*)");

        jLabel11.setText("Gender");

        jLabel5.setText("Address (*)");

        jLabel9.setText("Phone (*)");

        jLabel13.setText("Email");

        jLabel14.setText("Employee Sales (*)");

        jLabelPicture.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButtonChoosePicture.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButtonChoosePicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/upload.png"))); // NOI18N
        jButtonChoosePicture.setText("Upload photo");
        jButtonChoosePicture.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonChoosePicture.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonChoosePictureMouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel18.setText("Employee Information");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel11)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextDoanhSo, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(51, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jDateNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jTextDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jCbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(46, Short.MAX_VALUE))))
                    .addComponent(jLabel5)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextPicture, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonChoosePicture))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel9))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPicture, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel18))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel13))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel18)
                .addGap(26, 26, 26)
                .addComponent(jLabelPicture, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonChoosePicture)
                    .addComponent(jTextPicture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jDateNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jCbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextDoanhSo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addContainerGap(85, Short.MAX_VALUE))
        );

        jlbReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/refresh.png"))); // NOI18N
        jlbReset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlbReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlbResetMouseClicked(evt);
            }
        });

        jLabel15.setForeground(new java.awt.Color(51, 51, 255));
        jLabel15.setText("REFRESH");

        jLabel16.setText("From");

        jLabel17.setText("To");

        jButtonFilterPrice.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButtonFilterPrice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/filter.png"))); // NOI18N
        jButtonFilterPrice.setText("Price Filter");
        jButtonFilterPrice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonFilterPriceMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpnViewLayout = new javax.swing.GroupLayout(jpnView);
        jpnView.setLayout(jpnViewLayout);
        jpnViewLayout.setHorizontalGroup(
            jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnViewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 676, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addGroup(jpnViewLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnViewLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18))
                    .addGroup(jpnViewLayout.createSequentialGroup()
                        .addGroup(jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnViewLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel15))
                            .addGroup(jpnViewLayout.createSequentialGroup()
                                .addComponent(jlbAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jlbUpdate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jlbDelete)
                                .addGap(18, 18, 18)
                                .addComponent(jlbPrint)
                                .addGap(18, 18, 18)
                                .addComponent(jlbExcel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jlbCSV)
                                .addGap(18, 18, 18)
                                .addComponent(jlbReset)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpnViewLayout.createSequentialGroup()
                        .addComponent(jCbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnViewLayout.createSequentialGroup()
                        .addComponent(jTextMinPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(jTextMaxPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonFilterPrice)
                        .addGap(56, 56, 56)))
                .addGap(36, 36, 36))
        );
        jpnViewLayout.setVerticalGroup(
            jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnViewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jlbReset, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbAdd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbUpdate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbDelete, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbPrint, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbCSV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbExcel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnViewLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jpnViewLayout.createSequentialGroup()
                        .addGroup(jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jTextMinPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(jTextMaxPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonFilterPrice))
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpnView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Nút tải ảnh
    private void jButtonChoosePictureMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonChoosePictureMouseClicked
        // Tạo một đối tượng JFileChooser
        JFileChooser fileChooser = new JFileChooser();

        // Thiết lập bộ lọc để chỉ cho phép chọn tệp hình ảnh
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() || file.getAbsolutePath().toLowerCase().endsWith(".jpg")
                        || file.getAbsolutePath().toLowerCase().endsWith(".jpeg")
                        || file.getAbsolutePath().toLowerCase().endsWith(".png")
                        || file.getAbsolutePath().toLowerCase().endsWith(".gif");
            }

            @Override
            public String getDescription() {
                return "Images (*.jpg, *.jpeg, *.png, *.gif)";
            }
        });

        // Mở hộp thoại chọn tệp
        int returnValue = fileChooser.showOpenDialog(null);

        // Kiểm tra xem người dùng đã chọn tệp hay không
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            // Hiển thị đường dẫn ảnh trong JTextField (hoặc JLabel)
            jTextPicture.setText(filePath);

            // Hiển thị hình ảnh trong JLabel
            try {
                ImageIcon imageIcon = new ImageIcon(filePath);
                Image img = imageIcon.getImage();
                Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Thay đổi kích thước
                jLabelPicture.setIcon(new ImageIcon(scaledImg));

                // Cập nhật đường dẫn hình ảnh trong Map nếu có sản phẩm đã chọn
                int selectedRow = jTableNhanVien.getSelectedRow();
                if (selectedRow != -1) {
                    String maNV = (String) jTableNhanVien.getValueAt(selectedRow, 0);
                    // Gọi phương thức cập nhật hình ảnh NV vào cơ sở dữ liệu
                    nhanVienController.updateHinhAnhNV(maNV, filePath);
                }
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Unable to display the image: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonChoosePictureMouseClicked

    // Nút thêm
    private void jlbAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbAddMouseClicked
        String maNV = jTextMaNV.getText();
        String currentMaNV = nhanVienController.getCurrentMaNV();
        String tenNV = jTextTenNV.getText();
        Date ngaySinh = jDateNgaySinh.getDate();
        String gioiTinh = (String) jCbGioiTinh.getSelectedItem();
        String diaChi = jTextDiaChi.getText();
        String dienThoai = jTextDienThoai.getText();
        String email = jTextEmail.getText();

        BigDecimal doanhSo = null;
        doanhSo = new BigDecimal(jTextDoanhSo.getText());

        String hinhAnhNV = jTextPicture.getText();  
        // Kiểm tra dữ liệu đã nhập đủ 
        DataValidator.isValidDataNV(maNV, tenNV, ngaySinh, diaChi, dienThoai, email, doanhSo);

        // Kiểm tra mã nv có bị trùng không
        try {
            if (nhanVienController.isMaNVExist(maNV, currentMaNV)) {
                JOptionPane.showMessageDialog(this, "Emmployee ID already exists. Please enter a different ID");
                return; // Dừng thực hiện nếu mã đã tồn tại
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error checking Employee ID");
            return; // Dừng thực hiện nếu có lỗi
        }

        NhanVien newNhanVien = new NhanVien(maNV, tenNV, new java.sql.Date(ngaySinh.getTime()), gioiTinh, diaChi, dienThoai, email, doanhSo, hinhAnhNV);

        // Thêm nv mới
        try {
            nhanVienController.addNhanVien(newNhanVien);
            JOptionPane.showMessageDialog(this, "Added succeful!");

            // Làm mới bảng và hiển thị lại dữ liệu
            loadNhanVienData();

            // Xóa các trường dữ liệu
            clearFormField();

        } catch (HeadlessException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Failed to add employee: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jlbAddMouseClicked

    // Nút sửa
    private void jlbUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbUpdateMouseClicked
        String maNV = jTextMaNV.getText();
        String currentMaNV = nhanVienController.getCurrentMaNV();
        String tenNV = jTextTenNV.getText();
        Date ngaySinh = jDateNgaySinh.getDate();
        String gioiTinh = (String) jCbGioiTinh.getSelectedItem();
        String diaChi = jTextDiaChi.getText();
        String dienThoai = jTextDienThoai.getText();
        String email = jTextEmail.getText();

        BigDecimal doanhSo = null;
        doanhSo = new BigDecimal(jTextDoanhSo.getText());

        String hinhAnhNV = jTextPicture.getText();  

        // Kiểm tra dữ liệu đã nhập đủ chưa
        DataValidator.isValidDataNV(maNV, tenNV, ngaySinh, diaChi, dienThoai, email, doanhSo);

        // Cập nhật thông tin sp
        try {
            nhanVienController.updateNhanVien();
            // Xóa các trường dữ liệu
            clearFormField();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jlbUpdateMouseClicked

    // Nút in bảng
    private void jlbPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbPrintMouseClicked
        boolean inThanhCong = true;
        try {
            inThanhCong = jTableNhanVien.print(JTable.PrintMode.FIT_WIDTH, null, null);
        } catch (PrinterException ex) {
            Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (inThanhCong) {
            JOptionPane.showMessageDialog(null, "Print successful!");
        } else {
            JOptionPane.showMessageDialog(null, "Print failed");
        }
    }//GEN-LAST:event_jlbPrintMouseClicked

    // Nút xoá
    private void jlbDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbDeleteMouseClicked
        // Chọn dòng xoá
        int selectedRow = jTableNhanVien.getSelectedRow();

        // Kiểm tra người dùng đã chọn chưa
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy MANV từ dòng muốn xoá
        String maNV = jTableNhanVien.getValueAt(selectedRow, 0).toString();

        // Xác nhận xoá
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete?", "Notification", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                NhanVien nhanVien = new NhanVien(maNV, "", new Date(), "", "", "", "", BigDecimal.ZERO, null);

                // Gọi phương thức xoá
                nhanVienController.deleteNhanVien(nhanVien);

                // Hiển thị thông báo xoá thành 
                JOptionPane.showMessageDialog(this, "Delete successful");

                // Làm mới bảng
                loadNhanVienData();
                
                // xoá các trường
                clearFormField();
            } catch (HeadlessException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jlbDeleteMouseClicked

    // Nút xuất file excel
    private void jlbExcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbExcelMouseClicked
        // Gọi phương thức xuất file Excel
        nhanVienController.exportToExcel();

    }//GEN-LAST:event_jlbExcelMouseClicked

    // Nút xuất file csv
    private void jlbCSVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbCSVMouseClicked
        try {
            // Gọi phương thức xuất file CSV
            nhanVienController.exportToCSV();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jlbCSVMouseClicked

    // Ô tìm kiếm
    private void jTextFieldSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyPressed
        if (jTextFieldSearch.getText().isEmpty()) {
            jTextFieldSearch.setText("");
            jTextFieldSearch.setForeground(Color.BLACK);
        }
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Lấy giá trị tìm kiếm từ JTextField
            String searchTerm = jTextFieldSearch.getText().trim();

            // Lấy lựa chọn hiện tại từ ComboBox
            String selectedOption = (String) jCbSearch.getSelectedItem();

            if (!searchTerm.isEmpty()) {
                switch (selectedOption) {
                    case "All": {
                        try {
                            nhanVienController.searchInAllColumns(searchTerm);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "Not results found");
                        }
                    }
                    jTextFieldSearch.setText("");
                    break;

                    case "Search by Name":
                        nhanVienController.searchByName(searchTerm);
                        jTextFieldSearch.setText("");
                        break;
                    case "Search by Address":
                        nhanVienController.searchByAdress(searchTerm);
                        jTextFieldSearch.setText("");
                        break;
                    case "Search by Phone Number":
                        nhanVienController.searchByPhone(searchTerm);
                        jTextFieldSearch.setText("");
                        break;
                    case "Search by Employee Sales":
                        nhanVienController.searchByDoanhSo(searchTerm);
                        jTextFieldSearch.setText("");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid search selection!");
                        break;
                }
            }
        }
    }//GEN-LAST:event_jTextFieldSearchKeyPressed

    // Chọn dòng trên bảng
    private void jTableNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableNhanVienMouseClicked
        // Lấy dòng đã chọn
        int selectedRow = jTableNhanVien.getSelectedRow();

        // Kiểm tra dòng đã được chọn
        if (selectedRow != -1) {
            // Lấy dữ liệu từ dòng đã chọn và đưa vào các ô nhập liệu
            String maNV = (String) jTableNhanVien.getValueAt(selectedRow, 0);
            String tenNV = (String) jTableNhanVien.getValueAt(selectedRow, 1);
            Date ngaySinh = (Date) jTableNhanVien.getValueAt(selectedRow, 2);
            String gioiTinh = (String) jTableNhanVien.getValueAt(selectedRow, 3);
            String diaChi = (String) jTableNhanVien.getValueAt(selectedRow, 4);
            String dienThoai = (String) jTableNhanVien.getValueAt(selectedRow, 5);
            String email = (String) jTableNhanVien.getValueAt(selectedRow, 6);

            // Chuyển từ kiểu BigDecimal sang String
            BigDecimal doanhSoValue = (BigDecimal) jTableNhanVien.getValueAt(selectedRow, 7);
            String doanhSo = doanhSoValue.toString();

            String hinhAnhNV = null;
            try {
                hinhAnhNV = nhanVienController.getHinhAnhByMaNV(maNV);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Đưa dữ liệu vào các ô nhập liệu
            jTextMaNV.setText(maNV);
            jTextTenNV.setText(tenNV);
            jDateNgaySinh.setDate(ngaySinh);
            jCbGioiTinh.setSelectedItem(gioiTinh);
            jTextDiaChi.setText(diaChi);
            jTextDienThoai.setText(dienThoai);
            jTextEmail.setText(email);
            jTextDoanhSo.setText(doanhSo);
            jTextPicture.setText("");

            // Hiển thị hình ảnh trong JLabel
            if (hinhAnhNV != null && !hinhAnhNV.isEmpty()) {
                try {
                    ImageIcon imageIcon = new ImageIcon(hinhAnhNV);
                    Image img = imageIcon.getImage();
                    Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Thay đổi kích thước
                    jLabelPicture.setIcon(new ImageIcon(scaledImg));

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Unable to display the image: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Nếu không có hình ảnh,  hình ảnh default trong JLabel
                setDefaultImage();
            }

            // ngăn không chỉnh maNV
            jTextMaNV.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    int selectedRow = jTableNhanVien.getSelectedRow();
                    if (selectedRow != -1) {
                        // Hiển thị thông báo nếu hàng đã được chọn
                        JOptionPane.showMessageDialog(null, "Customer ID already exists. Please enter a different ID", "Warning", JOptionPane.WARNING_MESSAGE);
                        jTextMaNV.setText(maNV);
                        jTextMaNV.setEditable(false);
                    }
                }
            });

        }
    }//GEN-LAST:event_jTableNhanVienMouseClicked

    // Nút làm mới
    private void jlbResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbResetMouseClicked
        try {
            loadNhanVienData();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(NhanVienJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        clearFormField();
        jTableNhanVien.clearSelection();
        jTextMaNV.setEditable(true);
        setDefaultImage();
    }//GEN-LAST:event_jlbResetMouseClicked

    private void jTextFieldSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldSearchMouseClicked
        jTextFieldSearch.setText("");
        jTextFieldSearch.setForeground(Color.BLACK);

    }//GEN-LAST:event_jTextFieldSearchMouseClicked

    // Nút lọc giá
    private void jButtonFilterPriceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonFilterPriceMouseClicked

        String minPriceInput = jTextMinPrice.getText();
        String maxPriceInput = jTextMaxPrice.getText();

        try {
            // Gọi phương thức lọc theo giá
            nhanVienController.filterByDoanhSo(minPriceInput, maxPriceInput);

            // Xóa giá trị trong JTextField 
            jTextMinPrice.setText("");
            jTextMaxPrice.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No results found", "Notification", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButtonFilterPriceMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonChoosePicture;
    private javax.swing.JButton jButtonFilterPrice;
    private javax.swing.JComboBox<String> jCbGioiTinh;
    private javax.swing.JComboBox<String> jCbSearch;
    private com.toedter.calendar.JDateChooser jDateNgaySinh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelPicture;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableNhanVien;
    private javax.swing.JTextField jTextDiaChi;
    private javax.swing.JTextField jTextDienThoai;
    private javax.swing.JTextField jTextDoanhSo;
    private javax.swing.JTextField jTextEmail;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JTextField jTextMaNV;
    private javax.swing.JTextField jTextMaxPrice;
    private javax.swing.JTextField jTextMinPrice;
    private javax.swing.JTextField jTextPicture;
    private javax.swing.JTextField jTextTenNV;
    private javax.swing.JLabel jlbAdd;
    private javax.swing.JLabel jlbCSV;
    private javax.swing.JLabel jlbDelete;
    private javax.swing.JLabel jlbExcel;
    private javax.swing.JLabel jlbPrint;
    private javax.swing.JLabel jlbReset;
    private javax.swing.JLabel jlbUpdate;
    private javax.swing.JPanel jpnView;
    // End of variables declaration//GEN-END:variables
}
