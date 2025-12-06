package business;

import data.VeriDeposu;
import entities.Doktor;
import entities.Hasta;
import entities.Randevu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class HastaneService {

    private VeriDeposu<Doktor> doktorDeposu = new VeriDeposu<>();
    private VeriDeposu<Hasta> hastaDeposu = new VeriDeposu<>();
    private VeriDeposu<Randevu> randevuDeposu = new VeriDeposu<>();
    private Set<String> poliklinikler = new HashSet<>();

    public HastaneService() {
        doktorEkle(new Doktor(1, "Ahmet Yılmaz", "Kardiyoloji"));
        doktorEkle(new Doktor(2, "Ayşe Demir", "Göz Hastalıkları"));
        doktorEkle(new Doktor(3, "Mehmet Öz", "Kardiyoloji"));
        doktorEkle(new Doktor(4, "Fatma Kaya", "Dahiliye"));
        doktorEkle(new Doktor(5, "Canan Karatay", "Beslenme ve Diyet"));
        doktorEkle(new Doktor(6, "Ali Veli", "Ortopedi"));
        doktorEkle(new Doktor(7, "Ayça Çelik", "Göz Hastalıkları"));
        doktorEkle(new Doktor(8, "Ahmet Gedik", "Kardiyoloji"));
        doktorEkle(new Doktor(9, "Necati Süren", "Dahiliye"));
        doktorEkle(new Doktor(10, "Bilge Şahin", "Beslenme ve Diyet"));

        hastaDeposu.ekle(new Hasta(99, "Kadir", "1234"));
    }

    private void doktorEkle(Doktor d) {
        doktorDeposu.ekle(d);
        poliklinikler.add(d.getPoliklinik());
    }

    public Hasta login(String ad, String sifre) {
        return hastaDeposu.listele().stream()
                .filter(h -> h.getAdSoyad().equalsIgnoreCase(ad) && h.getSifre().equals(sifre))
                .findFirst().orElse(null);
    }

    public Set<String> getPoliklinikler() { return poliklinikler; }

    public List<Doktor> getDoktorlarByPoliklinik(String poliklinik) {
        return doktorDeposu.listele().stream()
                .filter(d -> d.getPoliklinik().equals(poliklinik))
                .collect(Collectors.toList());
    }



    private List<String> getGenelSaatler(String poliklinik) {
        List<String> saatler = new ArrayList<>();
        switch (poliklinik) {
            case "Kardiyoloji":
                saatler.add("09:00"); saatler.add("09:30"); saatler.add("10:00");
                break;
            case "Göz Hastalıkları":
                saatler.add("13:00"); saatler.add("13:30"); saatler.add("14:00");
                break;
            case "Ortopedi":
                saatler.add("14:30"); saatler.add("16:00");
                break;
            default:
                saatler.add("09:00"); saatler.add("10:00"); saatler.add("11:00");
                saatler.add("14:00"); saatler.add("15:00");
                break;
        }
        return saatler;
    }


    public List<String> getMusaitSaatler(String poliklinik, String tarih) {

        List<String> musaitSaatler = new ArrayList<>(getGenelSaatler(poliklinik));


        List<String> doluSaatler = randevuDeposu.listele().stream()
                .filter(r -> r.getTarih().equals(tarih) && r.getDoktor().getPoliklinik().equals(poliklinik))
                .map(Randevu::getSaat)
                .collect(Collectors.toList());


        musaitSaatler.removeAll(doluSaatler);

        return musaitSaatler;
    }

    public List<String> getGelecekTarihler() {
        List<String> tarihler = new ArrayList<>();
        LocalDate bugun = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        for (int i = 1; i <= 7; i++) {
            tarihler.add(bugun.plusDays(i).format(format));
        }
        return tarihler;
    }

    public boolean randevuOlustur(Doktor dr, Hasta h, String tarih, String saat) {
        boolean zatenVar = randevuDeposu.listele().stream()
                .anyMatch(r ->
                                r.getHasta().getId() == h.getId() &&
                                        r.getDoktor().getPoliklinik().equals(dr.getPoliklinik())

                );

        if (zatenVar) return false;

        randevuDeposu.ekle(new Randevu(dr, h, tarih, saat));
        return true;
    }
}
