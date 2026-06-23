/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplikasi_hijabella_collection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author achmad_khusnul_yakin
 */
public class BaseForm extends JFrame {
    
    protected void dashboard() {
        new DashboardFrom().setVisible(true);
        dispose();
    }

    protected void dataProduk() {
        new DataProdukFrom().setVisible(true);
        dispose();
    }

    protected void dataSupplier() {
        new DataSupplierFrom().setVisible(true);
        dispose();
    }

    protected void barangMasuk() {
        new BarangMasukFrom().setVisible(true);
        dispose();
    }

    protected void barangKeluar() {
        new BarangKeluarFrom().setVisible(true);
        dispose();
    }

    protected void laporan() {
        new LaporanFrom().setVisible(true);
        dispose();
    }

    protected void setting() {
        new SettingFrom().setVisible(true);
        dispose();
    }

    protected void about() {
        new AboutFrom().setVisible(true);
        dispose();
    }

    protected void logout() {
        new LogoutFrom().setVisible(true);
        dispose();
    }
    protected void LupaPasword(){
        new LupaPasswordForm().setVisible(true);
        dispose();
    }
    
    protected void UbahEmail() {
        new UbahEmailForm().setVisible(true);
        dispose();
    }
    
    protected void UbahWhatshApp() {
        new UbahWhatshAppFrom().setVisible(true);
        dispose();
    }

    protected void halamanAktif(String halaman) {

        JOptionPane.showMessageDialog(
                this,
                "Anda sudah berada di halaman "
                + halaman + "!"
        );
    }
}