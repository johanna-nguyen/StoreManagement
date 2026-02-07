/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDAO;
import javax.swing.JOptionPane;
import view.DangNhapUI;
import view.MainJFrame;

/**
 *
 * @author nguye
 */
public class DangNhapController {
    private DangNhapUI view;
    private UserDAO userDAO;

    public DangNhapController(DangNhapUI view) {
        this.view = view;
        this.userDAO = new UserDAO();
    }

    public void dangNhap(String email, String password) {
        try {
            if (userDAO.login(email, password)) {
                MainJFrame.getMainInstance().setVisible(true);
                view.dispose();
                
            } else {
                JOptionPane.showMessageDialog(
                    view,
                    "Invalid email or password",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                view,
                "Login error",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
}
