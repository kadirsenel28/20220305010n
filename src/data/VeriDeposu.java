package data;

import java.util.ArrayList;
import java.util.List;

public class VeriDeposu<T> {
    private List<T> veriListesi = new ArrayList<>();

    public void ekle(T veri) {
        veriListesi.add(veri);
    }

    public List<T> listele() {
        return veriListesi;
    }
}
