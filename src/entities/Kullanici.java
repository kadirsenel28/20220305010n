package entities;

public abstract class Kullanici implements IRandevuDavranisi {
    private int id;
    private String adSoyad;
    private String sifre;

    public Kullanici(int id, String adSoyad, String sifre) {
        this.id = id;
        this.adSoyad = adSoyad;
        this.sifre = sifre;
    }

    public int getId() { return id; }
    public String getAdSoyad() { return adSoyad; }
    public String getSifre() { return sifre; }

    @Override
    public String toString() {
        return getUnvan() + " " + adSoyad;
    }
}
