
package view;

import com.toedter.calendar.JDateChooser;
import controller.SanPhamController;
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
import model.SanPham;
import utils.DataValidator;

/**
 *
 * @author khanhnguyen
 */
public class SanPhamJPanel extends javax.swing.JPanel {

    private final SanPhamController sanPhamController;

    /**
     * Creates new form TrangChuJPanel
     *
     * @throws java.lang.ClassNotFoundException
     */
    public SanPhamJPanel() throws ClassNotFoundException {
        initComponents();
        sanPhamController = new SanPhamController(this);
        loadSanPhamData();
        loadMaNCCToComboBox();

        // Bắt sự kiện trường Search
        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSearchKeyPressed(evt);
            }
        });

    }

    public JComboBox<String> getjCbDVT() {
        return jCbDVT;
    }

    public void setjCbDVT(JComboBox<String> jCbDVT) {
        this.jCbDVT = jCbDVT;
    }

    public JComboBox<String> getjCbMaNCC() {
        return jCbMaNCC;
    }

    public void setjCbMaNCC(JComboBox<String> jCbMaNCC) {
        this.jCbMaNCC = jCbMaNCC;
    }

    public JComboBox<String> getjCbNuocSX() {
        return jCbNuocSX;
    }

    public void setjCbNuocSX(JComboBox<String> jCbNuocSX) {
        this.jCbNuocSX = jCbNuocSX;
    }

    public JComboBox<String> getjCbSearch() {
        return jCbSearch;
    }

    public void setjCbSearch(JComboBox<String> jCbSearch) {
        this.jCbSearch = jCbSearch;
    }

    public JDateChooser getjDateNgayCapNhat() {
        return jDateNgayHetHan;
    }

    public void setjDateNgayCapNhat(JDateChooser jDateNgayCapNhat) {
        this.jDateNgayHetHan = jDateNgayCapNhat;
    }

    public JTable getjTableSanPham() {
        return jTableSanPham;
    }

    public void setjTableSanPham(JTable jTableSanPham) {
        this.jTableSanPham = jTableSanPham;
    }

    public JTextField getjTextGiaBan() {
        return jTextGiaBan;
    }

    public void setjTextGiaBan(JTextField jTextGiaBan) {
        this.jTextGiaBan = jTextGiaBan;
    }

    public JTextField getjTextMaSP() {
        return jTextMaSP;
    }

    public void setjTextMaSP(JTextField jTextMaSP) {
        this.jTextMaSP = jTextMaSP;
    }

    public JTextField getjTextSoLuongTon() {
        return jTextSoLuongTon;
    }

    public void setjTextSoLuongTon(JTextField jTextSoLuongTon) {
        this.jTextSoLuongTon = jTextSoLuongTon;
    }

    public JTextField getjTextTenSP() {
        return jTextTenSP;
    }

    public void setjTextTenSP(JTextField jTextTenSP) {
        this.jTextTenSP = jTextTenSP;
    }

    public DefaultTableModel getTableModel() {
        return (DefaultTableModel) jTableSanPham.getModel();
    }

    // Load bảng sản phầm
    public void loadSanPhamData() throws ClassNotFoundException {
        List<SanPham> sanPhamList = sanPhamController.getAllSanPham();
        DefaultTableModel model = (DefaultTableModel) jTableSanPham.getModel();
        model.setRowCount(0);

        for (SanPham sp : sanPhamList) {
            model.addRow(new Object[]{
                sp.getMaSP(),
                sp.getTenSP(),
                sp.getDvt(),
                sp.getNuocSX(),
                sp.getGiaBan(),
                sp.getSoLuongTon(),
                sp.getMaNCC(),
                sp.getNgayHetHan(),
                sp.getHinhAnhSP()
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
        jTextMaSP.setText("");
        jTextTenSP.setText("");
        jCbDVT.setSelectedIndex(0);
        jCbNuocSX.setSelectedIndex(0);
        jTextGiaBan.setText("");
        jTextSoLuongTon.setText("");
        jCbMaNCC.setSelectedIndex(0);
        jDateNgayHetHan.setDate(null);
    }

    // thêm dữ liệu Mã NCC
    private void loadMaNCCToComboBox() {
        sanPhamController.loadMaNCCToComboBox();
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
        jTableSanPham = new javax.swing.JTable();
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
        jTextMaSP = new javax.swing.JTextField();
        jTextTenSP = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jCbNuocSX = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jCbDVT = new javax.swing.JComboBox<>();
        jDateNgayHetHan = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jTextGiaBan = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextSoLuongTon = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jCbMaNCC = new javax.swing.JComboBox<>();
        jLabelPicture = new javax.swing.JLabel();
        jTextPicture = new javax.swing.JTextField();
        jButtonChoosePicture = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jlbReset = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextMinPrice = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextMaxPrice = new javax.swing.JTextField();
        jButtonFilterPrice = new javax.swing.JButton();

        jpnView.setBackground(new java.awt.Color(255, 255, 255));

        jTableSanPham.setFont(new java.awt.Font("Helvetica Neue", 0, 10)); // NOI18N
        jTableSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Product ID", "Name", "Unit", "Made in", "Price", "Stock Quantity", "Suplier ID", "Expiration Date", "Product Image"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableSanPham.getTableHeader().setReorderingAllowed(false);
        jTableSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableSanPham);
        if (jTableSanPham.getColumnModel().getColumnCount() > 0) {
            jTableSanPham.getColumnModel().getColumn(8).setMinWidth(0);
            jTableSanPham.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTableSanPham.getColumnModel().getColumn(8).setMaxWidth(0);
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

        jCbSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Search by Name", "Search by Country", "Search by Price" }));

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

        jLabel2.setText("Product ID");

        jLabel3.setText("Name");

        jCbNuocSX.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vietnam", "France", "Korea", "Japan", "China", "USA", "UK", "Germany", "Italy", " " }));

        jLabel4.setText("Unit");

        jLabel11.setText("Made in");

        jCbDVT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Box", "Bottle", "Piece", "Unit", "Tube", " " }));

        jLabel5.setText("Price");

        jLabel9.setText("Stock Quantity");

        jLabel13.setText("Supplier ID");

        jLabel14.setText("Expiration Date");

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
        jButtonChoosePicture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChoosePictureActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel18.setText("Product Information");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9)
                            .addComponent(jLabel5)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextSoLuongTon, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jDateNgayHetHan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                                        .addComponent(jCbMaNCC, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(70, 70, 70))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextGiaBan, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCbNuocSX, javax.swing.GroupLayout.Alignment.LEADING, 0, 147, Short.MAX_VALUE)
                                    .addComponent(jTextTenSP, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextMaSP, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCbDVT, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPicture, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(19, 19, 19))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jTextPicture, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonChoosePicture)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel18)
                .addGap(30, 30, 30)
                .addComponent(jLabelPicture, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextPicture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonChoosePicture))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jCbDVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jCbNuocSX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextSoLuongTon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCbMaNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateNgayHetHan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jpnViewLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnViewLayout.createSequentialGroup()
                        .addGroup(jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnViewLayout.createSequentialGroup()
                                .addComponent(jlbAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlbUpdate)
                                .addGap(18, 18, 18)
                                .addComponent(jlbDelete)
                                .addGap(18, 18, 18)
                                .addComponent(jlbPrint)
                                .addGap(18, 18, 18)
                                .addComponent(jlbExcel)
                                .addGap(12, 12, 12)
                                .addComponent(jlbCSV)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jlbReset)
                                .addGap(0, 106, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnViewLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel16)))
                        .addGap(18, 18, 18)
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
                    .addGroup(jpnViewLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jpnViewLayout.setVerticalGroup(
            jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnViewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jlbAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbCSV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlbExcel, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGroup(jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jlbReset))
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
                        .addGroup(jpnViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jTextMinPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(jTextMaxPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonFilterPrice))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
                int selectedRow = jTableSanPham.getSelectedRow();
                if (selectedRow != -1) {
                    String maSP = (String) jTableSanPham.getValueAt(selectedRow, 0);
                    // Gọi phương thức cập nhật hình ảnh sản phẩm vào cơ sở dữ liệu
                    sanPhamController.updateHinhAnhSP(maSP, filePath);
                }
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Unable to display the image: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonChoosePictureMouseClicked

    // Nút thêm
    private void jlbAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbAddMouseClicked
        String maSP = jTextMaSP.getText();
        String currentMaSP = sanPhamController.getCurrentMaSP();
        String tenSP = jTextTenSP.getText();
        String dvt = (String) jCbDVT.getSelectedItem();
        String nuocSX = (String) jCbNuocSX.getSelectedItem();

        BigDecimal giaBan = null;
        giaBan = new BigDecimal(jTextGiaBan.getText());

        int soLuongTon = 0;
        soLuongTon = Integer.parseInt(jTextSoLuongTon.getText());

        String maNCC = (String) jCbMaNCC.getSelectedItem();
        Date ngayHetHan = jDateNgayHetHan.getDate();

        String hinhAnhSP = jTextPicture.getText();  

        // Kiểm tra dữ liệu đã nhập đủ chưa
        DataValidator.isValidDataSP(maSP, tenSP, dvt, nuocSX, giaBan, maNCC, ngayHetHan);

        // Kiểm tra mã nhập có bị trùng không
        try {
            if (sanPhamController.isMaSPExist(maSP, currentMaSP)) {
                JOptionPane.showMessageDialog(this, "Product ID already exists. Please enter a different ID");
                return; // Dừng thực hiện nếu mã đã tồn tại
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SanPhamJPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error checking Product ID");
            return; // Dừng thực hiện nếu có lỗi
        }

        SanPham newSanPham = new SanPham(maSP, tenSP, dvt, nuocSX, giaBan, soLuongTon, maNCC, new java.sql.Date(ngayHetHan.getTime()), hinhAnhSP);

        // Thêm sp mới
        try {
            sanPhamController.addSanPham(newSanPham);
            JOptionPane.showMessageDialog(this, "Added successful");

            // Làm mới bảng và hiển thị lại dữ liệu
            loadSanPhamData();

            // Xóa các trường dữ liệu
            clearFormField();

        } catch (HeadlessException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Failed to add product; " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(SanPhamJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jlbAddMouseClicked

    // Nút sửa
    private void jlbUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbUpdateMouseClicked
        String maSP = jTextMaSP.getText();
        String currentMaSP = sanPhamController.getCurrentMaSP();
        String tenSP = jTextTenSP.getText();
        String dvt = (String) jCbDVT.getSelectedItem();
        String nuocSX = (String) jCbNuocSX.getSelectedItem();

        BigDecimal giaBan = null;
        giaBan = new BigDecimal(jTextGiaBan.getText());

        int soLuongTon = 0;
        soLuongTon = Integer.parseInt(jTextSoLuongTon.getText());

        String maNCC = (String) jCbMaNCC.getSelectedItem();
        Date ngayHetHan = jDateNgayHetHan.getDate();

        // Kiểm tra dữ liệu đã nhập đủ chưa
        DataValidator.isValidDataSP(maSP, tenSP, dvt, nuocSX, giaBan, maNCC, ngayHetHan);

        // Cập nhật thông tin sp
        try {
            sanPhamController.updateSanPham();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SanPhamJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jlbUpdateMouseClicked

    // Nút in bảng
    private void jlbPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbPrintMouseClicked
        boolean inThanhCong = true;
        try {
            inThanhCong = jTableSanPham.print(JTable.PrintMode.FIT_WIDTH, null, null);
        } catch (PrinterException ex) {
            Logger.getLogger(SanPhamJPanel.class.getName()).log(Level.SEVERE, null, ex);
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
        int selectedRow = jTableSanPham.getSelectedRow();

        // Kiểm tra người dùng đã chọn chưa
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Lấy MASP từ dòng muốn xoá
        String maSP = jTableSanPham.getValueAt(selectedRow, 0).toString();

        // Xác nhận xoá
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete?", "Notification", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                SanPham sanPham = new SanPham(maSP, "", null, null, BigDecimal.ZERO, 0, "", new Date(), null);

                // Gọi phương thức xoá
                sanPhamController.deleteSanPham(sanPham);

                // Hiển thị thông báo xoá thành 
                JOptionPane.showMessageDialog(this, "Xoá thành công");

                // Làm mới bảng
                loadSanPhamData();
            } catch (HeadlessException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                Logger.getLogger(SanPhamJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jlbDeleteMouseClicked

    // Nút xuất file excel
    private void jlbExcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbExcelMouseClicked
        // Gọi phương thức xuất file Excel
        sanPhamController.exportToExcel();

    }//GEN-LAST:event_jlbExcelMouseClicked

    // Nút xuất file csv
    private void jlbCSVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbCSVMouseClicked
        try {
            // Gọi phương thức xuất file CSV
            sanPhamController.exportToCSV();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SanPhamJPanel.class.getName()).log(Level.SEVERE, null, ex);
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
                            sanPhamController.searchInAllColumns(searchTerm);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(SanPhamJPanel.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "No results found");
                        }
                    }
                    jTextFieldSearch.setText("");
                    break;

                    case "Search by Name":
                        sanPhamController.searchByName(searchTerm);
                        jTextFieldSearch.setText("");
                        break;
                    case "Search by Country":
                        sanPhamController.searchByNuocSX(searchTerm);
                        jTextFieldSearch.setText("");
                        break;
                    case "Search by Price":
                        sanPhamController.searchByGiaBan(searchTerm);
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
    private void jTableSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSanPhamMouseClicked
        // Lấy dòng đã chọn
        int selectedRow = jTableSanPham.getSelectedRow();

        // Kiểm tra dòng đã được chọn
        if (selectedRow != -1) {
            // Lấy dữ liệu từ dòng đã chọn và đưa vào các ô nhập liệu
            String maSP = (String) jTableSanPham.getValueAt(selectedRow, 0);
            String tenSP = (String) jTableSanPham.getValueAt(selectedRow, 1);
            String dvt = (String) jTableSanPham.getValueAt(selectedRow, 2);
            String nuocSX = (String) jTableSanPham.getValueAt(selectedRow, 3);

            // Chuyển giá trị "Giá Bán" từ kiểu BigDecimal sang String
            BigDecimal giaBanValue = (BigDecimal) jTableSanPham.getValueAt(selectedRow, 4);
            String giaBan = giaBanValue.toString();

            Integer soLuongTonValue = (Integer) jTableSanPham.getValueAt(selectedRow, 5);
            String soLuongTon = soLuongTonValue.toString();

            String maNCC = (String) jTableSanPham.getValueAt(selectedRow, 6);
            Date ngayHetHan = (Date) jTableSanPham.getValueAt(selectedRow, 7);

            String hinhAnhSP = null;
            try {
                hinhAnhSP = sanPhamController.getHinhAnhByMaSP(maSP);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SanPhamJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Đưa dữ liệu vào các ô nhập liệu
            jTextMaSP.setText(maSP);
            jTextTenSP.setText(tenSP);
            jCbDVT.setSelectedItem(dvt);
            jCbNuocSX.setSelectedItem(nuocSX);
            jTextGiaBan.setText(giaBan);
            jTextSoLuongTon.setText(soLuongTon);
            jCbMaNCC.setSelectedItem(maNCC);
            jDateNgayHetHan.setDate(ngayHetHan);
            jTextPicture.setText("");

            // Hiển thị hình ảnh trong JLabel
            if (hinhAnhSP != null && !hinhAnhSP.isEmpty()) {
                try {
                    ImageIcon imageIcon = new ImageIcon(hinhAnhSP);
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

            // ngăn không chỉnh maSP
            jTextMaSP.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    int selectedRow = jTableSanPham.getSelectedRow();
                    if (selectedRow != -1) {
                        // Hiển thị thông báo nếu hàng đã được chọn
                        JOptionPane.showMessageDialog(null, "You do not have permission to edit the product ID", "Warning", JOptionPane.WARNING_MESSAGE);
                        jTextMaSP.setText(maSP);
                        jTextMaSP.setEditable(false);
                    }
                }
            });

        }
    }//GEN-LAST:event_jTableSanPhamMouseClicked

    // Nút làm mới
    private void jlbResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlbResetMouseClicked
        try {
            loadSanPhamData();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SanPhamJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        clearFormField();
        jTableSanPham.clearSelection();
        jTextMaSP.setEditable(true);
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
            sanPhamController.filterByPriceRange(minPriceInput, maxPriceInput);

            // Xóa giá trị trong JTextField 
            jTextMinPrice.setText("");
            jTextMaxPrice.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No results found", "Notification", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButtonFilterPriceMouseClicked

    private void jButtonChoosePictureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChoosePictureActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonChoosePictureActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonChoosePicture;
    private javax.swing.JButton jButtonFilterPrice;
    private javax.swing.JComboBox<String> jCbDVT;
    private javax.swing.JComboBox<String> jCbMaNCC;
    private javax.swing.JComboBox<String> jCbNuocSX;
    private javax.swing.JComboBox<String> jCbSearch;
    private com.toedter.calendar.JDateChooser jDateNgayHetHan;
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
    private javax.swing.JTable jTableSanPham;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JTextField jTextGiaBan;
    private javax.swing.JTextField jTextMaSP;
    private javax.swing.JTextField jTextMaxPrice;
    private javax.swing.JTextField jTextMinPrice;
    private javax.swing.JTextField jTextPicture;
    private javax.swing.JTextField jTextSoLuongTon;
    private javax.swing.JTextField jTextTenSP;
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
