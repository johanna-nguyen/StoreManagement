package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.DanhMuc;
import view.HoaDonJPanel;
import view.KhachHangJPanel;
import view.KhoJPanel;
import view.NhaCungCapJPanel;
import view.NhanVienJPanel;
import view.PhieuNhapJPanel;
import view.PhieuXuatJPanel;
import view.SanPhamJPanel;
import view.ThongKeJPanel;
import view.TrangChuJPanel;

/**
 *
 * @author khanhnguyen
 */
public class ChuyenManHinhController {

    private JPanel root;
    private String kindSelected = "";
    private List<DanhMuc> listItem = null;

    public ChuyenManHinhController(JPanel jpnRoot) {
        this.root = jpnRoot;
    }

    public void setDashboard(JPanel jpnItem, JLabel jlbItem) {
        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new TrangChuJPanel());
        root.validate();
        root.repaint();
    }

    public void setEvent(List<DanhMuc> listDanhMuc) {
        this.listItem = listDanhMuc;
        for (DanhMuc item : listDanhMuc) {
            item.getJlb().addMouseListener(new LabelEvent(item.getKind(), item.getJpn(), item.getJlb()));
        }
    }

    class LabelEvent implements MouseListener {

        private JPanel node;
        private String kind;
        private JPanel jpnItem;
        private JLabel jlbItem;

        public LabelEvent(String kind, JPanel jpnItem, JLabel jlbItem) {
            this.kind = kind;
            this.jpnItem = jpnItem;
            this.jlbItem = jlbItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            switch (kind) {
                case "TrangChu":
                    node = new TrangChuJPanel();
                    break;
                case "SanPham": {
                    try {
                        node = new SanPhamJPanel();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ChuyenManHinhController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "KhachHang": {
                    try {
                        node = new KhachHangJPanel();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ChuyenManHinhController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "NhaCungCap": {
                    try {
                        node = new NhaCungCapJPanel();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ChuyenManHinhController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "HoaDon": {
                    try {
                        node = new HoaDonJPanel();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ChuyenManHinhController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "NhanVien": {
                    try {
                        node = new NhanVienJPanel();
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(ChuyenManHinhController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "Kho": {
                    try {
                        node = new KhoJPanel();
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(ChuyenManHinhController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "PhieuNhap":{
                    try {
                        node = new PhieuNhapJPanel();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ChuyenManHinhController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "PhieuXuat":{
                    try {
                        node = new PhieuXuatJPanel();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ChuyenManHinhController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "ThongKe": {
                try {
                    node = new ThongKeJPanel();
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(ChuyenManHinhController.class.getName()).log(Level.SEVERE, null, ex);
                }
                }

                default:
                    break;
            }
            root.removeAll();
            root.setLayout(new BorderLayout());
            root.add(node);
            root.validate();
            root.repaint();
            setChangeBackground(kind);

        }

        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
            jpnItem.setBackground(new Color(74, 144, 226));
            jlbItem.setBackground(new Color(74, 144, 226));
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            jpnItem.setBackground(new Color(74, 144, 226));
            jlbItem.setBackground(new Color(74, 144, 226));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (!kindSelected.equalsIgnoreCase(kind)) {
                jpnItem.setBackground(new Color(167, 199, 231));
                jlbItem.setBackground(new Color(167, 199, 231));
            }
        }

        @Override
        public void mouseReleased(MouseEvent me) {
        }

    }

    private void setChangeBackground(String kind) {

        for (DanhMuc item : listItem) {
            if (item.getKind().equalsIgnoreCase(kind)) {
                item.getJpn().setBackground(new Color(74, 144, 226));
                item.getJlb().setBackground(new Color(74, 144, 226));
            } else {
                item.getJpn().setBackground(new Color(167, 199, 231));
                item.getJlb().setBackground(new Color(167, 199, 231));
            }
        }
    }

    public void setView(JPanel jpnItem, JLabel jlbItem) throws ClassNotFoundException, SQLException {
        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(jpnItem, BorderLayout.CENTER);
        root.validate();
        root.repaint();
    }
}
