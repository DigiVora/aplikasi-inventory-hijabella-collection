/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aplikasi_hijabella_collection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import javax.swing.JOptionPane;

/**
 *
 * @author achmad_khusnul_yakin
 */
public class LoginForm extends javax.swing.JFrame {

    private boolean passwordTerlihat = false;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LoginForm.class.getName());

    private String hashPassword(String password) {

        try {

            java.security.MessageDigest md
                    = java.security.MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(
                    password.getBytes("UTF-8")
            );

            StringBuilder sb = new StringBuilder();

            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates new form LoginForm
     */
    public LoginForm() {
        initComponents();

        cbOpsiLogin.removeAllItems();
        cbOpsiLogin.addItem("Username");
        cbOpsiLogin.addItem("Email");
        cbOpsiLogin.addItem("WhatshApp");
        cbOpsiLogin.setBorder(null);
        cbOpsiLogin.setBackground(java.awt.Color.WHITE);
        cbOpsiLogin.setForeground(new java.awt.Color(102, 102, 102));
        txtOpsiLogin.requestFocus();
        txtPassword.setText("Masukkan Password");
        txtPassword.setEchoChar((char) 0);
    }

    private void eye() {

        if (String.valueOf(txtPassword.getPassword())
                .equals("Masukkan Password")) {
            return;
        }

        if (passwordTerlihat) {

            txtPassword.setEchoChar('•');

            btnEye.setIcon(
                    new javax.swing.ImageIcon(
                            getClass().getResource(
                                    "/aplikasi_hijabella_collection/icon/hide 20px.png"
                            )
                    )
            );

            passwordTerlihat = false;

        } else {

            txtPassword.setEchoChar((char) 0);

            btnEye.setIcon(
                    new javax.swing.ImageIcon(
                            getClass().getResource(
                                    "/aplikasi_hijabella_collection/icon/show 20px.png"
                            )
                    )
            );

            passwordTerlihat = true;
        }
    }

    void login() {

        String opsi = cbOpsiLogin.getSelectedItem().toString();
        String input = txtOpsiLogin.getText().trim();

        String password = hashPassword(
                String.valueOf(txtPassword.getPassword())
        );

        if (input.isEmpty()
                || String.valueOf(txtPassword.getPassword()).isEmpty()) {

            javax.swing.SwingUtilities.invokeLater(() -> {

                JOptionPane.showMessageDialog(
                        this,
                        "Data login wajib diisi!"
                );

                btnLogin.setText("Login");
                btnLogin.setEnabled(true);

            });

            return;
        }

        try {

            Connection conn = koneksi.getConnection();

            String sql = "";

            if (opsi.equals("Username")) {

                sql = "SELECT * FROM user "
                        + "WHERE username=? "
                        + "AND password=?";

            } else if (opsi.equals("Email")) {

                sql = "SELECT * FROM user "
                        + "WHERE email=? "
                        + "AND password=?";

            } else if (opsi.equals("WhatshApp")) {

                sql = "SELECT * FROM user "
                        + "WHERE whatshapp=? "
                        + "AND password=?";
            }

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, input);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                Thread.sleep(500);

                javax.swing.SwingUtilities.invokeLater(() -> {

                    LoadingForm loading
                            = new LoadingForm(
                                    "Login",
                                    "Dashboard"
                            );

                    loading.setVisible(true);
                    loading.mulaiLoading();

                    dispose();

                });

            } else {

                javax.swing.SwingUtilities.invokeLater(() -> {

                    btnLogin.setText("Login");
                    btnLogin.setEnabled(true);

                    JOptionPane.showMessageDialog(
                            this,
                            "Data Login Salah!"
                    );

                });

            }

        } catch (Exception e) {

            javax.swing.SwingUtilities.invokeLater(() -> {

                btnLogin.setText("Login");
                btnLogin.setEnabled(true);

                JOptionPane.showMessageDialog(
                        this,
                        "Error : " + e.getMessage()
                );

            });

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtOpsiLogin = new javax.swing.JTextField();
        btnEye = new javax.swing.JButton();
        btnLogin = new javax.swing.JButton();
        lblTeksDaftar = new javax.swing.JLabel();
        btnDaftar = new javax.swing.JButton();
        btnLupaPassword = new javax.swing.JButton();
        jpUsername = new javax.swing.JPanel();
        cbOpsiLogin = new javax.swing.JComboBox<>();
        txtPassword = new javax.swing.JPasswordField();
        lbBackgroundLogin = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login Akun");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtOpsiLogin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtOpsiLogin.setForeground(new java.awt.Color(153, 153, 153));
        txtOpsiLogin.setText("Masukkan Username");
        txtOpsiLogin.setBorder(null);
        txtOpsiLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtOpsiLoginFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtOpsiLoginFocusLost(evt);
            }
        });
        getContentPane().add(txtOpsiLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 320, 280, 30));

        btnEye.setForeground(new java.awt.Color(255, 255, 255));
        btnEye.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplikasi_hijabella_collection/icon/hide 20px.png"))); // NOI18N
        btnEye.setBorder(null);
        btnEye.setBorderPainted(false);
        btnEye.setContentAreaFilled(false);
        btnEye.setFocusPainted(false);
        btnEye.addActionListener(this::btnEyeActionPerformed);
        getContentPane().add(btnEye, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 380, 30, -1));

        btnLogin.setBackground(new java.awt.Color(96, 60, 17));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(201, 160, 160));
        btnLogin.setText("Login");
        btnLogin.setBorder(null);
        btnLogin.setBorderPainted(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setFocusPainted(false);
        btnLogin.addActionListener(this::btnLoginActionPerformed);
        getContentPane().add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 440, 340, 30));

        lblTeksDaftar.setText("Belum Punya Akun?");
        getContentPane().add(lblTeksDaftar, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 480, 120, -1));

        btnDaftar.setForeground(new java.awt.Color(51, 153, 255));
        btnDaftar.setText("Daftar Sekarang");
        btnDaftar.setBorder(null);
        btnDaftar.setBorderPainted(false);
        btnDaftar.setContentAreaFilled(false);
        btnDaftar.addActionListener(this::btnDaftarActionPerformed);
        getContentPane().add(btnDaftar, new org.netbeans.lib.awtextra.AbsoluteConstraints(783, 480, 100, -1));

        btnLupaPassword.setForeground(new java.awt.Color(51, 153, 255));
        btnLupaPassword.setText("Lupa Password");
        btnLupaPassword.setBorder(null);
        btnLupaPassword.setBorderPainted(false);
        btnLupaPassword.setContentAreaFilled(false);
        btnLupaPassword.setFocusPainted(false);
        btnLupaPassword.addActionListener(this::btnLupaPasswordActionPerformed);
        getContentPane().add(btnLupaPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 480, -1, -1));

        jpUsername.setBackground(new java.awt.Color(255, 255, 255));
        jpUsername.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbOpsiLogin.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbOpsiLogin.setForeground(new java.awt.Color(153, 153, 153));
        cbOpsiLogin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Username", "Email", "WhatshApp" }));
        cbOpsiLogin.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbOpsiLogin.setFocusable(false);
        cbOpsiLogin.addActionListener(this::cbOpsiLoginActionPerformed);
        jpUsername.add(cbOpsiLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 9, 270, 30));

        getContentPane().add(jpUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 260, 280, 40));

        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(153, 153, 153));
        txtPassword.setText("Masukkan Password");
        txtPassword.setBorder(null);
        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPasswordFocusLost(evt);
            }
        });
        txtPassword.addActionListener(this::txtPasswordActionPerformed);
        getContentPane().add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 370, 230, 40));

        lbBackgroundLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplikasi_hijabella_collection/background/login.png"))); // NOI18N
        getContentPane().add(lbBackgroundLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        btnLogin.setEnabled(false);

        new Thread(() -> {

            try {

                btnLogin.setText("Loading.");
                Thread.sleep(200);

                btnLogin.setText("Loading..");
                Thread.sleep(200);

                btnLogin.setText("Loading...");
                Thread.sleep(200);

                btnLogin.setText("Loading....");
                Thread.sleep(200);

                btnLogin.setText("Loading.....");
                Thread.sleep(200);

                login();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnDaftarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaftarActionPerformed
        // TODO add your handling code here:
        LoadingForm loading
                = new LoadingForm("Halaman Pendaftaran", "Daftar");

        loading.setVisible(true);
        loading.mulaiLoading();

        this.dispose();
    }//GEN-LAST:event_btnDaftarActionPerformed

    private void btnLupaPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLupaPasswordActionPerformed
        // TODO add your handling code here:
        LoadingForm loading
                = new LoadingForm("Halaman Lupa Password", "LupaPassword");

        loading.setVisible(true);
        loading.mulaiLoading();

        this.dispose();
    }//GEN-LAST:event_btnLupaPasswordActionPerformed

    private void txtOpsiLoginFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOpsiLoginFocusGained
        // TODO add your handling code here:
        if (txtOpsiLogin.getText().equals("Masukkan Username")
                || txtOpsiLogin.getText().equals("Masukkan Email")
                || txtOpsiLogin.getText().equals("Masukkan WhatshApp")) {
            txtOpsiLogin.setText("");
        }
    }//GEN-LAST:event_txtOpsiLoginFocusGained

    private void txtOpsiLoginFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOpsiLoginFocusLost
        // TODO add your handling code here:
        if (txtOpsiLogin.getText().trim().isEmpty()) {

            String opsi = cbOpsiLogin.getSelectedItem().toString();

            if (opsi.equals("Username")) {
                txtOpsiLogin.setText("Masukkan Username");
            } else if (opsi.equals("Email")) {
                txtOpsiLogin.setText("Masukkan Email");
            } else {
                txtOpsiLogin.setText("Masukkan WhatshApp");
            }
        }
    }//GEN-LAST:event_txtOpsiLoginFocusLost

    private void btnEyeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEyeActionPerformed
        // TODO add your handling code here:
        eye();
    }//GEN-LAST:event_btnEyeActionPerformed

    private void cbOpsiLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbOpsiLoginActionPerformed
        // TODO add your handling code here:
        Object selected = cbOpsiLogin.getSelectedItem();
        if (selected == null) {
            return;
        }
        String opsi = cbOpsiLogin.getSelectedItem().toString();
        if (opsi.equals("Username")) {
            txtOpsiLogin.setText("Masukkan Username");
        } else if (opsi.equals("Email")) {
            txtOpsiLogin.setText("Masukkan Email");
        } else if (opsi.equals("WhatshApp")) {
            txtOpsiLogin.setText("Masukkan WhatshApp");
        }
    }//GEN-LAST:event_cbOpsiLoginActionPerformed

    private void txtPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFocusGained
        // TODO add your handling code here:
        if (String.valueOf(txtPassword.getPassword())
                .equals("Masukkan Password")) {

            txtPassword.setText("");
            txtPassword.setEchoChar('•');
        }
    }//GEN-LAST:event_txtPasswordFocusGained

    private void txtPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFocusLost
        // TODO add your handling code here:
        if (String.valueOf(txtPassword.getPassword())
                .trim().isEmpty()) {

            txtPassword.setEchoChar((char) 0);
            txtPassword.setText("Masukkan Password");
        }
    }//GEN-LAST:event_txtPasswordFocusLost

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new LoginForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDaftar;
    private javax.swing.JButton btnEye;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnLupaPassword;
    private javax.swing.JComboBox<String> cbOpsiLogin;
    private javax.swing.JPanel jpUsername;
    private javax.swing.JLabel lbBackgroundLogin;
    private javax.swing.JLabel lblTeksDaftar;
    private javax.swing.JTextField txtOpsiLogin;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
