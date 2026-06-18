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
public class LupaPasswordForm extends javax.swing.JFrame {

    private boolean password1Terlihat = false;
    private boolean password2Terlihat = false;
    private String captcha = "";

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LupaPasswordForm.class.getName());

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
     * Creates new form LupaPasswordForm
     */
    public LupaPasswordForm() {
        initComponents();

        generateCaptcha();

        txtPassword3.setText("Masukkan Password");
        txtPassword3.setEchoChar((char) 0);

        txtPassword2.setText("Konfirmasi Password");
        txtPassword2.setEchoChar((char) 0);

        cbOpsiLogin2.removeAllItems();
        cbOpsiLogin2.addItem("Username");
        cbOpsiLogin2.addItem("Email");
        cbOpsiLogin2.addItem("WhatshApp");
        cbOpsiLogin2.setBorder(null);
        cbOpsiLogin2.setBackground(java.awt.Color.WHITE);
        cbOpsiLogin2.setForeground(new java.awt.Color(102, 102, 102));
        txtOpsiLogin2.requestFocus();
    }

    private void generateCaptcha() {

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }

        captcha = sb.toString();
        txtOutputCaptcha.setText(captcha);
    }

    private void eyePassword1() {

        if (String.valueOf(txtPassword3.getPassword())
                .equals("Masukkan Password")) {
            return;
        }

        if (password1Terlihat) {

            txtPassword3.setEchoChar('•');

            btnEye1.setIcon(
                    new javax.swing.ImageIcon(
                            getClass().getResource(
                                    "/aplikasi_hijabella_collection/icon/hide 20px.png"
                            )
                    )
            );

            password1Terlihat = false;

        } else {

            txtPassword3.setEchoChar((char) 0);

            btnEye1.setIcon(
                    new javax.swing.ImageIcon(
                            getClass().getResource(
                                    "/aplikasi_hijabella_collection/icon/show 20px.png"
                            )
                    )
            );

            password1Terlihat = true;
        }
    }

    private void eyePassword2() {

        if (String.valueOf(txtPassword2.getPassword())
                .equals("Konfirmasi Password")) {
            return;
        }

        if (password2Terlihat) {

            txtPassword2.setEchoChar('•');

            btnEye2.setIcon(
                    new javax.swing.ImageIcon(
                            getClass().getResource(
                                    "/aplikasi_hijabella_collection/icon/hide 20px.png"
                            )
                    )
            );

            password2Terlihat = false;

        } else {

            txtPassword2.setEchoChar((char) 0);

            btnEye2.setIcon(
                    new javax.swing.ImageIcon(
                            getClass().getResource(
                                    "/aplikasi_hijabella_collection/icon/show 20px.png"
                            )
                    )
            );

            password2Terlihat = true;
        }
    }

    private void simpanPassword() {

        String opsi
                = cbOpsiLogin2.getSelectedItem().toString();

        String input
                = txtOpsiLogin2.getText().trim();

        String pass1
                = String.valueOf(
                        txtPassword3.getPassword()
                );

        String pass2
                = String.valueOf(
                        txtPassword2.getPassword()
                );

        String inputCaptcha
                = txtInputCaptcha.getText().trim();

        if (input.isEmpty()
                || pass1.equals("Masukkan Password")
                || pass2.equals("Konfirmasi Password")
                || inputCaptcha.equals("Masukkan Captcha")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Semua data wajib diisi!"
            );
            return;
        }

        if (!pass1.equals(pass2)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Konfirmasi password tidak sama!"
            );
            return;
        }

        if (!inputCaptcha.equalsIgnoreCase(captcha)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Captcha salah!"
            );

            generateCaptcha();
            return;
        }

        try {

            Connection conn
                    = koneksi.getConnection();

            String kolom = "";

            if (opsi.equals("Username")) {

                kolom = "username";

            } else if (opsi.equals("Email")) {

                kolom = "email";

            } else {

                kolom = "whatshapp";
            }

            String cekUser
                    = "SELECT * FROM user "
                    + "WHERE " + kolom + "=?";

            PreparedStatement pstCek
                    = conn.prepareStatement(
                            cekUser
                    );

            pstCek.setString(1, input);

            ResultSet rs
                    = pstCek.executeQuery();

            if (!rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        opsi + " tidak ditemukan!"
                );

                return;
            }

            String sql
                    = "UPDATE user "
                    + "SET password=? "
                    + "WHERE " + kolom + "=?";

            PreparedStatement pst
                    = conn.prepareStatement(sql);

            pst.setString(
                    1,
                    hashPassword(pass1)
            );

            pst.setString(
                    2,
                    input
            );

            int berhasil
                    = pst.executeUpdate();

            if (berhasil > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Password berhasil diubah!"
                );

                LoadingForm loading
                        = new LoadingForm(
                                "Membuka Halaman Login",
                                "Login"
                        );

                loading.setVisible(true);
                loading.mulaiLoading();

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Gagal mengubah password!"
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error : "
                    + e.getMessage()
            );
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

        btnEye1 = new javax.swing.JButton();
        txtPassword2 = new javax.swing.JPasswordField();
        txtPassword3 = new javax.swing.JPasswordField();
        txtInputCaptcha = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        txtOutputCaptcha = new javax.swing.JLabel();
        btnRefreshCaptcha = new javax.swing.JButton();
        btnEye2 = new javax.swing.JButton();
        lblLogin = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        cbOpsiLogin2 = new javax.swing.JComboBox<>();
        txtOpsiLogin2 = new javax.swing.JTextField();
        lblBackgroundLupaPassword = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Lupa Password");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnEye1.setForeground(new java.awt.Color(255, 255, 255));
        btnEye1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplikasi_hijabella_collection/icon/hide 20px.png"))); // NOI18N
        btnEye1.setBorder(null);
        btnEye1.setBorderPainted(false);
        btnEye1.setContentAreaFilled(false);
        btnEye1.setFocusPainted(false);
        btnEye1.addActionListener(this::btnEye1ActionPerformed);
        getContentPane().add(btnEye1, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 300, 50, 40));

        txtPassword2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPassword2.setForeground(new java.awt.Color(153, 153, 153));
        txtPassword2.setText("Konfirmasi Password");
        txtPassword2.setBorder(null);
        txtPassword2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassword2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassword2FocusLost(evt);
            }
        });
        txtPassword2.addActionListener(this::txtPassword2ActionPerformed);
        getContentPane().add(txtPassword2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 360, 230, 30));

        txtPassword3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPassword3.setForeground(new java.awt.Color(153, 153, 153));
        txtPassword3.setText("Masukkan Password");
        txtPassword3.setBorder(null);
        txtPassword3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassword3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassword3FocusLost(evt);
            }
        });
        getContentPane().add(txtPassword3, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 300, 230, 30));

        txtInputCaptcha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtInputCaptcha.setForeground(new java.awt.Color(153, 153, 153));
        txtInputCaptcha.setText("Masukkan Captcha");
        txtInputCaptcha.setBorder(null);
        txtInputCaptcha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtInputCaptchaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtInputCaptchaFocusLost(evt);
            }
        });
        getContentPane().add(txtInputCaptcha, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 410, 140, 30));

        btnSimpan.setBackground(new java.awt.Color(96, 60, 17));
        btnSimpan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSimpan.setForeground(new java.awt.Color(201, 160, 160));
        btnSimpan.setText("Simpan");
        btnSimpan.setBorder(null);
        btnSimpan.setBorderPainted(false);
        btnSimpan.setContentAreaFilled(false);
        btnSimpan.setFocusPainted(false);
        btnSimpan.addActionListener(this::btnSimpanActionPerformed);
        getContentPane().add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 460, 340, 30));

        txtOutputCaptcha.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txtOutputCaptcha.setForeground(new java.awt.Color(124, 101, 82));
        txtOutputCaptcha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtOutputCaptcha.setText("Captcha");
        getContentPane().add(txtOutputCaptcha, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 410, 110, 30));

        btnRefreshCaptcha.setForeground(new java.awt.Color(255, 255, 255));
        btnRefreshCaptcha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplikasi_hijabella_collection/icon/refreshcaptcha 20px.png"))); // NOI18N
        btnRefreshCaptcha.setBorder(null);
        btnRefreshCaptcha.setBorderPainted(false);
        btnRefreshCaptcha.setContentAreaFilled(false);
        btnRefreshCaptcha.setFocusPainted(false);
        btnRefreshCaptcha.addActionListener(this::btnRefreshCaptchaActionPerformed);
        getContentPane().add(btnRefreshCaptcha, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 410, 30, 30));

        btnEye2.setForeground(new java.awt.Color(255, 255, 255));
        btnEye2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplikasi_hijabella_collection/icon/hide 20px.png"))); // NOI18N
        btnEye2.setBorder(null);
        btnEye2.setBorderPainted(false);
        btnEye2.setContentAreaFilled(false);
        btnEye2.setFocusPainted(false);
        btnEye2.addActionListener(this::btnEye2ActionPerformed);
        getContentPane().add(btnEye2, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 360, 30, 30));

        lblLogin.setText("Tiba-tiba Ingat Password?");
        getContentPane().add(lblLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 500, 180, 20));

        btnLogin.setForeground(new java.awt.Color(51, 153, 255));
        btnLogin.setText("Login!");
        btnLogin.setBorder(null);
        btnLogin.setBorderPainted(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.addActionListener(this::btnLoginActionPerformed);
        getContentPane().add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 500, 100, -1));

        cbOpsiLogin2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbOpsiLogin2.setForeground(new java.awt.Color(153, 153, 153));
        cbOpsiLogin2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Username", "Email", "WhatshApp" }));
        cbOpsiLogin2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbOpsiLogin2.setFocusable(false);
        cbOpsiLogin2.addActionListener(this::cbOpsiLogin2ActionPerformed);
        getContentPane().add(cbOpsiLogin2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 190, 270, 30));

        txtOpsiLogin2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtOpsiLogin2.setForeground(new java.awt.Color(153, 153, 153));
        txtOpsiLogin2.setText("Masukkan Username");
        txtOpsiLogin2.setBorder(null);
        txtOpsiLogin2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtOpsiLogin2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtOpsiLogin2FocusLost(evt);
            }
        });
        getContentPane().add(txtOpsiLogin2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 240, 270, 40));

        lblBackgroundLupaPassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplikasi_hijabella_collection/background/lupapassword.png"))); // NOI18N
        getContentPane().add(lblBackgroundLupaPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEye1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEye1ActionPerformed
        // TODO add your handling code here:
        eyePassword1();
    }//GEN-LAST:event_btnEye1ActionPerformed

    private void txtPassword2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassword2FocusGained
        // TODO add your handling code here:
        if (String.valueOf(txtPassword2.getPassword())
                .equals("Konfirmasi Password")) {

            txtPassword2.setText("");
            txtPassword2.setEchoChar('•');
        }
    }//GEN-LAST:event_txtPassword2FocusGained

    private void txtPassword2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassword2FocusLost
        // TODO add your handling code here:
        if (String.valueOf(txtPassword2.getPassword())
                .trim().isEmpty()) {

            txtPassword2.setEchoChar((char) 0);
            txtPassword2.setText("Konfirmasi Password");
        }
    }//GEN-LAST:event_txtPassword2FocusLost

    private void txtPassword3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassword3FocusGained
        // TODO add your handling code here:
        if (String.valueOf(txtPassword3.getPassword())
                .equals("Masukkan Password")) {

            txtPassword3.setText("");
            txtPassword3.setEchoChar('•');
        }
    }//GEN-LAST:event_txtPassword3FocusGained

    private void txtPassword3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassword3FocusLost
        // TODO add your handling code here:
        if (String.valueOf(txtPassword3.getPassword())
                .trim().isEmpty()) {

            txtPassword3.setEchoChar((char) 0);
            txtPassword3.setText("Masukkan Password");
        }
    }//GEN-LAST:event_txtPassword3FocusLost

    private void txtInputCaptchaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtInputCaptchaFocusGained
        // TODO add your handling code here:
        if (txtInputCaptcha.getText().equals("Masukkan Captcha")) {
            txtInputCaptcha.setText("");
        }
    }//GEN-LAST:event_txtInputCaptchaFocusGained

    private void txtInputCaptchaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtInputCaptchaFocusLost
        // TODO add your handling code here:
        if (txtInputCaptcha.getText().trim().isEmpty()) {
            txtInputCaptcha.setText("Masukkan Captcha");
        }
    }//GEN-LAST:event_txtInputCaptchaFocusLost

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        simpanPassword();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnEye2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEye2ActionPerformed
        // TODO add your handling code here:
        eyePassword2();
    }//GEN-LAST:event_btnEye2ActionPerformed

    private void btnRefreshCaptchaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshCaptchaActionPerformed
        // TODO add your handling code here:
        generateCaptcha();
    }//GEN-LAST:event_btnRefreshCaptchaActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        LoadingForm loading
                = new LoadingForm("Membuka Halaman Login", "Login");

        loading.setVisible(true);
        loading.mulaiLoading();

        dispose();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtPassword2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassword2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassword2ActionPerformed

    private void txtOpsiLogin2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOpsiLogin2FocusGained
        // TODO add your handling code here:
        if (txtOpsiLogin2.getText().equals("Masukkan Username")
                || txtOpsiLogin2.getText().equals("Masukkan Email")
                || txtOpsiLogin2.getText().equals("Masukkan WhatshApp")) {

            txtOpsiLogin2.setText("");
        }
    }//GEN-LAST:event_txtOpsiLogin2FocusGained

    private void txtOpsiLogin2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOpsiLogin2FocusLost
        // TODO add your handling code here:
        if (txtOpsiLogin2.getText().trim().isEmpty()) {

            String opsi
                    = cbOpsiLogin2.getSelectedItem().toString();

            if (opsi.equals("Username")) {

                txtOpsiLogin2.setText(
                        "Masukkan Username"
                );

            } else if (opsi.equals("Email")) {

                txtOpsiLogin2.setText(
                        "Masukkan Email"
                );

            } else {

                txtOpsiLogin2.setText(
                        "Masukkan WhatshApp"
                );
            }
        }
    }//GEN-LAST:event_txtOpsiLogin2FocusLost

    private void cbOpsiLogin2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbOpsiLogin2ActionPerformed
        // TODO add your handling code here:
        Object selected = cbOpsiLogin2.getSelectedItem();
        if (selected == null) {
            return;
        }
        String text = txtOpsiLogin2.getText().trim();

        if (text.equals("Masukkan Username")
                || text.equals("Masukkan Email")
                || text.equals("Masukkan WhatshApp")) {

            txtOpsiLogin2.setText("");
        }
    }//GEN-LAST:event_cbOpsiLogin2ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new LupaPasswordForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEye1;
    private javax.swing.JButton btnEye2;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnRefreshCaptcha;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cbOpsiLogin2;
    private javax.swing.JLabel lblBackgroundLupaPassword;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JTextField txtInputCaptcha;
    private javax.swing.JTextField txtOpsiLogin2;
    private javax.swing.JLabel txtOutputCaptcha;
    private javax.swing.JPasswordField txtPassword2;
    private javax.swing.JPasswordField txtPassword3;
    // End of variables declaration//GEN-END:variables
}
