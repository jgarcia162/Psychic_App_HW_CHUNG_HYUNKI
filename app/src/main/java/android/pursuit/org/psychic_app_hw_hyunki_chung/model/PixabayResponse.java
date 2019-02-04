package android.pursuit.org.psychic_app_hw_hyunki_chung.model;

import android.pursuit.org.psychic_app_hw_hyunki_chung.model.Hit;

import java.util.List;

public class PixabayResponse {
    List<Hit> hits;

    public List<Hit> getHits() {
        return hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }
}
