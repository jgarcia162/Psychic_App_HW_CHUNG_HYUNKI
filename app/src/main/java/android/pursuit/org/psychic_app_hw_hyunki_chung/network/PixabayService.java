package android.pursuit.org.psychic_app_hw_hyunki_chung.network;

import android.pursuit.org.psychic_app_hw_hyunki_chung.model.PixabayResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PixabayService {
//?key={ KEY }&q=yellow+flowers&image_type=photo

    @GET("/api")
    Call<PixabayResponse> getPixabayResponse(@Query("key") String apiKey,
                                             @Query("q") String query,
                                             @Query("image_type") String type);

}
