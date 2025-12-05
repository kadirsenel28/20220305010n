package entities;

public class Randevu {
    private Doktor doktor;
    private Hasta hasta;
    private String tarih;
    private String saat;

    public Randevu(Doktor doktor, Hasta hasta, String tarih, String saat) {
        this.doktor = doktor;
        this.hasta = hasta;
        this.tarih = tarih;
        this.saat = saat;
    }

    public Doktor getDoktor() { return doktor; }
    public Hasta getHasta() { return hasta; }
    public String getTarih() { return tarih; }
    public String getSaat() { return saat; }

    @Override
    public String toString() {
        return doktor.getPoliklinik() + " - " + doktor.getAdSoyad() + " | " + tarih + " " + saat;
    }
}
