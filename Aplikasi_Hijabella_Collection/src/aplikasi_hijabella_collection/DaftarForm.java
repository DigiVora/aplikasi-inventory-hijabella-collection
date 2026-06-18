/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aplikasi_hijabella_collection;

import java.time.LocalTime;
import java.util.Random;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import javax.swing.JOptionPane;
import java.security.MessageDigest;

/**
 *
 * @author achmad_khusnul_yakin
 */
public class DaftarForm extends javax.swing.JFrame {

    private boolean password1Terlihat = false;
    private boolean password2Terlihat = false;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DaftarForm.class.getName());

    private String generateIdUser(String whatsapp) {

        String chars
                = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();

        StringBuilder acak = new StringBuilder();

        for (int i = 0; i < 4; i++) {

            acak.append(
                    chars.charAt(
                            random.nextInt(chars.length())
                    )
            );
        }

        LocalTime now = LocalTime.now();

        String jamMenit = String.format(
                "%02d%02d",
                now.getHour(),
                now.getMinute()
        );

        String last4;

        if (whatsapp.length() >= 4) {

            last4 = whatsapp.substring(
                    whatsapp.length() - 4
            );

        } else {

            last4 = whatsapp;
        }

        return "USR-"
                + acak.toString()
                + "-"
                + jamMenit
                + "-"
                + last4;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));

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
     * Creates new form DaftarForm
     */
    public DaftarForm() {
        initComponents();

        txtPassword3.setText("Masukkan Password");
        txtPassword3.setEchoChar((char) 0);

        txtPassword4.setText("Konfirmasi Password");
        txtPassword4.setEchoChar((char) 0);

    }

    private void daftarAkun() {

        String nama = txtInputNamaLengkap.getText().trim();
        String username = txtInputUsername1.getText().trim();
        String email = txtInputEmail1.getText().trim();
        String whatsapp = txtInputWhatshApp.getText().trim();

        String password
                = String.valueOf(txtPassword3.getPassword());

        String konfirmasi
                = String.valueOf(txtPassword4.getPassword());

        if (nama.equals("Masukkan Nama Lengkap")
                || username.equals("Masukkan Username")
                || email.equals("Masukkan Email")
                || whatsapp.equals("Masukkan Nomor WhatshApp")
                || password.equals("Masukkan Password")
                || konfirmasi.equals("Masukkan Password")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Semua data wajib diisi!"
            );

            btnDaftar.setText("Daftar Sekarang");
            btnDaftar.setEnabled(true);

            return;
        }

        if (!password.equals(konfirmasi)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Konfirmasi password tidak sama!"
            );

            btnDaftar.setText("Daftar Sekarang");
            btnDaftar.setEnabled(true);

            return;
        }

        try {

            Connection conn = koneksi.getConnection();

            String cek = "SELECT * FROM user "
                    + "WHERE username=? "
                    + "OR email=? "
                    + "OR whatshapp=?";

            PreparedStatement pstCek
                    = conn.prepareStatement(cek);

            pstCek.setString(1, username);
            pstCek.setString(2, email);
            pstCek.setString(3, whatsapp);

            ResultSet rs = pstCek.executeQuery();

            if (rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Username, Email, atau WhatsApp sudah digunakan!"
                );

                btnDaftar.setText("Daftar Sekarang");
                btnDaftar.setEnabled(true);

                return;
            }

            String idUser
                    = generateIdUser(whatsapp);

            String sql
                    = "INSERT INTO user("
                    + "id_user,"
                    + "nama_lengkap,"
                    + "username,"
                    + "email,"
                    + "whatshapp,"
                    + "password"
                    + ") VALUES (?,?,?,?,?,?)";

            PreparedStatement pst
                    = conn.prepareStatement(sql);

            pst.setString(1, idUser);
            pst.setString(2, nama);
            pst.setString(3, username);
            pst.setString(4, email);
            pst.setString(5, whatsapp);
            pst.setString(6, hashPassword(password));

            pst.executeUpdate();

            JOptionPane.showMessageDialog(
                    this,
                    "Pendaftaran berhasil!\nID User : "
                    + idUser
            );

            btnDaftar.setText("Daftar Sekarang");
            btnDaftar.setEnabled(true);

            LoadingForm loading
                    = new LoadingForm(
                            "Halaman Login",
                            "Login"
                    );

            loading.setVisible(true);
            loading.mulaiLoading();

            dispose();

        } catch (Exception e) {

            btnDaftar.setText("Daftar Sekarang");
            btnDaftar.setEnabled(true);

            JOptionPane.showMessageDialog(
                    this,
                    "Error : " + e.getMessage()
            );
        }
    }

    private void placeholderKosong(javax.swing.JTextField field,
            String text) {

        if (field.getText().equals(text)) {
            field.setText("");
        }
    }

    private void placeholderIsi(javax.swing.JTextField field,
            String text) {

        if (field.getText().trim().isEmpty()) {
            field.setText(text);
        }
    }

    private void eyePassword1() {

        if (String.valueOf(txtPassword3.getPassword())
                .equals("Masukkan Password")) {
            return;
        }

        if (password1Terlihat) {

            txtPassword3.setEchoChar('•');

            btnEye3.setIcon(
                    new javax.swing.ImageIcon(
                            getClass().getResource(
                                    "/aplikasi_hijabella_collection/icon/hide 20px.png"
                            )
                    )
            );

            password1Terlihat = false;

        } else {

            txtPassword3.setEchoChar((char) 0);

            btnEye3.setIcon(
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

        if (String.valueOf(txtPassword4.getPassword())
                .equals("Konfirmasi Password")) {
            return;
        }

        if (password2Terlihat) {

            txtPassword4.setEchoChar('•');

            btnEye4.setIcon(
                    new javax.swing.ImageIcon(
                            getClass().getResource(
                                    "/aplikasi_hijabella_collection/icon/hide 20px.png"
                            )
                    )
            );

            password2Terlihat = false;

        } else {

            txtPassword4.setEchoChar((char) 0);

            btnEye4.setIcon(
                    new javax.swing.ImageIcon(
                            getClass().getResource(
                                    "/aplikasi_hijabella_collection/icon/show 20px.png"
                            )
                    )
            );

            password2Terlihat = true;
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

        txtInputNamaLengkap = new javax.swing.JTextField();
        txtInputWhatshApp = new javax.swing.JTextField();
        txtInputUsername1 = new javax.swing.JTextField();
        txtInputEmail1 = new javax.swing.JTextField();
        txtPassword3 = new javax.swing.JPasswordField();
        txtPassword4 = new javax.swing.JPasswordField();
        btnEye4 = new javax.swing.JButton();
        btnEye3 = new javax.swing.JButton();
        lblTeksLogin = new javax.swing.JLabel();
        btnDaftar = new javax.swing.JButton();
        btnLogin = new javax.swing.JButton();
        lblBackgroundDaftar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Daftar Akun");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtInputNamaLengkap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtInputNamaLengkap.setForeground(new java.awt.Color(153, 153, 153));
        txtInputNamaLengkap.setText("Masukkan Nama Lengkap");
        txtInputNamaLengkap.setBorder(null);
        txtInputNamaLengkap.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtInputNamaLengkapFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtInputNamaLengkapFocusLost(evt);
            }
        });
        getContentPane().add(txtInputNamaLengkap, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 110, 270, 40));

        txtInputWhatshApp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtInputWhatshApp.setForeground(new java.awt.Color(153, 153, 153));
        txtInputWhatshApp.setText("Masukkan Nomor WhatshApp");
        txtInputWhatshApp.setBorder(null);
        txtInputWhatshApp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtInputWhatshAppFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtInputWhatshAppFocusLost(evt);
            }
        });
        txtInputWhatshApp.addActionListener(this::txtInputWhatshAppActionPerformed);
        getContentPane().add(txtInputWhatshApp, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 280, 270, 30));

        txtInputUsername1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtInputUsername1.setForeground(new java.awt.Color(153, 153, 153));
        txtInputUsername1.setText("Masukkan Username");
        txtInputUsername1.setBorder(null);
        txtInputUsername1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtInputUsername1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtInputUsername1FocusLost(evt);
            }
        });
        getContentPane().add(txtInputUsername1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 170, 270, 30));

        txtInputEmail1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtInputEmail1.setForeground(new java.awt.Color(153, 153, 153));
        txtInputEmail1.setText("Masukkan Email");
        txtInputEmail1.setBorder(null);
        txtInputEmail1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtInputEmail1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtInputEmail1FocusLost(evt);
            }
        });
        getContentPane().add(txtInputEmail1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 220, 270, 40));

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
        txtPassword3.addActionListener(this::txtPassword3ActionPerformed);
        getContentPane().add(txtPassword3, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 330, 230, 40));

        txtPassword4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPassword4.setForeground(new java.awt.Color(153, 153, 153));
        txtPassword4.setText("Masukkan Password");
        txtPassword4.setBorder(null);
        txtPassword4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassword4FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassword4FocusLost(evt);
            }
        });
        txtPassword4.addActionListener(this::txtPassword4ActionPerformed);
        getContentPane().add(txtPassword4, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 390, 230, 30));

        btnEye4.setForeground(new java.awt.Color(255, 255, 255));
        btnEye4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplikasi_hijabella_collection/icon/hide 20px.png"))); // NOI18N
        btnEye4.setBorder(null);
        btnEye4.setBorderPainted(false);
        btnEye4.setContentAreaFilled(false);
        btnEye4.setFocusPainted(false);
        btnEye4.addActionListener(this::btnEye4ActionPerformed);
        getContentPane().add(btnEye4, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 390, 30, 30));

        btnEye3.setForeground(new java.awt.Color(255, 255, 255));
        btnEye3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplikasi_hijabella_collection/icon/hide 20px.png"))); // NOI18N
        btnEye3.setBorder(null);
        btnEye3.setBorderPainted(false);
        btnEye3.setContentAreaFilled(false);
        btnEye3.setFocusPainted(false);
        btnEye3.addActionListener(this::btnEye3ActionPerformed);
        getContentPane().add(btnEye3, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 340, 30, -1));

        lblTeksLogin.setText("Sudah Punya Akun?");
        getContentPane().add(lblTeksLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 506, 190, 20));

        btnDaftar.setBackground(new java.awt.Color(96, 60, 17));
        btnDaftar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDaftar.setForeground(new java.awt.Color(201, 160, 160));
        btnDaftar.setText("Daftar Sekarang");
        btnDaftar.setBorder(null);
        btnDaftar.setBorderPainted(false);
        btnDaftar.setContentAreaFilled(false);
        btnDaftar.setFocusPainted(false);
        btnDaftar.addActionListener(this::btnDaftarActionPerformed);
        getContentPane().add(btnDaftar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 460, 340, 40));

        btnLogin.setForeground(new java.awt.Color(51, 153, 255));
        btnLogin.setText("Login!");
        btnLogin.setBorder(null);
        btnLogin.setBorderPainted(false);
        btnLogin.setContentAreaFilled(false);
        btnLogin.addActionListener(this::btnLoginActionPerformed);
        getContentPane().add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 506, 40, 20));

        lblBackgroundDaftar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/aplikasi_hijabella_collection/background/daftarakun.png"))); // NOI18N
        getContentPane().add(lblBackgroundDaftar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtInputNamaLengkapFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtInputNamaLengkapFocusGained
        // TODO add your handling code here:
        placeholderKosong(
                txtInputNamaLengkap,
                "Masukkan Nama Lengkap"
        );
    }//GEN-LAST:event_txtInputNamaLengkapFocusGained

    private void txtInputNamaLengkapFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtInputNamaLengkapFocusLost
        // TODO add your handling code here:
        placeholderIsi(
                txtInputNamaLengkap,
                "Masukkan Nama Lengkap"
        );
    }//GEN-LAST:event_txtInputNamaLengkapFocusLost

    private void txtInputWhatshAppFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtInputWhatshAppFocusGained
        // TODO add your handling code here:
        placeholderKosong(
                txtInputWhatshApp,
                "Masukkan Nomor WhatshApp"
        );
    }//GEN-LAST:event_txtInputWhatshAppFocusGained

    private void txtInputWhatshAppFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtInputWhatshAppFocusLost
        // TODO add your handling code here:
        placeholderIsi(
                txtInputWhatshApp,
                "Masukkan Nomor WhatshApp"
        );
    }//GEN-LAST:event_txtInputWhatshAppFocusLost

    private void txtInputUsername1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtInputUsername1FocusGained
        // TODO add your handling code here:
        placeholderKosong(
                txtInputUsername1,
                "Masukkan Username"
        );
    }//GEN-LAST:event_txtInputUsername1FocusGained

    private void txtInputUsername1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtInputUsername1FocusLost
        // TODO add your handling code here:
        placeholderIsi(
                txtInputUsername1,
                "Masukkan Username"
        );
    }//GEN-LAST:event_txtInputUsername1FocusLost

    private void txtInputEmail1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtInputEmail1FocusGained
        // TODO add your handling code here:
        placeholderKosong(
                txtInputEmail1,
                "Masukkan Email"
        );
    }//GEN-LAST:event_txtInputEmail1FocusGained

    private void txtInputEmail1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtInputEmail1FocusLost
        // TODO add your handling code here:
        placeholderIsi(
                txtInputEmail1,
                "Masukkan Email"
        );
    }//GEN-LAST:event_txtInputEmail1FocusLost

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

    private void txtInputWhatshAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInputWhatshAppActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtInputWhatshAppActionPerformed

    private void txtPassword4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassword4FocusGained
        // TODO add your handling code here:
        if (String.valueOf(txtPassword4.getPassword())
                .equals("Konfirmasi Password")) {

            txtPassword4.setText("");
            txtPassword4.setEchoChar('•');
        }
    }//GEN-LAST:event_txtPassword4FocusGained

    private void txtPassword4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassword4FocusLost
        // TODO add your handling code here:
        if (String.valueOf(txtPassword4.getPassword())
                .trim().isEmpty()) {

            txtPassword4.setEchoChar((char) 0);
            txtPassword4.setText("Konfirmasi Password");
        }
    }//GEN-LAST:event_txtPassword4FocusLost

    private void txtPassword4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassword4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassword4ActionPerformed

    private void btnEye4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEye4ActionPerformed
        // TODO add your handling code here:
        eyePassword2();
    }//GEN-LAST:event_btnEye4ActionPerformed

    private void btnEye3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEye3ActionPerformed
        // TODO add your handling code here:
        eyePassword1();
    }//GEN-LAST:event_btnEye3ActionPerformed

    private void btnDaftarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDaftarActionPerformed
        // TODO add your handling code here:
        btnDaftar.setEnabled(false);

        new Thread(() -> {

            try {

                btnDaftar.setText("Loading.");
                Thread.sleep(200);

                btnDaftar.setText("Loading..");
                Thread.sleep(200);

                btnDaftar.setText("Loading...");
                Thread.sleep(200);

                btnDaftar.setText("Loading....");
                Thread.sleep(200);

                btnDaftar.setText("Loading.....");
                Thread.sleep(200);

                daftarAkun();

            } catch (InterruptedException e) {

                e.printStackTrace();

                javax.swing.SwingUtilities.invokeLater(() -> {
                    btnDaftar.setText("Daftar Sekarang");
                    btnDaftar.setEnabled(true);
                });

            }

        }).start();
    }//GEN-LAST:event_btnDaftarActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        LoadingForm loading
                = new LoadingForm("Halaman Login", "Login");

        loading.setVisible(true);
        loading.mulaiLoading();

        this.dispose();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtPassword3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassword3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassword3ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new DaftarForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDaftar;
    private javax.swing.JButton btnEye3;
    private javax.swing.JButton btnEye4;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel lblBackgroundDaftar;
    private javax.swing.JLabel lblTeksLogin;
    private javax.swing.JTextField txtInputEmail1;
    private javax.swing.JTextField txtInputNamaLengkap;
    private javax.swing.JTextField txtInputUsername1;
    private javax.swing.JTextField txtInputWhatshApp;
    private javax.swing.JPasswordField txtPassword3;
    private javax.swing.JPasswordField txtPassword4;
    // End of variables declaration//GEN-END:variables
}
