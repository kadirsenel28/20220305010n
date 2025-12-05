package entities;

public class Doktor extends Kullanici {
    private String poliklinik;

    public Doktor(int id, String adSoyad, String poliklinik) {
        super(id, adSoyad, "123");
        this.poliklinik = poliklinik;
    }

    public String getPoliklinik() { return poliklinik; }

    @Override
    public String getUnvan() { return "Dr."; }

    @Override
    public String toString() { return "Dr. " + getAdSoyad(); }
}
