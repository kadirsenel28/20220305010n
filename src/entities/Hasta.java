package entities;

public class Hasta extends Kullanici {
    public Hasta(int id, String adSoyad, String sifre) {
        super(id, adSoyad, sifre);
    }

    @Override
    public String getUnvan() { return "Sn."; }
}
