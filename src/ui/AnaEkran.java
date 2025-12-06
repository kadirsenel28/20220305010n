package ui;

import business.HastaneService;
import entities.Doktor;
import entities.Hasta;
import entities.Randevu;

import javax.swing.*;
import java.awt.*;

public class AnaEkran extends JFrame {

    private HastaneService service;
    private Hasta aktifHasta;

    private DefaultListModel<Doktor> listModelDoktor = new DefaultListModel<>();
    private JList<Doktor> listDoktorlar;
    private DefaultListModel<String> listModelSaat = new DefaultListModel<>();
    private JList<String> listSaatler;
    private JComboBox<String> cmbPoliklinik;
    private JComboBox<String> cmbTarih;
    private DefaultListModel<Randevu> listModelRandevularim = new DefaultListModel<>();

    public AnaEkran(HastaneService service, Hasta hasta) {
        this.service = service;
        this.aktifHasta = hasta;

        setTitle("Hastane Yönetim Sistemi");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlTop.setBackground(new Color(240, 248, 255));
        JLabel lblWelcome = new JLabel("Hoşgeldiniz, Sayın " + hasta.getAdSoyad());
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JButton btnCikis = new JButton("Güvenli Çıkış");
        pnlTop.add(lblWelcome, BorderLayout.WEST);
        pnlTop.add(btnCikis, BorderLayout.EAST);
        add(pnlTop, BorderLayout.NORTH);


        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel pnlRandevu = new JPanel(new GridLayout(1, 3, 15, 15));
        pnlRandevu.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));


        JPanel col1 = new JPanel(new BorderLayout());
        col1.setBorder(BorderFactory.createTitledBorder(" Doktor Seçimi"));
        listDoktorlar = new JList<>(listModelDoktor);
        col1.add(new JScrollPane(listDoktorlar));


        JPanel col2 = new JPanel(new GridLayout(8, 1, 5, 5));
        col2.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));
        col2.add(new JLabel("Poliklinik Bölümü:"));
        cmbPoliklinik = new JComboBox<>();
        cmbPoliklinik.addItem("-- Bölüm Seçiniz --");
        for(String p : service.getPoliklinikler()) cmbPoliklinik.addItem(p);
        col2.add(cmbPoliklinik);

        col2.add(new JLabel("Randevu Tarihi:"));
        cmbTarih = new JComboBox<>();
        for(String t : service.getGelecekTarihler()) cmbTarih.addItem(t);
        col2.add(cmbTarih);

        col2.add(new JLabel(" "));
        JButton btnRandevuAl = new JButton("RANDEVU OLUŞTUR");
        btnRandevuAl.setBackground(new Color(60, 179, 113));
        col2.add(btnRandevuAl);


        JPanel col3 = new JPanel(new BorderLayout());
        col3.setBorder(BorderFactory.createTitledBorder(" Saat Seçimi"));
        listSaatler = new JList<>(listModelSaat);
        col3.add(new JScrollPane(listSaatler));

        pnlRandevu.add(col1); pnlRandevu.add(col2); pnlRandevu.add(col3);
        tabs.addTab("Randevu Al", pnlRandevu);


        JPanel pnlList = new JPanel(new BorderLayout());
        pnlList.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JList<Randevu> lstRandevular = new JList<>(listModelRandevularim);
        lstRandevular.setFont(new Font("Monospaced", Font.PLAIN, 12));
        pnlList.add(new JLabel("Aktif Randevularınız:"), BorderLayout.NORTH);
        pnlList.add(new JScrollPane(lstRandevular), BorderLayout.CENTER);
        tabs.addTab("Randevularım", pnlList);
        add(tabs, BorderLayout.CENTER);




        btnCikis.addActionListener(e -> { this.dispose(); new LoginEkrani().setVisible(true); });


        cmbPoliklinik.addActionListener(e -> {
            String secilen = (String) cmbPoliklinik.getSelectedItem();
            listModelDoktor.clear();

            if(secilen != null && !secilen.equals("-- Bölüm Seçiniz --")) {
                service.getDoktorlarByPoliklinik(secilen).forEach(listModelDoktor::addElement);
                saatleriGuncelle();
            } else {
                listModelSaat.clear();
            }
        });


        cmbTarih.addActionListener(e -> {
            saatleriGuncelle();
        });


        btnRandevuAl.addActionListener(e -> {
            Doktor dr = listDoktorlar.getSelectedValue();
            String saat = listSaatler.getSelectedValue();
            String tarih = (String) cmbTarih.getSelectedItem();

            if (dr == null || saat == null || tarih == null) {
                JOptionPane.showMessageDialog(this, "Eksik Seçim!", "Hata", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (service.randevuOlustur(dr, aktifHasta, tarih, saat)) {
                listModelRandevularim.addElement(new Randevu(dr, aktifHasta, tarih, saat));
                JOptionPane.showMessageDialog(this, "Randevu Alındı!");


                saatleriGuncelle();
            } else {
                JOptionPane.showMessageDialog(this,
                        "HATA: Bu bölümde haftalık randevu limitiniz doldu!\n" +
                                "Kurallar gereği, 1 hafta içerisinde aynı poliklinikten en fazla 1 randevu alabilirsiniz.",
                        "Randevu Limiti Aşıldı", JOptionPane.ERROR_MESSAGE);
            }
        });
    }


    private void saatleriGuncelle() {
        listModelSaat.clear();
        String secilenPoli = (String) cmbPoliklinik.getSelectedItem();
        String secilenTarih = (String) cmbTarih.getSelectedItem();

        if (secilenPoli != null && !secilenPoli.equals("-- Bölüm Seçiniz --") && secilenTarih != null) {
            service.getMusaitSaatler(secilenPoli, secilenTarih).forEach(listModelSaat::addElement);
        }
    }
}
