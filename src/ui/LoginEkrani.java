package ui;

import business.HastaneService;
import entities.Hasta;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginEkrani extends JFrame {
    private HastaneService service = new HastaneService();


    private JTextField txtUser;
    private JPasswordField txtPass;

    public LoginEkrani() {
        setTitle("Hastane Yönetim Sistemi");
        setSize(420, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());


        getContentPane().setBackground(Color.WHITE);


        JPanel pnlTop = new JPanel();
        pnlTop.setLayout(new BoxLayout(pnlTop, BoxLayout.Y_AXIS));
        pnlTop.setBackground(Color.WHITE);
        pnlTop.setBorder(new EmptyBorder(20, 0, 20, 0));


        JLabel lblIcon = new JLabel("🏥");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);


        JLabel lblBaslik = new JLabel("Hastane Randevu Sistemi");
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblBaslik.setForeground(new Color(50, 50, 50));
        lblBaslik.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblHosgeldiniz = new JLabel("Sisteme Hoşgeldiniz");
        lblHosgeldiniz.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblHosgeldiniz.setForeground(Color.GRAY);
        lblHosgeldiniz.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlTop.add(lblIcon);
        pnlTop.add(Box.createVerticalStrut(10));
        pnlTop.add(lblBaslik);
        pnlTop.add(lblHosgeldiniz);
        add(pnlTop, BorderLayout.NORTH);


        JPanel pnlCenter = new JPanel(new GridBagLayout());
        pnlCenter.setBackground(Color.WHITE);


        JPanel pnlForm = new JPanel(new GridLayout(4, 1, 10, 10));
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Hasta Girişi"),
                new EmptyBorder(10, 20, 10, 20)
        ));
        pnlForm.setPreferredSize(new Dimension(320, 200));


        JLabel lblUser = new JLabel("Kullanıcı Adı / T.C.:");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 12));
        txtUser = new JTextField(15);
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));


        JLabel lblPass = new JLabel("Şifre:");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 12));
        txtPass = new JPasswordField(15);
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        pnlForm.add(lblUser);
        pnlForm.add(txtUser);
        pnlForm.add(lblPass);
        pnlForm.add(txtPass);

        pnlCenter.add(pnlForm);
        add(pnlCenter, BorderLayout.CENTER);


        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pnlBottom.setBackground(Color.WHITE);


        JButton btnKayit = new JButton("Kayıt Ol");
        btnKayit.setPreferredSize(new Dimension(120, 40));
        btnKayit.setBackground(new Color(230, 230, 230)); // Açık gri
        btnKayit.setFont(new Font("Segoe UI", Font.BOLD, 14));


        JButton btnGiris = new JButton("Giriş Yap");
        btnGiris.setPreferredSize(new Dimension(120, 40));
        btnGiris.setBackground(new Color(230, 230, 230)); // Açık gri
        btnGiris.setFont(new Font("Segoe UI", Font.BOLD, 14));

        pnlBottom.add(btnKayit);
        pnlBottom.add(btnGiris);

        add(pnlBottom, BorderLayout.SOUTH);




        btnGiris.addActionListener(e -> {
            String ad = txtUser.getText();
            String sifre = new String(txtPass.getPassword());

            Hasta girisYapan = service.login(ad, sifre);

            if (girisYapan != null) {
                this.dispose();
                new AnaEkran(service, girisYapan).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Hatalı Kullanıcı Adı veya Şifre!\n(Demo: Burak / 1234)",
                        "Giriş Başarısız", JOptionPane.ERROR_MESSAGE);
            }
        });


        btnKayit.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Kayıt sistemi şu an bakım aşamasındadır.\nLütfen mevcut kullanıcı ile giriş yapınız.",
                    "Bilgi", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new LoginEkrani().setVisible(true));
    }
}
